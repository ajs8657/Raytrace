package me.alexjs.raytrace.math;

/**
 * Immutable 3D Vector
 */
public final class Vector {

    public static final Vector ZERO = new Vector(0, 0, 0);

    private final double x;
    private final double y;
    private final double z;

    /**
     * Construct a 3D Vector using x, y, and z components
     *
     * @param x x component
     * @param y y component
     * @param z z component
     */
    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Construct a copy of a 3D Vector
     *
     * @param other the other Vector
     */
    public Vector(Vector other) {
        this(other.x, other.y, other.z);
    }

    /**
     * Get the magnitude of the vector using the pythagorean theorem
     *
     * @return magnitude
     */
    public double getMagnitude() {
        return distance(Vector.ZERO);
    }

    /**
     * Get a normalized copy of this Vector
     *
     * @return A Vector with a magnitude of 1 in the same direction as this Vector
     */
    public Vector normalize() {
        double mag = getMagnitude();
        return new Vector(x / mag, y / mag, z / mag);
    }

    /**
     * Get the distance squared from this Vector to another
     *
     * @param other the other Vector
     * @return the distance squared between this and the other Vector
     */
    public double distanceSquared(Vector other) {
        double dx = x - other.x;
        double dy = y - other.y;
        double dz = z - other.z;
        return dx * dx + dy * dy + dz * dz;
    }

    /**
     * Get the distance from this Vector to another
     *
     * @param other the other Vector
     * @return the distance between this and the other Vector
     */
    public double distance(Vector other) {
        return Math.sqrt(distanceSquared(other));
    }

    /**
     * Add a Vector to this
     *
     * @param other the other Vector
     * @return the addition result
     */
    public Vector add(Vector other) {
        return new Vector(x + other.x, y + other.y, z + other.z);
    }

    /**
     * Subtract a Vector from this
     *
     * @param other the other Vector
     * @return the subtraction result
     */
    public Vector subtract(Vector other) {
        return new Vector(x - other.x, y - other.y, z - other.z);
    }

    /**
     * Find the cross product between this and another Vector
     *
     * @param other the other Vector
     * @return the cross product Vector
     */
    public Vector crossProduct(Vector other) {
        double crossX = (y * other.z) - (z * other.y);
        double crossY = (z * other.x) - (x * other.z);
        double crossZ = (x * other.y) - (y * other.x);
        return new Vector(crossX, crossY, crossZ);
    }

    /**
     * Find the dot product between this and another Vector
     *
     * @param other the other Vector
     * @return the dot product value
     */
    public double dotProduct(Vector other) {
        return x * other.x + y * other.y + z * other.z;
    }

    /**
     * Scale this by a scalar value
     *
     * @param scalar the scalar value
     * @return the scaled Vector
     */
    public Vector scale(double scalar) {
        return new Vector(x * scalar, y * scalar, z * scalar);
    }

    /**
     * Scale this by another Vector
     *
     * @param other the other Vector
     * @return the scaled Vector
     */
    public Vector scale(Vector other) {
        return new Vector(x * other.x, y * other.y, z * other.z);
    }

    /**
     * Get the x component of this Vector
     *
     * @return the x component
     */
    public double getX() {
        return x;
    }

    /**
     * Get the y component of this Vector
     *
     * @return the y component
     */
    public double getY() {
        return y;
    }

    /**
     * Get the z component of this Vector
     *
     * @return the z component
     */
    public double getZ() {
        return z;
    }

}
