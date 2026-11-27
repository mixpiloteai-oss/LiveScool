// Générateur de morceaux de test (signaux synthétiques déterministes).
export function synth({ bpm = 145, seconds = 30, sr = 22050, kick = true, bass = false, hats = false,
  breakAt = null, noiseOnly = false, drift = 0, amp = 0.8 } = {}) {
  const n = Math.floor(seconds * sr);
  const x = new Float32Array(n);
  let seed = 0x9e3779b9;
  const rnd = () => { // mulberry32
    seed = (seed + 0x6d2b79f5) | 0;
    let t = Math.imul(seed ^ (seed >>> 15), 1 | seed);
    t = (t + Math.imul(t ^ (t >>> 7), 61 | t)) ^ t;
    return (((t ^ (t >>> 14)) >>> 0) / 4294967296) * 2 - 1;
  };
  if (noiseOnly) {
    for (let i = 0; i < n; i++) x[i] = 0.2 * rnd();
    return x;
  }
  const inBreak = (t) => breakAt && t >= breakAt[0] && t < breakAt[1];
  let t = 0;
  let beat = 0;
  while (t < seconds) {
    const period = 60 / (bpm + drift * (t / seconds));
    if (!inBreak(t)) {
      const start = Math.floor(t * sr);
      if (kick) {
        for (let i = 0; i < 0.18 * sr && start + i < n; i++) {
          const tt = i / sr;
          const f = 50 + 70 * Math.exp(-tt * 30);
          x[start + i] += amp * Math.exp(-tt * 18) * Math.sin(2 * Math.PI * f * tt);
        }
      }
      if (bass) {
        const bs = Math.floor((t + period / 2) * sr);
        for (let i = 0; i < (period / 2) * 0.8 * sr && bs + i < n; i++) {
          const tt = i / sr;
          x[bs + i] += 0.35 * Math.exp(-tt * 4) * Math.sin(2 * Math.PI * 55 * tt);
        }
      }
    }
    if (hats) {
      for (const off of [0, period / 2]) {
        const hs = Math.floor((t + off) * sr);
        for (let i = 0; i < 0.03 * sr && hs + i < n; i++) x[hs + i] += 0.08 * Math.exp(-(i / sr) * 120) * rnd();
      }
    }
    t += period;
    beat++;
  }
  return x;
}
