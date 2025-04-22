package Main.GUI;

import Main.GUI.Enemy.EnemiesView;
import Main.GUI.Player.PlayerGunView;
import Main.Game.Character.Enemy;
import Main.Game.Character.Player;
import Main.Game.Inventory;
import Main.Game.ScoreCounter;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainContainer extends JLayeredPane {
    private final PlayerGunView playerGunView;
    private final EnemiesView enemiesView;
    private final HUD hudView;
    private final ScoreCounter scoreCounter;

    public MainContainer(Player player, List<Enemy> enemies, Inventory inventory) {
        playerGunView = new PlayerGunView(player);
        enemiesView = new EnemiesView(enemies);
        scoreCounter = ScoreCounter.getInstance();
        hudView  = new HUD(player,scoreCounter, inventory);


        playerGunView.setBounds(0, 0, 1200, 750);
        enemiesView.setBounds(0, 0, 1200, 750);
        hudView.setBounds(0, 0, 500, 500);

        add(playerGunView, Integer.valueOf(0));
        add(enemiesView, Integer.valueOf(1));
        add(hudView, Integer.valueOf(2));

        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(1200, 750));
    }

    public PlayerGunView getPlayerGunView() {
        return playerGunView;
    }

    public EnemiesView getEnemiesView() {
        return enemiesView;
    }
    public HUD getHudView() {
        return hudView;
    }
   public void addScore(int score) {
        scoreCounter.addScore(score);
   }
}
