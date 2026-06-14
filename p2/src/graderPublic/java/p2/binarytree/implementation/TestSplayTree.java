package p2.binarytree.implementation;

import p2.binarytree.SplayNode;
import p2.binarytree.SplayTree;

public class TestSplayTree<T extends Comparable<T>> extends SplayTree<T> {

    @Override
    protected SplayNode<T> createNode(T key) {
        return new TestSplayNode<>(key);
    }
}
