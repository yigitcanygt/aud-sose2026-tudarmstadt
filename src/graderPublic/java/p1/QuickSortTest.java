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

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertCallEquals;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertEquals;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.call;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.contextBuilder;

@TestForSubmission
public class QuickSortTest {

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
    @JsonClasspathSource(value = "H2b_QuickSortTests.json", data = "partitionTest")
    public void testPartition(@Property("values") List<Integer> values,
                              @Property("left") Integer left,
                              @Property("right") Integer right,
                              @Property("pivot") Integer pivot,
                              @Property("expected") List<Integer> expected) {
        SortList<Integer> sortList = new ArraySortList<>(values);
        Context context = contextBuilder()
                .subject("HybridSort#partition()")
                .add("values", values)
                .add("left", left)
                .add("right", right)
                .add("expected", expected)
                .build();

        assertCallEquals(pivot, () -> hybridSort.partition(sortList, left, right), context,
                result -> "partition returned the wrong index.");
        assertSortListEquals(expected, sortList, context);
    }

    @ParameterizedTest
    @JsonClasspathSource(value = "H2b_QuickSortTests.json", data = "sortTest")
    public void testQuickSort(@Property("values") List<Integer> values,
                              @Property("k") Integer k,
                              @Property("expected") List<Integer> expected) {
        SortList<Integer> sortList = new ArraySortList<>(values);
        hybridSort.setK(k);
        Context context = contextBuilder()
                .subject("HybridSort#quickSort()")
                .add("values", values)
                .add("k", k)
                .add("expected", expected)
                .build();

        call(() -> hybridSort.quickSort(sortList, 0, sortList.getSize() - 1), context,
                result -> "quickSort should not throw an exception.");
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
