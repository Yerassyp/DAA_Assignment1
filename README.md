# Divide and Conquer & Asymptotic Notations

This project implement and analyze Divide-and-Conquer algorithms: MergeSort, QuickSort, QuickSelect and Deterministic Select (Median-of-Medians) in Java 17.

## Project Structure
* `src/main/java/daa/` - Have the source code for all algorithm and metrics tracker.
* `src/test/java/daa/` - Have JUnit 5 tests for correctness, edge cases and recursion depth limit.
* `plots/` - Folder where the generated benchmark plots is saved.
* `results.csv` - The output file from benchmark runner.
* `pom.xml` - Maven config file.

## Prerequisites
* Java 17 or higher
* Maven 3.6+
* Python 3.8+ (with `pandas` and `matplotlib` installed)

## How to build and run test
For compile project and run JUnit 5 tests, you need to write this command in root folder:
```bash
mvn clean test
```
## How to run benchmark
To run benchmark and make `results.csv` file, use this maven command:
```bash
mvn exec:java -Dexec.mainClass="daa.BenchmarkRunner"
```
It will run algorithms on array with size 1000, 10000, 100000 and 1000000 on three input type (random, sorted, duplicates). It do 5 runs for each and take median time for fix JVM warm-up.

## How to make plots
After you run benchmark, make plots with Python script:
```bash
pip install pandas matplotlib numpy
python plots/plot_results.py
```

The script will create three PNG files in `plots/` folder:
1. `time_vs_n.png`
2. `depth_vs_n.png`
3. `ratio_vs_n.png`