package p2;

import org.sourcegrade.jagr.api.rubric.*;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import p2.binarytree.*;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.Callable;

public class P2_RubricProvider implements RubricProvider {

    @SafeVarargs
    private static Criterion createCriterion(String shortDescription, int maxPoints, Callable<Method>... methodReferences) {
        return createCriterion(shortDescription, maxPoints, Arrays.stream(methodReferences).map(JUnitTestRef::ofMethod).toArray(JUnitTestRef[]::new));
    }

    private static Criterion createCriterion(String shortDescription, int maxPoints, JUnitTestRef... testRefs) {

        if (testRefs.length == 0) {
            return Criterion.builder()
                    .shortDescription(shortDescription)
                    .maxPoints(maxPoints)
                    .build();
        }

        Grader.TestAwareBuilder graderBuilder = Grader.testAwareBuilder();

        for (JUnitTestRef testRef : testRefs) {
            graderBuilder.requirePass(testRef);
        }

        return Criterion.builder()
                .shortDescription(shortDescription)
                .grader(graderBuilder
                        .pointsFailedMin()
                        .pointsPassedMax()
                        .build())
                .maxPoints(maxPoints)
                .build();
    }

    private static Criterion createParentCriterion(String task, String shortDescription, Criterion... children) {
        return Criterion.builder()
                .shortDescription("H" + task + " | " + shortDescription)
                .addChildCriteria(children)
                .build();
    }




    /// Criterions for H1:
    public static final Criterion H1_1_1 = createCriterion("Die Methode [[[inOrder]]] der Klasse [[[AbstractBinarySearchTree]]] funktioniert für alle Fälle korrekt", 1,
        () -> InOrderTest.class.getMethod("testInOrderComplex", JsonParameterSet.class));

    public static final Criterion H1_1 = createParentCriterion("1 a)", "Inorder", H1_1_1);



    public static final Criterion H1_2_1 = createCriterion("Die Methode [[[preOrder]]] der Klasse [[[AbstractBinarySearchTree]]] funktioniert für einfache Fälle", 1,
        () -> PreOrderTest.class.getMethod("testPreOrderSimple", JsonParameterSet.class));

    public static final Criterion H1_2 = createParentCriterion("1 b)", "Preorder", H1_2_1);



    public static final Criterion H1_3_1 = createCriterion("Die Methode [[[buildBSTFromPreorder]]] der Klasse [[[SimpleBinarySearchTree]]] erstellt einen Baum mit der selben Anzahl an Elementen wie die gegebene Liste", 2,
        () -> BuildBSTFromPreorderTest.class.getMethod("testBuildTreeSizeMatchesList"));

    public static final Criterion H1_3 = createParentCriterion("1 c)", "Build from Preorder", H1_3_1);

       public static final Criterion H1 = createParentCriterion("1", "Traversierung", H1_1, H1_2, H1_3);

    /// Criterions for H2:
    // H2 b) Rotations and splay - 4 Punkte
    public static final Criterion H2_2_1 = createCriterion("Die Methoden [[[rotateLeft]]] und [[[rotateRight]]] der Klasse [[[SplayTree]]] funktionieren korrekt", 1,
            () -> RotateSplayTreeTest.class.getMethod("testRotateLeft", JsonParameterSet.class),
            () -> RotateSplayTreeTest.class.getMethod("testRotateRight", JsonParameterSet.class));

    public static final Criterion H2_2_2 = createCriterion("Die Methode [[[splay]]] der Klasse [[[SplayTree]]] funktioniert für einfache Fälle (zig) korrekt", 1,
            () -> SplayTest.class.getMethod("testSplaySimple", JsonParameterSet.class));

    public static final Criterion H2_2_3 = createCriterion("Die Methode [[[splay]]] der Klasse [[[SplayTree]]] funktioniert für komplexe Fälle (zig-zig und zig-zag) korrekt", 2,
            () -> SplayTest.class.getMethod("testSplayComplex", JsonParameterSet.class));

    public static final Criterion H2_2 = createParentCriterion("2 b)", "Rotation und Splay", H2_2_1, H2_2_2, H2_2_3);

    // H2 d) Remove - 2 Punkte

