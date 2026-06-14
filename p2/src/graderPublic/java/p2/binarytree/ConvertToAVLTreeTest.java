package p2.binarytree;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import java.util.List;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertEquals;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.contextBuilder;

/**
 * Testet convertToAVLTree() der Klasse AVLTree.
 */
@TestForSubmission
public class ConvertToAVLTreeTest extends P2_TestBase {

    @Test
    public void testBuildAVLTreeSizeMatchesList() {
        // example RBTree
        RBTree<Integer> rbTree = buildExampleRBTree();

        // initializing AVL-Tree
        AVLTree<Integer> avlTree = new AVLTree<>();

        avlTree.convertToAVLTree(rbTree);

        List<Integer> inOrder = List.of(1, 2, 3, 4, 5, 6, 7);
        int expected = 7;

        List<Integer> actualInOrder = avlTree.inOrder();
        int actual = actualInOrder.size();

        Context.Builder<?> context = contextBuilder()
                .subject("convertToAVLTree()")
                .add("In-Order", inOrder)
                .add("AVLTree (In-Order)", actualInOrder);

        assertEquals(expected, actual, context.build(), result -> "size doesn't match");
    }

    /*
    TODO: wir brauchen für die testfälle der json datei hier folgende Einträge:
      "RBTree", "inOrder", "Size"

    @ParameterizedTest
    @JsonParameterSetTest(value = "ConvertToAVLTree_List.json", customConverters = "customConverters")
    public void testBuildAVLTreeSizeMatchesList(JsonParameterSet params) {
        // example RBTree
        RBTree<Integer> rbTree = params.<RBTree<Integer>>get("RBTree");
        List<Integer> inOrder = params.get("inOrder");
        int expectedSize = params.get("Size");


        // initializing AVL-Tree
        AVLTree<Integer> avlTree = new AVLTree<>();

        avlTree.convertToAVLTree(rbTree);


        List<Integer> actualInOrder = avlTree.inOrder();
        int actualSize = actualInOrder.size();

        Context.Builder<?> context = contextBuilder()
                .subject("convertToAVLTree()")
                .add("In-Order", inOrder)
                .add("AVLTree (In-Order)", actualInOrder);

        context.add("actual size of inOrder", actualSize);

        assertEquals(expectedSize, actualSize, context.build(), result -> "size doesn't match");
    } */

    @Test
    public void testBuildAVLTreeMatchesList() {
        // example RBTree
        RBTree<Integer> rbTree = buildExampleRBTree();

        // initializing AVL-Tree
        AVLTree<Integer> avlTree = new AVLTree<>();

        avlTree.convertToAVLTree(rbTree);

        List<Integer> inOrder = List.of(1, 2, 3, 4, 5, 6, 7);

        List<Integer> actualInOrder = avlTree.inOrder();

        Context.Builder<?> context = contextBuilder()
                .subject("convertToAVLTree()")
                .add("In-Order", inOrder)
                .add("AVLTree (In-Order)", actualInOrder);

        assertEquals(inOrder, actualInOrder, context.build(), result -> "The elements of the inOrder list don't match the expected elements");
    }

    @Test
    public void testRootIsSetCorrectlyAfterConversion() {
        // example RBTree bauen
        RBTree<Integer> rbTree = buildExampleRBTree();

        // initializing AVL-Tree
        AVLTree<Integer> avlTree = new AVLTree<>();

        avlTree.convertToAVLTree(rbTree);

        List<Integer> inOrder = List.of(1, 2, 3, 4, 5, 6, 7);

        Integer expectedRootValue = inOrder.get(inOrder.size() / 2); // 4 bei Liste von 7 Elementen

        AVLNode<Integer> actualRoot = avlTree.getRoot();

        Context.Builder<?> context = contextBuilder()
                .subject("convertToAVLTree()")
                .add("expectedRootValue", expectedRootValue)
                .add("actualRootValue", actualRoot == null ? null : actualRoot.getKey());

        Assertions.assertNotNull(actualRoot);
        assertEquals(expectedRootValue, actualRoot.getKey(), context.build(),
                result -> "Root node value does not match the expected middle element from the inOrder list");
    }

    @Test
    public void testConvertToAVLTreeNullPointer() {
        AVLTree<Integer> avlTree = new AVLTree<>();
        RBTree<Integer> rbTree = null;

        Context context = contextBuilder()
                .subject("buildAVLTree() ")
                .add("rbTree", rbTree)
                .build();

        Assertions2.assertThrows(NullPointerException.class, () -> avlTree.convertToAVLTree(null), context, result -> "Method should throw NullpointerException when RBTree is null");
    }

    private RBTree<Integer> buildExampleRBTree() {
        // example [[[,1,R,],2,B,[,3,R,]],4,B,[[,5,R,],6,B,[,7,R,]]]
        RBTree<Integer> tree = new RBTree<>();

        tree.setRoot(new RBNode<>(4, Color.BLACK));
        RBNode<Integer> n2 = new RBNode<>(2, Color.BLACK);
        RBNode<Integer> n6 = new RBNode<>(6, Color.BLACK);
        RBNode<Integer> n1 = new RBNode<>(1, Color.RED);
        RBNode<Integer> n3 = new RBNode<>(3, Color.RED);
        RBNode<Integer> n5 = new RBNode<>(5, Color.RED);
        RBNode<Integer> n7 = new RBNode<>(7, Color.RED);

        tree.getRoot().setLeft(n2);
        tree.getRoot().setRight(n6);
        n2.setLeft(n1);
        n2.setRight(n3);
        n6.setLeft(n5);
        n6.setRight(n7);

        return tree;
    }
}

