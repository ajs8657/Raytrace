package me.alexjs.raytrace.scene.lights;

import me.alexjs.raytrace.math.Ray;
import me.alexjs.raytrace.math.Vector;

public class SunLight extends Light {

    private Vector direction;
    private double angle; // Angular diameter

    /**
     * Construct a SunLight with a specified location Vector and intensity
     *
     * @param location  the location Vector (does not affect lighting)
     * @param intensity the power in watts
     * @param direction the direction Vector (should be normalized)
     * @param angle     the angular diameter of this sun
     */
    public SunLight(Vector location, double intensity, Vector direction, double angle) {
        super(location, intensity);
        this.direction = direction;
        this.angle = angle;
    }

    public Ray getLightRay(Vector origin) {
        // Deflect this.direction by rand(this.angle/2)
        return null;
    }

}
