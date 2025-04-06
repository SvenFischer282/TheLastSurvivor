package Main.Game.Character;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CharacterTest {
    // Concrete implementation for testing
    private class TestCharacter extends Character {
        public TestCharacter(int health, int positionX, int positionY, int damage) {
            super(health, positionX, positionY, damage);
        }

        @Override
        public void update(float deltaTime, Player player) {
            // Empty implementation for testing
        }
    }

    private TestCharacter character;

    @BeforeEach
    void setUp() {
        // Initialize with default values before each test
        character = new TestCharacter(100, 5, 10, 20);
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals(100, character.getHealth());
        assertEquals(5.0f, character.getX());
        assertEquals(10.0f, character.getY());
        assertEquals(20, character.getDamage());
    }

    @Test
    void testSetHealth() {
        character.setHealth(50);
        assertEquals(50, character.getHealth());
    }

    @Test
    void testTakeDamage() {
        character.takeDamage(30);
        assertEquals(70, character.getHealth());
    }

    @Test
    void testTakeDamageToZero() {
        character.takeDamage(150);
        assertEquals(0, character.getHealth());
    }

    @Test
    void testSetPositionX() {
        character.setPositionX(15.5f);
        assertEquals(15.5f, character.getX());
    }

    @Test
    void testSetPositionY() {
        character.setPositionY(20.0f);
        assertEquals(20.0f, character.getY());
    }

    @Test
    void testSetDamage() {
        character.setDamage(30);
        assertEquals(30, character.getDamage());
    }

    @Test
    void testGetHealthAfterMultipleDamages() {
        character.takeDamage(20);
        character.takeDamage(30);
        assertEquals(50, character.getHealth());
    }


}