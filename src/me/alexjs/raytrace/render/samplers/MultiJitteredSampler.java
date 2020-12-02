package me.alexjs.raytrace.render.samplers;

import me.alexjs.raytrace.Raytrace;

public class MultiJitteredSampler extends Sampler {

    public Sample[] getPixelSamples(int sampleCount) {
        Sample[] samples = new Sample[sampleCount];
        int s = 779;
        for (int p = 0; p < sampleCount; p++) {
            int m = (int) Math.sqrt(sampleCount);
            int n = (sampleCount + m - 1) / m;
            s = permute(s, sampleCount, p * 0x51633e2d);
            int sx = permute(s % m, m, p * 0x68bc21eb);
            int sy = permute(s / m, n, p * 0x02e5be93);
            double jx = Raytrace.RAND.nextDouble();
            double jy = Raytrace.RAND.nextDouble();
            samples[p] = new Sample((sx + (sy + jx) / n) / m, (s + jy) / n);
        }
        return samples;
    }

    private int permute(int i, int l, int p) {
        int w = l - 1;
        w |= w >> 1;
        w |= w >> 2;
        w |= w >> 4;
        w |= w >> 8;
        w |= w >> 16;
        do {
            i ^= p;
            i *= 0xe170893d;
            i ^= p >> 16;
            i ^= (i & w) >> 4;
            i ^= p >> 8;
            i *= 0x0929eb3f;
            i ^= p >> 23;
            i ^= (i & w) >> 1;
            i *= 1 | p >> 27;
            i *= 0x6935fa69;
            i ^= (i & w) >> 11;
            i *= 0x74dcb303;
            i ^= (i & w) >> 2;
            i *= 0x9e501cc3;
            i ^= (i & w) >> 2;
            i *= 0xc860a3df;
            i &= w;
            i ^= i >> 5;
        } while (i >= l);
        return (i + p) % l;
    }

}
