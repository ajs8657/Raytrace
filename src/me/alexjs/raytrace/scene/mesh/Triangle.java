package me.alexjs.raytrace.scene.mesh;

import me.alexjs.raytrace.math.IntersectionResult;
import me.alexjs.raytrace.math.Ray;
import me.alexjs.raytrace.math.Vector;

/**
 * Immutable Triangle
 */
public final class Triangle {

    // Small value greater than 0 used to help with rounding errors
    // TODO Maybe make this smaller or bigger later. I don't really know what a good value is
    private static final double EPSILON = 0.000000001;

    private final Vector v1;
    private final Vector v2;
    private final Vector v3;

    // Common values used for calculations
    private final Vector normal; // The Vector perpendicular to the plane
    private final double d; // d in the plane equation: ax + by + cz + d = 0
    private final Vector edge1;
    private final Vector edge2;
    private final Vector edge3;

    public Triangle(Vertex vert1, Vertex vert2, Vertex vert3) {
        this.v1 = new Vector(vert1.getX(), vert1.getY(), vert1.getZ());
        this.v2 = new Vector(vert2.getX(), vert2.getY(), vert2.getZ());
        this.v3 = new Vector(vert3.getX(), vert3.getY(), vert3.getZ());

        this.normal = (v1.subtract(v2)).crossProduct(v2.subtract(v3)).normalize();
        this.d = -normal.dotProduct(v1);
        this.edge1 = v2.subtract(v1);
        this.edge2 = v3.subtract(v2);
        this.edge3 = v1.subtract(v3);
    }

    public IntersectionResult intersects(Ray ray) {
        double dDotN = ray.getDirection().dotProduct(normal);
        if (Math.abs(dDotN) < EPSILON) {
            return IntersectionResult.NONE;
        }

        double t = -(ray.getOrigin().dotProduct(normal) + d) / dDotN;
        if (t < 0) {
            return IntersectionResult.NONE;
        }

        Vector planeIntersection = ray.getPoint(t);
        Vector C = edge1.crossProduct(planeIntersection.subtract(v1));
        if (normal.dotProduct(C) < 0) {
            return IntersectionResult.NONE;
        }

        C = edge2.crossProduct(planeIntersection.subtract(v2));
        if (normal.dotProduct(C) < 0) {
            return IntersectionResult.NONE;
        }

        C = edge3.crossProduct(planeIntersection.subtract(v3));
        if (normal.dotProduct(C) < 0) {
            return IntersectionResult.NONE;
        }

        return new IntersectionResult(t, planeIntersection);
    }

}
