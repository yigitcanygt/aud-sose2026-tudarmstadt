package p2.binarytree;

import p2.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static org.tudalgo.algoutils.student.Student.crash;

/**
 * An implementation of a avl tree.
 * <p>
 * An avl tree is a self-balancing binary search tree. It guarantees that the searching and inserting operation
 * have a logarithmic time complexity. This implementation has no self-balancing insert or delete functionality
 * implemented.
 *
 * @param <T> The type of the keys in the tree.
 * @see AbstractBinarySearchTree
 * @see AVLNode
 */

public class AVLTree<T extends Comparable<T>> extends AbstractBinarySearchTree<T, AVLNode<T>>{

    @Override
    public void insert(T value) {
        insert(createNode(value), null);
    }

    @Override
    public void inOrder(Node<T> node, List<? super T> result) {
        if (node instanceof AVLNode<T> avlNode) {
            super.inOrder(avlNode, result);
            return;
        }

        if (node != null) throw new IllegalArgumentException("Node must be of type AVLNode");
    }

    @Override
    public void inOrder(Node<T> node, List<? super T> result, Predicate<? super T> predicate) {
        if (node instanceof AVLNode<T> avlNode) {
            super.inOrder(avlNode, result,predicate);
            return;
        }
        if (node != null) throw new IllegalArgumentException("Node must be of type AVLNode");
    }

    @Override
    public void preOrder(Node<T> node, List<? super T> result) {
        if (node instanceof AVLNode<T> avlNode)    {
            super.preOrder(avlNode, result);
            return;
        }

        if (node != null) throw new IllegalArgumentException("Node must be of type RBNode");
    }

    @Override
    public void findNext(Node<T> node, List<? super T> result, int max, Predicate<? super T> predicate) {
        if (node instanceof AVLNode<T> avlNode) {
            super.findNext(avlNode, result, max, predicate);
            return;
        }
        if (node != null) throw new IllegalArgumentException("Node must be of type AVLNode");
    }

    @Override
    protected AVLNode<T> createNode(T key) {
        return new AVLNode<>(key);
    }

    /**
     * Converts a given Red-Black Tree to an AVL Tree.
     *
     * <p>This method performs an in-order traversal of the input Red-Black Tree to collect
     * the keys in sorted order. It then builds a balanced AVL Tree from the sorted list of keys,
     * ensuring that the resulting AVL Tree maintains the height-balance property.
     *
     * @param rbTree the Red-Black Tree to be converted to an AVL Tree.
     *                The tree must not be {@code null}.
     * @throws NullPointerException if {@code rbTree} is {@code null}.
     */

    public void convertToAVLTree(RBTree<T> rbTree) {
        if (rbTree == null) {
            throw new NullPointerException("Red-Black Tree cannot be null");
        }

        // 1. Create a list and fill it using in-order traversal
        List<T> inOrderList = new ArrayList<>();
        if (rbTree.getRoot() != null) {
            rbTree.inOrder(rbTree.getRoot(), inOrderList);
        }

        // 2 & 3. Build the AVL tree and set it as the root
        this.root = buildAVLTree(inOrderList, 0, inOrderList.size() - 1);
    }

    /**
     * Recursively builds a balanced AVL Tree from a sorted list of keys.
     *
     * <p>This method chooses the middle element of the given sublist as the root node,
     * recursively building the left and right subtrees from the corresponding sublists.
     * It also calculates and sets the height of each AVL node to maintain AVL balance.
     *
     * @param inOrderList the sorted list of keys to build the AVL Tree from.
     * @param start       the starting index of the sublist.
     * @param end         the ending index of the sublist.
     * @return the root node of the newly constructed AVL subtree,
     *         or {@code null} if {@code start > end}.
     */

    private AVLNode<T> buildAVLTree(List<T> inOrderList, int start, int end) {
        // Base case: the current list interval is empty
        if (start > end) {
            return null;
        }

        // Find the middle element to make it the root of the current subtree
        int mid = start + (end - start) / 2;
        AVLNode<T> node = createNode(inOrderList.get(mid));

        // Recursively build the left and right subtrees
        AVLNode<T> leftChild = buildAVLTree(inOrderList, start, mid - 1);
        AVLNode<T> rightChild = buildAVLTree(inOrderList, mid + 1, end);

        // Link the children and set parent references
        if (leftChild != null) {
            node.setLeft(leftChild);
            leftChild.setParent(node);
        }
        if (rightChild != null) {
            node.setRight(rightChild);
            rightChild.setParent(node);
        }

        // Calculate and set the height of the current node
        int leftHeight = (leftChild == null) ? 0 : leftChild.getHeight();
        int rightHeight = (rightChild == null) ? 0 : rightChild.getHeight();
        node.setHeight(Math.max(leftHeight, rightHeight) + 1);

        return node;
    }

    /**
     * Merges two given AVL Trees into this AVL Tree.
     *
     * <p>This method performs an in-order traversal of both input AVL Trees to collect
     * their keys in sorted order. It then merges the two sorted lists into one sorted list
     * without duplicates and builds a balanced AVL Tree from the result.
     *
     * @param AVLTree1 the first AVL Tree to merge. The tree must not be {@code null}.
     * @param AVLTree2 the second AVL Tree to merge. The tree must not be {@code null}.
     * @throws NullPointerException if either {@code AVLTree1} or {@code AVLTree2} is {@code null}.
     */
    public void mergeAVLTrees(AVLTree<T> AVLTree1, AVLTree<T> AVLTree2) {
        if (AVLTree1 == null || AVLTree2 == null) {
            throw new NullPointerException("AVL Trees to merge cannot be null");
        }

        List<T> list1 = new ArrayList<>();
        List<T> list2 = new ArrayList<>();

        // 1. Fill lists with the values of the AVL trees in in-order sequence
        if (AVLTree1.getRoot() != null) {
            AVLTree1.inOrder(AVLTree1.getRoot(), list1);
        }
        if (AVLTree2.getRoot() != null) {
            AVLTree2.inOrder(AVLTree2.getRoot(), list2);
        }

        // 2. Merge the two lists into a single sorted list without duplicates
        List<T> mergedList = merge(list1, list2);

        // 3. Build the new AVL tree and set the root
        this.root = buildAVLTree(mergedList, 0, mergedList.size() - 1);
    }
    /**
     * Merges two sorted lists of keys into one sorted list without duplicates.
     *
     * <p>The returned list contains all keys that appear in either {@code list1} or {@code list2},
     * in ascending order. If a key appears in both lists, it is included only once.
     *
     * @param list1 the first sorted list of keys. The list must not be {@code null}.
     * @param list2 the second sorted list of keys. The list must not be {@code null}.
     * @return a new sorted list containing all unique keys from both lists in ascending order.
     */
    public List<T> merge(List<T> list1, List<T> list2) {
        List<T> result = new ArrayList<>();
        int i = 0, j = 0;

        // Merge process similar to MergeSort, but handling duplicates
        while (i < list1.size() && j < list2.size()) {
            T val1 = list1.get(i);
            T val2 = list2.get(j);

            int cmp = val1.compareTo(val2);
            if (cmp < 0) {
                result.add(val1);
                i++;
            } else if (cmp > 0) {
                result.add(val2);
                j++;
            } else {
                // If values are equal, add only once and advance both pointers
                result.add(val1);
                i++;
                j++;
            }
        }

        // Add remaining elements from list1 (if any)
        while (i < list1.size()) {
            result.add(list1.get(i++));
        }

        // Add remaining elements from list2 (if any)
        while (j < list2.size()) {
            result.add(list2.get(j++));
        }

        return result;
    }
}
