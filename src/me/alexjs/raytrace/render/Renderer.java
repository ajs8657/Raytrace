package me.alexjs.raytrace.render;

import me.alexjs.raytrace.Raytrace;
import me.alexjs.raytrace.math.IntersectionResult;
import me.alexjs.raytrace.math.Ray;
import me.alexjs.raytrace.math.Vector;
import me.alexjs.raytrace.render.samplers.Sample;
import me.alexjs.raytrace.render.samplers.Sampler;
import me.alexjs.raytrace.scene.Scene;
import me.alexjs.raytrace.scene.camera.Camera;
import me.alexjs.raytrace.scene.lights.Light;
import me.alexjs.raytrace.scene.mesh.Triangle;
import me.alexjs.raytrace.tests.Bench;

// 1080p @ 5 samples:
// Single threaded no RenderContext: ~3200ms
// Single threaded with RenderContext: 2800ms
// Multi-threaded with RenderContext: lol nvm
public class Renderer {

    private static final int DEFAULT_SAMPLE_COUNT = 50;
    // TODO max or min bounces

    private int sampleCount;
    private Sampler sampler;

    public Renderer(Sampler sampler) {
        this(DEFAULT_SAMPLE_COUNT, sampler);
    }

    public Renderer(int sampleCount, Sampler sampler) {
        this.sampleCount = sampleCount;
        this.sampler = sampler;
    }

    public double[][] renderImage(Scene scene) {
        return renderImage(scene, scene.getPrimaryCamera());
    }

    public double[][] renderImage(Scene scene, Camera camera) {
        return renderImage(new RenderContext(scene, camera, sampler));
    }

    private double[][] renderImage(RenderContext context) {
        double w = context.imageWidth;
        double h = context.imageHeight;
        double s = sampleCount;
        double[][] image = new double[context.imageWidth][context.imageHeight];

        Bench.startBench(Raytrace.BENCH_RENDER_ID);

        for (int pixelX = 0; pixelX < w; pixelX++) {
            for (int pixelY = 0; pixelY < h; pixelY++) {
                double pixelAverage = 0;
                Sample[] samples = context.sampler.getPixelSamples(sampleCount);
                for (int sample = 0; sample < sampleCount; sample++) {
                    double randX = samples[sample].x;
                    double randY = samples[sample].y;
                    double u = ((2 * (pixelX + randX) + 1) / w) - 1;
                    double v = 1 - ((2 * (pixelY + randY) + 1) / h);
                    Ray ray = getCameraRay(context, u, v);

                    double intensity = traceRay(context, ray);
                    pixelAverage += intensity;

                    Bench.lap(Raytrace.BENCH_RENDER_ID);
                }
                image[pixelX][pixelY] = pixelAverage / s;
            }
        }

        Bench.endBench(Raytrace.BENCH_RENDER_ID);

        return image;
    }

    private double traceRay(RenderContext context, Ray ray) {

        // Find the closest triangle
        IntersectionResult closestIntersection = IntersectionResult.NONE;
        Triangle closestTriangle = null;
        for (Triangle tri : context.triangles) {
            IntersectionResult result = tri.intersects(ray);
            if (!result.intersects) {
                continue;
            }
            if (result.t < closestIntersection.t) {
                closestIntersection = result;
                closestTriangle = tri;
            }
        }

        if (!closestIntersection.intersects) {
            return 0; // Hits nothing. No illumination is added
        }

        // Calculate Direct Lighting (Light > Surface > Camera)
        double directIllumination = 0;
        Vector bouncePoint = closestIntersection.intersectionPoint;
        for (Light light : context.lights) {
            Ray lightRay = light.getLightRay(bouncePoint);

            // Make sure the light Ray is not obstructed
            boolean obstructed = false;
            for (Triangle tri : context.triangles) {
                if (tri == closestTriangle) {
                    continue;
                }
                IntersectionResult result = tri.intersects(lightRay);
                if (result.intersects) {
                    obstructed = true;
                    break;
                }
            }
            if (obstructed) {
                continue;
            }

            // Calculate the illumination of this light Ray using the inverse square law
            double lightDistSqr = bouncePoint.distanceSquared(light.getLocation());
            directIllumination += (light.getPower() / lightDistSqr);
        }

        // TODO Calculate indirectIllumination too

        return directIllumination;

    }

    private Ray getCameraRay(RenderContext context, double u, double v) {
        double sx = u * context.tanHalfFov;
        double sy = v * context.tanHalfFov / context.aspectRatio;

        /*
        Transform x/y camera space to x/y/z world space
            Vector(1, -sx, sy) means that a Camera orientation of (0, 0, 0) should yield camera rays that:
            point towards the positive x axis,
            scan from left to right along the negative y axis (-u => +y and +u => -y),
            and scan from top to bottom along the positive z axis (+v => +z and -v => -z)
        Normalization is necessary in order to get consistent values for t when calculating the closest triangle
         */
        Vector sampleDirection = context.rotationMatrix.multiplyVector(1, -sx, sy).normalize();

        return new Ray(context.cameraLocation, sampleDirection);
    }

    public void setSampleCount(int sampleCount) {
        this.sampleCount = sampleCount;
    }

    public void setSampler(Sampler sampler) {
        this.sampler = sampler;
    }

    public int getSampleCount() {
        return sampleCount;
    }

    public Sampler getSampler() {
        return sampler;
    }

}
