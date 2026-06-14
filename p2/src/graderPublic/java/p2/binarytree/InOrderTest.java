package p2.binarytree;

import org.junit.jupiter.params.ParameterizedTest;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;

/**
 * Test class for reverseInOrder traversal.
 */
@TestForSubmission
public class InOrderTest extends TraversingTest {

    @ParameterizedTest
    @JsonParameterSetTest(value = "InOrder_Simple.json", customConverters = "customConverters")
    public void testInOrderSimple(JsonParameterSet params) {
        testTraversing(params, (tree, node, result, max, predicate) ->
                // Here’s the traversal logic!
                tree.inOrder(node, result, predicate), "inOrder");
    }

    @ParameterizedTest
    @JsonParameterSetTest(value = "InOrder_Complex.json", customConverters = "customConverters")
    public void testInOrderComplex(JsonParameterSet params) {
        testTraversing(params, (tree, node, result, max, predicate) ->
                tree.inOrder(node, result, predicate), "inOrder");
    }
}
