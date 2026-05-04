package p1.sort.radix;

import static org.tudalgo.algoutils.student.Student.crash;

/**
 * A {@link RadixIndexExtractor} for sorting potions by their rune-encoded names.
 *
 * <p>Potion names are composed of rune symbols drawn from a fixed magical alphabet.
 * For organizational simplicity, each rune is represented by an uppercase ASCII letter
 * (its <em>shortcut</em>) in code. The full names and ordering of the runes are:
 *
 * <table>
 *   <caption>Rune Alphabet</caption>
 *   <tr><th>Shortcut</th><th>Full Name</th><th>Order (bucket index)</th></tr>
 *   <tr><td>{@code F}</td><td>Flarebind</td><td>0 (lowest)</td></tr>
 *   <tr><td>{@code H}</td><td>Hollowsong</td><td>1</td></tr>
 *   <tr><td>{@code E}</td><td>Emberdew</td><td>2</td></tr>
 *   <tr><td>{@code Y}</td><td>Yearnmist</td><td>3</td></tr>
 *   <tr><td>{@code A}</td><td>Ashenveil</td><td>4</td></tr>
 *   <tr><td>{@code S}</td><td>Stormwhisper</td><td>5</td></tr>
 *   <tr><td>{@code D}</td><td>Duskbane</td><td>6 (highest)</td></tr>
 * </table>
 *
 * <p>The ordering is: F &lt; H &lt; E &lt; Y &lt; A &lt; S &lt; D
 *
 * <p>Position 0 corresponds to the last (least significant) rune in the name.
 * All potion names are assumed to have the same length.
 */
public class RuneIndexExtractor implements RadixIndexExtractor<String> {

    /**
     * The ordered sequence of rune shortcuts.
     * The index of a shortcut in this array is its bucket index used during radix sort.
     * These letters are shortcuts used in code for organizational simplicity;
     * their full names are listed in the class documentation.
     */
    static final char[] RUNE_ORDER = {'F', 'H', 'E', 'Y', 'A', 'S', 'D'};

    @Override
    public int extractIndex(String value, int position) {
        crash(); // TODO: H3 a) - remove if implemented
        return 0;
    }

    @Override
    public int getRadix() {
        return RUNE_ORDER.length;
    }
}
