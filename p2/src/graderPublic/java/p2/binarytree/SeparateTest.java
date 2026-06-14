package p2.binarytree;

import org.junit.jupiter.params.ParameterizedTest;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertEquals;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.contextBuilder;

@TestForSubmission
public class SeparateTest extends P2_TestBase {

    /**
     * This Test tests if during the seperate method the parents of the left and right
     * Child of the joinKey are set to the proper parent (sentinel and joinKey.parent)
     * @param params
     */

    @ParameterizedTest
    @JsonParameterSetTest(value = "Separate_SetParent.json", customConverters = "customConverters")
    public void testSetParent(JsonParameterSet params) {

        RBTree<Integer> rbTree = params.<RBTree<Integer>>get("RBTree");
        Integer joinKey = params.get("joinKey");
        RBNode<Integer> joinNode = rbTree.search(joinKey);
        RBNode<Integer> joinNodeParent = joinNode.getParent();
        RBNode<Integer> joinNodeRightChild = joinNode.getRight();


        Integer expectedLeftParentValue = null;
        Integer expectedRightParentValue = params.get("rightParentValue");

        Context.Builder<?> context = contextBuilder()
                .subject("RBTree#separate")
                .add("rbTree (this)", treeToString(rbTree))
                .add("joinKey", joinKey)
                .add("expected Left Parent Value", expectedLeftParentValue)
                .add("expected Right Parent Value", expectedRightParentValue)
                .add("fixColorsAfterInsertion", "disabled");

        List<RBTree<Integer>> actualList = rbTree.separate(joinKey);

        RBTree<Integer> actualRightRBTree = actualList.get(1);

        RBNode<Integer> leftRoot = actualList.get(0).getRoot();

        RBNode<Integer> actualLeftParent = leftRoot.getParent();
        RBNode<Integer> actualRightParent = joinNodeRightChild.getParent();

        context.add("actual Left Parent Value", actualLeftParent);
        context.add("actual Left Parent Value", actualRightParent);

        assertEquals(expectedLeftParentValue, actualLeftParent.getKey(), context.build(), result -> "joinNode's left Child's new parent after separation was incorrectly set");
        assertEquals(expectedRightParentValue, actualRightParent.getKey(), context.build(), result -> "joinNode's right Child's new parent after separation was incorrectly set ");

    }


    @ParameterizedTest
    @JsonParameterSetTest(value = "Separate_RightGreater.json", customConverters = "customConverters")
    public void testSeparate(JsonParameterSet params) {

        RBTree<Integer> rbTree = params.get("RBTree");
        Integer joinKey = params.get("joinKey");

        String expectedLeftTree = params.get("expectedFirstTree");
        String expectedRightTree = params.get("expectedSecondTree");

        Context.Builder<?> context = contextBuilder()
                .subject("RBTree#separate")
                .add("rbTree (this)", treeToString(rbTree))
                .add("joinKey", joinKey)
                .add("expectedLeftTree", expectedLeftTree)
                .add("expectedRightTree", expectedRightTree)
                .add("fixColorsAfterInsertion", "disabled");

        List<RBTree<Integer>> actualList = rbTree.separate(joinKey);
        String actualLeftTree = treeToString(actualList.get(0));
        String actualRightTree = treeToString(actualList.get(1));

        context.add("actual left tree", actualLeftTree);
        context.add("actual right tree", actualRightTree);

        assertEquals(1,1, context.build(), result -> "What the hell");

        assert(expectedRightTree.equals(actualRightTree));

        assertEquals(expectedLeftTree, actualLeftTree, context.build(), result -> "Left subtree after separation is incorrect");
        assertEquals(expectedRightTree, actualRightTree, context.build(), result -> "Right subtree after separation is incorrect");
    }


    private <T extends Comparable<T>> List<RBTree<T>> separate(RBTree<T> rbTree, T joinKey) {
        return rbTree.separate(joinKey);
    }

}