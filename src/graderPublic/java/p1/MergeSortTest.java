package p1;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junitpioneer.jupiter.json.JsonClasspathSource;
import org.junitpioneer.jupiter.json.Property;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import p1.sort.ArraySortList;
import p1.sort.HybridSort;
import p1.sort.SortList;
import p1.transformers.MethodInterceptor;

import java.util.Comparator;
import java.util.List;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertEquals;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.call;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.contextBuilder;

@TestForSubmission
public class MergeSortTest {

    private static final Comparator<Integer> COMPARATOR = Comparator.naturalOrder();
    private HybridSort<Integer> hybridSort;

    @BeforeEach
    public void setup() {
        MethodInterceptor.reset();
        hybridSort = new HybridSort<>(5, COMPARATOR);
    }

    @AfterEach
    public void checkIllegalMethods() {
        IllegalMethodsCheck.checkMethods();
    }

    @ParameterizedTest
    @JsonClasspathSource(value = "H2a_MergeSortTests.json", data = "mergeTest")
    public void testMerge(@Property("values") List<Integer> values,
                          @Property("left") Integer left,
                          @Property("middle") Integer middle,
                          @Property("right") Integer right,
                          @Property("expected") List<Integer> expected) {
        SortList<Integer> sortList = new ArraySortList<>(values);
        Context context = contextBuilder()
                .subject("HybridSort#merge()")
                .add("values", values)
                .add("left", left)
                .add("middle", middle)
                .add("right", right)
                .add("expected", expected)
                .build();

        call(() -> hybridSort.merge(sortList, left, middle, right), context,
                result -> "merge should not throw an exception.");
        assertSortListEquals(expected, sortList, context);
    }

    @ParameterizedTest
    @JsonClasspathSource(value = "H2a_MergeSortTests.json", data = "mergeSortTest")
    public void testMergeSort(@Property("values") List<Integer> values,
                              @Property("left") Integer left,
                              @Property("right") Integer right,
                              @Property("expected") List<Integer> expected) {
        SortList<Integer> sortList = new ArraySortList<>(values);
        Context context = contextBuilder()
                .subject("HybridSort#mergeSort()")
                .add("values", values)
                .add("left", left)
                .add("right", right)
                .add("expected", expected)
                .build();

        call(() -> hybridSort.mergeSort(sortList, left, right), context,
                result -> "mergeSort should not throw an exception.");
        assertSortListEquals(expected, sortList, context);
    }

    private void assertSortListEquals(List<Integer> expected, SortList<Integer> actual, Context context) {
        for (int i = 0; i < expected.size(); i++) {
            int finalI = i;
            assertEquals(expected.get(i), actual.get(i), context,
                    result -> "sortList contains the wrong value at index %d.".formatted(finalI));
        }
    }
}
