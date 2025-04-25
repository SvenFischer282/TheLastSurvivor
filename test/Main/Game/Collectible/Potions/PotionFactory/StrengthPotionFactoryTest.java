package Main.Game.Collectible.Potions.PotionFactory;

import Main.Game.Collectible.Potions.StrengthPotion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StrengthPotionFactoryTest {
StrengthPotionFactory factory;
StrengthPotion potion;
    @BeforeEach
    void setUp() {
        factory = new StrengthPotionFactory(1);
    }

    @Test
    void createPotion() {
        potion = factory.createPotion();
        assertNotNull(potion);
    }
}