package daa;

public class MergeSort {
    private static final int CUTOFF = 15;

    public static void sort(int[] a, Metrics metrics) {
        if (a == null || a.length <= 1) {
            return;
        }
        int[] helper = new int[a.length];
        metrics.enterRecursion();
        sort(a, helper, 0, a.length - 1, metrics);
        metrics.exitRecursion();
    }

    private static void sort(int[] a, int[] helper, int left, int right, Metrics metrics) {
        if (right - left <= CUTOFF) {
            InsertionSort.sort(a, left, right, metrics);
            return;
        }

        int mid = left + (right - left) / 2;

        metrics.enterRecursion();
        sort(a, helper, left, mid, metrics);
        metrics.exitRecursion();

        metrics.enterRecursion();
        sort(a, helper, mid + 1, right, metrics);
        metrics.exitRecursion();

        merge(a, helper, left, mid, right, metrics);
    }

    private static void merge(int[] a, int[] helper, int left, int mid, int right, Metrics metrics) {
        System.arraycopy(a, left, helper, left, right - left + 1);

        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            metrics.addComparison();
            if (helper[i] <= helper[j]) {
                a[k++] = helper[i++];
            } else {
                a[k++] = helper[j++];
            }
        }

        while (i <= mid) {
            a[k++] = helper[i++];
        }
    }
}