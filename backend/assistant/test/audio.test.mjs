import { test } from 'node:test';
import assert from 'node:assert/strict';
import { createServer } from 'node:http';
import { createHandler, createRateLimiter } from '../src/app.mjs';
import { AudioFormatError, decodeWav, encodeWav } from '../src/audio/wav.mjs';
import { NOT_MEASURED, analyzeAudio, summarize } from '../src/audio/analyze.mjs';
import { AUDIO_RULES } from '../src/audio/advice.mjs';
import { synth } from './synth.mjs';

/** WAV multicanal en flottant 32 bits ou PCM 8/24 bits (pour tester le décodeur). */
function encodeWavCustom(channelsData, sampleRate, { bits = 16, float = false } = {}) {
  const ch = channelsData.length;
  const n = channelsData[0].length;
  const bytes = bits / 8;
  const buf = Buffer.alloc(44 + n * ch * bytes);
  buf.write('RIFF', 0, 'ascii'); buf.writeUInt32LE(36 + n * ch * bytes, 4); buf.write('WAVE', 8, 'ascii');
  buf.write('fmt ', 12, 'ascii'); buf.writeUInt32LE(16, 16); buf.writeUInt16LE(float ? 3 : 1, 20); buf.writeUInt16LE(ch, 22);
  buf.writeUInt32LE(sampleRate, 24); buf.writeUInt32LE(sampleRate * ch * bytes, 28); buf.writeUInt16LE(ch * bytes, 32); buf.writeUInt16LE(bits, 34);
  buf.write('data', 36, 'ascii'); buf.writeUInt32LE(n * ch * bytes, 40);
  for (let i = 0; i < n; i++) {
    for (let c = 0; c < ch; c++) {
      const p = 44 + (i * ch + c) * bytes;
      const v = Math.max(-1, Math.min(1, channelsData[c][i]));
      if (float) buf.writeFloatLE(v, p);
      else if (bits === 8) buf[p] = Math.round(v * 127 + 128);
      else if (bits === 24) buf.writeIntLE(Math.round(v * 8388607), p, 3);
      else buf.writeInt16LE(Math.round(v * 32767), p);
    }
  }
  return buf;
}

const near = (a, b, tol) => assert.ok(Math.abs(a - b) <= tol, `${a} ≉ ${b} (±${tol})`);

test('décodage WAV : 16/8/24 bits, flottant, stéréo ; refus des autres formats', () => {
  const x = synth({ bpm: 140, seconds: 6 });
  for (const opts of [{ bits: 16 }, { bits: 8 }, { bits: 24 }, { bits: 32, float: true }]) {
    const d = decodeWav(encodeWavCustom([x, x], 22050, opts));
    assert.equal(d.sampleRate, 22050);
    assert.equal(d.channels, 2);
    near(d.durationSec, 6, 0.01);
    near(d.samples[1000], x[1000], opts.bits === 8 ? 0.02 : 0.001);
  }
  assert.throws(() => decodeWav(Buffer.from('ID3\x04\x00\x00 faux mp3 ....................................')), AudioFormatError);
  assert.throws(() => decodeWav(Buffer.alloc(100)), AudioFormatError);
});

const cases = [
  { name: 'hardtek 145 kick + basse + charleys', opts: { bpm: 145, bass: true, hats: true }, bpm: 145, kickRegular: true, bass: true },
  { name: 'tribe 174 avec break 12–20 s', opts: { bpm: 174, bass: true, hats: true, breakAt: [12, 20] }, bpm: 174, kickRegular: true, breaks: [[12, 20]] },
  { name: 'house 128 kick + charleys', opts: { bpm: 128, hats: true }, bpm: 128, kickRegular: true, bass: false },
  { name: 'mentalcore 195', opts: { bpm: 195, bass: true }, bpm: 195, kickRegular: true },
  { name: 'stéréo flottant 44,1 kHz 150', opts: { bpm: 150, sr: 44100, bass: true }, bpm: 150, sr: 44100, stereoFloat: true },
];

for (const c of cases) {
  test(`analyse : ${c.name}`, () => {
    const sr = c.sr ?? 22050;
    const x = synth({ ...c.opts, sr });
    const audio = c.stereoFloat ? decodeWav(encodeWavCustom([x, x], sr, { bits: 32, float: true })) : { samples: x, sampleRate: sr };
    const m = analyzeAudio(audio);
    assert.equal(m.tempo.detected, true);
    near(m.tempo.bpm, c.bpm, 1);
    assert.equal(m.tempo.stability.label, 'stable');
    if (c.kickRegular) assert.ok(m.kick.onBeatCoverage > 0.8, `couverture ${m.kick.onBeatCoverage}`);
    if (c.bass === true) assert.match(m.bassBetweenKicks.label, /présents entre les kicks/);
    if (c.bass === false) assert.match(m.bassBetweenKicks.label, /surtout sur les temps/);
    if (c.breaks) {
      assert.equal(m.breaks.length, 1);
      near(m.breaks[0].startSec, c.breaks[0][0], 1);
      near(m.breaks[0].endSec, c.breaks[0][1], 1);
      assert.equal(m.breaks[0].kickReturns, true);
    } else assert.equal(m.breaks.length, 0);
    near(m.balance.reduce((a, b) => a + b.percent, 0), 100, 0.6);
    assert.deepEqual(m.notMeasured, NOT_MEASURED);
  });
}

