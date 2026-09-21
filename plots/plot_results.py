import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
import os

def main():
    # Ensure the plots directory exists
    os.makedirs('plots', exist_ok=True)

    # Load the benchmark results
    try:
        df = pd.read_csv('results.csv')
    except FileNotFoundError:
        print("Error: results.csv not found. Please run the BenchmarkRunner first.")
        return

    # Define algorithms and input types
    algorithms = df['algorithm'].unique()
    input_types = df['input'].unique()

    # 1. Plot Time vs N
    plt.figure(figsize=(12, 8))
    for algo in algorithms:
        for inp in input_types:
            subset = df[(df['algorithm'] == algo) & (df['input'] == inp)]
            if not subset.empty:
                plt.plot(subset['n'], subset['time_ms'], marker='o', label=f"{algo} ({inp})")

    plt.xscale('log')
    plt.yscale('log')
    plt.xlabel('Array Size (n)')
    plt.ylabel('Time (ms)')
    plt.title('Time vs Array Size')
    plt.legend(bbox_to_anchor=(1.05, 1), loc='upper left')
    plt.grid(True, which="both", ls="--")
    plt.tight_layout()
    plt.savefig('plots/time_vs_n.png')
    plt.close()

    # 2. Plot Max Recursion Depth vs N
    plt.figure(figsize=(12, 8))
    for algo in algorithms:
        for inp in input_types:
            subset = df[(df['algorithm'] == algo) & (df['input'] == inp)]
            if not subset.empty:
                plt.plot(subset['n'], subset['max_depth'], marker='o', label=f"{algo} ({inp})")

    plt.xscale('log')
    plt.xlabel('Array Size (n)')
    plt.ylabel('Max Recursion Depth')
    plt.title('Max Recursion Depth vs Array Size')
    plt.legend(bbox_to_anchor=(1.05, 1), loc='upper left')
    plt.grid(True, which="both", ls="--")
    plt.tight_layout()
    plt.savefig('plots/depth_vs_n.png')
    plt.close()

    # 3. Plot Ratio vs N
    plt.figure(figsize=(12, 8))
    for algo in algorithms:
        for inp in input_types:
            subset = df[(df['algorithm'] == algo) & (df['input'] == inp)].copy()
            if subset.empty:
                continue

            # Calculate ratio based on algorithm type
            if algo in ['MergeSort', 'QuickSort']:
                # Ratio: comparisons / (n * log2(n))
                subset['ratio'] = subset['comparisons'] / (subset['n'] * np.log2(subset['n']))
            else:
                # Ratio: comparisons / n for QuickSelect and DeterministicSelect
                subset['ratio'] = subset['comparisons'] / subset['n']

            plt.plot(subset['n'], subset['ratio'], marker='o', label=f"{algo} ({inp})")

    plt.xscale('log')
    plt.xlabel('Array Size (n)')
    plt.ylabel('Ratio (Comparisons / Expected Growth)')
    plt.title('Asymptotic Ratio vs Array Size')
    plt.legend(bbox_to_anchor=(1.05, 1), loc='upper left')
    plt.grid(True, which="both", ls="--")
    plt.tight_layout()
    plt.savefig('plots/ratio_vs_n.png')
    plt.close()

    print("Plots successfully generated and saved in the 'plots/' directory.")

if __name__ == "__main__":
    main()