package p1;

import org.opentest4j.AssertionFailedError;
import org.sourcegrade.jagr.api.rubric.Criterion;
import org.sourcegrade.jagr.api.rubric.GradeResult;
import org.sourcegrade.jagr.api.rubric.Grader;
import org.sourcegrade.jagr.api.rubric.JUnitTestRef;
import org.sourcegrade.jagr.api.rubric.Rubric;
import org.sourcegrade.jagr.api.rubric.RubricProvider;
import org.sourcegrade.jagr.api.testing.RubricConfiguration;
import p1.transformers.MethodInterceptorTransformer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings("unused")
public class P1_RubricProvider implements RubricProvider {

    private static Criterion createUntestedCriterion(String shortDescription, Callable<Method> illegalMethodCheck) {

        StringBuilder comment = new StringBuilder("Not graded by public grader");

        if (illegalMethodCheck != null) {
            try {
                Method method = illegalMethodCheck.call();
                Object instance = method.getDeclaringClass().getDeclaredConstructor().newInstance();
                method.invoke(instance);
            } catch (InvocationTargetException e) {
                if (e.getCause() instanceof AssertionFailedError) {
                    comment.append("\n").append(e.getCause().getMessage());
                } else {
                    comment.append("\nMethod threw an exception. Could not check whether illegal methods have been used. Exception: ").append(e.getCause().getMessage());
                }
            } catch (Exception ignored) {
            }
        }

        return Criterion.builder()
                .shortDescription(shortDescription)
                .grader((testCycle, criterion) ->
                        GradeResult.of(criterion.getMinPoints(), criterion.getMaxPoints(), comment.toString()))
                .maxPoints(1)
                .build();
    }

    @SafeVarargs
    private static Criterion createCriterion(String shortDescription, int maxPoints, Callable<Method>... methodReferences) {

        if (methodReferences.length == 0) {
            return Criterion.builder()
                    .shortDescription(shortDescription)
                    .maxPoints(maxPoints)
                    .build();
        }

        Grader.TestAwareBuilder graderBuilder = Grader.testAwareBuilder();

        for (Callable<Method> reference : methodReferences) {
            graderBuilder.requirePass(JUnitTestRef.ofMethod(reference));
        }

        return Criterion.builder()
                .shortDescription(shortDescription)
                .grader(graderBuilder
                        .pointsFailedMin()
                        .pointsPassedMax()
                        .build())
                .maxPoints(maxPoints)
                .build();
    }

    private static Criterion createParentCriterion(String task, String shortDescription, Criterion... children) {
        return Criterion.builder()
                .shortDescription("H" + task + " | " + shortDescription)
                .addChildCriteria(children)
                .build();
    }

    // H1: BubbleSort
    public static final Criterion H1_1_1 = createCriterion("Die Methode [[[bubbleSort]]] der Klasse SortingAlgorithms funktioniert korrekt wenn die Eingabe zwei Elemente enthält.", 1,
            () -> BubbleSortTest.class.getMethod("testTwoItems", List.class));

    public static final Criterion H1_1_2 = createCriterion("Die Methode [[[bubbleSort]]] der Klasse SortingAlgorithms verwendet die korrekten Lese- und Schreiboperationen in der korrekten Reihenfolge.", 1,
            () -> BubbleSortTest.class.getMethod("testOperationOrder", List.class, List.class));

    public static final Criterion H1_1_3 = createUntestedCriterion("Die Methode [[[bubbleSort]]] der Klasse SortingAlgorithms funktioniert korrekt wenn die Eingabe mehr als zwei Elemente enthält.",
                () -> BubbleSortTest.class.getMethod("checkIllegalMethods"));

    public static final Criterion H1_1 = createParentCriterion("1 a)","BubbleSort", H1_1_1, H1_1_2, H1_1_3);

    // H1: WeirdlySort

    public static final Criterion H1_2_1 = createUntestedCriterion("Die Methode [[[weirdlySort]]] der Klasse SortingAlgorithms verwendet die korrekten Lese- und Schreiboperationen in der korrekten Reihenfolge.",
                () -> WeirdlySortTest.class.getMethod("checkIllegalMethods"));

    public static final Criterion H1_2_2 = createCriterion("Die Methode [[[weirdlySort]]] der Klasse SortingAlgorithms funktioniert korrekt wenn die Eingabe zwei Elemente enthält.", 1, 
                () -> WeirdlySortTest.class.getMethod("testTwoItems", List.class));