    public static final Criterion H2_4_2 = createCriterion("Die Methode [[[remove]]] der Klasse [[[SplayTree]]] funktioniert korrekt", 2,
            () -> RemoveSplayTreeTest.class.getMethod("testRemoveSimple", JsonParameterSet.class),
            () -> RemoveSplayTreeTest.class.getMethod("testRemoveComplex", JsonParameterSet.class));

    public static final Criterion H2_4 = createParentCriterion("2 d)", "Remove", H2_4_2);


    public static final Criterion H2 = createParentCriterion("2", "Splay-Tree", H2_2, H2_4);



    ///  Criterions of H3
    // H3 a) blackHeight
    public static final Criterion H3_1_1 = createCriterion("Die Methode [[[blackHeight]]] der Klasse [[[RBTree]]] funktioniert vollständig korrekt", 1,
            () -> BlackHeightTest.class.getMethod("testBlackHeight", JsonParameterSet.class));
    public static final Criterion H3_1 = createParentCriterion("3 a)", "Black Height", H3_1_1);

    // H3 b) findBlackNodeWithBlackHeight
    public static final Criterion H3_2_1 = createCriterion("Die Methode [[[findBlackNodeWithBlackHeight]]] der Klasse [[[RBTree]]] funktioniert für Fälle, welche nach dem kleinsten Knoten suchen, korrekt", 1,
          () -> FindBlackNodeTest.class.getMethod("testFindSmallestBlackNodeComplex", JsonParameterSet.class));
    public static final Criterion H3_2 = createParentCriterion("3 b)", "Find Black Node With Black Height", H3_2_1);

    // H3 d) convertToAVLTree
    public static final Criterion H3_4_1 = createCriterion("Die Methode [[[convertToAVLTree]]] der Klasse [[[AVLTree]]] ruft korrekt die inOrder Methode auf", 1,
            () -> ConvertToAVLTreeTest.class.getMethod("testBuildAVLTreeMatchesList"));
    public static final Criterion H3_4 = createParentCriterion("3 d)", "Convert to AVL-Tree", H3_4_1);

    // H3 e) buildAVLTree
    public static final Criterion H3_5_1 = createCriterion("Die Methode [[[buildAVLTree]]] der Klasse [[[AVLTree]]] setzt die Wurzel auf den richtigen Knoten", 1,
            () -> BuildAVLTreeTest.class.getMethod("testBuildAVLTreeFromSortedList", JsonParameterSet.class));
    public static final Criterion H3_5_2 = createCriterion("Die Methode [[[buildAVLTree]]] der Klasse [[[AVLTree]]] baut einen BST", 1,
            () -> BuildAVLTreeTest.class.getMethod("testAVLTreeIsValidBST", JsonParameterSet.class));

    public static final Criterion H3_5 = createParentCriterion("3 e)", "Build AVL-Tree", H3_5_1, H3_5_2);

    // H3 g) merge
    public static final Criterion H3_7_1 = createCriterion("Die Methode [[[merge]]] der Klasse [[[AVLTree]]] funktioniert für Listen ohne Duplikate korrekt", 1,
            () -> MergeTest.class.getMethod("testMergeNoDuplicates", JsonParameterSet.class));
    public static final Criterion H3_7 = createParentCriterion("3 g)", "Merge Sorted Lists", H3_7_1);

    public static final Criterion H3 = createParentCriterion("3", "RB-Tree & AVL-Tree", H3_1, H3_2, H3_4, H3_5, H3_7);







