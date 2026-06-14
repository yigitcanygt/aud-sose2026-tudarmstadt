package p2.binarytree;

import org.junit.jupiter.params.ParameterizedTest;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;

@TestForSubmission
public class PreOrderTest extends TraversingTest {
    @ParameterizedTest
    @JsonParameterSetTest(value = "PreOrder_Simple.json", customConverters = "customConverters")
    public void testPreOrderSimple(JsonParameterSet params) {
        testTraversing(params, (tree, node, result, max, predicate) ->
                tree.preOrder(node, result), "preOrder");
    }
}
