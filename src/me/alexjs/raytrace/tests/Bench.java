package me.alexjs.raytrace.tests;

import java.util.ArrayList;
import java.util.List;

public class Bench {

    private static final List<Bench> benches = new ArrayList<>();

    private final String benchName;
    private final String lapName;
    private final List<Long> lapTimes;

    private boolean finished;
    private long start;
    private long end;

    private Bench(String benchName, String lapName) {
        this.benchName = benchName;
        this.lapName = lapName;
        this.lapTimes = new ArrayList<>(10000);
        this.finished = false;
    }

    public static int createBench(String benchName, String lapName) {
        Bench bench = new Bench(benchName, lapName);
        benches.add(bench);
        return benches.indexOf(bench);
    }

    public static void startBench(int benchIndex) {
        benches.get(benchIndex).begin();
    }

    public static void endBench(int benchIndex) {
        benches.get(benchIndex).end();
    }

    public static void lap(int benchIndex) {
        benches.get(benchIndex).lap();
    }

    public static void printReport(int benchIndex) {
        benches.get(benchIndex).printReport();
    }

    public static void printReports() {
        for (Bench bench : benches) {
            bench.printReport();
        }
    }

    private void begin() {
        if (!finished) {
            start = System.nanoTime();
        }
    }

    private void end() {
        if (!finished) {
            finished = true;
            end = System.nanoTime();
        }
    }

    private void lap() {
        lapTimes.add(System.nanoTime());
    }

    private void printReport() {
        long elapsedNano = getTotalNanoTime();
        long elapsedMilli = elapsedNano / 1000000;
        long lapNano = getAverageLapTime();
        long lapMilli = lapNano / 1000000;
        System.out.printf("\"%s\" elapsed time: %dns (%dms)\n", benchName, elapsedNano, elapsedMilli);
        System.out.printf("\"%s\" average \"%s\" lap time: %dns (%dms)\n", benchName, lapName, lapNano, lapMilli);
    }

    private long getTotalNanoTime() {
        return end - start;
    }

    private long getAverageLapTime() {
        long last = start;
        long totalLapTime = 0;
        for (long lap : lapTimes) {
            totalLapTime += lap - last;
            last = lap;
        }
        return totalLapTime / lapTimes.size();
    }

}
