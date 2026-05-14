# 🔢 Algorithms & Data Structures (AuD) - SoSe 26

This repository contains my solutions for the AuD course at **TU Darmstadt**.
It tracks my journey through classic and hybrid sorting algorithms, divide & conquer strategies, and non-comparison-based sorting.

## 📦 Assignments Overview

| Assignment | Topic | Description |
|---|---|---|
| **P1 - Blatt 03** | Sorting Algorithms | Implemented BubbleSort and a bidirectional variant (WeirdlySort / Cocktail Shaker Sort). Built a HybridSort combining QuickSort with a MergeSort fallback at a configurable recursion depth k, and an optimizer to find the best k. Also implemented RadixSort for both rune-encoded strings and integers. |

## 🛠️ Core Concepts

- **Divide & Conquer:** MergeSort, QuickSort (Hoare partitioning)
- **Hybrid Algorithms:** QuickSort with MergeSort fallback based on recursion depth
- **Non-comparison Sorting:** RadixSort with custom alphabet (rune order)
- **Algorithm Analysis:** Master Theorem, stability of sorting algorithms

## ⚙️ How to Run

```bash
./gradlew run                  # Run main
./gradlew test                 # Run tests
./gradlew mainBuildSubmission  # Build submission jar
```