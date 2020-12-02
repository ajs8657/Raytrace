package me.alexjs.raytrace.scene.mesh;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Mesh {

    private final List<Vertex> vertices;
    private final List<Face> faces;

    private final List<Triangle> triangles;

    public Mesh(Face... faces) {
        this.faces = new ArrayList<>();
        this.vertices = new ArrayList<>();
        this.triangles = new ArrayList<>();

        this.faces.addAll(Arrays.asList(faces));

    }

    public static Mesh newCube() {
        Vertex v0 = new Vertex(-1, -1, -1);
        Vertex v1 = new Vertex(-1, 1, -1);
        Vertex v2 = new Vertex(-1, 1, 1);
        Vertex v3 = new Vertex(-1, -1, 1);
        Vertex v4 = new Vertex(1, -1, -1);
        Vertex v5 = new Vertex(1, 1, -1);
        Vertex v6 = new Vertex(1, 1, 1);
        Vertex v7 = new Vertex(1, -1, 1);

        // Don't know why yet, but it looks like these vertices have to be placed in clockwise order
        // ^^ I think this no longer applies. Fixed it with Triangle.intersectPlane(). Added Math.abs()
        Face f0 = new Face(v2, v3, v0, v1); // left
        Face f1 = new Face(v1, v5, v6, v2); // back
        Face f2 = new Face(v4, v7, v6, v5); // right
        Face f3 = new Face(v7, v4, v0, v3); // front
        Face f4 = new Face(v7, v6, v2, v3); // top
        Face f5 = new Face(v5, v1, v0, v4); // bottom

        return new Mesh(f0, f1, f2, f3, f4, f5);
    }

    public static Mesh groundPlane() {
        Vertex v0 = new Vertex(-100, -100, -1);
        Vertex v1 = new Vertex(100, -100, -1);
        Vertex v2 = new Vertex(100, 100, -1);
        Vertex v3 = new Vertex(-100, 100, -1);

        Face f = new Face(v0, v1, v2, v3);
        return new Mesh(f);
    }

    public void triangulate() {
        // TODO Fill triangles list
        // TODO This is bad. Don't do this
        for (Face face : faces) {
            int count = face.getVertices().size();
            List<Vertex> vertices = face.getVertices();
            if (count == 3) {
                Vertex v0 = vertices.get(0);
                Vertex v1 = vertices.get(1);
                Vertex v2 = vertices.get(2);
                triangles.add(new Triangle(v0, v1, v2));
            } else if (count == 4) {
                Vertex v0 = vertices.get(0);
                Vertex v1 = vertices.get(1);
                Vertex v2 = vertices.get(2);
                Vertex v3 = vertices.get(3);
                triangles.add(new Triangle(v0, v1, v2));
                triangles.add(new Triangle(v2, v3, v0));
            }
        }
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public List<Face> getFaces() {
        return faces;
    }

    public List<Triangle> getTriangles() {
        return triangles;
    }

}
