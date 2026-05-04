package p1;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junitpioneer.jupiter.json.JsonClasspathSource;
import org.junitpioneer.jupiter.json.Property;
import org.mockito.stubbing.Answer;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import p1.sort.ArraySortList;
import p1.sort.HybridOptimizer;
import p1.sort.HybridSort;
import p1.transformers.MethodInterceptor;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.spy;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertCallEquals;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.contextBuilder;

@TestForSubmission
public class HybridOptimizerTest {

    private static final Comparator<Integer> COMPARATOR = Comparator.naturalOrder();

    @BeforeEach
    public void setup() {
        MethodInterceptor.reset();
    }

    @AfterEach
    public void checkIllegalMethods() {
        IllegalMethodsCheck.checkMethods("^java/util/Arrays.+");
    }

    @ParameterizedTest
    @JsonClasspathSource(value = "H2c_HybridOptimizerTests.json", data = "firstLocalMinimumTest")
    public void testFirstLocalMinimum(@Property("values") List<Integer> values,
                                      @Property("reads") List<Integer> reads,
                                      @Property("writes") List<Integer> writes,
                                      @Property("comparisons") List<Integer> comparisons,
                                      @Property("expected") int expected) {
        HybridSort<Integer> hybridSort = spy(new HybridSort<>(5, COMPARATOR));
        mockSort(reads, writes, comparisons, hybridSort);

        Context context = contextBuilder()
                .subject("HybridOptimizer.optimize")
                .add("values", values)
                .add("reads", reads)
                .add("writes", writes)
                .add("comparisons", comparisons)
                .build();

        assertCallEquals(expected, () -> HybridOptimizer.optimize(hybridSort, values.toArray(Integer[]::new)), context,
                result -> "The return value of optimize() is wrong.");
    }

    private void mockSort(List<Integer> reads,
                          List<Integer> writes,
                          List<Integer> comparisons,
                          HybridSort<Integer> hybridSort) {
        AtomicInteger calls = new AtomicInteger(0);

        Answer<?> answer = invocation -> {
            ArraySortList<Integer> sortList = invocation.getArgument(0);
            int callIndex = calls.getAndIncrement();
            setReadCount(sortList, reads.get(callIndex));
            setWriteCount(sortList, writes.get(callIndex));
            return null;
        };

        doAnswer(answer).when(hybridSort).sort(any());
        doAnswer(invocation -> comparisons.get(Math.max(0, calls.get() - 1))).when(hybridSort).getComparisonsCount();
    }

    private void setReadCount(ArraySortList<?> sortList, int value) throws ReflectiveOperationException {
        Field field = ArraySortList.class.getDeclaredField("readCount");
        field.setAccessible(true);
        field.set(sortList, value);
    }

    private void setWriteCount(ArraySortList<?> sortList, int value) throws ReflectiveOperationException {
        Field field = ArraySortList.class.getDeclaredField("writeCount");
        field.setAccessible(true);
        field.set(sortList, value);
    }
}
