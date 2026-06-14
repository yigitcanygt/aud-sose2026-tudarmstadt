package p2.binarytree;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class BuildAVLTreeTest extends P2_TestBase {

    /*
    TODO: json datei: "BuildAVLTree_Root.json", Einträge:
      "RBTree", "Root"
    */
    @ParameterizedTest
    @JsonParameterSetTest(value = "BuildAVLTree_Root.json", customConverters = "customConverters")
    public void testBuildAVLTreeFromSortedList(JsonParameterSet params) {
        RBTree<Integer> rbTree = params.<RBTree<Integer>>get("RBTree");
        AVLTree<Integer> avlTree = new AVLTree<>();
        int expectedRootValue = params.get("Root");

        avlTree.convertToAVLTree(rbTree);

        AVLNode<Integer> actualRoot = avlTree.getRoot();

        Context.Builder<?> context = contextBuilder()
                .subject("buildAVLTree()")
                .add("expectedRoot", expectedRootValue)
                .add("actualRoot", actualRoot == null ? null : actualRoot.getKey());

        Assertions.assertNotNull(actualRoot);
        assertEquals(expectedRootValue, actualRoot.getKey(), context.build(),
                result -> "The root of the built AVL tree is incorrect");
    }


    /*
    TODO: json datei: "BuildAVLTree_Trinity.json", Einträge:
      "RBTree"
    */
    private boolean isAVL(AVLNode<?> node) {
        if (node == null) return true;

        int leftHeight = node.getLeft() != null ? node.getLeft().getHeight() : 0;
        int rightHeight = node.getRight() != null ? node.getRight().getHeight() : 0;
        int balanceFactor = Math.abs(leftHeight - rightHeight);

        // Balance-Faktor muss <= 1 sein
        if (balanceFactor > 1) return false;

        return isAVL(node.getLeft()) && isAVL(node.getRight());
    }

    @ParameterizedTest
    @JsonParameterSetTest(value = "BuildAVLTree_Trinity.json", customConverters = "customConverters")
    public void testAVLTreeIsBalancedAfterConversion(JsonParameterSet params) {
        RBTree<Integer> rbTree = params.<RBTree<Integer>>get("RBTree");
        AVLTree<Integer> avlTree = new AVLTree<>();
        avlTree.convertToAVLTree(rbTree);

        boolean balanced = isAVL(avlTree.getRoot());

        Context context = contextBuilder()
                .subject("buildAVLTree() - balance check")
                .add("expected Balance", true)
                .add("AVL balanced", balanced)
                .build();

        assertTrue(balanced, context, result -> "AVL-Tree is not balanced");
    }


    /*
    TODO: json datei: "BuildAVLTree_Trinity.json", Einträge:
      "RBTree"
    */
    private <T extends Comparable<T>> boolean isBST(AVLNode<T> node, T min, T max) {
        if (node == null) return true;

        T key = node.getKey();
        if ((min != null && key.compareTo(min) <= 0) || (max != null && key.compareTo(max) >= 0)) {
            return false;
        }

        return isBST(node.getLeft(), min, key) && isBST(node.getRight(), key, max);
    }

    @ParameterizedTest
    @JsonParameterSetTest(value = "BuildAVLTree_Trinity.json", customConverters = "customConverters")
    public void testAVLTreeIsValidBST(JsonParameterSet params) {
        RBTree<Integer> rbTree = params.<RBTree<Integer>>get("RBTree");
        AVLTree<Integer> avlTree = new AVLTree<>();
        avlTree.convertToAVLTree(rbTree);

        boolean bst = isBST(avlTree.getRoot(), null, null);

        Context context = contextBuilder()
                .subject("buildAVLTree() - BST check")
                .add("expected BST", true)
                .add("AVL BST", bst)
                .build();

        assertTrue(bst, context,result -> "Tree is not a valid BST");
    }


    /*
    TODO: json datei: "BuildAVLTree_Trinity.json", Einträge:
      "RBTree"
    */
    private boolean hasCorrectHeights(AVLNode<?> node) {
        if (node == null) return true;

        int leftHeight = node.getLeft() != null ? node.getLeft().getHeight() : 0;
        int rightHeight = node.getRight() != null ? node.getRight().getHeight() : 0;
        int expectedHeight = 1 + Math.max(leftHeight, rightHeight);

        if (node.getHeight() != expectedHeight) return false;

        return hasCorrectHeights(node.getLeft()) && hasCorrectHeights(node.getRight());
    }

    @ParameterizedTest
    @JsonParameterSetTest(value = "BuildAVLTree_Trinity.json", customConverters = "customConverters")
    public void testAVLTreeHasCorrectHeights(JsonParameterSet params) {
        RBTree<Integer> rbTree = params.<RBTree<Integer>>get("RBTree");
        AVLTree<Integer> avlTree = new AVLTree<>();
        avlTree.convertToAVLTree(rbTree);

        boolean height = hasCorrectHeights(avlTree.getRoot());

        Context context = contextBuilder()
                .subject("convertToAVLTree()")
                .add("expectedHeightProperty", true)
                .add("actually height correct", height)
                .build();

        assertTrue(height, context, result -> "Some nodes in the AVL tree have incorrect height values");
    }


    /*@Test
    public void testBuildAVLTreeFromSortedList() {
        AVLTree<Integer> avlTree = new AVLTree<>();
        RBTree<Integer> rbTree = buildExampleRBTree();
        List<Integer> sortedList = List.of(1, 2, 3, 4, 5, 6, 7);

        avlTree.convertToAVLTree(rbTree);

        int expectedRootValue = 4;
        AVLNode<Integer> actualRoot = avlTree.getRoot();

        Context.Builder<?> context = contextBuilder()
                .subject("buildAVLTree()")
                .add("inOrderList", sortedList)
                .add("expectedRoot", expectedRootValue)
                .add("actualRoot", actualRoot == null ? null : actualRoot.getKey());

        Assertions.assertNotNull(actualRoot);
        assertEquals(expectedRootValue, actualRoot.getKey(), context.build(),
                result -> "The root of the built AVL tree is incorrect");
    }*/

    /*
    TODO: wir brauchen für die testfälle der json datei "BuildAVLTree_Heights.json" hier folgende Einträge:
      "RBTree", "Height", "Root"

    @ParameterizedTest
    @JsonParameterSetTest(value = "BuildAVLTree_Heights.json", customConverters = "customConverters")
    public void testSubtreeHeightsAreCorrect(JsonParameterSet params) {
        AVLTree<Integer> avlTree = new AVLTree<>();
        RBTree<Integer> rbTree = params.<RBTree<Integer>>get("RBTree");
        int expectedHeight = params.get("Height");

        avlTree.convertToAVLTree(rbTree);

        Context.Builder<?> context = contextBuilder()
                .subject("buildAVLTree() - height check");

        AVLNode<Integer> actualRoot = avlTree.getRoot();

        assertEquals(expectedHeight, actualRoot.getHeight(), context.add("expectedHeight", expectedHeight)
                        .add("actualHeight", actualRoot.getHeight()).build(),
                result -> "Height of root node is incorrect");
    }*/

    /*@Test
    public void testSubtreeHeightsAreCorrect() {
        AVLTree<Integer> avlTree = new AVLTree<>();
        RBTree<Integer> rbTree = buildExampleRBTree();
        List<Integer> sortedList = List.of(1, 2, 3, 4, 5, 6, 7);

        avlTree.convertToAVLTree(rbTree);

        Context.Builder<?> context = contextBuilder()
                .subject("buildAVLTree() - height check");

        int expectedHeight = 3;
        AVLNode<Integer> actualRoot = avlTree.getRoot();

        assertEquals(expectedHeight, actualRoot.getHeight(), context.add("expectedHeight", expectedHeight)
                        .add("actualHeight", actualRoot.getHeight()).build(),
                result -> "Height of root node is incorrect");
    }*/

    /*@Test
    public void testAVLTreeIsBalancedAfterConversion() {
        RBTree<Integer> rbTree = buildExampleRBTree();
        AVLTree<Integer> avlTree = new AVLTree<>();
        avlTree.convertToAVLTree(rbTree);

        boolean balanced = isAVL(avlTree.getRoot());

        Context context = contextBuilder()
                .subject("buildAVLTree() - balance check")
                .add("AVL balanced", balanced)
                .build();

        assertTrue(balanced, context, result -> "AVL-Tree is not balanced");
    }*/

    /*@Test
    public void testAVLTreeHasCorrectHeightsAfterConversion() {
        RBTree<Integer> rbTree = buildExampleRBTree();
        AVLTree<Integer> avlTree = new AVLTree<>();
        avlTree.convertToAVLTree(rbTree);

        AVLNode<Integer> root = avlTree.getRoot();
        Context context = contextBuilder()
                .subject("convertToAVLTree()")
                .add("expectedHeightProperty", true)
                .build();

        assertTrue(hasCorrectHeights(root), context,
                result -> "Some nodes in the AVL tree have incorrect height values");
    }*/

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
