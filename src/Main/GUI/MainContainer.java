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
 * Container panel that holds and layers the player, gun, and enemy views.
 */
public class MainContainer extends JPanel {
    private final PlayerView playerView;
    private final GunView gunView;
    private final EnemyView enemyView;

    /**
     * Constructs a new MainContainer instance.
     * @param player The player to be displayed
     * @param gun The gun to be displayed
     * @param enemy The enemy to be displayed
     */
    public MainContainer(Player player, Gun gun, Enemy enemy) {
        setLayout(new OverlayLayout(this)); // OverlayLayout pre vrstvenie

        // Inicializácia pohľadov
        playerView = new PlayerView(player);
        gunView = new GunView(player, gun);
        enemyView = new EnemyView(enemy);

        // Nastavenie rozmerov pre každý pohľad (rovnaké ako kontajner)
        playerView.setBounds(0, 0, 1200, 750);
        gunView.setBounds(0, 0, 1200, 750);
        enemyView.setBounds(0, 0, 1200, 750);

        // Pridanie pohľadov (poradie určuje vrstvy: enemy dole, player hore)
        add(enemyView);  // Najnižšia vrstva
        add(gunView);    // Stredná vrstva
        add(playerView); // Najvyššia vrstva

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

    /**
     * Gets the enemy view component.
     * @return The EnemyView instance
     */
    public EnemyView getEnemyView() {
        return enemyView;
    }
}