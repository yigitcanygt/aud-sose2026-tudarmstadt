package p2.binarytree;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

/**
 * Grader to test buildBSTFromPreorder() method.
 */
@TestForSubmission
public class BuildBSTFromPreorderTest extends P2_TestBase {

    // H2_3_1
    @Test
    public void testBuildTreeSizeMatchesList() {
        SimpleBinaryTree<Integer> bst = new SimpleBinaryTree<>();
        List<Integer> preOrder = List.of(1, 7, 5, 50, 40, 10);
        List<Integer> inorder = List.of(1, 5, 7, 10, 40, 50);

        bst.buildBTFromPreorder(preOrder, inorder);
        int expected = 6;

        Context.Builder<?> context = contextBuilder()
                .subject("buildBSTFromPreorder")
                .add("preorder", preOrder)
                .add("expectedSize", expected);

        List<Integer> actualInOrderList = new ArrayList<>();
        bst.inOrder(bst.getRoot(), actualInOrderList);
        int actualSize = actualInOrderList.size();

        assertEquals(expected, actualSize, context.build(), result -> "Tree size does not match List size");
    }
}
