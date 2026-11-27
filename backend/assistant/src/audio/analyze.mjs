// Mesures objectives d'un extrait audio, sans dépendance : niveaux, spectre par bandes,
// tempo (autocorrélation de l'enveloppe d'attaques), stabilité, kick, graves entre les kicks,
// courbe d'énergie, breaks et changements de section. Chaque mesure porte une confiance ;
// ce qui ne peut pas être mesuré est listé explicitement.

export const AUDIO_LIMITS = Object.freeze({ minSeconds: 5, maxSeconds: 75, targetRate: 22050 });

const FFT_SIZE = 1024;
const HOP = 256;
const BANDS = [
  { id: 'sub', label: 'Sub (20–60 Hz)', lo: 20, hi: 60 },
  { id: 'low', label: 'Graves (60–150 Hz)', lo: 60, hi: 150 },
  { id: 'lowmid', label: 'Bas-médiums (150–500 Hz)', lo: 150, hi: 500 },
  { id: 'mid', label: 'Médiums (500 Hz–2 kHz)', lo: 500, hi: 2000 },
  { id: 'high', label: 'Hauts-médiums (2–6 kHz)', lo: 2000, hi: 6000 },
  { id: 'air', label: 'Aigus (6 kHz et plus)', lo: 6000, hi: 20000 },
];

export const NOT_MEASURED = Object.freeze([
  'Tonalité, accords et justesse',
  'Swing fin et micro-décalages du groove',
  'Identification des instruments et des sons (seules les bandes de fréquences sont mesurées)',
  'Qualité perçue, émotion et conformité au style (jugements subjectifs)',
]);

const db = (x) => (x > 1e-12 ? 10 * Math.log10(x) : -120);
const round = (x, d = 1) => Math.round(x * 10 ** d) / 10 ** d;
const mean = (a) => (a.length ? a.reduce((s, x) => s + x, 0) / a.length : 0);
const std = (a) => {
  const m = mean(a);
  return Math.sqrt(mean(a.map((x) => (x - m) ** 2)));
};
const median = (a) => {
  if (!a.length) return 0;
  const s = [...a].sort((x, y) => x - y);
  return s[Math.floor(s.length / 2)];
};

/** Ramène à ~22 kHz par moyenne de blocs (suffisant pour ces mesures). */
function downsample(samples, sampleRate) {
  const factor = Math.max(1, Math.floor(sampleRate / AUDIO_LIMITS.targetRate));
  if (factor === 1) return { x: samples, sr: sampleRate };
  const out = new Float32Array(Math.floor(samples.length / factor));
  for (let i = 0; i < out.length; i++) {
    let s = 0;
    for (let k = 0; k < factor; k++) s += samples[i * factor + k];
    out[i] = s / factor;
  }
  return { x: out, sr: sampleRate / factor };
}

function fft(re, im) {
  const n = re.length;
  for (let i = 1, j = 0; i < n; i++) {
    let bit = n >> 1;
    for (; j & bit; bit >>= 1) j ^= bit;
    j ^= bit;
    if (i < j) {
      [re[i], re[j]] = [re[j], re[i]];
      [im[i], im[j]] = [im[j], im[i]];
    }
  }
  for (let len = 2; len <= n; len <<= 1) {
    const ang = (-2 * Math.PI) / len;
    const wr = Math.cos(ang);
    const wi = Math.sin(ang);
    for (let i = 0; i < n; i += len) {
      let cr = 1;
      let ci = 0;
      for (let k = 0; k < len / 2; k++) {
        const ar = re[i + k + len / 2] * cr - im[i + k + len / 2] * ci;
        const ai = re[i + k + len / 2] * ci + im[i + k + len / 2] * cr;
        re[i + k + len / 2] = re[i + k] - ar;
        im[i + k + len / 2] = im[i + k] - ai;
        re[i + k] += ar;
        im[i + k] += ai;
        const t = cr * wr - ci * wi;
        ci = cr * wi + ci * wr;
        cr = t;
      }
    }
  }
}

