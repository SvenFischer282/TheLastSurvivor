package Main.Game.Collectible.Potions;

import Main.Game.Character.Player;
import Main.Game.Inventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PotionCollisionHandlerTest {
    private PotionCollisionHandler collisionHandler;
    private TestPlayer player;
    private TestInventory inventory;
    private List<Potion> potions;

    // Minimal Player implementation for testing
    static class TestPlayer extends Player {
        private float x, y;

        public TestPlayer(float x, float y) {
            super(0, 0); // Assuming Player constructor takes health, maxHealth
            this.x = x;
            this.y = y;
        }

        @Override
        public float getX() {
            return x;
        }

        @Override
        public float getY() {
            return y;
        }
    }

    // Minimal Potion implementation for testing
    static class TestPotion extends Potion {
        public TestPotion(int x, int y) {
            super(x, y, 1); // Assuming Potion constructor takes x, y, effectStrength
        }
    }

    // Minimal Inventory implementation for testing
    static class TestInventory extends Inventory {
        private final List<Potion> potions = new ArrayList<>();

        /**
         * Constructs a new Inventory for the specified player.
         *
         * @param player The player who owns this inventory
         */
        public TestInventory(Player player) {
            super(player);
        }

        public void addPotion(Potion potion) {
            potions.add(potion);
        }

        public List<Potion> getPotions() {
            return potions;
        }

        public boolean contains(Potion potion) {
            return potions.contains(potion);
        }
    }

    @BeforeEach
    void setUp() {
        player = new TestPlayer(50, 50); // Player at (50, 50)
        inventory = new TestInventory(player);
        potions = new ArrayList<>();
        collisionHandler = new PotionCollisionHandler(potions, player, inventory);
    }

    @Test
    void constructor_initializesCorrectly() {
        assertSame(potions, collisionHandler.getPotions());
        assertSame(player, collisionHandler.getPlayer());
        assertSame(inventory, collisionHandler.getInventory());
    }

    @Test
    void checkCollisions_noPotions_doesNothing() {
        collisionHandler.checkCollisions();
        assertTrue(potions.isEmpty());
        assertTrue(inventory.getPotions().isEmpty());
    }

    @Test
    void checkCollisions_potionCollides_addsToInventoryAndRemoves() {
        Potion potion = new TestPotion(60, 60); // Within player's 64x64 bounds
        potions.add(potion);

        collisionHandler.checkCollisions();

        assertTrue(potions.isEmpty(), "Potion should be removed from list");
        assertTrue(inventory.contains(potion), "Potion should be added to inventory");
        assertEquals(1, inventory.getPotions().size());
    }

    @Test
    void checkCollisions_potionDoesNotCollide_staysInList() {
        Potion potion = new TestPotion(120, 120); // Outside player's 64x64 bounds
        potions.add(potion);

        collisionHandler.checkCollisions();

        assertEquals(1, potions.size(), "Potion should remain in list");
        assertSame(potion, potions.get(0));
        assertTrue(inventory.getPotions().isEmpty(), "Inventory should be empty");
    }

    @Test
    void checkCollisions_multiplePotions_someCollide() {
        Potion collidingPotion = new TestPotion(55, 55); // Collides
        Potion nonCollidingPotion = new TestPotion(200, 200); // Does not collide
        potions.add(collidingPotion);
        potions.add(nonCollidingPotion);

        collisionHandler.checkCollisions();

        assertEquals(1, potions.size(), "Only non-colliding potion should remain");
        assertSame(nonCollidingPotion, potions.get(0));
        assertEquals(1, inventory.getPotions().size(), "Only colliding potion should be added");
        assertTrue(inventory.contains(collidingPotion));
        assertFalse(inventory.contains(nonCollidingPotion));
    }

    @Test
    void checkCollisions_potionAtPlayerPosition_collides() {
        Potion potion = new TestPotion(50, 50); // Exact player position
        potions.add(potion);

        collisionHandler.checkCollisions();

        assertTrue(potions.isEmpty(), "Potion should be removed from list");
        assertTrue(inventory.contains(potion), "Potion should be added to inventory");
        assertEquals(1, inventory.getPotions().size());
    }
}