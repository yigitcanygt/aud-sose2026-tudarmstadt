package p2.gui;

import javafx.application.Platform;
import p2.binarytree.Color;
import p2.binarytree.RBNode;
import p2.binarytree.SplayNode;
import p2.binarytree.SplayTree;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public class SplayTreeAnimation<T extends Comparable<T>> extends SplayTree<T> implements AnimatedBinaryTree<T> {
    private BinaryTreeAnimationScene<T> animationScene;

    private boolean animate = false;
    private boolean finishWithNextStep = false;


    @Override
    public void insert(T value) {
        if (search(value) != null) return;  // duplicate check, no splaying
        SplayNode<T> node = createNode(value);
        insert(node, null);
        splay(node);
    }
    @Override
    public void init(BinaryTreeAnimationScene<T> animationScene) {
        this.animationScene = animationScene;
    }

    @Override
    protected SplayNode<T> createNode(T key) {
        return new AnimatedSplayNode(key);
    }

    @Override
    public void turnOnAnimation() {
        animate = true;
    }

    @Override
    public void turnOffAnimation() {
        animate = false;
    }

    @Override
    public boolean isAnimating() {
        return animate;
    }

    @Override
    public boolean isFinishingWithNextStep() {
        return finishWithNextStep;
    }

    @Override
    public void disableFinishWithNextStep() {
        finishWithNextStep = false;
    }

    @Override
    public void finishWithNextStep() {
        finishWithNextStep = true;
    }

    private void updateState(StackTraceElement[] stackTrace, String operation) {
        animationScene.getAnimationState().setStackTrace(Arrays.stream(stackTrace)
                .filter(e -> e.getClassName().startsWith("p2"))
                .filter(e -> !e.getClassName().contains(this.getClass().getSimpleName()))
                .toArray(StackTraceElement[]::new));
        animationScene.getAnimationState().setOperation(operation);
    }

    public class AnimatedSplayNode extends SplayNode<T> {

        public AnimatedSplayNode(T key) {
            super(key);
        }
        @Override
        public SplayNode<T> getLeft() {
            SplayNode<T> left = super.getLeft();

            if (animate && left != null) {
                StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

                Platform.runLater(() -> {
                    updateState(stackTrace, "(%s).getLeft()".formatted(getKey()));
                    animationScene.refresh(this, left);
                });
                waitUntilNextStep();
            }
            return left;
        }

        @Override
        protected void setLeft(SplayNode<T> left) {
            super.setLeft(left);

            if (animate) {
                StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

                Platform.runLater(() -> {
                    updateState(stackTrace, "(%s).setLeft(%s)".formatted(getKey(), left == null ? "null" : left.getKey()));
                    runWithoutAnimation(() -> animationScene.getTreePane().setTree(getRoot()));
                    animationScene.refresh(this, left);
                });
                waitUntilNextStep();
            }
        }

        @Override
        public SplayNode<T> getRight() {
            SplayNode<T> right = super.getRight();

            if (animate && right != null) {
                StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

                Platform.runLater(() -> {
                    updateState(stackTrace, "(%s).getRight()".formatted(getKey()));
                    animationScene.refresh(this, right);
                });
                waitUntilNextStep();
            }

            return right;
        }

        @Override
        protected void setRight(SplayNode<T> right) {
            super.setRight(right);

            if (animate) {
                StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

                Platform.runLater(() -> {
                    updateState(stackTrace, "(%s).setRight(%s)".formatted(getKey(), right == null ? "null" : right.getKey()));
                    runWithoutAnimation(() -> animationScene.getTreePane().setTree(getRoot()));
                    animationScene.refresh(this, right);
                });
                waitUntilNextStep();
            }
        }

        @Override
        public SplayNode<T> getParent() {
            SplayNode<T> parent = super.getParent();

            if (animate && parent != null) {
                StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

                Platform.runLater(() -> {
                    updateState(stackTrace, "(%s).getParent()".formatted(getKey()));
                    animationScene.refresh(this, parent);
                });
                waitUntilNextStep();
            }

            return parent;
        }
        @Override
        protected void setParent(SplayNode<T> parent) {
            super.setParent(parent);

            if (animate) {
                StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

                Platform.runLater(() -> {
                    updateState(stackTrace, "(%s).setParent(%s)".formatted(getKey(), parent == null ? "null" : parent.getKey()));
                    runWithoutAnimation(() -> animationScene.getTreePane().setTree(getRoot()));
                    animationScene.refresh(this, parent);
                });
                waitUntilNextStep();
            }
        }
    }



}
