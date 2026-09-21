package daa;

import java.util.Random;

public class QuickSelect {
    private static final Random RANDOM = new Random();

    public static int select(int[] a, int k, Metrics metrics) {
        if (a == null || a.length == 0) {
            throw new IllegalArgumentException("Array is empty or null");
        }
        if (k < 0 || k >= a.length) {
            throw new IllegalArgumentException("k is out of bounds");
        }

        int left = 0;
        int right = a.length - 1;

        while (left <= right) {
            if (left == right) {
                return a[left];
            }

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

            if (k >= lt && k <= gt) {
                return a[k];
            } else if (k < lt) {
                right = lt - 1;
            } else {
                left = gt + 1;
            }
        }

        return -1;
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}