package Main.Game.Collectible.Coins;

import Main.Game.Character.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
class GoldCoinTest {
    GoldCoin goldCoin;
    Player player;
    @BeforeEach
    void setUp() {
        player = new Player(0,0);
        goldCoin = new GoldCoin(10,10);
    }

    @Test
    void getName() {
        assertEquals("Gold Coin", goldCoin.getName());
    }

    @Test
    void collect() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        goldCoin.collect(player);
        System.setOut(originalOut);
        assertEquals("Gold coin collected\n", outContent.toString());
    }

    @Test
    void getValue() {
        assertEquals(5, goldCoin.getValue());
    }
}