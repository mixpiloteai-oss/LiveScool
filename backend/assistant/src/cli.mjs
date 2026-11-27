// Analyse locale d'un ou plusieurs fichiers WAV : npm run analyze -- morceau.wav autre.wav
import { readFileSync } from 'node:fs';
import { decodeWav } from './audio/wav.mjs';
import { analyzeAudio, summarize } from './audio/analyze.mjs';

for (const path of process.argv.slice(2)) {
  try {
    const audio = decodeWav(readFileSync(path));
    console.log(`\n${path}`);
    for (const item of summarize(analyzeAudio(audio))) console.log(`  ${item.label} : ${item.value}${item.kind === 'estimation' ? ' (estimation)' : ''}`);
  } catch (e) {
    console.log(`\n${path} : ${e.message}`);
  }
}
