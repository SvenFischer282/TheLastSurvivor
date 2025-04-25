package Main.Game.Collectible.Coins;

import Main.Game.Character.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
class SilverCoinTest {
    SilverCoin coin;
    Player player;
    @BeforeEach
    void setUp() {
        player = new Player(0,0);
        coin = new SilverCoin(10,10);
    }

    @Test
    void getName() {
        assertEquals("Silver Coin", coin.getName());
    }

    @Test
    void collect() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        coin.collect(player);
        System.setOut(originalOut);
        assertEquals("Silver coin collected\n", outContent.toString());
    }

    @Test
    void getValue() {
        assertEquals(2, coin.getValue());
    }
}