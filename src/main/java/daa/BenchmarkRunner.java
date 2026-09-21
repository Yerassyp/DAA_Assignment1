package daa;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class BenchmarkRunner {

    private static final String[] ALGORITHMS = {"MergeSort", "QuickSort", "QuickSelect", "DeterministicSelect"};
    private static final String[] INPUT_TYPES = {"random", "sorted", "duplicates"};
    private static final int[] SIZES = {1000, 10000, 100000, 1000000};
    private static final int RUNS = 5;

    static class RunResult implements Comparable<RunResult> {
        long timeMs;
        long comparisons;
        int maxDepth;

        RunResult(long timeMs, long comparisons, int maxDepth) {
            this.timeMs = timeMs;
            this.comparisons = comparisons;
            this.maxDepth = maxDepth;
        }

        @Override
        public int compareTo(RunResult o) {
            return Long.compare(this.timeMs, o.timeMs);
        }
    }

    public static void main(String[] args) {
        Random random = new Random(42);

        try (PrintWriter writer = new PrintWriter(new FileWriter("results.csv"))) {
            writer.println("algorithm,input,n,time_ms,comparisons,max_depth");

            for (String algo : ALGORITHMS) {
                for (String inputType : INPUT_TYPES) {
                    for (int n : SIZES) {
                        System.out.printf("Running %s on %s array of size %d...%n", algo, inputType, n);

                        RunResult[] results = new RunResult[RUNS];

                        for (int i = 0; i < RUNS; i++) {
                            int[] arr = generateArray(n, inputType, random);
                            Metrics metrics = new Metrics();

                            System.gc();

                            metrics.startTimer();
                            switch (algo) {
                                case "MergeSort":
                                    MergeSort.sort(arr, metrics);
                                    break;
                                case "QuickSort":
                                    QuickSort.sort(arr, metrics);
                                    break;
                                case "QuickSelect":
                                    QuickSelect.select(arr, n / 2, metrics);
                                    break;
                                case "DeterministicSelect":
                                    DeterministicSelect.select(arr, n / 2, metrics);
                                    break;
                            }
                            metrics.stopTimer();

                            results[i] = new RunResult(metrics.getTimeMs(), metrics.getComparisons(), metrics.getMaxDepth());
                        }

                        Arrays.sort(results);
                        RunResult medianResult = results[RUNS / 2];

                        writer.printf("%s,%s,%d,%d,%d,%d%n",
                                algo, inputType, n,
                                medianResult.timeMs, medianResult.comparisons, medianResult.maxDepth);
                    }
                }
            }
            System.out.println("Benchmark finished. Results saved to results.csv");
        } catch (IOException e) {
            System.err.println("Error writing to results.csv: " + e.getMessage());
        }
    }

    private static int[] generateArray(int n, String type, Random random) {
        int[] a = new int[n];
        switch (type) {
            case "random":
                for (int i = 0; i < n; i++) {
                    a[i] = random.nextInt();
                }
                break;
            case "sorted":
                for (int i = 0; i < n; i++) {
                    a[i] = i;
                }
                break;
            case "duplicates":
                for (int i = 0; i < n; i++) {
                    a[i] = random.nextInt(10);
                }
                break;
        }
        return a;
    }
}