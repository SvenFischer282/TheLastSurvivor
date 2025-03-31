package Main.GUI;

import Main.Game.Character.Enemy;
import Main.Game.Character.Player;
import Main.GUI.Player.PlayerView;
import Main.GUI.Weapons.Gun.GunView;
import Main.Game.Weapons.Gun;
import Main.GUI.Enemy.EnemyView;

import javax.swing.*;
import java.awt.*;

/**
 * Container panel that holds and layers the player and gun views.
 */
public class MainContainer extends JPanel {
    private PlayerView playerView;
    private GunView gunView;
    private EnemyView enemyView;
    private int width;
    private int height;

    /**
     * Constructs a new MainContainer instance.
     * @param player The player to be displayed
     * @param gun The gun to be displayed
     */
    public MainContainer(Player player, Gun gun, Enemy enemy) {
        setLayout(new OverlayLayout(this)); // Changed to OverlayLayout


        // Get screen size


        // Initialize views
        playerView = new PlayerView(player);
        gunView = new GunView(player, gun);
        enemyView = new EnemyView(enemy);


        // Add views (gunView on top of playerView)
        add(gunView);
        add(playerView);
        add(enemyView);

        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(1200, 750));
    }

    /**
     * Gets the player view component.
     * @return The PlayerView instance
     */
    public PlayerView getPlayerView() {
        return playerView;
    }

    /**
     * Gets the gun view component.
     * @return The GunView instance
     */
    public GunView getGunView() {
        return gunView;
    }

    public EnemyView getEnemyView(){return  enemyView;}



}