package Main.Game.Collectible.Coins.CoinFactory;

import Main.Game.Collectible.Coins.GoldCoin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GoldCoinFactoryTest {
    GoldCoinFactory goldCoinFactory ;
    @BeforeEach
    void setUp() {
        goldCoinFactory = new GoldCoinFactory();
    }

    @Test
    void createCoin() {
        goldCoinFactory.createCoin(10,10);
        assertInstanceOf(GoldCoin.class, goldCoinFactory.createCoin(10,10));
    }
}