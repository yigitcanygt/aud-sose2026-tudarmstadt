package p1;

import p1.comparator.CountingComparator;
import p1.potion.Potion;
import p1.potion.PotionGenerator;
import p1.sort.*;
import p1.sort.radix.IntegerIndexExtractor;
import p1.sort.radix.RadixSort;
import p1.sort.radix.RuneIndexExtractor;

import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * Main entry point in executing the program.
 */
public class Main {

    private static final Random random = new Random();

    /**
     * Main entry point in executing the program.
     *
     * @param args program arguments, currently ignored
     */
    public static void main(String[] args) {

        int[] sizes = {100, 1000, 10000};// Verschiedene Eingabegrößen
        CountingComparator<Potion> countingComparator = new CountingComparator<>(Comparator.comparingInt(Potion::getStrength));
        final CountingComparator<Potion> comparator = new CountingComparator<>(countingComparator);

        SortingAlgorithms<Potion> sorter = new SortingAlgorithms<>(comparator);
        PotionGenerator generator = new PotionGenerator();

        for (int size : sizes) {
            System.out.println("\n===== Sortiervergleich für Listengröße: " + size + " =====");

            ArraySortList<Potion> original = generator.generateRandomPotionList(size);

            // BubbleSort
            ArraySortList<Potion> list1 = cloneList(original);
            long start1 = System.nanoTime();
            sorter.bubbleSort(list1);
            long end1 = System.nanoTime();
            System.out.printf("BubbleSort dauerte: %.2f ms\n", (end1 - start1) / 1e6);

            // WeirdlySort
            ArraySortList<Potion> list2 = cloneList(original);
            long start2 = System.nanoTime();
            sorter.weirdlySort(list2);
            long end2 = System.nanoTime();
            System.out.printf("WeirdlySort dauerte: %.2f ms\n", (end2 - start2) / 1e6);

            // MagicSort 
            ArraySortList<Potion> list3 = cloneList(original);
            long start3 = System.nanoTime();
            sorter.magicSort(list3, 0, list3.getSize() - 1);
            long end3 = System.nanoTime();
            System.out.printf("MagicSort dauerte: %.2f ms\n", (end3 - start3) / 1e6);
        }

        hybridSort();

        runeSortDemo(List.of("DAY", "FHE", "SHY", "AHS", "FFF"));

        integerSortDemo(List.of(123, 7, 9400, 25, 1000));
    }

    private static ArraySortList<Potion> cloneList(ArraySortList<Potion> original) {
        ArraySortList<Potion> copy = new ArraySortList<>(original.getSize());
        for (int i = 0; i < original.getSize(); i++) {
            copy.set(i, original.get(i));
        }
        return copy;
    }

    private static void hybridSort() {

        SortList<Integer> list = createRandomList();

        HybridSort<Integer> hybridSort = new HybridSort<>(1, Integer::compareTo);

        int k = HybridOptimizer.optimize(hybridSort, list.toArray());
        System.out.println("first local minimum: " + k);

        hybridSort.setK(k);
        hybridSort.sort(list);

        System.out.println("hybridSort comparisons: " + hybridSort.getComparisonsCount());
    }

    private static void runeSortDemo(List<String> runes) {
        SortList<String> list = new ArraySortList<>(runes);
        int maxInputLength = runes.stream().mapToInt(String::length).max().orElse(0);
        RadixSort<String> radixSort = new RadixSort<>(7, new RuneIndexExtractor());
        radixSort.setMaxInputLength(maxInputLength);
        radixSort.sort(list);
        System.out.println("runeSortDemo: " + list);
    }

    private static void integerSortDemo(List<Integer> numbers) {
        SortList<Integer> list = new ArraySortList<>(numbers);
        int maxInputLength = numbers.stream().mapToInt(n -> String.valueOf(n).length()).max().orElse(0);
        RadixSort<Integer> radixSort = new RadixSort<>(10, new IntegerIndexExtractor(10));
        radixSort.setMaxInputLength(maxInputLength);
        radixSort.sort(list);
        System.out.println("integerSortDemo: " + list);
    }

    private static SortList<Integer> createRandomList() {
        int size = 100;
        Integer[] array = new Integer[size];

        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(size);
        }

        return new ArraySortList<>(array);
    }
}