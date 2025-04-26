package Main.Game.Collectible.Coins.CoinFactory;

import Main.Game.Collectible.Coins.Coins;
import Main.Game.Collectible.Coins.GoldCoin;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles Gold coin creation
 */
public class GoldCoinFactory implements CoinFactory {

    public GoldCoinFactory() {
        super();
    }

    /**
     * Creates gold coin on given coordinates
     * @param x X coordinate
     * @param y Y coordinate
     * @return new gold coin
     */
    @Override
    public Coins createCoin(int x, int y) {
        return new GoldCoin(x, y);
    }
}
