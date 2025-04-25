package Main.Game.Collectible.Potions;

import Main.Game.Character.Player;
import Main.Game.Inventory;

import java.awt.*;
import java.util.Iterator;
import java.util.List;

public class PotionCollisionHandler {
    private final List<Potion> potions;
    private final Player player;
    private final Inventory inventory;

    public PotionCollisionHandler(List<Potion> potions, Player player, Inventory inventory) {
        this.potions = potions;
        this.player = player;
        this.inventory = inventory;
    }

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

   public List<Potion> getPotions() {
        return potions;
   }

    public Player getPlayer() {
        return player;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
