package p2.binarytree.implementation;

import p2.binarytree.SplayNode;

public class TestSplayNode<T extends Comparable<T>> extends SplayNode<T> {

    private int getLeftCount = 0;
    private int getRightCount = 0;

    private static boolean counting = false;

    public TestSplayNode(T key) {
        super(key);
    }

    public static void startCounting() {
        counting = true;
    }

    public static void stopCounting() {
        counting = false;
    }

    @Override
    public SplayNode<T> getLeft() {
        if (counting) {
            getLeftCount++;
        }
        return super.getLeft();
    }

    @Override
    public SplayNode<T> getRight() {
        if (counting) getRightCount++;
        return super.getRight();
    }

    public int getLeftCount() {
        return getLeftCount;
    }

    public int getRightCount() {
        return getRightCount;
    }
}
