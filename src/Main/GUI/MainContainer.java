package Main.GUI;

import Main.GUI.Enemy.EnemyView;
import Main.GUI.Player.PlayerGunView;
import Main.Game.Character.Enemy;
import Main.Game.Character.Player;
import javax.swing.*;
import java.awt.*;

public class MainContainer extends JLayeredPane { // Zmeníme na JLayeredPane
    private final PlayerGunView playerGunView;
    private final EnemyView enemyView;

    public MainContainer(Player player, Enemy enemy) {
        playerGunView = new PlayerGunView(player);
        enemyView = new EnemyView(enemy);

        playerGunView.setBounds(0, 0, 1200, 750);
        enemyView.setBounds(0, 0, 1200, 750);

        // Pridanie vrstiev s explicitným poradím
        add(playerGunView, Integer.valueOf(0)); // Najnižšia vrstva (background)
        add(enemyView, Integer.valueOf(1));     // Vyššia vrstva

        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(1200, 750));
    }

    public PlayerGunView getPlayerGunView() {
        return playerGunView;
    }

    public EnemyView getEnemyView() {
        return enemyView;
    }
}