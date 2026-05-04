package p1;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junitpioneer.jupiter.json.JsonClasspathSource;
import org.junitpioneer.jupiter.json.Property;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import p1.sort.ArraySortList;
import p1.sort.SortList;
import p1.sort.SortingAlgorithms;
import p1.comparator.CountingComparator;
import p1.transformers.MethodInterceptor;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;
import static p1.OperationSortList.Operation.toHTMLList;

@TestForSubmission
public class WeirdlySortTest {

    private static final Comparator<Integer> COMPARATOR = Comparator.naturalOrder();
    private static CountingComparator<Integer> countingComparator;
    private static SortingAlgorithms<Integer> sortingAlgorithms;

    @BeforeEach
    public void setup() {
        MethodInterceptor.reset();
        countingComparator = new CountingComparator<>(COMPARATOR);
        sortingAlgorithms = new SortingAlgorithms<>(countingComparator);
    }

    @AfterEach
    public void checkIllegalMethods() {
        IllegalMethodsCheck.checkMethods();
    }

    @ParameterizedTest
    @JsonClasspathSource(value = "H1b_WeirdlySortTests.json", data = "twoItemsTest")
    public void testTwoItems(@Property("values") List<Integer> values) {
        testSorting(values, values.stream().sorted(COMPARATOR).toList());
    }

    @ParameterizedTest
    @JsonClasspathSource(value = "H1b_WeirdlySortTests.json", data = "multipleItemsTest")
    public void testMultipleItems(@Property("values") List<Integer> values) {
        testSorting(values, values.stream().sorted(COMPARATOR).toList());
    }

    private void testSorting(List<Integer> values, List<Integer> expected) {
        SortList<Integer> sortList = new ArraySortList<>(values);
        call(() -> sortingAlgorithms.weirdlySort(sortList), contextBuilder()
                        .subject("SortingAlgorithms#weirdlySort()")
                        .add("values", values)
                        .add("comparator", "natural_order")
                        .build(),
                result -> "weirdlySort() should not throw an exception.");

        Context context = contextBuilder()
                .subject("SortingAlgorithms#weirdlySort()")
                .add("values", values)
                .add("comparator", "natural_order")
                .add("actual", sortList)
                .add("expected", expected)
                .build();

        assertTrue(isSorted(sortList, expected), context,
                result -> "The sortList should be sorted after calling weirdlySort().");
    }

    private boolean isSorted(SortList<Integer> sortList, List<Integer> expected) {
        for (int i = 0; i < expected.size(); i++) {
            if (!Objects.equals(sortList.get(i), expected.get(i))) {
                return false;
            }
        }
        return true;
    }
}