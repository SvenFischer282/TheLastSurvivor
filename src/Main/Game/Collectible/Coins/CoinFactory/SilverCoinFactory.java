package Main.Game.Collectible.Coins.CoinFactory;

import Main.Game.Collectible.Coins.Coins;
import Main.Game.Collectible.Coins.SilverCoin;

public class SilverCoinFactory implements CoinFactory{
    public SilverCoinFactory() {
        super();
    }

    @Override
    public Coins createCoin(int x, int y) {
        return new SilverCoin(x, y);
    }
}
