package Main.Game.Collectible.Coins;

import Main.Game.Character.Player;
import Main.Game.Collectible.Collectible;
import Main.Game.Collectible.Item;

/**
 * Represents a collectible coin item that adds value to the player's inventory.
 * Extends the base Item class and implements Collectible functionality.
 */
public class Coins extends Item implements Collectible {
    private int value;
    private int x;
    private int y;

    /**
     * Creates a new coin item at specified coordinates.
     * @param x The x-coordinate position in the game world
     * @param y The y-coordinate position in the game world
     * @param name The display name of the coin collection
     */
    public Coins(int x, int y, String name) {
        super(x, y, name);
        this.x = x;
        this.y = y;
    }

    /**
     * Handles collection by the player.
     * Currently just logs collection - should be extended to modify player's coin count.
     * @param player The player collecting the coins
     */
    @Override
    public void collect(Player player) {
        System.out.println("Coins collected");
        // TODO: Implement actual coin collection logic
        // player.addCoins(this.value);
    }

    // Basic property accessors

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}