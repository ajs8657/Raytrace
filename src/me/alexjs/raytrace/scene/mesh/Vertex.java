package me.alexjs.raytrace.scene.mesh;

public class Vertex {

    private double x;
    private double y;
    private double z;

    public Vertex() {
        this(0, 0, 0);
    }

    public Vertex(double x, double y, double z) {
        set(x, y, z);
    }

    public void moveX(double dx) {
        this.x += x;
    }

    public void moveY(double dy) {
        this.y += y;
    }

    public void moveZ(double dz) {
        this.z += z;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

}
