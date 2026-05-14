package p1.potion;
/**
 * Enum representing different types of potions, each with unique effects and a priority for ordering.
 *
 */
public enum PotionType {
    HEALING(1),
    INVISIBILITY(2),
    POWER(3),
    MASTER(4);

    private final int priority;

    PotionType(int priority) {
        this.priority = priority;
    }

    /**
     * Gets the priority of the potion type.
     *
     * @return The priority of the potion type.
     */
    public int getPriority() {
        return priority;
    }
}
