package Main.Game.Collectible;

import Main.Game.Character.Player;

/**
 * A gold coin collectible that awards points when collected by the player.
 * Extends the base Coins class with a fixed value reward.
 */
public class GoldCoin extends Coins implements Collectible {

    /** The point value awarded for collecting this coin */
    private static final int GOLD_COIN_VALUE = 5;

    /**
     * Creates a new gold coin at specified coordinates.
     * @param x The x-coordinate position in the game world
     * @param y The y-coordinate position in the game world
     * @param name The display name of the coin (will be overridden to "Gold coin")
     */
    public GoldCoin(int x, int y, String name) {
        super(x, y, "Gold coin");
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
}