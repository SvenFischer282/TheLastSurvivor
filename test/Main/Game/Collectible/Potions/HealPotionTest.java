package Main.Game.Collectible.Potions;

import Main.Game.Character.Player;
import Main.Utils.Exceptions.NegativeValueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Main.Utils.Exceptions.NegativeValueException;

import static org.junit.jupiter.api.Assertions.*;

class HealPotionTest {
HealPotion healPotion;
Player player;
    @BeforeEach
    void setUp() {
        player = new Player(10,10);
        healPotion = new HealPotion(10,10,1);
    }

    @Test
    void use() {
        healPotion.use(player);
        assertEquals(10, player.getHealth());

    }


    @Test
    void getDescription() {
        assertEquals("This potion heals you", healPotion.getDescription());
    }
}