package p2.binarytree;

import org.junit.jupiter.params.ParameterizedTest;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertEquals;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.contextBuilder;

@TestForSubmission
public class ReplaceTest extends P2_TestBase {

    @ParameterizedTest
    @JsonParameterSetTest(value = "Replace.json", customConverters = "customConverters")
    public void testReplace(JsonParameterSet params) {
        RBTree<Integer> tree = params.get("RBTree");
        int nodeKey = params.getInt("nodeKey");
        Integer expectedKey = params.get("expectedKey");

        RBNode<Integer> node = tree.search(nodeKey);

        Context.Builder<?> context = contextBuilder()
                .subject("replace")
                .add("tree", treeToString(tree))
                .add("nodeKey", nodeKey)
                .add("expectedKey", expectedKey);

        RBNode<Integer> actualReplacement = tree.replace(node);


        Integer actualKey = actualReplacement == null ? null : actualReplacement.getKey();

        context.add("actualKey", actualKey);

        assertEquals(expectedKey, actualKey, context.build(),
                result -> "The replacement node key does not match the expected key");
    }


}
