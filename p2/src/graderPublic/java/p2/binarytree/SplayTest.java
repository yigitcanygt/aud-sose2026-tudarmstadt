package p2.binarytree;

import org.junit.jupiter.params.ParameterizedTest;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.call;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.contextBuilder;

/**
 * Private grader tests for {@link SplayTree#splay(SplayNode)}.
 * Covers zig, zig-zig, zig-zag and multi-step splay sequences.
 */
@TestForSubmission
public class SplayTest extends P2_TestBase {

    @ParameterizedTest
    @JsonParameterSetTest(value = "Splay_Simple.json", customConverters = "customConverters")
    public void testSplaySimple(JsonParameterSet params) {
        testSplay(params);
    }

    @ParameterizedTest
    @JsonParameterSetTest(value = "Splay_Complex.json", customConverters = "customConverters")
    public void testSplayComplex(JsonParameterSet params) {
        testSplay(params);
    }

    private void testSplay(JsonParameterSet params) {
        SplayTree<Integer> tree = params.get("splayTree");
        int nodeKey = params.getInt("nodeKey");
        SplayTree<Integer> expectedTree = params.get("expectedSplayTree");

        SplayNode<Integer> node = tree.search(nodeKey);

        Context.Builder<?> context = contextBuilder()
                .subject("SplayTree#splay")
                .add("splay tree", treeToString(tree))
                .add("node to splay", nodeKey)
                .add("expected tree", treeToString(expectedTree));

        call(() -> tree.splay(node), context.build(),
                result -> "splay should not throw an exception");

        context.add("actual tree", treeToString(tree));

        assertTreeEquals(expectedTree, tree, context.build(),
                "The tree after splaying is not correct");
    }
}