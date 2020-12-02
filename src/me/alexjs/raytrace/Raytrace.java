package me.alexjs.raytrace;

import me.alexjs.raytrace.math.Vector;
import me.alexjs.raytrace.render.Renderer;
import me.alexjs.raytrace.render.samplers.MultiJitteredSampler;
import me.alexjs.raytrace.scene.Scene;
import me.alexjs.raytrace.scene.camera.Camera;
import me.alexjs.raytrace.scene.camera.Dimensions;
import me.alexjs.raytrace.scene.lights.PointLight;
import me.alexjs.raytrace.scene.mesh.Mesh;
import me.alexjs.raytrace.tests.Bench;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Raytrace {

    private static final long RAND_SEED = 0;

    public static final Random RAND = new Random(RAND_SEED);

    // TODO Remove benchmarking code
    public static final int BENCH_RENDER_ID;
    public static final int BENCH_TRI_ID;

    static {
        BENCH_RENDER_ID = Bench.createBench("Render image", "sample");
        BENCH_TRI_ID = Bench.createBench("Triangulate scene", "triangulation");
    }

    public Raytrace() {
        Scene scene = createScene();
        Renderer renderer = new Renderer(5, new MultiJitteredSampler());

        saveImage(scene.getPrimaryCamera().getSensorDimensions(), renderer.renderImage(scene));
        Bench.printReports();
    }

    private Scene createScene() {
        Scene scene = new Scene();

        scene.addMesh(Mesh.newCube());
        scene.addMesh(Mesh.groundPlane());

        Camera camera = new Camera(1080, 1080, 36);
        camera.setLocation(new Vector(7.5, -6.5, 5.5));
        camera.alignTo(Vector.ZERO);
        camera.setFocalLength(40);
        scene.addCamera(camera);

        scene.addLight(new PointLight(new Vector(3, 3, 6), 400, 0.2));
        scene.addLight(new PointLight(new Vector(-5, -3, 6), 400, 0.2));

        return scene;
    }

    private void saveImage(Dimensions dimensions, double[][] image) {
        int imageWidth = dimensions.getResolutionX();
        int imageHeight = dimensions.getResolutionY();
        BufferedImage buf = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        for (int pixelY = 0; pixelY < imageHeight; pixelY++) {
            for (int pixelX = 0; pixelX < imageWidth; pixelX++) {
                int g = (int) Math.round(image[pixelX][pixelY] * 10);
                g = g | g << 8 | g << 16 | g << 24;
                buf.setRGB(pixelX, pixelY, g);
            }
        }
        try {
            ImageIO.write(buf, "png", new File("C:/Users/Alex/Desktop/test.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        new Raytrace();
        long end = System.currentTimeMillis();
        System.out.println("Total run time: " + (end - start) + "ms");
    }

}
