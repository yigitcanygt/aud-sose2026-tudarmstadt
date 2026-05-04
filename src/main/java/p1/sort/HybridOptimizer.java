package p1.sort;

/**
 * Optimizes the {@link HybridSort} by trying to find the k-value with the lowest number of read, write and comparison operations.
 */
public class HybridOptimizer {

    /**
     * Optimizes the {@link HybridSort} by trying to find the k-value with the lowest number of read, write and comparison operations.
     * The method tries all k-values from 0 to ceil(log2(n)) + 4, where n is the length of the given array.
     * It stops once it finds the first local minimum. If several consecutive k-values have the same minimum cost,
     * the last index of this plateau is returned.
     * <p>
     * The cost for one k-value is computed as
     * {@code sortList.getReadCount() + sortList.getWriteCount() + hybridSort.getComparisonsCount()}.
     *
     * @param hybridSort the {@link HybridSort} to optimize.
     * @param array the array to sort.
     * @return the k-value of the first local minimum.
     * @param <T> the type of the elements to be sorted.
     */
    public static <T> int optimize(HybridSort<T> hybridSort, T[] array) {
        throw new UnsupportedOperationException("Not implemented yet"); //TODO H2 c): remove if implemented
    }

}
