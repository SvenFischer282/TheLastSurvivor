package Main.Game.Collectible.Coins.CoinFactory;

import Main.Game.Collectible.Coins.Coins;
import Main.Game.Collectible.Coins.SilverCoin;

/**
 * Handles creating silver coins
 */
public class SilverCoinFactory implements CoinFactory{
    public SilverCoinFactory() {
        super();
    }

    /**
     * Creates Silver coin on given coordinates
     * @param x X coordinate
     * @param y Y coordinate
     * @return New silver coin
     */
    @Override
    public Coins createCoin(int x, int y) {
        return new SilverCoin(x, y);
    }
}
