package me.alexjs.raytrace.render.samplers;

import me.alexjs.raytrace.Raytrace;

public class RandomSampler extends Sampler {

    public Sample[] getPixelSamples(int sampleCount) {
        Sample[] samples = new Sample[sampleCount];
        for (int i = 0; i < sampleCount; i++) {
            double x = Raytrace.RAND.nextDouble() - 0.5;
            double y = Raytrace.RAND.nextDouble() - 0.5;
            samples[i] = new Sample(x, y);
        }
        return samples;
    }

}
