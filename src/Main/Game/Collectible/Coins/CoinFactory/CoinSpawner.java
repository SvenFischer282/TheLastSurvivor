package Main.Game.Collectible.Coins.CoinFactory;

import Main.Game.Collectible.Coins.Coins;
import Main.Game.Collectible.Coins.GoldCoin;
import Main.Game.Collectible.Coins.SilverCoin;

import java.util.ArrayList;
import java.util.List;

public class CoinSpawner {
    private List<Coins> coins = new ArrayList<>();
    private GoldCoinFactory goldCoinFactory;
    private SilverCoinFactory silverCoinFactory;

    public CoinSpawner() {
        this.goldCoinFactory = new GoldCoinFactory();
        this.silverCoinFactory = new SilverCoinFactory();
    }
    public void spawnGoldCoin(int x, int y) {
        coins.add(new GoldCoin(x, y));
    }
    public void spawnSilverCoin(int x, int y) {
        coins.add(new SilverCoin(x, y));
    }

    public List<Coins> getCoins() {
        return coins;
    }
}
