package me.alexjs.raytrace.scene.lights;

import me.alexjs.raytrace.math.Ray;
import me.alexjs.raytrace.math.Vector;

/**
 * Abstract World Light with a location and power
 */
public abstract class Light {

    protected Vector location;
    protected double power; // In watts

    /**
     * Construct a Light given a location and intensity
     *
     * @param location the location Vector
     * @param power    the power in watts
     */
    public Light(Vector location, double power) {
        this.location = location;
        this.power = power;
    }

    /**
     * Get a light Ray with a specified origin Vector
     * Some subclasses may introduce some randomness depending on the type of light
     *
     * @param origin the origin Vector of the light ray
     * @return the light Ray
     */
    public abstract Ray getLightRay(Vector origin);

    /**
     * Get the location of this Light
     *
     * @return the location Vector
     */
    public Vector getLocation() {
        return location;
    }

    /**
     * Get the power of this light
     *
     * @return the power in watts
     */
    public double getPower() {
        return power;
    }

}
