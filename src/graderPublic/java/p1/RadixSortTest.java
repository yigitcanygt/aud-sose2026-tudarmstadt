package p1;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junitpioneer.jupiter.json.JsonClasspathSource;
import org.junitpioneer.jupiter.json.Property;
import org.mockito.ArgumentCaptor;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import p1.sort.ArraySortList;
import p1.sort.SortList;
import p1.sort.radix.Bucket;
import p1.sort.radix.IntegerIndexExtractor;
import p1.sort.radix.RuneIndexExtractor;
import p1.sort.radix.RadixSort;
import p1.transformers.MethodInterceptor;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@SuppressWarnings("DuplicatedCode")
@TestForSubmission
public class RadixSortTest {

    private static final String RUNE_ORDER_STR = "FHEYASD";

    @BeforeEach
    public void setup() {
        MethodInterceptor.reset();
    }

    @AfterEach
    public void checkIllegalMethods() {
        IllegalMethodsCheck.checkMethods("^java/lang/String.+");
    }

    @Test
    public void checkIllegalMethodsUntested() {
        MethodInterceptor.reset();
        RadixSort<Integer> radixSort = spy(new RadixSort<>(1, new IntegerIndexExtractor(1) {
            @Override
            public int extractIndex(Integer value, int position) {
                return 0;
            }
        }));
        radixSort.setMaxInputLength(1);
        radixSort.sort(new ArraySortList<>(List.of(5, 4, 3, 2, 1)));
        IllegalMethodsCheck.checkMethods();
    }

    @ParameterizedTest
    @JsonClasspathSource(value = "H3a_RadixSortTests.json", data = "multipleItemsTest")
    public void testRuneSort(@Property("values") List<String> values,
                             @Property("expected") List<String> expected,
                             @Property("maxInputLength") Integer maxInputLength) {
        testRuneSorting(values, expected, maxInputLength);
    }

    @ParameterizedTest
    @JsonClasspathSource(value = "H3a_RadixSortTests.json", data = "putBucketTest")
    public void testPutBucketCallSingle(@Property("values") List<String> values,
                                        @Property("position") List<Integer> position,
                                        @Property("expected") List<String> expected) {
        RadixSort<String> radixSort = spy(new RadixSort<>(7, new RuneIndexExtractor()));
        radixSort.setMaxInputLength(1);

        SortList<String> sortList = new ArraySortList<>(values);
        Context context = contextBuilder()
            .subject("RadixSort#putBucket()")
            .add("values", values)
            .add("actual", sortList)
            .add("expected", expected)
            .build();
        call(() -> radixSort.sort(sortList), contextBuilder()
                .subject("RadixSort#sort()")
                .add("values", values)
                .build(),
            result -> "sort() should not throw an exception.");

        ArgumentCaptor<String> valueCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Integer> positionCaptor = ArgumentCaptor.forClass(Integer.class);
        doNothing().when(radixSort).putBucket(valueCaptor.capture(), positionCaptor.capture());
        doCallRealMethod().doNothing().when(radixSort).sort(any());

        radixSort.sort(sortList);

        List<String> capturedValues = valueCaptor.getAllValues();
        List<Integer> capturedPositions = positionCaptor.getAllValues();
        for (int i = 0; i < values.size(); i++) {
            int finalI = i;
            assertEquals(expected.get(i), capturedValues.get(i), context, result -> "putBucket() was called with the wrong value at index %d.".formatted(finalI));
            assertEquals(position.get(i), capturedPositions.get(i), context, result -> "putBucket() was called with the wrong position value at index %d.".formatted(finalI));
        }
    }

    @ParameterizedTest()
    @JsonClasspathSource(value = "H3a_RadixSortTests.json", data = "putBucketCallTest")
    public void testPutBucketCall(@Property("values") List<String> values,
                                  @Property("maxInputLength") Integer maxInputLength) {
        testPutBucketCallInternal(values, maxInputLength, 7);
    }

    private void testPutBucketCallInternal(List<String> values, int maxInputLength, int radix) {
        Context context = contextBuilder()
                .subject("RadixSort#sort()")
                .add("values", values)
                .add("maxInputLength", maxInputLength)
                .build();

        RadixSort<String> radixSort = spy(new RadixSort<>(radix, new RuneIndexExtractor()));
        radixSort.setMaxInputLength(maxInputLength);

        ArgumentCaptor<String> valueCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Integer> positionCaptor = ArgumentCaptor.forClass(Integer.class);
        doNothing().when(radixSort).putBucket(valueCaptor.capture(), positionCaptor.capture());
        doCallRealMethod().doNothing().when(radixSort).sort(any());

        SortList<String> sortList = new ArraySortList<>(values);
        call(() -> radixSort.sort(sortList), context, result -> "sort() should not throw an exception.");

        List<String> capturedValues = valueCaptor.getAllValues();
        List<Integer> capturedPositions = positionCaptor.getAllValues();

        int expectedCalls = values.size() * maxInputLength;

        if (capturedValues.size() != expectedCalls) {
            fail(contextBuilder()
                    .subject("RadixSort#sort()")
                    .add("values", values)
                    .add("maxInputLength", maxInputLength)
                    .add("expected calls", expectedCalls)
                    .add("actual calls", capturedValues.size())
                    .build(), result -> "putBucket() was not called the expected amount of times.");
        }

        for (int i = 0; i < values.size(); i++) {
            int finalI = i;
            assertEquals(values.get(i), capturedValues.get(i), context,
                    result -> "putBucket() was called with the wrong value at the %d-th call.".formatted(finalI + 1));
            assertEquals(i / values.size(), capturedPositions.get(i), context,
                    result -> "putBucket() was called with the wrong position at the %d-th call.".formatted(finalI + 1));
        }
    }

