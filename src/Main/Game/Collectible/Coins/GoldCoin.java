package Main.Game.Collectible.Coins;

import Main.Game.Character.Player;
import Main.Game.Collectible.Collectible;

/**
 * A gold coin collectible that awards points when collected by the player.
 * Extends the base Coins class with a fixed value reward.
 */
public class GoldCoin extends Coins implements Collectible {

    /** The point value awarded for collecting this coin */
    private static final int GOLD_COIN_VALUE = 5;
    private final String name = "Gold Coin";

    /**
     * Creates a new gold coin at specified coordinates.
     * @param x The x-coordinate position in the game world
     * @param y The y-coordinate position in the game world

     */
    public GoldCoin(int x, int y) {
        super(x, y);
    }
    public String getName() {
        return name;
    }
    /**
     * Handles collection by the player:
     * - Logs the collection event
     * - Awards 5 points to the player's score
     * @param player The player collecting the coin
     */
    @Override
    public void collect(Player player) {
        System.out.println("Gold coin collected");
        player.addScore(GOLD_COIN_VALUE);
    }

    /**
     * Handles getting th evalue of the coin
     * @return value of the coin
     */
    public int getValue(){
        return GOLD_COIN_VALUE;
    }
}