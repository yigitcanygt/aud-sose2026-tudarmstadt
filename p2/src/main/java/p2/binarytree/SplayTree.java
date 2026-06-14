package p2.binarytree;

import p2.Node;

import java.util.List;
import java.util.function.Predicate;
import static org.tudalgo.algoutils.student.Student.crash;


public class SplayTree <T extends Comparable<T>> extends AbstractBinarySearchTree<T, SplayNode<T>> {

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
    @Override
    protected SplayNode<T> createNode(T key) {
        return new SplayNode<>(key);
    }

    public void rotate(SplayNode<T> node, boolean rotateLeft) {
        SplayNode<T> parent = node.getParent();

        if (rotateLeft) {
            // LEFT ROTATION (around the node)
            SplayNode<T> rightChild = node.getRight();
            node.setRight(rightChild.getLeft());
            if (rightChild.getLeft() != null) {
                rightChild.getLeft().setParent(node);
            }
            rightChild.setParent(parent);
            if (parent == null) {
                root = rightChild; // Update root
            } else if (node == parent.getLeft()) {
                parent.setLeft(rightChild);
            } else {
                parent.setRight(rightChild);
            }
            rightChild.setLeft(node);
            node.setParent(rightChild);

        } else {
            // RIGHT ROTATION (around the node)
            SplayNode<T> leftChild = node.getLeft();
            node.setLeft(leftChild.getRight());
            if (leftChild.getRight() != null) {
                leftChild.getRight().setParent(node);
            }
            leftChild.setParent(parent);
            if (parent == null) {
                root = leftChild; // Update root
            } else if (node == parent.getRight()) {
                parent.setRight(leftChild);
            } else {
                parent.setLeft(leftChild);
            }
            leftChild.setRight(node);
            node.setParent(leftChild);
        }
    }


    public void splay(SplayNode<T> node) {
        // Continue until the node becomes the root
        while (node.getParent() != null) {
            SplayNode<T> p = node.getParent();
            SplayNode<T> g = p.getParent();

            if (g == null) {
                // Zig Case: If the parent is the root, a single rotation is sufficient
                if (node == p.getLeft()) rotate(p, false);
                else rotate(p, true);
            } else if (node == p.getLeft() && p == g.getLeft()) {
                // Zig-Zig Case: Both are left children
                rotate(g, false);
                rotate(p, false);
            } else if (node == p.getRight() && p == g.getRight()) {
                // Zig-Zig Case: Both are right children
                rotate(g, true);
                rotate(p, true);
            } else if (node == p.getRight() && p == g.getLeft()) {
                // Zig-Zag Case: One is a right child, the other is a left child
                rotate(p, true);
                rotate(g, false);
            } else {
                // Zig-Zag Case: The other diagonal direction
                rotate(p, false);
                rotate(g, true);
            }
        }
    }

    /**
     * Inserts the given value into the tree.
     *
     * @param value the value to insert.
     */
    @Override
    public void insert(T value) {
// 1. Check if the node already exists using the search method (which doesn't splay)
        if (search(value) != null) return;

        // 2. Create the new node
        SplayNode<T> newNode = createNode(value);

        // 3. Add to the tree using the insert method of the superclass
        super.insert(newNode, null);

        // 4. Move the newly added node to the root using splay
        splay(newNode);
    }

    /**
     * searches for the given value in the tree and moves the last-accessed node to the root.
     * the tree is descended like in a normal BST. The last visited node is either the node containing the
     *{@code value}  if it is present in the tree or the last visited node before reching a {@code null} reference
     * if the tree is empty nothing is splayed
     * @param value the value to search for.
     * @return
     */
    public SplayNode<T> find(T value) {
        if (root == null) return null;

        SplayNode<T> current = root;
        SplayNode<T> lastVisited = null;

        // Standard BST search operation
        while (current != null) {
            lastVisited = current;
            int cmp = value.compareTo(current.getKey());

            if (cmp == 0) break; // Match found
            else if (cmp < 0) current = current.getLeft();
            else current = current.getRight();
        }

        // Move the last visited node to the root using splay (whether found or not)
        if (lastVisited != null) splay(lastVisited);

        // Return the node if the value is actually found
        if (lastVisited != null && value.compareTo(lastVisited.getKey()) == 0) {
            return lastVisited;
        }
        return null;
    }

    /**
     * removes a node with a given value from the tree
     * The node to remove is first located and moved to the root using {@link #find(Comparable)}.
     *        If the value is not present, the tree is left unchanged apart from the splaying already
     *        performed by {@code find}.
     *Otherwise the (now root) node is removed, which splits the tree into its left subtree {@code L}
     *        and its right subtree {@code R}. The two subtrees are reassembled into a single tree as
     *        follows: if {@code L} is empty, {@code R} becomes the new tree. Otherwise, the largest node of
     *        {@code L} is splayed to the root of {@code L}; this node then has no right child, so {@code R}
     *        can be attached as its right child.
     * @param value the value to remove
     */

    public void remove(T value){
        // 1. Find the node and splay it to the root
        SplayNode<T> target = find(value);
        if (target == null) return; // Terminate if the element cannot be found

        SplayNode<T> L = root.getLeft();
        SplayNode<T> R = root.getRight();

        // Detach the root
        if (L != null) L.setParent(null);
        if (R != null) R.setParent(null);

        // Reassemble the tree
        if (L == null) {
            root = R;
        } else {
            // Find the largest value in the left subtree
            SplayNode<T> maxInL = L;
            while (maxInL.getRight() != null) {
                maxInL = maxInL.getRight();
            }

            // Move the maxInL node to the root of the left subtree
            root = L;
            splay(maxInL);

            // Attach the old R tree to its right
            maxInL.setRight(R);
            if (R != null) R.setParent(maxInL);

            // Update the root of the main tree
            root = maxInL;
        }
    }


    @Override
    public void inOrder(Node<T> node, List<? super T> result) {
        if (node instanceof SplayNode<T> splayNode) {
            super.inOrder(splayNode, result);
            return;
        }
        if (node != null) throw new IllegalArgumentException("Node must be of type SplayNode");
    }

    @Override
    public void inOrder(Node<T> node, List<? super T> result, Predicate<? super T> predicate) {
        if (node instanceof SplayNode<T> splayNode) {
            super.inOrder(splayNode, result, predicate);
            return;
        }
        if (node != null) throw new IllegalArgumentException("Node must be of type SplayNode");
    }

    @Override
    public void preOrder(Node<T> node, List<? super T> result) {
        if (node instanceof SplayNode<T> splayNode) {
            super.preOrder(splayNode, result);
            return;
        }
        if (node != null) throw new IllegalArgumentException("Node must be of type SplayNode");
    }

    @Override
    public void findNext(Node<T> node, List<? super T> result, int max, Predicate<? super T> predicate) {
        if (node instanceof SplayNode<T> splayNode) {
            super.findNext(splayNode, result, max, predicate);
            return;
        }
        if (node != null) throw new IllegalArgumentException("Node must be of type SplayNode");
    }

}
