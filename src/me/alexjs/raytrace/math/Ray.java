package me.alexjs.raytrace.math;

/**
 * Immutable Ray with an origin Vector and a direction Vector
 */
public final class Ray {

    private final Vector origin;
    private final Vector direction;

    /**
     * Construct a Ray with an origin and a direction
     *
     * @param origin    the origin Vector
     * @param direction the direction Vector
     */
    public Ray(Vector origin, Vector direction) {
        this.origin = origin;
        this.direction = direction;
    }

    /**
     * Get the point Vector at R(t)
     *
     * @param t the parameter for the Ray
     * @return the point Vector
     */
    public Vector getPoint(double t) {
        return direction.scale(t).add(origin);
    }

    /**
     * Get the origin Vector of this Ray
     *
     * @return the origin Vector
     */
    public Vector getOrigin() {
        return origin;
    }

    /**
     * Get the direction Vector of this Ray
     *
     * @return the direction Vector
     */
    public Vector getDirection() {
        return direction;
    }

}
