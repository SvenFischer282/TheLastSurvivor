package Main.Game.Character;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class CharacterTest {
    private Character character;
    @BeforeEach
    void setUp() {
         character = new Character(10,100,200);
    }

    @org.junit.jupiter.api.Test
    void getHealth() {
        assertEquals(10, character.getHealth());
    }

    @org.junit.jupiter.api.Test
    void setHealth() {
        character.setHealth(10);
        assertEquals(10, character.getHealth());
    }

    @org.junit.jupiter.api.Test
    void getPositionX() {
        assertEquals(100, character.getPositionX());
    }

    @org.junit.jupiter.api.Test
    void setPositionX() {
        character.setPositionX(200);
        assertEquals(200, character.getPositionX());
    }

    @org.junit.jupiter.api.Test
    void getPositionY() {
        assertEquals(200, character.getPositionY());
    }

    @org.junit.jupiter.api.Test
    void setPositionY() {
        character.setPositionY(400);
        assertEquals(400, character.getPositionY());
    }
}