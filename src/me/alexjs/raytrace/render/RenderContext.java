package me.alexjs.raytrace.render;

import me.alexjs.raytrace.Raytrace;
import me.alexjs.raytrace.math.Matrix;
import me.alexjs.raytrace.math.Vector;
import me.alexjs.raytrace.render.samplers.Sampler;
import me.alexjs.raytrace.scene.Scene;
import me.alexjs.raytrace.scene.camera.Camera;
import me.alexjs.raytrace.scene.camera.Dimensions;
import me.alexjs.raytrace.scene.lights.Light;
import me.alexjs.raytrace.scene.mesh.Mesh;
import me.alexjs.raytrace.scene.mesh.Triangle;
import me.alexjs.raytrace.tests.Bench;

import java.util.ArrayList;
import java.util.List;

public final class RenderContext {

    // Scene
    public final Mesh[] meshes;
    public final Light[] lights;
    public final Triangle[] triangles;

    // Camera
    public final int imageWidth;
    public final int imageHeight;
    public final double aspectRatio;
    public final Vector cameraLocation;
    public final double tanHalfFov;
    public final Matrix rotationMatrix;
    public final Sampler sampler;

    public RenderContext(final Scene scene, final Camera primaryCamera, final Sampler sampler) {

        // Meshes (for materials (not yet implemented))
        this.meshes = new Mesh[scene.getMeshes().size()];
        scene.getMeshes().toArray(meshes);

        // Lights (for their type (not yet implemented), location, and power)
        this.lights = new Light[scene.getLights().size()];
        scene.getLights().toArray(lights);

        // Triangles (for ray intersections)
        Bench.startBench(Raytrace.BENCH_TRI_ID);
        List<Triangle> triangleList = new ArrayList<>();
        for (Mesh mesh : meshes) {
            mesh.triangulate();
            triangleList.addAll(mesh.getTriangles());
            Bench.lap(Raytrace.BENCH_TRI_ID);
        }
        this.triangles = new Triangle[triangleList.size()];
        triangleList.toArray(triangles);
        Bench.endBench(Raytrace.BENCH_TRI_ID);

        // Camera settings and values
        Dimensions dimensions = primaryCamera.getSensorDimensions();
        this.imageWidth = dimensions.getResolutionX();
        this.imageHeight = dimensions.getResolutionY();
        this.aspectRatio = dimensions.getAspectRatio();
        this.cameraLocation = primaryCamera.getLocation();
        this.tanHalfFov = Math.tan(primaryCamera.getFov() / 2);
        this.rotationMatrix = primaryCamera.getRotationMatrix();

        // Sampler
        this.sampler = sampler;

    }

}
