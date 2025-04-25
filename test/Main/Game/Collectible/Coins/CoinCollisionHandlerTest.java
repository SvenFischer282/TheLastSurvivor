package Main.Game.Collectible.Coins;

import Main.Game.Character.Player;
import Main.Game.Collectible.Coins.CoinCollisionHandler;
import Main.Game.Collectible.Coins.Coins;
import Main.Game.Collectible.Coins.GoldCoin;
import Main.Game.Collectible.Coins.SilverCoin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CoinCollisionHandlerTest {
    private List<Coins> coins;
    private Player player;
    private CoinCollisionHandler handler;

    @BeforeEach
    void setUp() {
        coins = new ArrayList<>();
        player = new Player(0, 0);
        handler = new CoinCollisionHandler(coins, player);
    }

    @Test
    void testNoCollisionWhenNoCoins() {
        handler.checkCollisions();
        assertEquals(0, coins.size());
        assertEquals(0, player.getScoreCounter().getScore());
    }

    @Test
    void testCollisionWithGoldCoin() {
        coins.add(new GoldCoin(0, 0));
        handler.checkCollisions();
        assertEquals(0, coins.size());
        assertEquals(5, player.getScoreCounter().getScore());
    }

    @Test
    void testCollisionWithSilverCoin() {
        coins.add(new SilverCoin(0, 0));
        handler.checkCollisions();
        assertEquals(0, coins.size());
        assertEquals(2, player.getScoreCounter().getScore());
    }

    @Test
    void testNoCollisionWhenCoinFarAway() {
        coins.add(new GoldCoin(100, 100));
        handler.checkCollisions();
        assertEquals(1, coins.size());
        assertEquals(0, player.getScoreCounter().getScore());
    }

    @Test
    void testMultipleCoinsWithMixedCollisions() {
        coins.add(new GoldCoin(0, 0));
        coins.add(new SilverCoin(100, 100));
        coins.add(new GoldCoin(10, 10));
        handler.checkCollisions();
        assertEquals(1, coins.size());
        assertEquals(10, player.getScoreCounter().getScore());
    }

    @Test
    void testCoinCollectionWithBaseCoinsClass() {
        Coins coin = new Coins(0, 0);
        coins.add(coin);
        handler.checkCollisions();
        assertEquals(0, coins.size());
        assertEquals(0, player.getScoreCounter().getScore());
    }

    @Test
    void testCollisionWithPlayerMovement() {
        coins.add(new GoldCoin(70, 70));
        player.setPositionX(50);
        player.setPositionY(50);
        handler.checkCollisions();
        assertEquals(1, coins.size());
        player.setPositionX(60);
        player.setPositionY(60);
        handler.checkCollisions();
        assertEquals(0, coins.size());
        assertEquals(5, player.getScoreCounter().getScore());
    }
}