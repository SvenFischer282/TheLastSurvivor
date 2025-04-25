package Main.Game.Collectible.Coins.CoinFactory;

import Main.Game.Collectible.Coins.Coins;
import Main.Game.Collectible.Coins.SilverCoin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class SilverCoinFactoryTest {
    SilverCoinFactory silverCoinFactory ;
    @BeforeEach
    void setUp() {
    silverCoinFactory = new SilverCoinFactory();
    }

    @Test
    void createCoin() {
      Coins coin = silverCoinFactory.createCoin(10,10);
      assertInstanceOf(SilverCoin.class, coin);
    }
}