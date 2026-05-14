package p1.potion;

import p1.sort.ArraySortList;
import java.util.Random;

public class PotionGenerator {

    public ArraySortList<Potion> generateRandomPotionList(int size) {
        ArraySortList<Potion> potions = new ArraySortList<>(size);
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            int strength = random.nextInt(100); // Random strength between 0 and 99
            PotionType type = PotionType.values()[random.nextInt(PotionType.values().length)];
            potions.set(i, new Potion(strength, type));
        }

        return potions;
    }
}
