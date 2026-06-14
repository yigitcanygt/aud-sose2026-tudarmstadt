
package p2.binarytree;

import java.util.List;
import java.util.function.Predicate;

import static org.tudalgo.algoutils.student.Student.crash;

/**
 * A base implementation of a binary tree containing common methods.
 * <p>It contains the root node of the tree and provides methods for searching and inserting elements.
 *
 * <p>It assumes that only binary nodes are used,
 * i.e. every node contains exactly one key and has at most two children.
 *
 * @param <T> the type of the keys in the tree.
 * @param <N> the type of the nodes in the tree, e.g., {@link BSTNode} or {@link RBNode}.
 * @see AbstractBinaryNode
 */
public abstract class AbstractBinaryTree<T extends Comparable<T>, N extends AbstractBinaryNode<T, N>> {

    /**
    * The root node of the tree.
    */
    protected N root;

    /**
     * Returns the root node of the tree.
     *
     * @return the root node of the tree.
     */
    public N getRoot() {
        return root;
    }

    /**
     * Sets the root node of the tree.
     *
     * @param root the new root node of the tree.
     */
    public void setRoot(N root) {
        this.root = root;
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
        N x = root;
        N px = initialPX;

        while (x != null) {
            px = x;
            if (x.getKey().compareTo(node.getKey()) > 0) {
                x = x.getLeft();
            } else {
                x = x.getRight();
            }
        }

        node.setParent(px);
        if (px == initialPX) {
            root = node;
        } else if (px.getKey().compareTo(node.getKey()) > 0) {
            px.setLeft(node);
        } else {
            px.setRight(node);
        }
    }

    /**
    * Adds all elements in the subtree represented by the given node to the given list.
    * <p>
    * The elements are added in ascending order.
    * The method stops traversing the tree if the predicate returns {@code false} for one of the elements and does
    * not add any further elements. The first element which did not satisfy the predicate is also excluded.
    * It assumes that the predicate returns {@code false} for all greater values once it returned {@code false} for
    * one value.
    *
    * @param node   The root of the subtree to traverse.
    * @param result The list to store the elements in.
    * @param max  The predicate to test the elements against. If the predicate returns {@code false} for an element,
    *               the traversal stops.
    */
    protected void inOrder(N node, List<? super T> result, Predicate<? super T> max) {
        inOrderHelper(node, result, max);
    }

    private boolean inOrderHelper(N node, List<? super T> result, Predicate<? super T> max) {
        if (node == null) {
            return true; // empty element, traversal can continue
        }

        if (!inOrderHelper(node.getLeft(), result, max)) {
            return false; // left subtree has already stopped
        }

        if (!max.test(node.getKey())) {
            return false; // do not add this element and stop
        }

        result.add(node.getKey());

        return inOrderHelper(node.getRight(), result, max);
    }

    /**
    * Adds all elements in the subtree represented by the given node to the given list.
    * <p>
    * The elements are added in ascending order.
    * The method stops traversing the tree if the predicate returns {@code false} for one of the elements and does
    * not add any further elements. The first element which did not satisfy the predicate is also excluded.
    * If later elements exist that satisfy the predicate, they are excluded as well.
    *
    * @param node      The root of the subtree to traverse.
    * @param list    The list to store the elements in.
    */

    void inOrder(N node, List<? super T> list) {
        this.inOrder(node, list, _x -> true);
    }

    /**
    * Adds all elements in the subtree represented by the given node to the given list.
    * <p>
    * The elements are added in pre-order: the current node, left subtree, then the right subtree.
    * The method stops traversing the tree if the predicate returns {@code false} for one of the elements and does
    *
    * @param node   The root of the subtree to traverse.
    * @param result The list to store the elements in.
    *               the traversal stops.
    */
    protected void preOrder(N node, List<? super T> result) {
        if (node == null) {
            return;
        }

        result.add(node.getKey());
        preOrder(node.getLeft(), result);
        preOrder(node.getRight(), result);
    }

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
}