test('analyse : tempo qui dérive → instabilité signalée', () => {
  const m = analyzeAudio({ samples: synth({ bpm: 160, drift: 8, seconds: 40 }), sampleRate: 22050 });
  assert.notEqual(m.tempo.stability.label, 'stable');
  assert.ok(m.tempo.stability.maxBpm - m.tempo.stability.minBpm > 2);
});

test('analyse : bruit sans pulsation → pas de tempo inventé ; silence → signalé', () => {
  const noise = analyzeAudio({ samples: synth({ noiseOnly: true }), sampleRate: 22050 });
  assert.equal(noise.tempo.detected, false);
  assert.equal(noise.kick, undefined);
  const silence = analyzeAudio({ samples: new Float32Array(22050 * 10), sampleRate: 22050 });
  assert.equal(silence.silent, true);
  assert.match(summarize(silence).map((s) => s.value).join(' '), /silencieux/);
});

test('résumé lisible : mesures et estimations distinguées', () => {
  const items = summarize(analyzeAudio({ samples: synth({ bpm: 145, bass: true }), sampleRate: 22050 }));
  assert.match(items.find((i) => i.label === 'Tempo').value, /≈ 145(\.\d)? BPM/);
  assert.equal(items.find((i) => i.label === 'Graves entre les kicks').kind, 'estimation');
});

test('règles IA : mesures seulement, hypothèses signalées, aucun bouton inventé', () => {
  assert.match(AUDIO_RULES, /Tu n'entends PAS l'audio/);
  assert.match(AUDIO_RULES, /N'ajoute aucune mesure/);
  assert.match(AUDIO_RULES, /hypothèses/);
  assert.match(AUDIO_RULES, /n'invente aucun bouton/);
});

// --- HTTP -------------------------------------------------------------------

const meta = { machine: { id: 'circuit-tracks', name: 'Novation Circuit Tracks' }, style: 'hardtek', goal: 'live', level: 'intermédiaire',
  context: [{ title: 'Le Mixer', text: 'Mutes sur la rangée 1.', ref: 'Guide Circuit Tracks p. 88' }] };
const wavB64 = (x, sr = 22050) => encodeWav(x, sr).toString('base64');

async function withServer(provider, fn, audioRateLimiter) {
  const server = createServer(createHandler({ provider, audioRateLimiter, log: {} }));
  await new Promise((r) => server.listen(0, r));
  const url = `http://127.0.0.1:${server.address().port}/v1/audio-analysis`;
  try { return await fn(url); } finally { server.close(); }
}
const post = (url, body) => fetch(url, { method: 'POST', headers: { 'content-type': 'application/json' }, body: JSON.stringify(body) });

test('HTTP analyse audio : mesures + conseils, l\'IA ne reçoit que les mesures', async () => {
  let seen;
  const provider = { name: 'fake', complete: async (c) => { seen = c; return { text: '## Mesures\n…', truncated: false }; } };
  await withServer(provider, async (url) => {
    const b64 = wavB64(synth({ bpm: 145, bass: true, seconds: 20 }));
    const r = await post(url, { ...meta, audio: { format: 'wav', base64: b64 } });
    assert.equal(r.status, 200);
    const json = await r.json();
    near(json.measures.tempo.bpm, 145, 1);
    assert.ok(json.summary.length > 4);
    assert.equal(json.advice, '## Mesures\n…');
    const text = seen.messages[0].text;
    assert.match(text, /Style visé : hardtek\. Objectif : live\. Niveau : intermédiaire/);
    assert.match(text, /Guide Circuit Tracks p\. 88/);
    assert.ok(!text.includes(b64.slice(100, 160)), "l'audio ne doit pas être transmis au fournisseur IA");
  });
});

test('HTTP analyse audio : formats, durées et erreurs', async () => {
  const failing = { name: 'fake', complete: async () => { throw new Error('IA indisponible'); } };
  await withServer(failing, async (url) => {
    const mp3 = Buffer.from('ID3\x04\x00 pas un wav').toString('base64');
    let r = await post(url, { ...meta, audio: { format: 'wav', base64: mp3 } });
    assert.equal(r.status, 415);
    assert.equal((await r.json()).error.code, 'unsupported_format');
    r = await post(url, { ...meta, audio: { format: 'mp3', base64: mp3 } });
    assert.equal(r.status, 415);
    r = await post(url, { ...meta, audio: { format: 'wav', base64: wavB64(synth({ seconds: 2 })) } });
    assert.equal((await r.json()).error.code, 'audio_too_short');
    r = await post(url, { ...meta, audio: { format: 'wav', base64: wavB64(new Float32Array(8000 * 80), 8000) } });
    assert.equal((await r.json()).error.code, 'audio_too_long');
    // IA en panne : les mesures sont quand même renvoyées
    r = await post(url, { ...meta, audio: { format: 'wav', base64: wavB64(synth({ bpm: 128, seconds: 12 })) } });
    assert.equal(r.status, 200);
    const json = await r.json();
    assert.equal(json.advice, null);
    assert.match(json.adviceError, /mesures ci-dessus restent valables/);
    near(json.measures.tempo.bpm, 128, 1);
  });
});

test('HTTP analyse audio : limite de débit dédiée', async () => {
  const provider = { name: 'fake', complete: async () => ({ text: 'ok', truncated: false }) };
  await withServer(provider, async (url) => {
    const body = { ...meta, audio: { format: 'wav', base64: wavB64(synth({ seconds: 6 })) } };
    assert.equal((await post(url, body)).status, 200);
    assert.equal((await post(url, body)).status, 429);
  }, createRateLimiter({ max: 1 }));
});
