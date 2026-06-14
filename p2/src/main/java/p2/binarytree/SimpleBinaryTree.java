package p2.binarytree;

import p2.Node;

import java.util.List;
import java.util.function.Predicate;
import static org.tudalgo.algoutils.student.Student.crash;


/**
 * A simple implementation of a binary search tree.
 *
 * @param <T> The type of the keys in the tree.
 * @see AbstractBinarySearchTree
 */
public class SimpleBinaryTree<T extends Comparable<T>> extends AbstractBinaryTree<T, BSTNode<T>> {
    @Override
    protected BSTNode<T> createNode(T key) {
        return new BSTNode<>(key);
    }

    public void insert(T value) {
        super.insert(this.createNode(value), null);
    }

    /**
     * Adds all elements in the subtree represented by the given node to the given list.
     *
     * <p>The elements are added in ascending order.
     * The method adds at most {@code max} elements.
     *
     * @param node   The root of the subtree to traverse.
     * @param result The list to store the elements in.
     */
    public void inOrder(Node<T> node, List<? super T> result) {
        if (node instanceof BSTNode<T> bstNode) {
            super.inOrder(bstNode, result);
            return;
        }

        if (node != null) {
            throw new IllegalArgumentException("Node must be of type BSTNode");
        }
    }

    /**
     * Adds all elements in the subtree represented by the given node to the given list.
     *
     * <p>The elements are added in ascending order.
     * The method adds at most {@code max} elements.
     * The method stops traversing the tree if the predicate returns {@code false} for one of the elements and does
     * not add any further elements. The first element which did not satisfy the predicate is also excluded.
     * It assumes that the predicate returns {@code false} for all greater values once it returned {@code false} for
     * one value, i.e. it represents a limit check.
     *
     * @param node       The root of the subtree to traverse.
     * @param result     The list to store the elements in.
     * @param predicate  The predicate to test the elements against. If the predicate returns {@code false} for an element,
     *                   the traversal stops.
     */
    public void inOrder(Node<T> node, List<? super T> result, Predicate<? super T> predicate) {
        if (node instanceof BSTNode<T> bstNode) {
            super.inOrder(bstNode, result, predicate);
            return;
        }

        if (node != null) {
            throw new IllegalArgumentException("Node must be of type BSTNode");
        }
    }

    /**
     * Adds all elements in the subtree represented by the given node to the given list.
     *
     * <p>The elements are added in pre-order: the current node, left subtree, then the right subtree.
     *
     * @param node       The root of the subtree to traverse.
     * @param result     The list to store the elements in.
     */
    public void preOrder(Node<T> node, List<? super T> result) {
        if (node instanceof BSTNode<T> rbNode) {
            super.preOrder(rbNode, result);
            return;
        }

        if (node != null) {
            throw new IllegalArgumentException("Node must be of type BSTNode");
        }
    }


    /**
     * Builds a Binary Tree (BT) from a given preorder traversal list.
     *
     * <p>This method reconstructs a BT assuming the input lists represent a valid preorder
     * and inorder traversal of such a tree. The method then runs a recursive process to build
     * a valid Binary Tree from the preorder and inorder.
     *
     * @param preorder the list of keys representing preorder traversal of a Binary Tree
     * @throws NullPointerException if the preorder list is {@code null}
     */
    public void buildBTFromPreorder(List<T> preorder, List<T> inorder) {
        if (preorder == null) {
            throw new NullPointerException();
        }

        this.root = build(preorder, inorder, 0, preorder.size() - 1, 0, inorder.size() - 1);
    }

    /**
     * Recursively builds a BT (Binary Tree) from a preorder traversal list using bounds.
     *
     * <p>This method is called in preorder (root, left, right) and uses upper and lower
     * bounds to ensure the BT property is maintained during reconstruction. It constructs the
     * left subtree before the right subtree because the preorder list is being processed from start to end.
     *
     * @param preorder the list of keys in preorder traversal
     * @param inorder the list of keys in inorder traversal
     * @param preorderLower the lower bound for allowable key values (inclusive)
     * @param preorderUpper the upper bound for allowable key values (inclusive)
     * @param inorderLower the lower bound for the allowable key values (inclusive)
     * @param inorderUpper the upper bound for the allowable key values (inclusive)
     * @return the root node of the subtree constructed from the current preorder index
     */
    private BSTNode<T> build(List<T> preorder, List<T> inorder, int preorderLower, int preorderUpper, int inorderLower, int inorderUpper) {
        if (preorderLower > preorderUpper) {
            return null;
        }

        T rootValue = preorder.get(preorderLower);
        BSTNode<T> node = createNode(rootValue);

        // Find the index of the root in the inorder list
        int rootIndexInorder = inorderLower;
        while (!inorder.get(rootIndexInorder).equals(rootValue)) {
            rootIndexInorder++;
        }

        int leftSize = rootIndexInorder - inorderLower;

        // left subtree first, then right subtree (preorder is traversed forward)
        BSTNode<T> leftChild = build(preorder, inorder,
                preorderLower + 1, preorderLower + leftSize,
                inorderLower, rootIndexInorder - 1);

        BSTNode<T> rightChild = build(preorder, inorder,
                preorderLower + leftSize + 1, preorderUpper,
                rootIndexInorder + 1, inorderUpper);

        if (leftChild != null) {
            node.setLeft(leftChild);
            leftChild.setParent(node);
        }

        if (rightChild != null) {
            node.setRight(rightChild);
            rightChild.setParent(node);
        }

        return node;
    }
}