/** Enveloppe d'attaques : flux spectral (total et graves) + énergie par bande et par trame. */
function stft(x, sr) {
  const frames = Math.max(0, Math.floor((x.length - FFT_SIZE) / HOP) + 1);
  const win = new Float64Array(FFT_SIZE).map((_, i) => 0.5 - 0.5 * Math.cos((2 * Math.PI * i) / (FFT_SIZE - 1)));
  const binHz = sr / FFT_SIZE;
  const bandBins = BANDS.map((b) => [Math.max(1, Math.floor(b.lo / binHz)), Math.min(FFT_SIZE / 2, Math.ceil(b.hi / binHz))]);
  const kickBins = [Math.max(1, Math.floor(35 / binHz)), Math.ceil(150 / binHz)];
  const flux = new Float64Array(frames);
  const lowFlux = new Float64Array(frames);
  const bandEnergy = BANDS.map(() => new Float64Array(frames));
  let prev = new Float64Array(FFT_SIZE / 2 + 1);
  const re = new Float64Array(FFT_SIZE);
  const im = new Float64Array(FFT_SIZE);
  for (let t = 0; t < frames; t++) {
    for (let i = 0; i < FFT_SIZE; i++) {
      re[i] = x[t * HOP + i] * win[i];
      im[i] = 0;
    }
    fft(re, im);
    const cur = new Float64Array(FFT_SIZE / 2 + 1);
    let f = 0;
    let lf = 0;
    for (let k = 1; k <= FFT_SIZE / 2; k++) {
      const p = re[k] * re[k] + im[k] * im[k];
      cur[k] = Math.log1p(100 * Math.sqrt(p));
      const d = cur[k] - prev[k];
      if (d > 0) {
        f += d;
        if (k >= kickBins[0] && k <= kickBins[1]) lf += d;
      }
      for (let b = 0; b < BANDS.length; b++) {
        if (k >= bandBins[b][0] && k < bandBins[b][1]) bandEnergy[b][t] += p;
      }
    }
    flux[t] = f;
    lowFlux[t] = lf;
    prev = cur;
  }
  return { frames, fps: sr / HOP, flux, lowFlux, bandEnergy };
}

/** Retire la tendance locale et garde la partie positive : ne restent que les attaques. */
function onsetEnvelope(raw, fps) {
  const w = Math.max(1, Math.round(fps * 0.4));
  const out = new Float64Array(raw.length);
  let acc = 0;
  for (let i = 0; i < raw.length; i++) {
    acc += raw[i];
    if (i >= w) acc -= raw[i - w];
    const avg = acc / Math.min(i + 1, w);
    out[i] = Math.max(0, raw[i] - avg);
  }
  const m = Math.max(...out, 1e-9);
  return out.map((v) => v / m);
}

function autocorr(env, maxLag) {
  const ac = new Float64Array(maxLag + 1);
  const n = env.length;
  for (let l = 0; l <= maxLag && l < n; l++) {
    let s = 0;
    for (let t = 0; t + l < n; t++) s += env[t] * env[t + l];
    ac[l] = s / (n - l);
  }
  return ac;
}

/**
 * Tempo par autocorrélation en peigne (période + multiples), 70–210 BPM, pondérée par un a priori
 * centré sur 140 BPM (styles visés : 130–200 BPM). La force de périodicité (0..1) est calculée sur
 * l'enveloppe centrée : un bruit sans pulsation donne une force proche de 0.
 */
