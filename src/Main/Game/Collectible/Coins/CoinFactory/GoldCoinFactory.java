package Main.Game.Collectible.Coins.CoinFactory;

import Main.Game.Collectible.Coins.Coins;
import Main.Game.Collectible.Coins.GoldCoin;

import java.util.ArrayList;
import java.util.List;

public class GoldCoinFactory implements CoinFactory {

    public GoldCoinFactory() {
        super();
    }

    @Override
    public Coins createCoin(int x, int y) {
        return new GoldCoin(x, y);
    }
}
