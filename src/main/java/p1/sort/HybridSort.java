package p1.sort;

import p1.comparator.CountingComparator;

import java.util.Comparator;

/**
 * A hybrid sorting algorithm. It uses a combination of quickSort and mergeSort.
 * <p>
 * quickSort is used until the recursion depth reaches {@link #k}.
 * <p>
 * mergeSort is used as a fallback once the maximum recursion depth is reached.
 *
 * @param <T> the type of the elements to be sorted.
 *
 * @see Sort
 */
public class HybridSort<T> implements Sort<T> {

    /**
     * The maximum recursion depth for quickSort before switching to mergeSort.
     */
    private int k;

    /**
     * The comparator used for comparing the sorted elements.
     */
    private final CountingComparator<T> comparator;

    /**
     * Creates a new {@link HybridSort} instance.
     *
     * @param k          the maximum recursion depth for quickSort.
     * @param comparator the comparator used for comparing the sorted elements.
     */
    public HybridSort(int k, Comparator<T> comparator) {
        this.k = k;
        this.comparator = new CountingComparator<>(comparator);
    }

    @Override
    public void sort(SortList<T> sortList) {
        comparator.reset();
        quickSort(sortList, 0, sortList.getSize() - 1);
    }

    @Override
    public int getComparisonsCount() {
        return comparator.getComparisonsCount();
    }

    /**
     * Returns the maximum recursion depth for quickSort.
     * @return the maximum recursion depth for quickSort.
     */
    public int getK() {
        return k;
    }

    /**
     * Sets the maximum recursion depth for quickSort.
     * @param k the new maximum recursion depth.
     */
    public void setK(int k) {
        this.k = k;
    }

    /**
     * Sorts the given {@link SortList} using the quickSort algorithm.
     * It will only consider the elements between the given left and right indices (both inclusive).
     * Elements with indices less than left or greater than right will not be altered.
     * <p>
     * This method starts the recursion at depth 0.
     * @param sortList the {@link SortList} to be sorted.
     * @param left The leftmost index of the list to be sorted.
     * @param right The rightmost index of the list to be sorted.
     */
    public void quickSort(SortList<T> sortList, int left, int right) {
        quickSort(sortList, left, right, 0);
    }

    /**
     * Sorts the given {@link SortList} using quickSort until the given recursion depth reaches {@link #k}.
     * If the depth limit is reached, this method switches to {@link #mergeSort(SortList, int, int)} for the current
     * subrange and does not continue the quickSort recursion in that branch.
     *
     * @param sortList the {@link SortList} to be sorted.
     * @param left The leftmost index of the list to be sorted.
     * @param right The rightmost index of the list to be sorted.
     * @param depth The current recursion depth.
     */
    public void quickSort(SortList<T> sortList, int left, int right, int depth) {
        if (left < right) {
            if (depth >= k) {
                mergeSort(sortList, left, right);
            } else {
                int p = partition(sortList, left, right);
                quickSort(sortList, left, p , depth + 1);
                quickSort(sortList, p + 1, right, depth + 1);
            }
        }
    }

    /**
     * Partitions the given {@link SortList} between the given left and right indices (both inclusive).
     * Elements with indices less than left or greater than right will not be altered.
     * <p>
     * The pivot is the element at the given left index.
     *
     * @param sortList the {@link SortList} to be partitioned.
     * @param left The leftmost index of the list to be partitioned.
     * @param right The rightmost index of the list to be partitioned.
     * @return An index between left and right (both inclusive) such that all elements to the left or at the index are less than or equal to the pivot,
     * and all elements to the right of the index are greater than or equal to the pivot.
     */
    public int partition(SortList<T> sortList, int left, int right) {
        T pivot = sortList.get(left);
        int p = left - 1;
        int q = right + 1;

        while (p < q) {
            do { p++; } while (comparator.compare(sortList.get(p), pivot) < 0);
            do { q--; } while (comparator.compare(sortList.get(q), pivot) > 0);
            if (p < q) {
                T tmp = sortList.get(p);
                sortList.set(p, sortList.get(q));
                sortList.set(q, tmp);
            }
        }
        return q;
    }

    /**
     * Sorts the given {@link SortList} using the mergeSort algorithm.
     * It will only consider the elements between the given left and right indices (both inclusive).
     * Elements with indices less than left or greater than right will not be altered.
     *
     * @param sortList the {@link SortList} to be sorted.
     * @param left The leftmost index of the list to be sorted.
     * @param right The rightmost index of the list to be sorted.
     */
    public void mergeSort(SortList<T> sortList, int left, int right) {
        if ( left < right) {
            int middle = (left + right) / 2;
            mergeSort(sortList, left, middle);
            mergeSort(sortList, middle + 1, right);
            merge(sortList, left, middle, right);
        }
    }

    /**
     * Merges two sorted sublists in the given {@link SortList}.
     * The left sublist ranges from {@code left} to {@code middle} (both inclusive), and the right sublist ranges from
     * {@code middle + 1} to {@code right} (both inclusive).
     *
     * @param sortList the {@link SortList} containing the sorted sublists.
     * @param left The leftmost index of the first sublist.
     * @param middle The last index of the first sublist.
     * @param right The rightmost index of the second sublist.
     */
    public void merge(SortList<T> sortList, int left, int middle, int right) {
        T[] temp = (T[]) new Object[right - left + 1];

        int p = left;
        int q = middle + 1;

        for (int i = 0; i <= right - left; i++) {
            if (q > right || (p <= middle && comparator.compare(sortList.get(p), sortList.get(q)) <= 0)) {
                temp[i] = sortList.get(p);
                p++;
            } else {
                temp[i] = sortList.get(q);
                q++;
            }
        }

        for (int i = 0; i <= right - left; i++) {
            sortList.set(i + left, temp[i]);
        }
    }
}
