package Main.Game.Collectible.Potions;

import Main.Game.Character.Player;
import Main.Game.Inventory;

import java.awt.*;
import java.util.List;

/**
 * Handles collision detection between the player and potions.
 */
public class PotionCollisionHandler {
    private final List<Potion> potions;
    private final Player player;
    private final Inventory inventory;

    /**
     * Constructs a PotionCollisionHandler.
     * @param potions List of potions to check for collisions.
     * @param player The player character.
     * @param inventory The player's inventory for storing potions.
     */
    public PotionCollisionHandler(List<Potion> potions, Player player, Inventory inventory) {
        this.potions = potions;
        this.player = player;
        this.inventory = inventory;
    }

    /**
     * Checks for collisions between the player and potions, adding collided potions to the inventory.
     */
    public void checkCollisions() {
        int playerWidth = 64;
        int playerHeight = 64;

        Rectangle playerBounds = new Rectangle(
                (int) player.getX(),
                (int) player.getY(),
                playerWidth,
                playerHeight
        );

        potions.removeIf(potion -> {
            Rectangle potionBounds = new Rectangle(potion.getX(), potion.getY(), 16, 16);
            if (playerBounds.intersects(potionBounds)) {
                inventory.addPotion(potion);
                return true;
            }
            return false;
        });
    }

    /**
     * Gets the list of potions.
     * @return List of potions.
     */
    public List<Potion> getPotions() {
        return potions;
    }

    /**
     * Gets the player character.
     * @return The player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the player's inventory.
     * @return The inventory.
     */
    public Inventory getInventory() {
        return inventory;
    }
}
