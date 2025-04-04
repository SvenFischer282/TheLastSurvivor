package Main.GUI;

import Main.GUI.Enemy.EnemyView;
import Main.GUI.Player.PlayerGunView;
import Main.Game.Character.Enemy;
import Main.Game.Character.Player;
import javax.swing.*;
import java.awt.*;

/**
 * A layered container that manages the visual components of the game.
 * Uses JLayeredPane to properly layer game elements (player, enemies, etc.).
 */
public class MainContainer extends JLayeredPane {
    /**
     * The view component for the player and gun.
     */
    private final PlayerGunView playerGunView;

    /**
     * The view component for enemies.
     */
    private final EnemyView enemyView;

    /**
     * Creates a new MainContainer with specified game entities.
     * @param player The player entity to display
     * @param enemy The enemy entity to display
     */
    public MainContainer(Player player, Enemy enemy) {
        playerGunView = new PlayerGunView(player);
        enemyView = new EnemyView(enemy);

        playerGunView.setBounds(0, 0, 1200, 750);
        enemyView.setBounds(0, 0, 1200, 750);

        // Adding layers with explicit ordering
        add(playerGunView, Integer.valueOf(0)); // Bottom layer (background)
        add(enemyView, Integer.valueOf(1));     // Higher layer

        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(1200, 750));
    }

    /**
     * Gets the player and gun view component.
     * @return The PlayerGunView instance
     */
    public PlayerGunView getPlayerGunView() {
        return playerGunView;
    }

    /**
     * Gets the enemy view component.
     * @return The EnemyView instance
     */
    public EnemyView getEnemyView() {
        return enemyView;
    }
}