package me.alexjs.raytrace.scene;

import me.alexjs.raytrace.scene.camera.Camera;
import me.alexjs.raytrace.scene.lights.Light;
import me.alexjs.raytrace.scene.mesh.Mesh;

import java.util.ArrayList;
import java.util.List;

/**
 * Scenes consist of meshes, lights, and cameras
 */
public class Scene {

    private final List<Mesh> meshes;
    private final List<Light> lights;
    private Camera camera;

    public Scene() {
        this.meshes = new ArrayList<>();
        this.lights = new ArrayList<>();
    }

    public void addMesh(Mesh mesh) {
        meshes.add(mesh);
    }

    public void addLight(Light light) {
        lights.add(light);
    }

    public void addCamera(Camera camera) {
        this.camera = camera;
    }

    public List<Mesh> getMeshes() {
        return meshes;
    }

    public List<Light> getLights() {
        return lights;
    }

    public Camera getPrimaryCamera() {
        return camera;
    }

}
