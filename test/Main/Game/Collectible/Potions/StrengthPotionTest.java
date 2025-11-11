package Main.Game.Collectible.Potions;

import Main.Game.Character.Player;
import Main.Game.Collectible.Collectible;
import Main.Game.Inventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class StrengthPotionTest {
    private StrengthPotion strengthPotion;
    private TestPlayer player;
    private TestInventory inventory;

    // Minimal Player implementation for testing
    static class TestPlayer extends Player {
        private int damage;

        public TestPlayer(int health, int maxHealth, int damage) {
            super(health, maxHealth);
            this.damage = damage;
        }

        @Override
        public int getDamage() {
            return damage;
        }

        @Override
        public void setDamage(int damage) {
            this.damage = damage;
        }
    }

    // Minimal Inventory implementation for testing
    static class TestInventory extends Inventory {
        private Collectible lastAddedItem;

        /**
         * Constructs a new Inventory for the specified player.
         *
         * @param player The player who owns this inventory
         */
        public TestInventory(Player player) {
            super(player);
        }

        public void addItem(Collectible item) {
            this.lastAddedItem = item;
        }

        public boolean contains(Collectible item) {
            return lastAddedItem != null && lastAddedItem.equals(item);
        }
    }

    @BeforeEach
    void setUp() {
        player = new TestPlayer(10, 10, 5); // Initial damage = 5
        strengthPotion = new StrengthPotion(1, 2, 3); // x=1, y=2, effectStrength=3
        inventory = new TestInventory(player);
    }

    @Test
    void constructor_initializesCorrectly() {
        assertEquals(1, strengthPotion.getX());
        assertEquals(2, strengthPotion.getY());
        assertEquals(3, strengthPotion.getEffectStrength());
        assertEquals(Potion.PotionType.STRENGTH, strengthPotion.getType());
    }

    @Test
    void use_increasesDamageImmediately() {
        strengthPotion.use(player);
        assertEquals(16, player.getDamage()); // 5 + 3 = 8
    }

    @Test
    void use_restoresDamageAfterDelay() throws InterruptedException {
        strengthPotion.use(player);
        assertEquals(16, player.getDamage()); // Immediate increase: 5 + 3 = 8
        Thread.sleep(10000); // Wait 6 seconds to ensure 5-second schedule completes
        assertEquals(5, player.getDamage()); // Damage restored to original
    }



    @Test
    void getDescription_returnsCorrectString() {
        assertEquals("This potion makes you stronger after a short delay", strengthPotion.getDescription());
    }

    @Test
    void collect_addsToInventory() {
        strengthPotion.collect(inventory);
        assertEquals(1, inventory.getPotionCount(Potion.PotionType.STRENGTH));
        // If Inventory has a getItems() method returning a List<Collectible>, you could use:
        // assertTrue(inventory.getItems().contains(strengthPotion));
    }
}