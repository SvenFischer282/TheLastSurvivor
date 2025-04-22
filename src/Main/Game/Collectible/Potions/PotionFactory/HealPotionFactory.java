package Main.Game.Collectible.Potions.PotionFactory;

import Main.Game.Collectible.Potions.HealPotion;
import Main.Game.Collectible.Potions.Potion;

import java.util.Random;
import java.util.Scanner;


public class HealPotionFactory implements PotionFactory {
    Random random = new Random();
    int MAP_WIDTH = 800;
    int MAP_HEIGHT = 550;
    int level;
public HealPotionFactory(int level) {
    this.level = level;

}
    @Override
    public HealPotion createPotion() {

        int x = random.nextInt(MAP_WIDTH)+100;
        int y = random.nextInt(MAP_HEIGHT)+100;
        return new HealPotion(x, y, this.level);

    }
}
