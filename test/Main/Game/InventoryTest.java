package Main.Game;

import Main.Game.Character.Player;
import Main.Game.Collectible.Potions.Potion;
import Main.Utils.Observer.GameStateObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {

    private Inventory inventory;
    private TestPlayer player;

    @BeforeEach
    void setUp() {
        player = new TestPlayer();
        inventory = new Inventory(player);
    }

    @Test
    void testAddPotionIncreasesCount() {
        TestPotion potion = new TestPotion(Potion.PotionType.HEAL);
        assertEquals(0, inventory.getPotionCount(Potion.PotionType.HEAL));
        inventory.addPotion(potion);
        assertEquals(1, inventory.getPotionCount(Potion.PotionType.HEAL));
    }

    @Test
    void testUsePotionRemovesPotion() {
        TestPotion potion = new TestPotion(Potion.PotionType.STRENGTH);
        inventory.addPotion(potion);
        assertEquals(1, inventory.getPotionCount(Potion.PotionType.STRENGTH));
        assertTrue(inventory.usePotion(Potion.PotionType.STRENGTH));
        assertEquals(0, inventory.getPotionCount(Potion.PotionType.STRENGTH));
        assertTrue(potion.wasUsed);
    }

    @Test
    void testUsePotionReturnsFalseWhenNoneAvailable() {
        assertFalse(inventory.usePotion(Potion.PotionType.HEAL));
    }

    @Test
    void testAddObserverAndNotify() {
        TestObserver observer = new TestObserver();
        inventory.addObserver(observer);
        assertFalse(observer.updated);
        inventory.addPotion(new TestPotion(Potion.PotionType.PLAIN));
        assertTrue(observer.updated);
    }

    @Test
    void testRemoveObserver() {
        TestObserver observer = new TestObserver();
        inventory.addObserver(observer);
        inventory.removeObserver(observer);
        inventory.addPotion(new TestPotion(Potion.PotionType.PLAIN));
        assertFalse(observer.updated);
    }

    @Test
    void testGetPotionCountWithMultipleTypes() {
        inventory.addPotion(new TestPotion(Potion.PotionType.HEAL));
        inventory.addPotion(new TestPotion(Potion.PotionType.STRENGTH));
        inventory.addPotion(new TestPotion(Potion.PotionType.HEAL));
        assertEquals(2, inventory.getPotionCount(Potion.PotionType.HEAL));
        assertEquals(1, inventory.getPotionCount(Potion.PotionType.STRENGTH));
        assertEquals(0, inventory.getPotionCount(Potion.PotionType.PLAIN));
    }

    // Stub Player class
    static class TestPlayer extends Player {
        public TestPlayer() {
            super(100, 10);
        }
    }

    // Stub Potion with type and usage tracking
    static class TestPotion extends Potion {
        boolean wasUsed = false;

        public TestPotion(PotionType type) {
            super(0, 0, 5);
            setType(type);
        }

        @Override
        public void use(Player player) {
            wasUsed = true;
        }
    }

    // Stub GameStateObserver
    static class TestObserver implements GameStateObserver {
        boolean updated = false;

        @Override
        public void update() {
            updated = true;
        }
    }
}