    @ParameterizedTest
    @JsonClasspathSource(value = "H3a_RadixSortTests.json", data = "putBucketTest1")
    public void testPutBucket(@Property("value") String value,
                              @Property("position") Integer position) throws ReflectiveOperationException {

        int radix = 7;

        RuneIndexExtractor extractor = spy(new RuneIndexExtractor());
        RadixSort<String> radixSort = spy(new RadixSort<>(radix, extractor));
        radixSort.setMaxInputLength(1);

        // Compute expected bucket index directly from the known rune order — independent of student code
        char runeChar = value.charAt(value.length() - position - 1);
        int index = RUNE_ORDER_STR.indexOf(runeChar);

        Context context = contextBuilder()
                .subject("RadixSort#putBucket()")
                .add("value", value)
                .add("position", position)
                .add("expected bucket index", index)
                .build();

        call(() -> radixSort.putBucket(value, position), context, TR -> "putBucket() should not throw an exception.");

        checkVerify(() -> verify(extractor, times(1)).extractIndex(value, position), context,
                "extractIndex was not called with the expected values.");

        Bucket<String>[] buckets = getBuckets(radixSort);

        for (int i = 0; i < radix; i++) {
            int finalI = i;

            if (i == index) {
                assertEquals(1, buckets[i].size(), context, result -> "Bucket at position %d should contain exactly one element.".formatted(finalI));
                assertEquals(value, buckets[i].remove(), context, result -> "Bucket at position %d should contain the value %s.".formatted(finalI, value));
            } else {
                assertEquals(0, buckets[i].size(), context, result -> "Bucket at position %d should be empty.".formatted(finalI));
            }
        }
    }

    @ParameterizedTest
    @JsonClasspathSource(value = "H3b_IntegerExtractorTests.json", data = "extractIndexTest")
    public void testIntegerExtractIndex(@Property("value") Integer value,
                                        @Property("position") Integer position,
                                        @Property("radix") Integer radix,
                                        @Property("expected") Integer expected) {
        Context context = contextBuilder()
                .subject("IntegerIndexExtractor#extractIndex")
                .add("value", value)
                .add("position", position)
                .add("radix", radix)
                .add("expected", expected)
                .build();

        assertEquals(expected, new IntegerIndexExtractor(radix).extractIndex(value, position),
                context, result -> "The method extractIndex did not return the expected digit.");
    }

    private void checkVerify(Runnable verifier, Context context, String msg) {
        try {
            verifier.run();
        } catch (AssertionError e) {
            fail(context, result -> msg + " Original error message:\n" + e.getMessage());
        } catch (Exception e) {
            fail(context, result -> "Unexpected Exception:\n" + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private <T> Bucket<T>[] getBuckets(RadixSort<T> radixSort) throws ReflectiveOperationException {
        Field field = RadixSort.class.getDeclaredField("buckets");
        field.setAccessible(true);
        return (Bucket<T>[]) field.get(radixSort);
    }

    private void testRuneSorting(List<String> values, List<String> expected, int maxInputLength) {
        RadixSort<String> radixSort = new RadixSort<>(7, new RuneIndexExtractor());
        radixSort.setMaxInputLength(maxInputLength);
        SortList<String> sortList = new ArraySortList<>(values);

        Context context = contextBuilder()
            .subject("RadixSort#sort()")
            .add("values", values)
            .add("actual", sortList)
            .add("expected", expected)
            .build();

        call(() -> radixSort.sort(sortList), contextBuilder()
                .subject("RadixSort#sort()")
                .add("values", values)
                .build(),
            result -> "sort() should not throw an exception.");

        assertTrue(isRuneSorted(sortList, expected), context,
            result -> "The sortList should be sorted after calling sort().");
    }

    private boolean isRuneSorted(SortList<String> sortList, List<String> expected) {
        for (int i = 0; i < expected.size(); i++) {
            if (!Objects.equals(sortList.get(i), expected.get(i))) {
                return false;
            }
        }
        return true;
    }
}