    public static final Criterion H1_2_3 = createCriterion("Die Methode [[[weirdlySort]]] der Klasse SortingAlgorithms funktioniert korrekt wenn die Eingabe mehr als zwei Elemente enthält.", 1, 
                () -> WeirdlySortTest.class.getMethod("testMultipleItems", List.class));

    public static final Criterion H1_2 = createParentCriterion("1 b)","WeirdlySort",  H1_2_1, H1_2_2, H1_2_3);

    // H2: HybridSort
    public static final Criterion H2_1_1 = createCriterion("Die Methode [[[merge]]] der Klasse HybridSort führt zwei sortierte Teilbereiche korrekt zusammen", 1,
            () -> MergeSortTest.class.getMethod("testMerge", List.class, Integer.class, Integer.class, Integer.class, List.class));

    public static final Criterion H2_1_2 = createUntestedCriterion("Die Methode [[[mergeSort]]] der Klasse HybridSort verwendet korrekte Rekursionsgrenzen und den korrekten Mittelindex", null);

    public static final Criterion H2_1_3 = createCriterion("Die Methode [[[mergeSort]]] der Klasse HybridSort verändert nur Werte im Indexbereich [left, right]", 1,
            () -> MergeSortTest.class.getMethod("testMergeSort", List.class, Integer.class, Integer.class, List.class));

    public static final Criterion H2_1_4 = createUntestedCriterion("Die Methoden [[[merge]]] und [[[mergeSort]]] verwenden den Comparator und die erwarteten Lese-/Schreibzugriffe", null);

    public static final Criterion H2_1 = createParentCriterion("2 a)", "MergeSort", H2_1_1, H2_1_2, H2_1_3, H2_1_4);

    public static final Criterion H2_2_1 = createCriterion("Die Methode [[[partition]]] der Klasse HybridSort implementiert das vorgegebene Hoare-Partitionsschema", 1,
            () -> QuickSortTest.class.getMethod("testPartition", List.class, Integer.class, Integer.class, Integer.class, List.class));

    public static final Criterion H2_2_2 = createUntestedCriterion("Die Methode [[[quickSort(sortList, left, right)]]] startet die Rekursion mit Tiefe 0", null);

    public static final Criterion H2_2_3 = createUntestedCriterion("Die Methode [[[quickSort(sortList, left, right, depth)]]] verwendet korrekte Rekursionsgrenzen und erhöht die Tiefe um 1", null);

    public static final Criterion H2_2_4 = createUntestedCriterion("Die Methode [[[quickSort]]] schaltet bei depth >= k auf [[[mergeSort]]] um und beendet den Rekursionszweig", null);

    public static final Criterion H2_2_5 = createCriterion("Die Methode [[[quickSort]]] sortiert Basis-, Teilbereichs- und Fallback-Fälle korrekt", 1,
            () -> QuickSortTest.class.getMethod("testQuickSort", List.class, Integer.class, List.class));

    public static final Criterion H2_2 = createParentCriterion("2 b)", "QuickSort mit MergeSort-Fallback", H2_2_1, H2_2_2, H2_2_3, H2_2_4, H2_2_5);

    public static final Criterion H2_3_1 = createUntestedCriterion("Die Methode [[[optimize]]] berechnet K_MAX = ceil(log2(n)) + 4 und testet k in der richtigen Reihenfolge", null);

    public static final Criterion H2_3_2 = createUntestedCriterion("Die Methode [[[optimize]]] erzeugt für jeden k-Wert eine frische ArraySortList", null);

    public static final Criterion H2_3_3 = createCriterion("Die Methode [[[optimize]]] misst readCount + writeCount + getComparisonsCount", 1,
            () -> HybridOptimizerTest.class.getMethod("testFirstLocalMinimum", List.class, List.class, List.class, List.class, int.class));

    public static final Criterion H2_3_4 = createCriterion("Die Methode [[[optimize]]] beendet die Suche beim ersten lokalen Minimum", 1,
            () -> HybridOptimizerTest.class.getMethod("testFirstLocalMinimum", List.class, List.class, List.class, List.class, int.class));

    public static final Criterion H2_3_5 = createUntestedCriterion("Die Methode [[[optimize]]] behandelt Plateaus korrekt und gibt für INPUT2 k=8 zurück", null);

    public static final Criterion H2_3 = createParentCriterion("2 c)", "HybridOptimizer", H2_3_1, H2_3_2, H2_3_3, H2_3_4, H2_3_5);

