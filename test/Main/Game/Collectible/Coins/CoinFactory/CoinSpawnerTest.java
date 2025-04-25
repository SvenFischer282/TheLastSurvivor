package Main.Game.Collectible.Coins.CoinFactory;

import Main.Game.Collectible.Coins.Coins;
import Main.Game.Collectible.Coins.GoldCoin;
import Main.Game.Collectible.Coins.SilverCoin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class CoinSpawnerTest {

    SilverCoinFactory silverCoinFactory;
    GoldCoinFactory goldCoinFactory;
    CoinSpawner spawner;

    @BeforeEach
    void setUp() {
        silverCoinFactory = new SilverCoinFactory();
        goldCoinFactory = new GoldCoinFactory();
        spawner = new CoinSpawner();

    }

    @Test
    void spawnGoldCoin() {
        spawner.spawnGoldCoin(10,10);
        spawner.spawnGoldCoin(10,10);
        assertEquals(2,spawner.getCoins().size());
    }

    @Test
    void spawnSilverCoin() {
        spawner.spawnSilverCoin(10,10);
        spawner.spawnSilverCoin(10,10);
        assertEquals(2,spawner.getCoins().size());
    }

    @Test
    void getCoins() {
        spawner.spawnSilverCoin(10,10);
        spawner.spawnSilverCoin(10,10);
        assertEquals(2,spawner.getCoins().size());
    }
}