package Main.GUI;

import Main.GUI.Enemy.EnemyController;
import Main.GUI.Player.PlayerController;
import Main.GUI.Weapons.Gun.GunController;
import Main.Game.Character.Enemy;
import Main.Game.Character.Player;
import Main.Game.Character.Zombie;
import Main.Game.Weapons.Gun;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Vytvorenie modelu
            Player player = new Player(400, 300);
            Gun gun = new Gun(10, player);
            Enemy enemy = new Enemy(10, 100, 100, 2);
            Zombie zombie = new Zombie(10, 200, 200, 2);

            // Hlavný kontajner
            MainContainer mainContainer = new MainContainer(player, gun, zombie);
            player.setPositionX(600);
            player.setPositionY(500);

            // Nastavenie okna
            JFrame frame = new JFrame("Player Movement Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(mainContainer);
            frame.setSize(1200, 750);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            // Kontroléry
            PlayerController playerController = new PlayerController(player);
            mainContainer.getPlayerView().addKeyListener(playerController);
            mainContainer.getPlayerView().requestFocusInWindow();

            GunController gunController = new GunController(player, gun);
            mainContainer.getGunView().addMouseListener(gunController);

            EnemyController enemyController = new EnemyController(zombie,player);

            // Jedno vlákno pre hernú logiku (~60 FPS)
            ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
            final long[] lastTime = {System.nanoTime()};

            executor.scheduleAtFixedRate(() -> {
                long currentTime = System.nanoTime();
                float deltaTime = Math.min((currentTime - lastTime[0]) / 1_000_000_000.0f, 0.1f);
                lastTime[0] = currentTime;

                // Synchronizované aktualizácie
                synchronized (player) {
                    playerController.update(deltaTime);
                    gunController.update(deltaTime);
                }
                synchronized (enemy) {
                    enemyController.update(deltaTime);
                }

                // Prekreslenie na EDT
                SwingUtilities.invokeLater(() -> {
                    mainContainer.getPlayerView().repaint();
                    mainContainer.getGunView().repaint();
                    mainContainer.getEnemyView().repaint();
                });
            }, 0, 16, TimeUnit.MILLISECONDS);

            // Zatvorenie executora pri zatvorení okna
            frame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    executor.shutdown();
                }
            });
        });
    }
}