    public static final Criterion H3_1_1 = createCriterion("Die Methode [[[extractIndex]]] der Klasse RuneIndexExtractor gibt den korrekten Bucket-Index basierend auf der Runenreihenfolge zurück", 1,
            () -> RuneIndexExtractorTest.class.getMethod("testExtractRuneIndex", String.class, int.class, int.class));

    public static final Criterion H3_1_2 = createUntestedCriterion("Die Methode [[[extractIndex]]] der Klasse RuneIndexExtractor funktioniert korrekt für alle gültigen Runensymbole und Positionen",
            () -> RuneIndexExtractorTest.class.getMethod("checkIllegalMethods"));

    public static final Criterion H3_1_3 = createCriterion("Die Methode [[[putBucket]]] der Klasse RadixSort funktioniert vollständig korrekt", 1,
            () -> RadixSortTest.class.getMethod("testPutBucket", String.class, Integer.class));

    public static final Criterion H3_1_4 = createCriterion("Die Methode [[[sort]]] der Klasse RadixSort ruft die Methode putBucket in der korrekten Reihenfolge mit den korrekten Werten auf wenn maxInputLength 1 ist", 1,
            () -> RadixSortTest.class.getMethod("testPutBucketCallSingle", List.class, List.class, List.class));

    public static final Criterion H3_1_5 = createCriterion("Die Methode [[[sort]]] der Klasse RadixSort ruft die Methode putBucket in der korrekten Reihenfolge mit den korrekten Werten auf wenn maxInputLength größer als 1 ist", 1,
            () -> RadixSortTest.class.getMethod("testPutBucketCall", List.class, Integer.class));

    public static final Criterion H3_1_6 = createUntestedCriterion("Die Methode [[[sort]]] der Klasse RadixSort schreibt die Werte aus den Buckets korrekt zurück in die zu sortierende Liste",
            () -> RadixSortTest.class.getMethod("checkIllegalMethodsUntested"));

    public static final Criterion H3_1_7 = createCriterion("Die Methode [[[sort]]] der Klasse RadixSort sortiert die Liste vollständig korrekt", 1,
            () -> RadixSortTest.class.getMethod("testRuneSort", List.class, List.class, Integer.class));

    public static final Criterion H3_1 = createParentCriterion("3 a)", "RuneIndexExtractor + RadixSort", H3_1_1, H3_1_2, H3_1_3, H3_1_4, H3_1_5, H3_1_6, H3_1_7);

    public static final Criterion H3_2_1 = createCriterion("Die Methode [[[extractIndex]]] der Klasse IntegerIndexExtractor gibt die korrekte Stelle zurück, wenn die Position innerhalb des Zahlenbereichs liegt", 1,
            () -> RadixSortTest.class.getMethod("testIntegerExtractIndex", Integer.class, Integer.class, Integer.class, Integer.class));

    public static final Criterion H3_2_2 = createUntestedCriterion("Die Methode [[[extractIndex]]] der Klasse IntegerIndexExtractor gibt 0 zurück, wenn die Position außerhalb des Zahlenbereichs liegt (Padding)",
            () -> RadixSortTest.class.getMethod("checkIllegalMethodsUntested"));

    public static final Criterion H3_2_3 = createUntestedCriterion("Die Methode [[[extractIndex]]] der Klasse IntegerIndexExtractor funktioniert vollständig korrekt",
            () -> RadixSortTest.class.getMethod("checkIllegalMethodsUntested"));

    public static final Criterion H3_2 = createParentCriterion("3 b)", "IntegerIndexExtractor", H3_2_1, H3_2_2, H3_2_3);

    public static final Criterion H1 = createParentCriterion("1", "SortingAlgorithms", H1_1, H1_2);
    public static final Criterion H2 = createParentCriterion("2", "Hybrid-Sort", H2_1, H2_2, H2_3);
    public static final Criterion H3 = createParentCriterion("3", "Radix-Sort", H3_1, H3_2);

    public static final Rubric RUBRIC = Rubric.builder()
            .title("P1")
            .addChildCriteria(H1)
            .addChildCriteria(H2)
            .addChildCriteria(H3)
            .build();

    @Override
    public Rubric getRubric() {
        return RUBRIC;
    }

    @Override
    public void configure(RubricConfiguration configuration) {
        configuration.addTransformer(new MethodInterceptorTransformer());
    }
}
