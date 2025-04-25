package Main.Game.Collectible.Coins;

import Main.Game.Character.Player;
import Main.Game.ScoreCounter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CoinCollisionHandlerTest {

    private TestPlayer player;
    private List<Coins> coins;

    // Custom player that tracks score locally
    static class TestPlayer extends Player {
        private int testScore = 0;

        public TestPlayer(int x, int y) {
            super(x, y);
        }

        @Override
        public void addScore(int value) {
            testScore += value;
        }

        public int getTestScore() {
            return testScore;
        }
    }

    @BeforeEach
    void setUp() {
        player = new TestPlayer(100, 100); // 64x64 hitbox centered at (100,100)
        coins = new ArrayList<>();
    }

    @Test
    void testCoinIsCollectedAndRemovedWhenIntersecting() {
        SilverCoin coin = new SilverCoin(110, 110); // inside player's hitbox
        coins.add(coin);

        CoinCollisionHandler handler = new CoinCollisionHandler(coins, player);
        handler.checkCollisions();

        assertEquals(0, coins.size(), "Coin should be removed from the list after collection");
        assertEquals(2, player.getTestScore(), "Player should receive 2 points for collecting SilverCoin");
    }

    @Test
    void testCoinIsNotCollectedWhenOutsidePlayerBounds() {
        SilverCoin coin = new SilverCoin(200, 200); // outside player's hitbox
        coins.add(coin);

        CoinCollisionHandler handler = new CoinCollisionHandler(coins, player);
        handler.checkCollisions();

        assertEquals(1, coins.size(), "Coin should remain in the list if not intersecting with player");
        assertEquals(0, player.getTestScore(), "Player score should remain unchanged");
    }

    @Test
    void testMultipleCoinsMixedCollection() {
        SilverCoin coin1 = new SilverCoin(110, 110); // collides
        SilverCoin coin2 = new SilverCoin(200, 200); // doesn't collide
        coins.add(coin1);
        coins.add(coin2);

        CoinCollisionHandler handler = new CoinCollisionHandler(coins, player);
        handler.checkCollisions();

        assertEquals(1, coins.size(), "Only one coin should remain");
        assertEquals(2, player.getTestScore(), "Player should get 2 points for one coin");
        assertEquals(coin2, coins.get(0), "Remaining coin should be the non-colliding one");
    }
}