    /// Criterions of previous exercise:

//    public static final Criterion H4_1_1 = createCriterion("Die Methode [[[blackHeight]]] der Klasse [[[RBTree]]] funktioniert vollständig korrekt", 1,
//        () -> BlackHeightTest.class.getMethod("testBlackHeight", JsonParameterSet.class));
//
//    public static final Criterion H4_1 = createParentCriterion("4 a)", "Black Height", H4_1_1);
//
//    public static final Criterion H4_2_1 = createCriterion("Die Methode [[[findBlackNodeWithBlackHeight]]] der Klasse [[[RBTree]]] funktioniert für einfache Fälle, welche nach dem kleinsten Knoten suchen, korrekt", 1,
//        () -> FindBlackNodeTest.class.getMethod("testFindSmallestBlackNodeSimple", JsonParameterSet.class));
//
//    public static final Criterion H4_2_2 = createCriterion("Die Methode [[[findBlackNodeWithBlackHeight]]] der Klasse [[[RBTree]]] funktioniert für komplexe Fälle, welche nach dem kleinsten Knoten suchen, korrekt", 1,
//        () -> FindBlackNodeTest.class.getMethod("testFindSmallestBlackNodeComplex", JsonParameterSet.class));
//
//    public static final Criterion H4_2_3 = createCriterion("Die Methode [[[findBlackNodeWithBlackHeight]]] der Klasse [[[RBTree]]] funktioniert für Fälle, welche nach dem größten Knoten suchen, korrekt", 1,
//        () -> FindBlackNodeTest.class.getMethod("testFindGreatestBlackNode", JsonParameterSet.class));
//
//    public static final Criterion H4_2 = createParentCriterion("4 b)", "Find Black Node", H4_2_1, H4_2_2, H4_2_3);
//
//    public static final Criterion H4_3_1 = createCriterion("Die Methode [[[join]]] der Klasse [[[RBTree]]] funktioniert korrekt, wenn beide Bäume die selbe Schwarzhöhe besitzten", 1,
//        JUnitTestRef.or(
//            JUnitTestRef.ofMethod(() -> JoinTest.class.getMethod("testJoinSameHeight", JsonParameterSet.class)),
//            JUnitTestRef.ofMethod(() -> JoinTest.class.getMethod("testJoinSameHeightOverride", JsonParameterSet.class))
//        ));
//
//    public static final Criterion H4_3_2 = createCriterion("Die Methode [[[join]]] der Klasse [[[RBTree]]] funktioniert korrekt, wenn der rechte Baum eine größere Schwarzhöhe besitzt", 2,
//        JUnitTestRef.or(
//            JUnitTestRef.ofMethod(() -> JoinTest.class.getMethod("testJoinRightGreater", JsonParameterSet.class)),
//            JUnitTestRef.ofMethod(() -> JoinTest.class.getMethod("testJoinRightGreaterOverride", JsonParameterSet.class))
//        ));
//
//    public static final Criterion H4_3_3 = createCriterion("Die Methode [[[join]]] der Klasse [[[RBTree]]] funktioniert korrekt, wenn der linke Baum eine größere Schwarzhöhe besitzt", 2,
//        JUnitTestRef.or(
//            JUnitTestRef.ofMethod(() -> JoinTest.class.getMethod("testJoinLeftGreater", JsonParameterSet.class)),
//            JUnitTestRef.ofMethod(() -> JoinTest.class.getMethod("testJoinLeftGreaterOverride", JsonParameterSet.class))
//        ));
//
//    public static final Criterion H4_3_4 = createCriterion("Die Methode [[[join]]] der Klasse [[[RBTree]]] funktioniert vollständig korrekt", 1,
//        JUnitTestRef.or(
//            JUnitTestRef.ofMethod(() -> JoinTest.class.getMethod("testJoinSameHeight", JsonParameterSet.class)),
//            JUnitTestRef.ofMethod(() -> JoinTest.class.getMethod("testJoinSameHeightOverride", JsonParameterSet.class))
//        ),
//        JUnitTestRef.or(
//            JUnitTestRef.ofMethod(() -> JoinTest.class.getMethod("testJoinRightGreater", JsonParameterSet.class)),
//            JUnitTestRef.ofMethod(() -> JoinTest.class.getMethod("testJoinRightGreaterOverride", JsonParameterSet.class))
//        ),
//        JUnitTestRef.or(
//            JUnitTestRef.ofMethod(() -> JoinTest.class.getMethod("testJoinLeftGreater", JsonParameterSet.class)),
//            JUnitTestRef.ofMethod(() -> JoinTest.class.getMethod("testJoinLeftGreaterOverride", JsonParameterSet.class))
//        ));
//
//    public static final Criterion H4_3 = createParentCriterion("4 c)", "Joinen von RB-Trees", H4_3_1, H4_3_2, H4_3_3, H4_3_4);
//
//    public static final Criterion H4 = createParentCriterion("4", "Joinen von RB-Trees", H4_1, H4_2, H4_3);
//    public static final Criterion H4 = createParentCriterion("4", "Separieren von RB-Trees", H4_1, H4_2);

    public static final Rubric RUBRIC = Rubric.builder()
            .title("P2")
            .addChildCriteria(H1, H2, H3)
            .build();

    @Override
    public Rubric getRubric() {
        return RUBRIC;
    }

}
