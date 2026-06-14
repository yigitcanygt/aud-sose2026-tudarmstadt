package p2.binarytree;

import p2.SearchTree;

import java.util.List;
import java.util.function.Predicate;

import static org.tudalgo.algoutils.student.Student.crash;

/**
 * A base implementation of a binary search tree containing common methods.
 * <p>
 * It contains the root node of the tree and provides methods for searching and inserting elements.
 * <p>
 * It assumes that only binary nodes are used, i.e. every node contains exactly one key and has at most two children,
 * where the left child is smaller than the parent and the right child is greater than the parent.
 *
 * @param <T> the type of the keys in the tree.
 * @param <N> the type of the nodes in the tree, e.g., {@link BSTNode} or {@link RBNode}.
 * @see SearchTree
 * @see AbstractBinaryNode
 */
public abstract class AbstractBinarySearchTree<T extends Comparable<T>, N extends AbstractBinaryNode<T, N>> extends AbstractBinaryTree<T, N> implements BinarySearchTree<T> {

    /**
     * The root node of the tree.
     */
    protected N root;

    @Override
    public N search(T value) {

        N x = root;

        while (x != null && x.getKey().compareTo(value) != 0) {
            if (x.getKey().compareTo(value) > 0) {
                x = x.getLeft();
            } else {
                x = x.getRight();
            }
        }

        return x;
    }

    /**
     * Inserts the given node into the tree.
     *
     * @param node      the node to insert.
     * @param initialPX The initial value used for the pointer to the parent node.
     *                  This is required for implementations that use a sentinel node. For normal trees, this value
     *                  should be {@code null}.
     */
    protected void insert(N node, N initialPX) {
        N y = initialPX; // To keep track of the parent node
        N x = root;      // Start from the root to traverse the tree

        // Traverse down the tree to find the appropriate position for the new node
        while (x != null) {
            y = x;
            if (node.getKey().compareTo(x.getKey()) < 0) {
                x = x.getLeft(); // Go left if the new value is smaller
            } else {
                x = x.getRight(); // Go right if the new value is greater or equal
            }
        }

        // Set the parent of the new node
        node.setParent(y);

        // Case when the tree is empty (y hasn't changed)
        if (y == initialPX) {
            root = node;
        }
        // Assign the new node to the correct child
        else if (node.getKey().compareTo(y.getKey()) < 0) {
            y.setLeft(node);
        } else {
            y.setRight(node);
        }
    }


    /**
     * Adds all elements in the tree that are greater than or equal to the given node to the given list.
     * <p>
     * The elements are added in ascending order.
     * The method adds at most {@code max} elements.
     * The method stops traversing the tree if the predicate returns {@code false} for one of the elements and does
     * not add any further elements. The first element which did not satisfy the predicate is also excluded.
     * It assumes that the predicate returns {@code false} for all greater values once it returned {@code false} for
     * one value, i.e. it represents a limit check.
     *
     * @param node   The node to start the search from. The node itself is included in the search.
     * @param result The list to store the elements in.
     * @param max    The maximum number of elements to include in the result.
     * @param limit  The predicate to test the elements against. If the predicate returns {@code false} for an element,
     *               the traversal stops.
     */
    protected void findNext(N node, List<? super T> result, int max, Predicate<? super T> limit) {
        findNext(node, null, max, result, limit);
    }

    private void findNext(N node, N prev, int max, List<? super T> result, Predicate<? super T> predicate) {

        if ((prev == null || node.getRight() != prev) && result.size() < max) {

            if (node == null || !predicate.test(node.getKey())) return;

            result.add(node.getKey());
        }

        if (node.hasRight() && prev != node.getRight() && result.size() < max) {
            inOrder(node.getRight(), result);
        }

        if (result.size() < max && node != root) {
            findNext(node.getParent(), node, max, result, predicate);
        }
    }

    @Override
    public N findSmallest() {
        N x = root;
        while (x.hasLeft()) {
            x = x.getLeft();
        }
        return x;
    }

    @Override
    public N getRoot() {
        return root;
    }
    public void setRoot(N root) {
        this.root = root;
    }

    /**
     * Creates a new node with the given key.
     * <p>
     * The type of the node is determined by the concrete implementation. If the implementation uses additional
     * information within the node, a standard value is used for them, e.g., red for the color of a node in a
     * red-black tree.
     *
     * @param key the key of the new node.
     * @return a new node with the given key.
     */
    protected abstract N createNode(T key);

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        if (root == null) {
            sb.append("[]");
        } else {
            root.buildString(sb);
        }

        return sb.toString();
    }

}
