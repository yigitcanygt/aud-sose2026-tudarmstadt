\# 🔢 Algorithms \& Data Structures (AuD) - SoSe 26



This repository contains my solutions for the AuD course at \*\*TU Darmstadt\*\*. It tracks my journey through classic and hybrid sorting algorithms, divide \& conquer strategies, non-comparison-based sorting, and advanced self-balancing search trees.



\## 📦 Assignments Overview



| Assignment | Topic | Description |

| :--- | :--- | :--- |

| \*\*P1 - Blatt 03\*\* | Sorting Algorithms | Implemented BubbleSort and a bidirectional variant (WeirdlySort / Cocktail Shaker Sort). Built a HybridSort combining QuickSort with a MergeSort fallback at a configurable recursion depth k, and an optimizer to find the best k. Also implemented RadixSort for both rune-encoded strings and integers. |

| \*\*P2 - Blatt 07\*\* | Search Trees | Implemented tree traversals (In-order, Pre-order) and binary tree reconstruction from traversal lists. Built a \*\*Splay Tree\*\* with self-balancing rotations (zig, zig-zig, zig-zag) for insert, find, and remove operations. Implemented \*\*Red-Black Tree\*\* features like black-height calculation and tree joining. Developed \*\*AVL Tree\*\* utilities to convert Red-Black Trees to AVL Trees and merge multiple AVL Trees. |



\## 🛠️ Core Concepts



\* \*\*Divide \& Conquer:\*\* MergeSort, QuickSort (Hoare partitioning)

\* \*\*Hybrid Algorithms:\*\* QuickSort with MergeSort fallback based on recursion depth

\* \*\*Non-comparison Sorting:\*\* RadixSort with custom alphabet (rune order)

\* \*\*Tree Traversals \& Reconstruction:\*\* Rebuilding unique binary trees using Pre-order and In-order sequences.

\* \*\*Self-Balancing Search Trees:\*\* 

&#x20; \* \*\*Splay Trees:\*\* Amortized access optimization and splay rotations.

&#x20; \* \*\*Red-Black Trees:\*\* Color invariants, black-height calculations, and joining trees with Sentinel node edge-case handling.

&#x20; \* \*\*AVL Trees:\*\* Height balancing properties and in-order list merging.



\## ⚙️ How to Run



```bash

./gradlew run                  # Run main (including Binary Tree GUI)

./gradlew test                 # Run tests

./gradlew mainBuildSubmission  # Build submission jar

