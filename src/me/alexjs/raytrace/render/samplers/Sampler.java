package me.alexjs.raytrace.render.samplers;

public abstract class Sampler {

    // TODO Fix these lol. Idk how I want samplers to work but this ain't it
    // TODO these must generate values on [-1/2, 1/2)
    public abstract Sample[] getPixelSamples(int samples);

}
