package Main.GUI;

import Main.GUI.Enemy.EnemiesView;
import Main.GUI.Player.PlayerGunView;
import Main.Game.Character.Enemy;
import Main.Game.Character.Player;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainContainer extends JLayeredPane {
    private final PlayerGunView playerGunView;
    private final EnemiesView enemiesView;

    public MainContainer(Player player, List<Enemy> enemies) {
        playerGunView = new PlayerGunView(player);
        enemiesView = new EnemiesView(enemies);

        playerGunView.setBounds(0, 0, 1200, 750);
        enemiesView.setBounds(0, 0, 1200, 750);

        add(playerGunView, Integer.valueOf(0));
        add(enemiesView, Integer.valueOf(1));

        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(1200, 750));
    }

    public PlayerGunView getPlayerGunView() {
        return playerGunView;
    }

    public EnemiesView getEnemiesView() {
        return enemiesView;
    }
}
