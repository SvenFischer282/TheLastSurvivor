package Main.Game.Collectible.Coins;

import Main.Game.Character.Player;

import java.awt.*;
import java.util.List;

public class CoinCollisionHandler {
    private final List<Coins> coins;
    private final Player player;

    public CoinCollisionHandler(List<Coins> coins, Player player) {
        this.coins = coins;
        this.player = player;
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

        coins.removeIf(coin -> {
            Rectangle coinBounds = new Rectangle(coin.getX(), coin.getY(), 16, 16);
            if (playerBounds.intersects(coinBounds)) {
                coin.collect(player);  // calls the collect method, e.g. adds points
                return true; // remove the coin from the list
            }
            return false;
        });
    }
}
