package Main.Game.Collectible.Coins;

import Main.Game.Character.Player;
import Main.Game.Collectible.Collectible;

/**
 * A gold coin collectible that awards points when collected by the player.
 * Extends the base Coins class with a fixed value reward.
 */
public class SilverCoin extends Coins implements Collectible {

    /** The point value awarded for collecting this coin */
    private static final int SILVER_COIN_VALUE = 2;

    /**
     * Creates a new silver coin at specified coordinates.
     * @param x The x-coordinate position in the game world
     * @param y The y-coordinate position in the game world
     * @param name The display name of the coin (will be overridden to "Gold coin")
     */
    public SilverCoin(int x, int y, String name) {
        super(x, y, "Silever coin");
    }

    /**
     * Handles collection by the player:
     * - Logs the collection event
     * - Awards 2 points to the player's score
     * @param player The player collecting the coin
     */
    @Override
    public void collect(Player player) {
        System.out.println("Silver coin collected");
        player.addScore(SILVER_COIN_VALUE);
    }
}