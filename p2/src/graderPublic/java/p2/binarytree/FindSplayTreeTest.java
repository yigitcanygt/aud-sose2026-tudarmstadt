package p2.binarytree;

import org.junit.jupiter.params.ParameterizedTest;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertEquals;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.callObject;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.contextBuilder;

/**
 * Private grader tests for {@link SplayTree#find(Comparable)}.
 * Covers hits (root, leaf, internal node, multi-step splay) and misses
 * (below min, above max, in middle, single-node tree).
 */
@TestForSubmission
public class FindSplayTreeTest extends P2_TestBase {

    @ParameterizedTest
    @JsonParameterSetTest(value = "FindSplay_Hit_Private.json", customConverters = "customConverters")
    public void testFindHit(JsonParameterSet params) {
        testFind(params, true);
    }

    @ParameterizedTest
    @JsonParameterSetTest(value = "FindSplay_Miss_Private.json", customConverters = "customConverters")
    public void testFindMiss(JsonParameterSet params) {
        testFind(params, false);
    }

    private void testFind(JsonParameterSet params, boolean hit) {
        SplayTree<Integer> tree = params.get("splayTree");
        int searchKey = params.getInt("searchKey");
        SplayTree<Integer> expectedTree = params.get("expectedSplayTree");
        int expectedRootKey = params.getInt("expectedRootKey");

        Context.Builder<?> context = contextBuilder()
                .subject("SplayTree#find")
                .add("splay tree", treeToString(tree))
                .add("search key", searchKey)
                .add("value present", hit)
                .add("expected root key", expectedRootKey)
                .add("expected tree", treeToString(expectedTree));

        SplayNode<Integer> actual = callObject(() -> tree.find(searchKey), context.build(),
                result -> "find should not throw an exception");

        context.add("actual tree", treeToString(tree));
        context.add("actual root key",
                tree.getRoot() == null ? null : tree.getRoot().getKey());

        // check return value
        if (hit) {
            assertEquals(searchKey, actual == null ? null : actual.getKey(), context.build(),
                    result -> "find should return the node containing the value when present");
        } else {
            assertEquals(null, actual, context.build(),
                    result -> "find should return null when value is not present");
        }

        // check last accessed node was splayed to root
        assertEquals(expectedRootKey,
                tree.getRoot() == null ? null : tree.getRoot().getKey(),
                context.build(),
                result -> "find did not splay the last accessed node to the root");

        // check full tree shape
        assertTreeEquals(expectedTree, tree, context.build(),
                "The tree shape after find is not correct");

        // root parent must be null
        assertEquals(null, tree.getRoot().getParent(), context.build(),
                result -> "The root's parent should be null after find");
    }
}