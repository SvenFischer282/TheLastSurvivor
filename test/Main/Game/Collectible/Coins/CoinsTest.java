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
        coins = new Coins(2, 2);
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
    }

    @Test
    void setValue() {
    }

    @Test
    void getX() {
    }

    @Test
    void setX() {
    }

    @Test
    void getY() {
    }

    @Test
    void setY() {
    }
}