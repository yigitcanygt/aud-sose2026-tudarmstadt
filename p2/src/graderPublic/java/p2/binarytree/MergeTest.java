package p2.binarytree;

import org.junit.jupiter.params.ParameterizedTest;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;

import java.util.List;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class MergeTest extends P2_TestBase {

    @ParameterizedTest
    @JsonParameterSetTest(value = "Merge_NoDuplicates.json", customConverters = "customConverters")
    public void testMergeNoDuplicates(JsonParameterSet params) {
        runMergeTest(params, "AVLTree#merge (no duplicates)");
    }

    @ParameterizedTest
    @JsonParameterSetTest(value = "Merge_WithDuplicates.json", customConverters = "customConverters")
    public void testMergeWithDuplicates(JsonParameterSet params) {
        runMergeTest(params, "AVLTree#merge (with duplicates)");
    }

    private void runMergeTest(JsonParameterSet params, String subject) {

        List<Integer> list1 = params.get("list1");
        List<Integer> list2 = params.get("list2");
        List<Integer> expected = params.get("expected");

        AVLTree<Integer> avlTree = new AVLTree<>();

        Context context = contextBuilder()
            .subject(subject)
            .add("list1", list1)
            .add("list2", list2)
            .add("expected", expected)
            .build();

        List<Integer> actual = callObject(() -> avlTree.merge(list1, list2), context,
            r -> "merge should not throw an exception");

        assertEquals(expected, actual, context,
            r -> "The merged list is not correct");
    }
}
