package Main.Game.Collectible;

import Main.Game.Character.Player;
import java.awt.*;

/**
 * Abstract base class representing a collectible game item.
 * Provides core functionality for position, naming, and collection behavior.
 * All specific game items should extend this class.
 */
public abstract class Item implements Collectible {
    private int x;        // X-coordinate position in the game world
    private int y;        // Y-coordinate position in the game world
    private String name;  // Display name of the item

    /**
     * Constructs a new Item at specified coordinates.
     * @param x The horizontal position in the game world
     * @param y The vertical position in the game world
     * @param name The display name of the item
     */
    public Item(int x, int y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    /**
     * Default collection behavior to be overridden by subclasses.
     * @param player The player collecting the item
     */
    @Override
    public void collect(Player player) {
        // Intentionally empty - subclasses should implement specific behavior
    }

    /**
     * Default usage behavior to be overridden by subclasses.
     * @param player The player using the item
     */
    public void use(Player player) {
        // Intentionally empty - subclasses should implement specific behavior
    }

    /**
     * Gets the x-coordinate of the item.
     * @return The x-coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the x-coordinate of the item.
     * @param x The new x-coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Gets the y-coordinate of the item.
     * @return The y-coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the y-coordinate of the item.
     * @param y The new y-coordinate
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Gets the name of the item.
     * @return The item's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the item.
     * @param name The new name
     */
    public void setName(String name) {
        this.name = name;
    }
}