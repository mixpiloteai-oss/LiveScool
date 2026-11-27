// Lecture de fichiers WAV (PCM 8/16/24/32 bits ou flottant 32 bits, mono ou multicanal) en mémoire.
// Aucun fichier n'est écrit sur disque.

export class AudioFormatError extends Error {
  constructor(message) {
    super(message);
    this.code = 'unsupported_format';
  }
}

/** Retourne { samples: Float32Array (mono, -1..1), sampleRate, channels, bitsPerSample, durationSec }. */
export function decodeWav(buffer) {
  const buf = Buffer.isBuffer(buffer) ? buffer : Buffer.from(buffer);
  if (buf.length < 44 || buf.toString('ascii', 0, 4) !== 'RIFF' || buf.toString('ascii', 8, 12) !== 'WAVE') {
    throw new AudioFormatError('Format non pris en charge : seul le WAV est accepté par le serveur.');
  }
  let offset = 12;
  let fmt = null;
  let data = null;
  while (offset + 8 <= buf.length) {
    const id = buf.toString('ascii', offset, offset + 4);
    const size = buf.readUInt32LE(offset + 4);
    const start = offset + 8;
    if (id === 'fmt ') {
      let format = buf.readUInt16LE(start);
      if (format === 0xfffe && size >= 26) format = buf.readUInt16LE(start + 24); // WAVE_FORMAT_EXTENSIBLE
      fmt = {
        format,
        channels: buf.readUInt16LE(start + 2),
        sampleRate: buf.readUInt32LE(start + 4),
        bitsPerSample: buf.readUInt16LE(start + 14),
      };
    } else if (id === 'data') {
      data = buf.subarray(start, Math.min(buf.length, start + size));
    }
    offset = start + size + (size % 2);
  }
  if (!fmt || !data) throw new AudioFormatError('Fichier WAV incomplet.');
  const { format, channels, sampleRate, bitsPerSample } = fmt;
  const pcm = format === 1 && [8, 16, 24, 32].includes(bitsPerSample);
  const float = format === 3 && bitsPerSample === 32;
  if (!(pcm || float) || channels < 1 || channels > 8 || sampleRate < 8000 || sampleRate > 192000) {
    throw new AudioFormatError('Encodage WAV non pris en charge (PCM 8/16/24/32 bits ou flottant 32 bits).');
  }
  const bytes = bitsPerSample / 8;
  const frames = Math.floor(data.length / (bytes * channels));
  const samples = new Float32Array(frames);
  for (let i = 0; i < frames; i++) {
    let sum = 0;
    for (let c = 0; c < channels; c++) {
      const p = (i * channels + c) * bytes;
      let v;
      if (float) v = data.readFloatLE(p);
      else if (bitsPerSample === 8) v = (data[p] - 128) / 128;
      else if (bitsPerSample === 16) v = data.readInt16LE(p) / 32768;
      else if (bitsPerSample === 24) v = data.readIntLE(p, 3) / 8388608;
      else v = data.readInt32LE(p) / 2147483648;
      sum += v;
    }
    samples[i] = sum / channels;
  }
  return { samples, sampleRate, channels, bitsPerSample, durationSec: frames / sampleRate };
}

/** Encode un signal mono en WAV PCM 16 bits (tests et outils). */
export function encodeWav(samples, sampleRate) {
  const buf = Buffer.alloc(44 + samples.length * 2);
  buf.write('RIFF', 0, 'ascii');
  buf.writeUInt32LE(36 + samples.length * 2, 4);
  buf.write('WAVE', 8, 'ascii');
  buf.write('fmt ', 12, 'ascii');
  buf.writeUInt32LE(16, 16);
  buf.writeUInt16LE(1, 20);
  buf.writeUInt16LE(1, 22);
  buf.writeUInt32LE(sampleRate, 24);
  buf.writeUInt32LE(sampleRate * 2, 28);
  buf.writeUInt16LE(2, 32);
  buf.writeUInt16LE(16, 34);
  buf.write('data', 36, 'ascii');
  buf.writeUInt32LE(samples.length * 2, 40);
  for (let i = 0; i < samples.length; i++) {
    buf.writeInt16LE(Math.max(-32768, Math.min(32767, Math.round(samples[i] * 32767))), 44 + i * 2);
  }
  return buf;
}
