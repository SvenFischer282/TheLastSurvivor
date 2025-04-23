package Main.Game.Collectible.Coins.CoinFactory;

import Main.Game.Collectible.Coins.Coins;

public interface CoinFactory {
     Coins createCoin(int x, int y);
}