function estimateTempo(env, fps, range = null) {
  const minBpm = range ? range[0] : 70;
  const maxBpm = range ? range[1] : 210;
  const lagMin = Math.max(2, Math.floor((60 * fps) / maxBpm));
  const lagMax = Math.ceil((60 * fps) / minBpm);
  if (env.length < lagMax * 3) return null;
  const m = mean(env);
  const centered = env.map((v) => v - m);
  const ac = autocorr(centered, lagMax * 3 + 2);
  const prior = (l) => (range ? 1 : Math.exp(-0.5 * Math.log2((60 * fps) / l / 140) ** 2));
  const score = (l) => (ac[l] + 0.5 * (ac[2 * l] ?? 0) + 0.25 * (ac[3 * l] ?? 0)) * prior(l);
  const scores = [];
  for (let l = lagMin; l <= lagMax; l++) scores.push({ l, s: score(l) });
  let best = scores.reduce((a, b) => (b.s > a.s ? b : a));
  // Ambiguïté d'octave : si le double tempo est presque aussi périodique, on le préfère (styles rapides visés).
  const halves = scores.filter((c) => Math.abs(c.l - best.l / 2) <= 1);
  if (halves.length) {
    const half = halves.reduce((a, b) => (b.s > a.s ? b : a));
    if (half.s >= 0.75 * best.s) best = half;
  }
  const i = best.l - lagMin;
  let lag = best.l;
  if (i > 0 && i < scores.length - 1) {
    const [a, b, c] = [scores[i - 1].s, scores[i].s, scores[i + 1].s];
    const den = a - 2 * b + c;
    if (den < 0) lag += (0.5 * (a - c)) / den;
  }
  const strength = ac[0] > 0 ? Math.max(0, Math.min(1, ac[best.l] / ac[0])) : 0;
  return { bpm: (60 * fps) / lag, lagFrames: lag, strength };
}

/** Lissage triangulaire : élargit les pics d'attaque pour que l'autocorrélation tolère les périodes non entières. */
function smooth(env, radius = 2) {
  const out = new Float64Array(env.length);
  for (let i = 0; i < env.length; i++) {
    let s = 0;
    let w = 0;
    for (let k = -radius; k <= radius; k++) {
      const v = env[i + k];
      if (v === undefined) continue;
      const wk = radius + 1 - Math.abs(k);
      s += v * wk;
      w += wk;
    }
    out[i] = s / w;
  }
  return out;
}

function acAt(x, lag) {
  let s = 0;
  for (let t = 0; t + lag < x.length; t++) s += x[t] * x[t + lag];
  return s / (x.length - lag);
}

/** Affine la période en cherchant le pic d'autocorrélation près de k × période (k = 4, 8, 16…) : précision ≈ période / k. */
function refinePeriod(env, period) {
  const m = mean(env);
  const x = env.map((v) => v - m);
  let p = period;
  for (let k = 4; k * p < x.length / 2; k *= 2) {
    const center = Math.round(k * p);
    let bestLag = center;
    let bestVal = -Infinity;
    const vals = new Map();
    for (let l = center - 3; l <= center + 3; l++) {
      const v = acAt(x, l);
      vals.set(l, v);
      if (v > bestVal) {
        bestVal = v;
        bestLag = l;
      }
    }
    const a = vals.get(bestLag - 1) ?? acAt(x, bestLag - 1);
    const c = vals.get(bestLag + 1) ?? acAt(x, bestLag + 1);
    const den = a - 2 * bestVal + c;
    const lag = den < 0 ? bestLag + (0.5 * (a - c)) / den : bestLag;
    const next = lag / k;
    if (Math.abs(next - p) > p * 0.03) break;
    p = next;
  }
  return p;
}

function isLocalMax(scores, i) {
  return (i === 0 || scores[i].s >= scores[i - 1].s) && (i === scores.length - 1 || scores[i].s >= scores[i + 1].s);
}

function peaks(env, minDist, threshold) {
  const out = [];
  for (let i = 1; i < env.length - 1; i++) {
    if (env[i] < threshold || env[i] < env[i - 1] || env[i] < env[i + 1]) continue;
    if (out.length && i - out[out.length - 1] < minDist) {
      if (env[i] > env[out[out.length - 1]]) out[out.length - 1] = i;
    } else out.push(i);
  }
  return out;
}

