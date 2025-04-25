package Main.Game.Collectible.Potions.PotionFactory;

import Main.Game.Collectible.Potions.HealPotion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HealPotionFactoryTest {
    HealPotionFactory healPotionFactory;
    HealPotion healPotion;
    @BeforeEach
    void setUp() {
        healPotionFactory = new HealPotionFactory(1);
    }

    @Test
    void createPotion() {
       healPotion =  healPotionFactory.createPotion();
       assertNotNull(healPotion);
    }
}