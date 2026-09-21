package daa;

public class Metrics {
    private long comparisons;
    private int maxDepth;
    private int currentDepth;
    private long startTime;
    private long timeMs;

    public void reset() {
        this.comparisons = 0;
        this.maxDepth = 0;
        this.currentDepth = 0;
        this.startTime = 0;
        this.timeMs = 0;
    }

    public void addComparison() {
        this.comparisons++;
    }

    public void enterRecursion() {
        this.currentDepth++;
        if (this.currentDepth > this.maxDepth) {
            this.maxDepth = this.currentDepth;
        }
    }

    public void exitRecursion() {
        this.currentDepth--;
    }

    public void startTimer() {
        this.startTime = System.nanoTime();
    }

    public void stopTimer() {
        this.timeMs = (System.nanoTime() - this.startTime) / 1_000_000;
    }

    public long getComparisons() {
        return comparisons;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public long getTimeMs() {
        return timeMs;
    }
}