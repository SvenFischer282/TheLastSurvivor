package Main.Game.Collectible.Potions.PotionFactory;

import Main.Game.Collectible.Potions.Potion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PotionSpawnerTest {
    PotionSpawner potionSpawner;

    HealPotionFactory healPotionFactory;
    StrengthPotionFactory strengthPotionFactory;

    @BeforeEach
    void setUp() {
        potionSpawner = new PotionSpawner();

        healPotionFactory = new HealPotionFactory(1);
        strengthPotionFactory = new StrengthPotionFactory(1);
    }


    @Test
    void spawnHealPotion() {
        potionSpawner.spawnHealPotion(1);
        assertEquals(1, potionSpawner.getPotions().size());
    }

    @Test
    void spawnStrengthPotion() {
        potionSpawner.spawnStrengthPotion(1);
        assertEquals(1, potionSpawner.getPotions().size());
    }

    @Test
    void spawnRandomPotion() {
        potionSpawner.spawnRandomPotion(5);
        assertEquals(5, potionSpawner.getPotions().size());
    }

    @Test
    void getPotions() {
        potionSpawner.spawnHealPotion(1);
        potionSpawner.spawnStrengthPotion(1);
        assertEquals(2, potionSpawner.getPotions().size());

    }
}