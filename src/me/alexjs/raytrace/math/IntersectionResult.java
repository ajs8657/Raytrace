package me.alexjs.raytrace.math;

public final class IntersectionResult {

    public static final IntersectionResult NONE = new IntersectionResult();

    public final boolean intersects;
    public final double t;
    public final Vector intersectionPoint;

    public IntersectionResult(double t, Vector intersectionPoint) {
        this.intersects = true;
        this.t = t;
        this.intersectionPoint = intersectionPoint;
    }

    // TODO Document
    private IntersectionResult() {
        this.intersects = false;
        this.t = Double.MAX_VALUE;
        this.intersectionPoint = null;
    }

}
