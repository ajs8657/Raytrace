package me.alexjs.raytrace.scene.lights;

import me.alexjs.raytrace.Raytrace;
import me.alexjs.raytrace.math.Ray;
import me.alexjs.raytrace.math.Vector;

/**
 * World Light that emits light from point
 */
public class PointLight extends Light {

    private double radius;

    /**
     * Construct a PointLight with a specified location Vector and intensity
     *
     * @param location  the location Vector
     * @param intensity the power in watts
     */
    public PointLight(Vector location, double intensity, double radius) {
        super(location, intensity);
        this.radius = radius;
    }

    /**
     * Get the light Ray from the origin to this PointLight
     *
     * @param origin the origin Vector of the light ray
     * @return the light Ray
     */
    public Ray getLightRay(Vector origin) {
        // This is bad. fix this
        double dx = Raytrace.RAND.nextDouble();
        double dy = Raytrace.RAND.nextDouble();
        double dz = Raytrace.RAND.nextDouble();
        Vector randLightSampleDir = new Vector(dx, dy, dz).normalize();
        Vector lightSample = randLightSampleDir.scale(radius).add(location);

        // Normalization is necessary
        return new Ray(origin, lightSample.subtract(origin).normalize());
    }

    public double getRadius() {
        return radius;
    }

}
