package p2.binarytree;

import org.junit.jupiter.params.ParameterizedTest;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;

import java.util.ArrayList;
import java.util.List;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

/**
 * Private grader tests for {@link SplayTree#remove(Comparable)}.
 * Covers: only element, no left subtree, leaf removal, absent value,
 * root removal with join, internal node removal, min/max removal,
 * and multi-step find+join sequences.
 */
@TestForSubmission
public class RemoveSplayTreeTest extends P2_TestBase {

    @ParameterizedTest
    @JsonParameterSetTest(value = "RemoveSplay_Simple.json", customConverters = "customConverters")
    public void testRemoveSimple(JsonParameterSet params) {
        testRemove(params);
    }

    @ParameterizedTest
    @JsonParameterSetTest(value = "RemoveSplay_Complex.json", customConverters = "customConverters")
    public void testRemoveComplex(JsonParameterSet params) {
        testRemove(params);
    }

    private void testRemove(JsonParameterSet params) {
        SplayTree<Integer> tree = params.get("splayTree");
        int removeKey = params.getInt("removeKey");
        SplayTree<Integer> expectedTree = params.get("expectedSplayTree");

        Context.Builder<?> context = contextBuilder()
                .subject("SplayTree#remove")
                .add("splay tree", treeToString(tree))
                .add("key to remove", removeKey)
                .add("expected tree", treeToString(expectedTree));

        call(() -> tree.remove(removeKey), context.build(),
                result -> "remove should not throw an exception");

        context.add("actual tree", treeToString(tree));

        // removed value must no longer be present
        assertEquals(null, tree.search(removeKey), context.build(),
                result -> "The removed value should no longer be in the tree");

        // tree shape must be correct
        if (expectedTree.getRoot() == null) {
            assertEquals(null, tree.getRoot(), context.build(),
                    result -> "Tree should be empty after removing the only element");
        } else {
            assertTreeEquals(expectedTree, tree, context.build(),
                    "The tree shape after removal is not correct");

            // root parent must be null
            assertEquals(null, tree.getRoot().getParent(), context.build(),
                    result -> "The root's parent should be null after remove");

            // BST property must still hold — inorder must be sorted
            List<Integer> inorder = new ArrayList<>();
            tree.inOrder(tree.getRoot(), inorder);
            for (int i = 0; i < inorder.size() - 1; i++) {
                int finalI = i;
                assertEquals(true, inorder.get(i) < inorder.get(i + 1), context.build(),
                        result -> "BST property violated after remove: inorder is not sorted at index " + finalI);
            }
        }
    }
}