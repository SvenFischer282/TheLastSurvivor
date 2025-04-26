package Main.Game.Collectible.Potions.PotionFactory;

import Main.Game.Collectible.Potions.HealPotion;
import Main.Game.Collectible.Potions.Potion;

import java.util.Random;
import java.util.Scanner;

/**
 * Class responsible for spawning HealPotions
 */
public class HealPotionFactory implements PotionFactory {
    Random random = new Random();
    int MAP_WIDTH = 800;
    int MAP_HEIGHT = 550;
    int level;

    /**
     * Constructor for class HealPotionFactory
     * @param level Level of created potion
     */
    public HealPotionFactory(int level) {
    this.level = level;

}

    /**
     * Function wich handles creating heal potions
     * @return new Heal potion
     */
    @Override
    public HealPotion createPotion() {

        int x = random.nextInt(MAP_WIDTH)+100;
        int y = random.nextInt(MAP_HEIGHT)+100;
        return new HealPotion(x, y, this.level);

    }
}
