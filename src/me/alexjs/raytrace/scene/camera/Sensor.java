package me.alexjs.raytrace.scene.camera;

/**
 * Camera Sensor with X and Y pixel dimensions, and sensor width
 */
public class Sensor {

    private Dimensions dimensions;
    private double sensorWidth; // In millimeters

    /**
     * Constructs a Camera Sensor with a specified width and resolution
     *
     * @param resolutionX the width of this Sensor in pixels
     * @param resolutionY the height of this Sensor in pixels
     * @param sensorWidth the width of this Sensor in millimeters
     */
    public Sensor(int resolutionX, int resolutionY, double sensorWidth) {
        setDimensions(resolutionX, resolutionY);
        this.sensorWidth = sensorWidth;
    }

    /**
     * Set the dimensions of this Sensor
     *
     * @param resolutionX the width in pixels
     * @param resolutionY the height in pixels
     */
    public void setDimensions(int resolutionX, int resolutionY) {
        this.dimensions = new Dimensions(resolutionX, resolutionY);
    }

    /**
     * Set the width of this Sensor
     *
     * @param sensorWidth the width of this Sensor in millimeters
     */
    public void setSensorWidth(double sensorWidth) {
        this.sensorWidth = sensorWidth;
    }

    /**
     * Get the dimensions of this sensor
     *
     * @return the dimensions of this sensor in pixels
     */
    public Dimensions getDimensions() {
        return dimensions;
    }

    /**
     * Get the width of this sensor
     *
     * @return the width of this Sensor in millimeters
     */
    public double getSensorWidth() {
        return sensorWidth;
    }

}