/** Analyse principale : retourne un objet de mesures sérialisable. */
export function analyzeAudio({ samples, sampleRate }) {
  const { x, sr } = downsample(samples, sampleRate);
  const durationSec = x.length / sr;
  let peak = 0;
  let sumSq = 0;
  let clipped = 0;
  for (const v of x) {
    const a = Math.abs(v);
    if (a > peak) peak = a;
    if (a >= 0.999) clipped++;
    sumSq += v * v;
  }
  const rmsDb = db(sumSq / Math.max(1, x.length));
  const levels = {
    peakDbfs: round(db(peak * peak)),
    rmsDbfs: round(rmsDb),
    crestDb: round(db(peak * peak) - rmsDb),
    clippingPercent: round((100 * clipped) / Math.max(1, x.length), 2),
  };
  const result = { durationSec: round(durationSec), sampleRate: sr, levels, notMeasured: [...NOT_MEASURED] };
  if (rmsDb < -60) return { ...result, silent: true };

  const s = stft(x, sr);
  const fps = s.fps;
  const lowEnv = onsetEnvelope(s.lowFlux, fps);
  const fullEnv = onsetEnvelope(s.flux, fps);
  const env = lowEnv.map((v, i) => v + 0.5 * fullEnv[i]);

  // Équilibre spectral (part d'énergie par bande)
  const bandTotals = s.bandEnergy.map((b) => b.reduce((a, v) => a + v, 0));
  const total = bandTotals.reduce((a, v) => a + v, 0) || 1;
  result.balance = BANDS.map((b, i) => ({ band: b.id, label: b.label, percent: round((100 * bandTotals[i]) / total) }));

  // Courbe d'énergie : 8 segments, dB relatifs au plus fort
  const segN = 8;
  const perSeg = Math.floor(x.length / segN);
  const segDb = [];
  for (let k = 0; k < segN; k++) {
    let e = 0;
    for (let i = k * perSeg; i < (k + 1) * perSeg; i++) e += x[i] * x[i];
    segDb.push(db(e / Math.max(1, perSeg)));
  }
  const segMax = Math.max(...segDb);
  result.energyCurve = segDb.map((v, k) => ({ startSec: round((k * perSeg) / sr), relativeDb: round(v - segMax) }));

  // Graves par seconde → breaks (graves à plus de 10 dB sous la médiane pendant au moins 2 s)
  const lowBand = s.bandEnergy[0].map((v, i) => v + s.bandEnergy[1][i]);
  const perSec = Math.max(1, Math.round(fps));
  const lowSecDb = [];
  for (let t = 0; t + perSec <= lowBand.length; t += perSec) {
    let e = 0;
    for (let i = t; i < t + perSec; i++) e += lowBand[i];
    lowSecDb.push(db(e / perSec));
  }
  const lowMedian = median(lowSecDb);
  const breaks = [];
  let start = -1;
  lowSecDb.forEach((v, i) => {
    const quiet = v < lowMedian - 10;
    if (quiet && start < 0) start = i;
    if ((!quiet || i === lowSecDb.length - 1) && start >= 0) {
      const end = quiet ? i + 1 : i;
      if (end - start >= 2) breaks.push({ startSec: start, endSec: end, kickReturns: !quiet });
      start = -1;
    }
  });
  result.breaks = breaks;

  // Changements de section : écart de niveau ≥ 4 dB entre blocs de 4 s consécutifs
  const blockSec = 4;
  const blocks = [];
  for (let t = 0; t + blockSec * sr <= x.length; t += blockSec * sr) {
    let e = 0;
    for (let i = t; i < t + blockSec * sr; i++) e += x[i] * x[i];
    blocks.push(db(e / (blockSec * sr)));
  }
  result.sectionChanges = [];
  for (let k = 1; k < blocks.length; k++) {
    const d = blocks[k] - blocks[k - 1];
    if (Math.abs(d) >= 4) result.sectionChanges.push({ atSec: k * blockSec, changeDb: round(d) });
  }

  // Tempo global
  const tempoEnv = smooth(env);
  const tempo = estimateTempo(tempoEnv, fps);
  if (!tempo || tempo.strength < 0.15) {
    result.tempo = { detected: false, reason: 'Pas de pulsation assez régulière pour mesurer un tempo.' };
    return result;
  }
  const lowThreshold = mean(lowEnv) + 1.5 * std(lowEnv);
  const refined = refinePeriod(tempoEnv, tempo.lagFrames);
  tempo.lagFrames = refined;
  tempo.bpm = (60 * fps) / refined;
  const confidence = tempo.strength > 0.45 ? 'élevée' : tempo.strength > 0.2 ? 'moyenne' : 'faible';
  result.tempo = {
    detected: true,
    bpm: round(tempo.bpm),
    confidence,
    periodicity: round(tempo.strength, 2),
    note: 'La moitié ou le double de ce tempo reste possible selon la perception (demi-temps / double-temps).',
  };

  // Stabilité : tempo local sur fenêtres de 12 s (pas de 6 s), recherché à ±8 % du tempo global
  const winFrames = Math.round(12 * fps);
  const stepFrames = Math.round(6 * fps);
  const local = [];
  for (let t = 0; t + winFrames <= env.length; t += stepFrames) {
    const sub = tempoEnv.slice(t, t + winFrames);
    const lt = estimateTempo(sub, fps, [tempo.bpm * 0.92, tempo.bpm * 1.08]);
    if (lt && lt.strength > 0.15) local.push((60 * fps) / refinePeriod(sub, lt.lagFrames));
  }
  if (local.length >= 2) {
    const dev = std(local);
    result.tempo.stability = {
      windows: local.length,
      minBpm: round(Math.min(...local)),
      maxBpm: round(Math.max(...local)),
      stdBpm: round(dev, 2),
      label: dev < 0.6 ? 'stable' : dev < 2 ? 'légères variations' : 'instable',
    };
  } else {
    result.tempo.stability = { windows: local.length, label: 'extrait trop court pour mesurer la stabilité' };
  }

  // Kick : attaques dans les graves, alignement sur la grille du tempo (hors breaks)
  const lag = tempo.lagFrames;
  const kicks = peaks(lowEnv, Math.max(2, Math.round(lag * 0.35)), lowThreshold);
  const tol = Math.max(1, Math.round(0.035 * fps));
  const inBreak = (frame) => breaks.some((b) => frame / fps >= b.startSec && frame / fps < b.endSec);
  let bestPhase = 0;
  let bestHits = -1;
  for (let ph = 0; ph < Math.ceil(lag); ph++) {
    let hits = 0;
    for (const k of kicks) {
      const r = ((k - ph) % lag + lag) % lag;
      if (r <= tol || lag - r <= tol) hits++;
    }
    if (hits > bestHits) {
      bestHits = hits;
      bestPhase = ph;
    }
  }
  let beats = 0;
  let covered = 0;
  for (let b = bestPhase; b < lowEnv.length; b += lag) {
    if (inBreak(b)) continue;
    beats++;
    if (kicks.some((k) => Math.abs(k - b) <= tol)) covered++;
  }
  const coverage = beats ? covered / beats : 0;
  const intervals = kicks.slice(1).map((k, i) => (k - kicks[i]) / fps);
  // Graves entre les kicks : énergie basse au contretemps comparée à celle sur le temps
  let onE = 0;
  let offE = 0;
  const sumBand = (from, to) => {
    let e = 0;
    for (let i = Math.round(from); i < Math.round(to) && i < lowBand.length; i++) e += lowBand[i];
    return e;
  };
  for (let b = bestPhase; b + lag < lowBand.length; b += lag) {
    if (inBreak(b)) continue;
    onE += sumBand(b, b + lag * 0.25);
    offE += sumBand(b + lag * 0.5, b + lag * 0.75);
  }
  const offVsOnDb = onE > 0 ? round(Math.max(-60, db(offE + 1e-12) - db(onE))) : null;
  result.kick = {
    detectedHits: kicks.length,
    onBeatCoverage: round(coverage, 2),
    label: coverage > 0.8 ? 'kick sur chaque temps (régulier)'
      : (result.tempo.stability?.stdBpm ?? 0) >= 0.6 && kicks.length > 8 ? 'kick présent, alignement difficile à mesurer (tempo variable)'
      : coverage > 0.4 ? 'kick sur une partie des temps' : 'pas de kick régulier détecté',
    note: 'Mesuré sur les attaques sous 150 Hz : un son grave percussif autre que le kick peut être compté.',
    intervalRegularity: intervals.length > 3 ? round(std(intervals) / Math.max(1e-6, mean(intervals)), 2) : null,
  };
  result.bassBetweenKicks = {
    offbeatVsBeatDb: offVsOnDb,
    label: offVsOnDb === null ? 'non mesurable'
      : offVsOnDb > -6 ? 'graves présents entre les kicks (basse roulante, ou kick long)'
      : 'graves surtout sur les temps (basse discrète ou absente entre les kicks)',
    note: 'Estimation par bande de fréquences : le kick et la basse ne sont pas séparés.',
  };
  return result;
}

