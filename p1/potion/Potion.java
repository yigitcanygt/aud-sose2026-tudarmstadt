package p1.potion;

/**
 * Represents a potion with a specific name, type and strength.
 * Potions can be compared based on their type priority and strength.
 */
public class Potion implements Comparable<Potion>{

    /**
     * Name of the Potion
     */
    private String name;
    /**
     * Strength of the Potion
     */
    private int strength;
    /**
     *  Type of Potion
     */
    private PotionType potionType;

    public Potion(int strength, PotionType potionType) {
        this.strength = strength;
        this.potionType = potionType;
    }

    /**
     * Gets the strength of the potion.
     *
     * @return The strength of the potion.
     */
    public int getStrength() {
        return strength;
    }

    @Override
    public int compareTo(Potion otherPotion) {
        int strengthComparison = Integer.compare(this.strength, otherPotion.strength);
        if (strengthComparison != 0) {
            return strengthComparison;
        }
        return Integer.compare(this.potionType.getPriority(), otherPotion.potionType.getPriority());
    }

    @Override
    public String toString() {
        return "Potion{" +
                "strength=" + strength +
                '}';
    }
}
