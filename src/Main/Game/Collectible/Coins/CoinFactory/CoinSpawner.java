package Main.Game.Collectible.Coins.CoinFactory;

import Main.Game.Collectible.Coins.Coins;
import Main.Game.Collectible.Coins.GoldCoin;
import Main.Game.Collectible.Coins.SilverCoin;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the spawning of coins in the game.
 */
public class CoinSpawner {
    private List<Coins> coins = new ArrayList<>();
    private GoldCoinFactory goldCoinFactory;
    private SilverCoinFactory silverCoinFactory;

    /**
     * Constructs a CoinSpawner and initializes coin factories.
     */
    public CoinSpawner() {
        this.goldCoinFactory = new GoldCoinFactory();
        this.silverCoinFactory = new SilverCoinFactory();
    }

    /**
     * Spawns a gold coin at the specified coordinates.
     * @param x The x-coordinate for the coin's position.
     * @param y The y-coordinate for the coin's position.
     */
    public void spawnGoldCoin(int x, int y) {
        coins.add(new GoldCoin(x, y));
    }

    /**
     * Spawns a silver coin at the specified coordinates.
     * @param x The x-coordinate for the coin's position.
     * @param y The y-coordinate for the coin's position.
     */
    public void spawnSilverCoin(int x, int y) {
        coins.add(new SilverCoin(x, y));
    }

    /**
     * Gets the list of spawned coins.
     * @return List of coins.
     */
    public List<Coins> getCoins() {
        return coins;
    }
}