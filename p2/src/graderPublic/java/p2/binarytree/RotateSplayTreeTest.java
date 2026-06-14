package p2.binarytree;

import org.junit.jupiter.params.ParameterizedTest;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.call;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.contextBuilder;

/**
 * Private grader tests for {@link SplayTree#rotate(SplayNode, boolean)}.
 * Covers non-root pivots, pivots with one child, and deep rotations.
 */
@TestForSubmission
public class RotateSplayTreeTest extends P2_TestBase {

    @ParameterizedTest
    @JsonParameterSetTest(value = "RotateLeft_Splay.json", customConverters = "customConverters")
    public void testRotateLeft(JsonParameterSet params) {
        testRotate(params, true);
    }

    @ParameterizedTest
    @JsonParameterSetTest(value = "RotateRight_Splay.json", customConverters = "customConverters")
    public void testRotateRight(JsonParameterSet params) {
        testRotate(params, false);
    }

    private void testRotate(JsonParameterSet params, boolean left) {
        SplayTree<Integer> tree = params.get("splayTree");
        int pivotKey = params.getInt("pivotKey");
        SplayTree<Integer> expectedTree = params.get("expectedSplayTree");

        SplayNode<Integer> pivot = tree.search(pivotKey);

        Context.Builder<?> context = contextBuilder()
                .subject("SplayTree#rotate")
                .add("splay tree", treeToString(tree))
                .add("pivot key", pivotKey)
                .add("rotateLeft", left)
                .add("expected tree", treeToString(expectedTree));

        call(() -> tree.rotate(pivot, left), context.build(),
                result -> "rotate(" + pivotKey + ", " + left + ") should not throw an exception");

        context.add("actual tree", treeToString(tree));

        assertTreeEquals(expectedTree, tree, context.build(),
                "The tree after rotation is not correct");
    }
}