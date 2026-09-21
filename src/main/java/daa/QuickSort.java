package daa;

import java.util.Random;

public class QuickSort {
    private static final Random RANDOM = new Random();

    public static void sort(int[] a, Metrics metrics) {
        if (a == null || a.length <= 1) {
            return;
        }
        metrics.enterRecursion();
        sort(a, 0, a.length - 1, metrics);
        metrics.exitRecursion();
    }

    private static void sort(int[] a, int left, int right, Metrics metrics) {
        while (left < right) {
            int pivotIndex = left + RANDOM.nextInt(right - left + 1);
            int pivot = a[pivotIndex];

            int lt = left;
            int gt = right;
            int i = left;

            while (i <= gt) {
                metrics.addComparison();
                if (a[i] < pivot) {
                    swap(a, i++, lt++);
                } else {
                    metrics.addComparison();
                    if (a[i] > pivot) {
                        swap(a, i, gt--);
                    } else {
                        i++;
                    }
                }
            }

            int leftSize = lt - left;
            int rightSize = right - gt;

            if (leftSize < rightSize) {
                metrics.enterRecursion();
                sort(a, left, lt - 1, metrics);
                metrics.exitRecursion();
                left = gt + 1;
            } else {
                metrics.enterRecursion();
                sort(a, gt + 1, right, metrics);
                metrics.exitRecursion();
                right = lt - 1;
            }
        }
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}