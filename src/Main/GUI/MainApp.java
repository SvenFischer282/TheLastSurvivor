package Main.GUI;

import Main.GUI.Enemy.EnemyController;
import Main.GUI.Player.PlayerController;
import Main.GUI.Weapons.Gun.GunController;
import Main.Game.Character.Enemy;
import Main.Game.Character.Player;
import Main.Game.Weapons.Gun;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create model
            Player player = new Player(400, 300);
            Gun gun = new Gun(10, player);
            Enemy enemy = new Enemy(10, 100, 100, 2);

            // Create main container with player view and gun view
            MainContainer mainContainer = new MainContainer(player, gun, enemy);
            player.setPositionX(600);
            player.setPositionY(500);

            // Set up the frame
            JFrame frame = new JFrame("Player Movement Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(mainContainer);
            frame.pack();
            frame.setLocationRelativeTo(null);

            // Set up controllers
            PlayerController playerController = new PlayerController(player);
            mainContainer.getPlayerView().addKeyListener(playerController);
            mainContainer.getPlayerView().requestFocusInWindow();

            GunController gunController = new GunController(player, gun);
            mainContainer.getGunView().addMouseListener(gunController);
            mainContainer.getGunView().setFocusable(true);

            EnemyController enemyController = new EnemyController(enemy);

            frame.setSize(1200, 750);
            frame.setVisible(true);

            // Debug output
            SwingUtilities.invokeLater(() -> {
                System.out.println("POST-VISIBLE DEBUG:");
                System.out.println("Frame size: " + frame.getWidth() + "x" + frame.getHeight());
                System.out.println("Content size: " + frame.getContentPane().getSize());
            });

            // Thread pool for game logic (2 threads: Player+Gun, Enemy)
            ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);

            // Shared timing variable
            final long[] lastTime = {System.nanoTime()};

            // Player and Gun update thread (~60 FPS)
            executor.scheduleAtFixedRate(() -> {
                long currentTime = System.nanoTime();
                float deltaTime = Math.min((currentTime - lastTime[0]) / 1_000_000_000.0f, 0.1f); // Cap at 0.1s
                lastTime[0] = currentTime;

                synchronized (player) { // Synchronize on player since it’s shared
                    playerController.update(deltaTime);
                    gunController.update(deltaTime); // Gun updates after player
                }

                // Trigger repaint for player and gun views on EDT
                SwingUtilities.invokeLater(() -> {
                    mainContainer.getPlayerView().repaint();
                    mainContainer.getGunView().repaint();
                });
            }, 0, 16, TimeUnit.MILLISECONDS);

            // Enemy update thread (~20 FPS)
            executor.scheduleAtFixedRate(() -> {
                long currentTime = System.nanoTime();
                float deltaTime = Math.min((currentTime - lastTime[0]) / 1_000_000_000.0f, 0.1f);

                synchronized (enemy) {
                    enemyController.update(deltaTime);
                }

                // Trigger repaint for enemy view on EDT
                SwingUtilities.invokeLater(mainContainer.getEnemyView()::repaint);
            }, 0, 50, TimeUnit.MILLISECONDS);

            // Shutdown executor when window closes
            frame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    executor.shutdown();
                }
            });
        });
    }
}