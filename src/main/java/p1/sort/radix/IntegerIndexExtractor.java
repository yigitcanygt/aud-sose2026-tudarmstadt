package p1.sort.radix;

import static org.tudalgo.algoutils.student.Student.crash;

/**
 * An {@link RadixIndexExtractor} for extracting the index corresponding to a digit in an integer value.
 *
 * <p>The digit at the given position in the {@code radix}-base representation of the value is extracted.
 * Position 0 corresponds to the least significant digit (ones place).
 *
 * <p>If {@code position} is greater than or equal to the number of digits in the value, {@code 0} is
 * returned as a padding value. This allows sorting integers of different lengths correctly — shorter
 * numbers are implicitly padded with leading zeros at positions beyond their most significant digit.
 */
public class IntegerIndexExtractor implements RadixIndexExtractor<Integer> {

    /**
     * The radix (base) of this extractor. The number is interpreted in this base to extract its digits.
     */
    private final int radix;

    /**
     * Creates a new {@link IntegerIndexExtractor} instance.
     * @param radix The radix (base) of the extractor.
     */
    public IntegerIndexExtractor(int radix) {
        if (radix < 1) {
            throw new IllegalArgumentException("The radix must be greater than 0.");
        }

        this.radix = radix;
    }

    @Override
    public int extractIndex(Integer value, int position) {
        crash(); // TODO: H3 b) - remove if implemented
        return 0;
    }

    @Override
    public int getRadix() {
        return radix;
    }
}
