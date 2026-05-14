package p1.sort;

import p1.comparator.CountingComparator;

/**
 * Provides sorting algorithms using a {@link CountingComparator}.
 * Supported algorithms:
 * <ul>
 *     <li>Bubble-Sort - Simple comparison and swapping of adjacent elements.</li>
 *     <li>Weirdly-Sort - A magical algorithm involving pairwise swaps and backward traversal.</li>
 *     <li>Magic-Sort - Recursive Merge-Sort for efficient sorting.</li>
 * </ul>
 *
 * @param <Potion> the type of elements to sort
 */
public class SortingAlgorithms<Potion> {
    
    /**
     * The comparator used for comparing the sorted elements.
     */
    private final CountingComparator<Potion> comparator;

    public SortingAlgorithms(CountingComparator<Potion> comparator) {
        this.comparator = comparator;
    }

    /**
     * Sorts the given {@link SortList} using the bubbleSort algorithm
     *
     * @param potions the {@link SortList} to be sorted.
     *
     */
    public void bubbleSort(SortList<Potion> potions){
        int n = potions.getSize();
        for (int i = 1; i <= n - 1; i++) {
            for (int j = 0; j <= n - i - 1; j++) {
                if (comparator.compare(potions.get(j), potions.get(j + 1)) > 0) {
                    Potion tmp =  potions.get(j);
                    potions.set(j, potions.get(j + 1));
                    potions.set(j + 1, tmp);
                }
            }
        }
    }

    /**
     * Sorts the given {@link SortList} using the algorithm from the magic book
     * <p>
     *     - Start at the beginning of the list.
     *     - Forward pass (left to right):
     *         -- Compare each pair of neighboring potions in the unsorted section.
     *         -- If they are in the wrong order, swap them.
     *     - If no potions are swapped, the algorithm exits immediately
     *     - Backward pass (right to left):
     *         -- Compare each pair of neighboring potions, moving backwards.
     *         -- If they are in the wrong order, swap them.
     *     - Narrow the unsorted boundaries, repeat until all potions are in order and no potions are swapped.
     * <p>
     * @param potions the {@link SortList} to be sorted
     */
    public void weirdlySort(SortList<Potion> potions){
        int left = 0;
        int right = potions.getSize() - 1;

        while (left < right) {
            boolean swapped = false;

            for (int j = left; j <= right - 1; j++) {
                if (comparator.compare(potions.get(j), potions.get(j + 1)) > 0) {
                    Potion tmp =  potions.get(j);
                    potions.set(j, potions.get(j + 1));
                    potions.set(j + 1, tmp);
                    swapped = true;
                }
            }
            right--;
            if (!swapped) break;

            swapped = false;
            for (int j = right - 1; j >= left; j--) {
                if (comparator.compare(potions.get(j), potions.get(j + 1)) > 0) {
                    Potion tmp =  potions.get(j);
                    potions.set(j, potions.get(j + 1));
                    potions.set(j + 1, tmp);
                    swapped = true;
                }
            }
            left++;
            if (!swapped) break;
        }
    }

    /**
     * Sorts the given {@link SortList} using the magicSort algorithm.
     * It will only consider the elements between the given left and right indices (both inclusive).
     * Elements with indices less than left or greater than right will not be altered.
     * <p>
     *
     * @param potions the {@link SortList} to be sorted.
     * @param wand1 The leftmost index of the list to be sorted. (inclusive)
     * @param wand2 The rightmost index of the list to be sorted. (inclusive)
     */

    public void magicSort(SortList<Potion> potions, int wand1, int wand2){
        if(wand1 < wand2) {
            for (int i = wand1 + 1; i < wand2 + 1; i++) {
                Potion key = potions.get(i);
                int j = i - 1;
                while (j >= 0 && comparator.compare(potions.get(j), key) > 0) {
                    potions.set(j + 1, potions.get(j));
                    j--;
                }

                potions.set(j + 1, key);
            }
        }
    }


}