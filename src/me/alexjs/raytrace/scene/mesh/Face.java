package me.alexjs.raytrace.scene.mesh;

import java.util.ArrayList;
import java.util.Arrays;

public class Face {

    private final ArrayList<Vertex> vertices;

    public Face(Vertex... vertexArray) {
        this(new ArrayList<>(Arrays.asList(vertexArray)));
    }

    public Face(ArrayList<Vertex> vertices) {
        this.vertices = vertices;
    }

    public ArrayList<Vertex> getVertices() {
        return vertices;
    }

}
