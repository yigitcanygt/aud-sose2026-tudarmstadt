package p2.binarytree;

import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertEquals;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.contextBuilder;

@TestForSubmission
public class SwapValuesTest extends P2_TestBase {

    @Test
    public void testSwapValues() {

        RBNode<Integer> u = new RBNode<>(5, Color.BLACK);
        RBNode<Integer> v = new RBNode<>(10, Color.BLACK);
        RBTree<Integer> tree = new RBTree<>();

        Context.Builder<?> context = contextBuilder()
                .subject("swap values")
                .add("Node u", 5)
                .add("node v", 10)
                .add("Node u expected after swap", 10)
                .add("Node v expected after swap", 5);

        // Perform swap
        swapValues(tree, u, v);

        context.add("Node u actual after swap", u.getKey());
        context.add("Node v actual after swap", v.getKey());



        // Verify swapped
        assertEquals(10, u.getKey(), context.build(),
                result -> "Node u's Value was not correctly swapped");

        assertEquals(5, v.getKey(), context.build(),
                result -> "Node v's Value was not correctly swapped");
    }

    private <T extends Comparable<T>> void swapValues(RBTree<T> tree, RBNode<T> u, RBNode<T> v) {
        tree.swapValues(u, v);
    }
}
