package p2.binarytree;

public class SplayNode <T extends Comparable<T>> extends AbstractBinaryNode<T, SplayNode<T>>{


    /**
     * Creates a new node with the given key.
     *
     * @param key the key of the node.
     */
    public SplayNode(T key) {
        super(key);
    }

    @Override
    public String toString() {
        return "SplayNode{" +
                "key=" + getKey() +
                '}';
    }
}
