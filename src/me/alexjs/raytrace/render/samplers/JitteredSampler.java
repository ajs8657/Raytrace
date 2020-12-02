package me.alexjs.raytrace.render.samplers;

import me.alexjs.raytrace.Raytrace;

public class JitteredSampler extends Sampler {

    public Sample[] getPixelSamples(int sampleCount) {
        int m = (int) Math.sqrt(sampleCount);
        int n = (sampleCount / m) + (sampleCount % m);
        Sample[] samples = new Sample[sampleCount];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                double x = (((Raytrace.RAND.nextDouble() + j) / n) + i) / m;
                double y = (((Raytrace.RAND.nextDouble() + i) / m) + j) / n;
                samples[i * m + j] = new Sample(x, y);
            }
        }
        return samples;
    }

}
