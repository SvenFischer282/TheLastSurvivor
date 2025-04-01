package Main.GUI;

import Main.GUI.Enemy.EnemyController;
import Main.GUI.Player.PlayerGunController;
import Main.Game.Character.Enemy;
import Main.Game.Character.Player;
import Main.Game.Character.Zombie;
import Main.Game.Collectible.Potions.HealPotion;
import Main.Game.Collectible.Potions.Potion;
import Main.Game.Collectible.Potions.StrenghtPotion;
import Main.Game.Inventory;
import Main.Game.ScoreCounter;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Player player = new Player(400, 300);
            Enemy enemy = new Enemy(10, 100, 100, 2);
            Zombie zombie = new Zombie(10, 200, 200, 2);

            MainContainer mainContainer = new MainContainer(player, zombie);
            player.setPositionX(600);
            player.setPositionY(500);

            JFrame frame = new JFrame("Player Movement Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(mainContainer);
            frame.setSize(1200, 750);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            PlayerGunController PlayerController = new PlayerGunController(player);
            mainContainer.getPlayerGunView().addKeyListener(PlayerController);
            mainContainer.getPlayerGunView().addMouseListener(PlayerController);
            mainContainer.getPlayerGunView().requestFocusInWindow();

            EnemyController enemyController = new EnemyController(zombie, player);




            Inventory inventory = new Inventory(player);

            Potion potion1 = new StrenghtPotion(10,10,3);
            Potion potion2= new HealPotion(50,50,5);

            inventory.addPotion(potion1);
            inventory.addPotion(potion2);
            inventory.usePotion(Potion.PotionType.HEAL);
            inventory.usePotion(Potion.PotionType.HEAL);




            ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
            final long[] lastTime = {System.nanoTime()};

            executor.scheduleAtFixedRate(() -> {
                long currentTime = System.nanoTime();
                float deltaTime = Math.min((currentTime - lastTime[0]) / 1_000_000_000.0f, 0.1f);
                lastTime[0] = currentTime;

                synchronized (player) {
                    PlayerController.update(deltaTime);
                }
                synchronized (enemy) {
                    enemyController.update(deltaTime);
                }

                SwingUtilities.invokeLater(() -> {
                    mainContainer.getPlayerGunView().repaint();
                    mainContainer.getEnemyView().repaint();
                });
            }, 0, 16, TimeUnit.MILLISECONDS);

            frame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    executor.shutdown();
                }
            });
        });
    }
}