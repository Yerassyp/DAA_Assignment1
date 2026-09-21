package daa;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class AlgorithmTest {

    private static final Random RANDOM = new Random(42);

    @Test
    public void testSortsCorrectness() {
        for (int i = 0; i < 100; i++) {
            int size = RANDOM.nextInt(1000) + 10; // Random size between 10 and 1009
            int[] original = generateRandomArray(size);

            int[] expected = original.clone();
            Arrays.sort(expected);

            int[] mergeSorted = original.clone();
            MergeSort.sort(mergeSorted, new Metrics());
            assertArrayEquals(expected, mergeSorted, "MergeSort failed on random array " + i);

            int[] quickSorted = original.clone();
            QuickSort.sort(quickSorted, new Metrics());
            assertArrayEquals(expected, quickSorted, "QuickSort failed on random array " + i);
        }
    }

    @Test
    public void testEdgeCases() {
        // 1. Empty array
        int[] empty = new int[0];
        MergeSort.sort(empty, new Metrics());
        assertArrayEquals(new int[0], empty);

        QuickSort.sort(empty, new Metrics());
        assertArrayEquals(new int[0], empty);

        // 2. One element array
        int[] single = {42};
        MergeSort.sort(single, new Metrics());
        assertArrayEquals(new int[]{42}, single);

        QuickSort.sort(single, new Metrics());
        assertArrayEquals(new int[]{42}, single);

        // 3. All elements equal
        int[] equal = {7, 7, 7, 7, 7, 7, 7, 7, 7, 7};
        int[] expectedEqual = equal.clone();

        int[] mergeEqual = equal.clone();
        MergeSort.sort(mergeEqual, new Metrics());
        assertArrayEquals(expectedEqual, mergeEqual);

        int[] quickEqual = equal.clone();
        QuickSort.sort(quickEqual, new Metrics());
        assertArrayEquals(expectedEqual, quickEqual);

        // 4. Already sorted array
        int[] sorted = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] expectedSorted = sorted.clone();

        int[] mergeSorted = sorted.clone();
        MergeSort.sort(mergeSorted, new Metrics());
        assertArrayEquals(expectedSorted, mergeSorted);

        int[] quickSorted = sorted.clone();
        QuickSort.sort(quickSorted, new Metrics());
        assertArrayEquals(expectedSorted, quickSorted);
    }

    @Test
    public void testQuickSortDepth() {
        int n = 100000;
        int[] sortedArray = new int[n];
        for (int i = 0; i < n; i++) {
            sortedArray[i] = i;
        }

        Metrics metrics = new Metrics();
        QuickSort.sort(sortedArray, metrics);

        // maxDepth <= 2 * log2(n)
        double log2n = Math.log(n) / Math.log(2);
        int maxAllowedDepth = (int) Math.ceil(2 * log2n);

        assertTrue(metrics.getMaxDepth() <= maxAllowedDepth,
                "QuickSort recursion depth exceeded limit! Max depth: " + metrics.getMaxDepth() +
                        ", Allowed: " + maxAllowedDepth);
    }

    @Test
    public void testSelectCorrectness() {
        for (int i = 0; i < 100; i++) {
            int size = RANDOM.nextInt(1000) + 10;
            int[] original = generateRandomArray(size);

            int[] sorted = original.clone();
            Arrays.sort(sorted);

            int k = RANDOM.nextInt(size);

            int quickSelectResult = QuickSelect.select(original.clone(), k, new Metrics());
            assertEquals(sorted[k], quickSelectResult, "QuickSelect failed on random array " + i + " for k=" + k);

            int deterministicSelectResult = DeterministicSelect.select(original.clone(), k, new Metrics());
            assertEquals(sorted[k], deterministicSelectResult, "DeterministicSelect failed on random array " + i + " for k=" + k);
        }
    }

    @Test
    public void testSelectEdgeCases() {
        // 1. Empty array should throw exception
        int[] empty = new int[0];
        assertThrows(IllegalArgumentException.class, () -> QuickSelect.select(empty, 0, new Metrics()));
        assertThrows(IllegalArgumentException.class, () -> DeterministicSelect.select(empty, 0, new Metrics()));

        // 2. Invalid k should throw exception
        int[] arr = {1, 2, 3};
        assertThrows(IllegalArgumentException.class, () -> QuickSelect.select(arr, -1, new Metrics()));
        assertThrows(IllegalArgumentException.class, () -> QuickSelect.select(arr, 3, new Metrics()));
        assertThrows(IllegalArgumentException.class, () -> DeterministicSelect.select(arr, -1, new Metrics()));
        assertThrows(IllegalArgumentException.class, () -> DeterministicSelect.select(arr, 3, new Metrics()));

        // 3. One element array
        int[] single = {42};
        assertEquals(42, QuickSelect.select(single.clone(), 0, new Metrics()));
        assertEquals(42, DeterministicSelect.select(single.clone(), 0, new Metrics()));

        // 4. All elements equal
        int[] equal = {5, 5, 5, 5, 5};
        assertEquals(5, QuickSelect.select(equal.clone(), 2, new Metrics()));
        assertEquals(5, DeterministicSelect.select(equal.clone(), 2, new Metrics()));

        // 5. Already sorted array
        int[] sorted = {10, 20, 30, 40, 50};
        assertEquals(40, QuickSelect.select(sorted.clone(), 3, new Metrics()));
        assertEquals(40, DeterministicSelect.select(sorted.clone(), 3, new Metrics()));
    }

    private int[] generateRandomArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = RANDOM.nextInt(10000) - 5000; // Random values between -5000 and 4999
        }
        return arr;
    }
}