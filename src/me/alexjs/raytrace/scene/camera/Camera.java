package me.alexjs.raytrace.scene.camera;

import me.alexjs.raytrace.math.Matrix;
import me.alexjs.raytrace.math.Vector;

/**
 * Camera object with a world location and orientation
 */
public class Camera {

    private Sensor sensor;
    private Vector location;
    private Vector orientation; // (x, y, z) = (yaw, pitch, roll)

    // Depend on each other.
    private double fov; // Horizontal picture angle
    private double focalLength; // Distance from sensor to camera origin

    /**
     * Constructs a Camera with specified sensor attributes
     *
     * @param resolutionX the width of the sensor in pixels
     * @param resolutionY the height of the sensor in pixels
     * @param sensorWidth the width of the sensor in millimeters
     */
    public Camera(int resolutionX, int resolutionY, double sensorWidth) {
        this.sensor = new Sensor(resolutionX, resolutionY, sensorWidth);
    }

    /**
     * Align the Camera to a specified point with a default roll value of 0
     *
     * @param to The Vector to align the Camera to
     */
    public void alignTo(Vector to) {
        alignTo(to, 0);
    }

    /**
     * Align the Camera to a specified point and set the Camera's roll value
     *
     * @param to   the Vector to align the Camera to
     * @param roll the roll value for this Camera's orientation
     */
    public void alignTo(Vector to, double roll) {
        // Find the normalized direction Vector from 'to' to this Camera's location
        Vector direction = to.subtract(location).normalize();

        // Since the (0, 0, 0) orientation is the +x axis, yaw == azimuth and no conversion is necessary
        double yaw = Math.atan2(direction.getY(), direction.getX());

        // Inclination goes from the +z axis to the -z axis from 0 to pi
        double inclination = Math.acos(direction.getZ());

        // Pitch goes from -pi/2 to pi/2 where 0 is horizontal
        double pitch = (Math.PI / 2) - inclination;

        // Set the Camera's orientation (which subsequently updates the rotation Matrix too)
        setOrientation(new Vector(yaw, pitch, roll));
    }

    /**
     * Update the camera's rotation Matrix to transform a Camera Ray to a specified orientation
     * Taken from Wikipedia's 'Rotation matrix' page under 'General rotations'
     *
     * @link https://en.wikipedia.org/wiki/Rotation_matrix#General_rotations
     *
     * <p>
     * Both 'sPitch' values here are inverted from what Wikipedia listed. I don't know why, but using Wikipedia's
     * exact Pitch matrix, the the camera acted as if the pitch were inverted. So I inverted pitch again. cPitch
     * should also be inverted too, but since cosine is an even function, cos(-pitch) = cos(pitch)
     * </p>
     */
    public Matrix getRotationMatrix() {
        double sYaw = Math.sin(orientation.getX());
        double sPitch = Math.sin(orientation.getY());
        double sRoll = Math.sin(orientation.getZ());
        double cYaw = Math.cos(orientation.getX());
        double cPitch = Math.cos(orientation.getY());
        double cRoll = Math.cos(orientation.getZ());

        Matrix matYaw = new Matrix(cYaw, -sYaw, 0, sYaw, cYaw, 0, 0, 0, 1);
        Matrix matPitch = new Matrix(cPitch, 0, -sPitch, 0, 1, 0, sPitch, 0, cPitch);
        Matrix matRoll = new Matrix(1, 0, 0, 0, cRoll, -sRoll, 0, sRoll, cRoll);
        return matYaw.multiply(matPitch).multiply(matRoll);
    }

    /**
     * Set the x resolution of this Camera's Sensor Dimensions
     *
     * @param resolutionX the x resolution
     */
    public void setResolutionX(int resolutionX) {
        int resolutionY = sensor.getDimensions().getResolutionY();
        setResolution(resolutionX, resolutionY);
    }

    /**
     * Set the y resolution of this Camera's Sensor Dimensions
     *
     * @param resolutionY the y resolution
     */
    public void setResolutionY(int resolutionY) {
        int resolutionX = sensor.getDimensions().getResolutionX();
        setResolution(resolutionX, resolutionY);
    }

    /**
     * Set the resolution of this Camera's Sensor Dimensions
     *
     * @param resolutionX the x resolution
     * @param resolutionY the y resolution
     */
    public void setResolution(int resolutionX, int resolutionY) {
        sensor.setDimensions(resolutionX, resolutionY);
    }

    /**
     * Set the location of this Camera
     *
     * @param location the new Vector location
     */
    public void setLocation(Vector location) {
        this.location = location;
    }

    /**
     * Set the orientation of this Camera
     *
     * @param orientation the new orientation Vector with (x, y, z) = (yaw, pitch, roll)
     */
    public void setOrientation(Vector orientation) {
        this.orientation = orientation;
    }

    /**
     * Set the field of view of this Camera
     *
     * @param fov the new FOV
     */
    public void setFov(double fov) {
        this.fov = fov;
        this.focalLength = (sensor.getSensorWidth() / Math.tan(fov / 2));
    }

    /**
     * Set the focal length of this Camera
     *
     * @param focalLength the new focal length
     */
    public void setFocalLength(double focalLength) {
        this.focalLength = focalLength;
        this.fov = (2 * Math.atan2(sensor.getSensorWidth() / 2, focalLength));
    }

    /**
     * Get this Camera's Sensor
     *
     * @return the Sensor
     */
    public Sensor getSensor() {
        return sensor;
    }

    /**
     * Get this Camera's Sensor Dimensions
     *
     * @return the Sensor's Dimensions
     */
    public Dimensions getSensorDimensions() {
        return sensor.getDimensions();
    }

    /**
     * Get the location of this Camera
     *
     * @return the Vector location
     */
    public Vector getLocation() {
        return location;
    }

    /**
     * Get the Orientation of this Camera
     *
     * @return the orientation Vector with (x, y, z) = (yaw, pitch, roll)
     */
    public Vector getOrientation() {
        return orientation;
    }

    /**
     * Get the field of view of this Camera
     *
     * @return the FOV
     */
    public double getFov() {
        return fov;
    }

    /**
     * Get the focal length of this camera
     *
     * @return the focal length
     */
    public double getFocalLength() {
        return focalLength;
    }

    /**
     * Get the X resolution of this sensor
     *
     * @return the width of this sensor in pixels
     */
    public int getResolutionX() {
        return getSensorDimensions().getResolutionX();
    }

    /**
     * Get the Y resolution of this sensor
     *
     * @return the height of this sensor in pixels
     */
    public int getResolutionY() {
        return getSensorDimensions().getResolutionY();
    }

}
