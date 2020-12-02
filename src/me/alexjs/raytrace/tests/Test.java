package me.alexjs.raytrace.tests;

public class Test {

    public Test() {

    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        new Test();
        long end = System.currentTimeMillis();
        System.out.println("Immutable average run time: " + (end - start) + "ms");
    }

}
