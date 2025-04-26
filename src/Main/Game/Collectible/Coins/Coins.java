package Main.Game.Collectible.Coins;

import Main.Game.Character.Player;
import Main.Game.Collectible.Collectible;
import Main.Game.Collectible.Item;

/**
 * Represents a collectible coin item that adds value to the player's inventory.
 * Extends the base Item class and implements Collectible functionality.
 */
public class Coins extends Item implements Collectible {
    private int value = 0;
    private int x;
    private int y;

    /**
     * Creates a new coin item at specified coordinates.
     * @param x The x-coordinate position in the game world
     * @param y The y-coordinate position in the game world
     */
    public Coins(int x, int y) {
        super(x, y, "Coin");
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


    /**
     * Getter for value of the coin
     * @return value of the coin
     */
    public int getValue() {
        return value;
    }

    /**
     * sets value of the coin
     * @param value new value of the coin
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * gets X position
     * @return X position of the coin
     */
    public int getX() {
        return x;
    }

    /**
     * sets the X of the coin
     * @param x new x position of the coin
     */
    public void setX(int x) {
        this.x = x;
    }
    /**
     * gets Y position
     * @return Y position of the coin
     */
    public int getY() {
        return y;
    }
    /**
     * sets the Y of the coin
     * @param y new Y position of the coin
     */
    public void setY(int y) {
        this.y = y;
    }
}