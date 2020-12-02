package me.alexjs.raytrace.scene.camera;

// Immutable
public final class Dimensions {

    private final int resolutionX; // In pixels
    private final int resolutionY; // In pixels
    private final double aspectRatio; // Automatically calculated

    public Dimensions(int resolutionX, int resolutionY) {
        this.resolutionX = resolutionX;
        this.resolutionY = resolutionY;
        this.aspectRatio = resolutionX / (double) resolutionY;
    }

    /**
     * Get the X resolution of this sensor
     *
     * @return the width of this sensor in pixels
     */
    public int getResolutionX() {
        return resolutionX;
    }

    /**
     * Get the Y resolution of this sensor
     *
     * @return the height of this sensor in pixels
     */
    public int getResolutionY() {
        return resolutionY;
    }

    /**
     * Get the aspect ratio of this sensor
     *
     * @return the aspect ration (width / height)
     */
    public double getAspectRatio() {
        return aspectRatio;
    }

}
