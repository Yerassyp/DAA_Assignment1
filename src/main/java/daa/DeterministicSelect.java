package daa;

public class DeterministicSelect {

    public static int select(int[] a, int k, Metrics metrics) {
        if (a == null || a.length == 0) {
            throw new IllegalArgumentException("Array is empty or null");
        }
        if (k < 0 || k >= a.length) {
            throw new IllegalArgumentException("k is out of bounds");
        }

        metrics.enterRecursion();
        int result = kthSmallest(a, 0, a.length - 1, k, metrics);
        metrics.exitRecursion();

        return result;
    }

    private static int kthSmallest(int[] a, int left, int right, int k, Metrics metrics) {
        if (left == right) {
            return a[left];
        }

        int pivot = medianOfMedians(a, left, right, metrics);

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
            metrics.enterRecursion();
            int res = kthSmallest(a, left, lt - 1, k, metrics);
            metrics.exitRecursion();
            return res;
        } else {
            metrics.enterRecursion();
            int res = kthSmallest(a, gt + 1, right, k, metrics);
            metrics.exitRecursion();
            return res;
        }
    }

    private static int medianOfMedians(int[] a, int left, int right, Metrics metrics) {
        int n = right - left + 1;
        if (n <= 5) {
            InsertionSort.sort(a, left, right, metrics);
            return a[left + n / 2];
        }

        int numMedians = 0;
        for (int i = left; i <= right; i += 5) {
            int subRight = Math.min(i + 4, right);
            InsertionSort.sort(a, i, subRight, metrics);
            int medianIndex = i + (subRight - i) / 2;
            swap(a, left + numMedians, medianIndex);
            numMedians++;
        }

        metrics.enterRecursion();
        int pivot = kthSmallest(a, left, left + numMedians - 1, left + numMedians / 2, metrics);
        metrics.exitRecursion();

        return pivot;
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}