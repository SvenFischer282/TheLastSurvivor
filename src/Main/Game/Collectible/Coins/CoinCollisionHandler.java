package Main.Game.Collectible.Coins;

import Main.Game.Character.Player;

import java.awt.*;
import java.util.List;

/**
 * Handles collision detection between the player and coins.
 */
public class CoinCollisionHandler {
    private final List<Coins> coins;
    private final Player player;

    /**
     * Constructs a CoinCollisionHandler.
     * @param coins List of coins to check for collisions.
     * @param player The player character.
     */
    public CoinCollisionHandler(List<Coins> coins, Player player) {
        this.coins = coins;
        this.player = player;
    }

    /**
     * Checks for collisions between the player and coins, collecting coins on contact.
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

        coins.removeIf(coin -> {
            Rectangle coinBounds = new Rectangle(coin.getX(), coin.getY(), 16, 16);
            if (playerBounds.intersects(coinBounds)) {
                coin.collect(player);
                return true;
            }
            return false;
        });
    }
}