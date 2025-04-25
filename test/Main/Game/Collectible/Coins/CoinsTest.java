package Main.Game.Collectible.Coins;

import Main.Game.Character.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class CoinsTest {
    Coins coins;
    Player player;

    @BeforeEach
    void setUp() {
        coins = new Coins(10, 10);
        Player player = new Player(10, 10);
    }

    @Test
    void collect() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        coins.collect(player);
        System.setOut(originalOut);
        assertEquals("Coins collected\n", outContent.toString());
    }

    @Test
    void getValue() {
        assertEquals(0, coins.getValue());
    }

    @Test
    void setValue() {
        coins.setValue(5);
        assertEquals(5, coins.getValue());
    }

    @Test
    void getX() {
        assertEquals(10, coins.getX());
    }

    @Test
    void setX() {
        coins.setX(50);
        assertEquals(50, coins.getX());
    }

    @Test
    void getY() {
        assertEquals(10, coins.getY());
    }

    @Test
    void setY() {
        coins.setY(50);
        assertEquals(50, coins.getY());
    }
}