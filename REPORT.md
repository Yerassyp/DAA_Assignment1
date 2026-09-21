# Design and Analysis of Algorithms - Assignment 1 Report

* **Student:** Yerassyl Irangait (Group SE-2523)
* **GitHub Repository:** https://github.com/Yerassyp/DAA_Assignment1
* **Target Branch:** `main`
* **Release Tag:** `v1.0`

---

## 1. Asymptotic Bounds

| Algorithm | Best Case | Average Case | Worst Case | Reason |
| :--- | :--- | :--- | :--- | :--- |
| **MergeSort** | $\Theta(n \log n)$ | $\Theta(n \log n)$ | $\Theta(n \log n)$ | Array always divide in two equal halfs, it not depend on input data. |
| **QuickSort** | $\Omega(n \log n)$ | $\Theta(n \log n)$ | $O(n^2)$ | Best/Avg is when random pivot balance the parts. Worst is if pivot always minimum or maximum element. |
| **QuickSelect** | $\Omega(n)$ | $\Theta(n)$ | $O(n^2)$ | Best/Avg is when pivot drop constant part of array. Worst is if partition only make size smaller by 1. |
| **InsertionSort** | $\Omega(n)$ | $\Theta(n^2)$ | $O(n^2)$ | Best is on already sorted array (loop break fast). Avg/Worst need to shift elements every time. |

## 2. Recurrences and Master Theorem

### MergeSort
* **Recurrence:** $T(n) = 2T(n/2) + O(n)$
* **Parameters:** $a = 2$, $b = 2$, $f(n) = O(n)$
* **Master Theorem Case:** Case 2, because $f(n) = \Theta(n^{\log_2 2}) = \Theta(n)$.
* **Result:** $T(n) = \Theta(n \log n)$.

### QuickSort (Average Case)
* **Recurrence:** If we have balanced split on average, $T(n) = 2T(n/2) + O(n)$.
* **Parameters:** $a = 2$, $b = 2$, $f(n) = O(n)$.
* **Master Theorem Case:** Case 2.
* **Result:** $T(n) = \Theta(n \log n)$.
* **Explanation:** Random pivot give us good split on average (like 1/4 and 3/4). The tree depth is logarithmic and work on each level is linear, so it give $O(n \log n)$ average time.

### QuickSelect (Average Case)
* **Recurrence:** If we have balanced split and go only in one half, $T(n) = 1T(n/2) + O(n)$.
* **Parameters:** $a = 1$, $b = 2$, $f(n) = O(n)$.
* **Master Theorem Case:** Case 3, because $f(n) = \Omega(n^{\log_2 1 + \epsilon})$ for $\epsilon = 1$, and $a f(n/b) \le c f(n)$ is true ($1 \cdot (n/2) \le c \cdot n$ for $c = 1/2 < 1$).
* **Result:** $T(n) = \Theta(n)$.

## 3. $\Theta$ Check and Ratio Analysis
When we plot ratio of comparisons to expected growth ($n \log_2 n$ for sorts, $n$ for select), we can see that lines become flat when $n$ is growing. This confirm the $\Theta$ bounds.
From definition of $\Theta$: $c_1 \cdot g(n) \le f(n) \le c_2 \cdot g(n)$ for all $n \ge n_0$.
In ratio plots for QuickSort on random data, ratio stop around 1.3 to 1.5. So, rough values is $c_1 \approx 1.0$, $c_2 \approx 2.0$, and $n_0 \approx 10000$. For MergeSort, ratio is very close to 1.0, so $c_1 \approx 0.8$, $c_2 \approx 1.2$, and $n_0 \approx 1000$.

## 4. Bonus Task A: Deterministic Select (Median of Medians)
Deterministic Select algorithm have $O(n)$ worst-case time because it choose pivot very careful using Median-of-Medians. But benchmark results shows that QuickSelect is much more faster on average. Deterministic Select is slow because it have big constant factor. It need to divide array to groups of 5, sort them with InsertionSort and do recursive call to find pivot. QuickSelect random pivot is very cheap and give $O(n)$ on average with small constant.

## 5. Discussion
The practical measurements is very close to theory, but some real-world things make differences. JVM warm-up make first runs very slow because of JIT compilation, so we take median of 5 runs to get good metrics. Garbage Collector (GC) can sometimes stop program and make time bigger, but our MergeSort use only one reusable buffer to help with this. CPU cache like when we read memory in order. QuickSort is very good for cache because it do partition in-place, so it is faster than MergeSort in practice. Also, cutoff size 15 for MergeSort help to not do many recursive calls on small arrays, it use InsertionSort which is fast for small size.
