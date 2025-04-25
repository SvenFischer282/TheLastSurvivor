package Main.Game.Collectible.Potions;

import Main.Game.Character.Player;
import Main.Game.Inventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PotionTest {

    private Potion potion;
    private TestInventory inventory;
    private TestPlayer player;

    @BeforeEach
    void setUp() {
        potion = new Potion(10, 20, 5);
        inventory = new TestInventory();
        player = new TestPlayer();
    }

    @Test
    void testInitialValues() {
        assertEquals(10, potion.getX());
        assertEquals(20, potion.getY());
        assertEquals("Plain potion", potion.getName());
        assertEquals(5, potion.getEffectStrength());
        assertEquals(Potion.PotionType.PLAIN, potion.getType());
    }

    @Test
    void testSetAndGetEffectStrength() {
        potion.setEffectStrength(7);
        assertEquals(7, potion.getEffectStrength());
    }

    @Test
    void testSetAndGetType() {
        potion.setType(Potion.PotionType.HEAL);
        assertEquals(Potion.PotionType.HEAL, potion.getType());
    }

    @Test
    void testGetDescription() {
        assertEquals("This potion is used for brewing potions. It does not have any effects on its own", potion.getDescription());
    }

    @Test
    void testCollectAddsPotionToInventory() {
        assertEquals(0, inventory.getPotionCount(Potion.PotionType.PLAIN));
        potion.collect(inventory);
        assertEquals(1, inventory.getPotionCount(Potion.PotionType.PLAIN));
    }

    @Test
    void testUsePlainPotionDoesNotCrash() {
        // Just check that it runs without errors
        potion.use(player);
    }

    // Minimal stub class for Inventory
    static class TestInventory extends Inventory {
        public TestInventory() {
            super(new TestPlayer());
        }
    }

    // Minimal stub class for Player
    static class TestPlayer extends Player {
        public TestPlayer() {
            super(100, 10);  // Some dummy health and attack values
        }
    }
}
