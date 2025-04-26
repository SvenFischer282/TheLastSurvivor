package Main.Game.Collectible.Potions.PotionFactory;

import Main.Game.Collectible.Potions.HealPotion;
import Main.Game.Collectible.Potions.Potion;
import Main.Game.Collectible.Potions.StrengthPotion;

import java.util.Random;
import java.util.Scanner;

/**
 * Class responsible for creating Strength potions
 */
public class StrengthPotionFactory implements PotionFactory {
    Random random = new Random();
    int MAP_WIDTH = 800;
    int MAP_HEIGHT = 550;
    int level;

    /**
     * Constructor for this class
     *
     * @param level level of potion which it will be creating
     */
    public StrengthPotionFactory(int level) {
        this.level = level;
    }

    /**
     * Function woch handles creating Strength potions
     * @return new strength potion
     */
    @Override
    public StrengthPotion createPotion() {

        int x = random.nextInt(MAP_WIDTH);
        int y = random.nextInt(MAP_HEIGHT);
        return new StrengthPotion(x, y, this.level);

    }
}
