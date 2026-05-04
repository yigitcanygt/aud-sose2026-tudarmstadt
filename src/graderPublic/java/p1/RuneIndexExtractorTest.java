package p1;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junitpioneer.jupiter.json.JsonClasspathSource;
import org.junitpioneer.jupiter.json.Property;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import p1.sort.radix.RuneIndexExtractor;
import p1.transformers.MethodInterceptor;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertEquals;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.contextBuilder;

@TestForSubmission
public class RuneIndexExtractorTest {

    @BeforeEach
    public void setup() {
        MethodInterceptor.reset();
    }

    @AfterEach
    public void checkIllegalMethods() {
        IllegalMethodsCheck.checkMethods("^java/lang/String.+", "^java/lang/Character.+");
    }

    @ParameterizedTest
    @JsonClasspathSource(value = "H3a_RuneIndexExtractorTests.json", data = "validRuneTest")
    public void testExtractRuneIndex(@Property("value") String value,
                                     @Property("position") int position,
                                     @Property("expected") int expected) {
        Context context = contextBuilder()
            .subject("RuneIndexExtractor#extractIndex")
            .add("value", value)
            .add("position", position)
            .add("expected", expected)
            .build();

        assertEquals(expected, new RuneIndexExtractor().extractIndex(value, position),
            context, result -> "The method extractIndex did not return the expected bucket index for the rune at the given position.");
    }
}