/** Résumé lisible (français) des mesures, affiché tel quel dans l'app. */
export function summarize(m) {
  const items = [{ label: 'Extrait analysé', value: `${m.durationSec} s`, kind: 'mesure' }];
  items.push({
    label: 'Niveau',
    value: `crête ${m.levels.peakDbfs} dBFS, moyenne ${m.levels.rmsDbfs} dBFS` + (m.levels.clippingPercent > 0.1 ? `, saturation ${m.levels.clippingPercent} %` : ''),
    kind: 'mesure',
  });
  if (m.silent) {
    items.push({ label: 'Signal', value: 'quasi silencieux : rien à analyser', kind: 'mesure' });
    return items;
  }
  if (m.tempo?.detected) {
    items.push({ label: 'Tempo', value: `≈ ${m.tempo.bpm} BPM (confiance ${m.tempo.confidence})`, kind: 'mesure' });
    if (m.tempo.stability?.stdBpm !== undefined) {
      items.push({ label: 'Stabilité du tempo', value: `${m.tempo.stability.label} (${m.tempo.stability.minBpm}–${m.tempo.stability.maxBpm} BPM)`, kind: 'mesure' });
    }
  } else if (m.tempo) {
    items.push({ label: 'Tempo', value: m.tempo.reason, kind: 'mesure' });
  }
  if (m.kick) items.push({ label: 'Kick', value: `${m.kick.label} (${Math.round(m.kick.onBeatCoverage * 100)} % des temps)`, kind: 'mesure' });
  if (m.bassBetweenKicks) items.push({ label: 'Graves entre les kicks', value: m.bassBetweenKicks.label, kind: 'estimation' });
  if (m.balance) {
    items.push({ label: 'Équilibre', value: m.balance.map((b) => `${b.label.split(' (')[0]} ${b.percent} %`).join(', '), kind: 'mesure' });
  }
  items.push({
    label: 'Breaks',
    value: m.breaks?.length ? m.breaks.map((b) => `${b.startSec}–${b.endSec} s`).join(', ') : 'aucune coupure des graves détectée',
    kind: 'mesure',
  });
  if (m.sectionChanges?.length) {
    items.push({ label: 'Changements d\'énergie', value: m.sectionChanges.map((c) => `${c.atSec} s (${c.changeDb > 0 ? '+' : ''}${c.changeDb} dB)`).join(', '), kind: 'mesure' });
  }
  return items;
}
