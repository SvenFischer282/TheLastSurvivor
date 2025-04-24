package Main.Game.Collectible.Potions.PotionFactory;

import Main.Game.Collectible.Potions.HealPotion;
import Main.Game.Collectible.Potions.Potion;
import Main.Game.Collectible.Potions.StrengthPotion;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PotionSpawner {
    private final List<Potion> potions = new ArrayList<>();
    private final HealPotionFactory healPotionFactory;
    private final StrengthPotionFactory strengthPotionFactory;


    public PotionSpawner() {
        this.healPotionFactory = new HealPotionFactory(2);
        this.strengthPotionFactory = new StrengthPotionFactory(2);
    }
    public void spawnHealPotion(int amount) {
        for (int i = 0; i < amount; i++) {
            HealPotion heal = healPotionFactory.createPotion();
            potions.add(heal);

        }
    }
    public void spawnStrengthPotion(int amount) {
        for (int i = 0; i < amount; i++) {
            StrengthPotion strength = strengthPotionFactory.createPotion();
            potions.add(strength);
        }
    }
    public void spawnRandomPotion(int amount) {
        Random random = new Random();
        for (int i = 0; i < amount; i++) {
            if(random.nextBoolean()) {
                spawnHealPotion(1);
            }else {
                spawnStrengthPotion(1);
            }
        }
    }
    public List<Potion> getPotions() {
        return potions;
    }

}
