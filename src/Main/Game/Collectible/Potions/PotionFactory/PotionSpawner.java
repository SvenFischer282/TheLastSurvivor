
package Main.Game.Collectible.Potions.PotionFactory;

import Main.Game.Collectible.Potions.HealPotion;
import Main.Game.Collectible.Potions.Potion;
import Main.Game.Collectible.Potions.StrengthPotion;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Manages the spawning of potions in the game.
 */
public class PotionSpawner {
    private final List<Potion> potions = new ArrayList<>();
    private final HealPotionFactory healPotionFactory;
    private final StrengthPotionFactory strengthPotionFactory;

    /**
     * Constructs a PotionSpawner and initializes potion factories.
     */
    public PotionSpawner() {
        this.healPotionFactory = new HealPotionFactory(2);
        this.strengthPotionFactory = new StrengthPotionFactory(2);
    }

    /**
     * Spawns a specified number of healing potions.
     *
     * @param amount Number of healing potions to spawn.
     */
    public void spawnHealPotion(int amount) {
        for (int i = 0; i < amount; i++) {
            HealPotion heal = healPotionFactory.createPotion();
            potions.add(heal);
        }
    }

    /**
     * Spawns a specified number of strength potions.
     *
     * @param amount Number of strength potions to spawn.
     */
    public void spawnStrengthPotion(int amount) {
        for (int i = 0; i < amount; i++) {
            StrengthPotion strength = strengthPotionFactory.createPotion();
            potions.add(strength);
        }
    }

    /**
     * Spawns a random mix of healing and strength potions.
     *
     * @param amount Number of potions to spawn.
     */
    public void spawnRandomPotion(int amount) {
        Random random = new Random();
        for (int i = 0; i < amount; i++) {
            if (random.nextBoolean()) {
                spawnHealPotion(1);
            } else {
                spawnStrengthPotion(1);
            }
        }
    }

    /**
     * Gets the list of spawned potions.
     *
     * @return List of potions.
     */
    public List<Potion> getPotions() {
        return potions;
    }
}
