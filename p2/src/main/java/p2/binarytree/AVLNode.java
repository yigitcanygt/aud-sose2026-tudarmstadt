package p2.binarytree;

/**
 * A node in a {@link AVLTree}.
 * Additionally, to the normal properties of a {@link AbstractBinaryNode}, it also has a height Attribute,
 *
 * @param <T> the type of the key in the node.
 * @see AVLTree
 * @see AbstractBinaryNode
 */
public class AVLNode<T extends Comparable<T>> extends AbstractBinaryNode<T, AVLNode<T>> {

    private int height = 0;

    public AVLNode(T key) {
        super(key);
    }

    public int getHeight() {return height;}

    public void setHeight(int height) {this.height = height;}

    @Override
    public String toString() {
        return "BSTNode{" +
                "key=" + getKey() +
                '}';
    }
}


