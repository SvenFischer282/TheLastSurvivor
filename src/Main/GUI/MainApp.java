package Main.GUI;

import Main.GUI.Enemy.EnemyController;
import Main.GUI.Player.PlayerController;
import Main.GUI.Weapons.Gun.GunController;
import Main.Game.Character.Enemy;
import Main.Game.Character.Player;
import Main.Game.Weapons.Gun;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Main application class for the game demo.
 */
public class MainApp {
    /**
     * Entry point for the application.
     * @param args Command line arguments (not used)
     */

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create model
            Player player = new Player(400, 300);
            Gun gun = new Gun(10, player);
            Enemy enemy = new Enemy(10, 100,100,2);

            // Create main container with player view and gun view
            MainContainer mainContainer = new MainContainer(player, gun,enemy);
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

            frame.setSize( 1200, 750);

            frame.setVisible(true);
            SwingUtilities.invokeLater(() -> {
                System.out.println("POST-VISIBLE DEBUG:");
                System.out.println("Frame size: " + frame.getWidth() + "x" + frame.getHeight());
                System.out.println("Content size: " + frame.getContentPane().getSize());
            });

            Timer gameTimer = new Timer(16, new ActionListener() {
                private long lastTime = System.nanoTime();

                @Override
                public void actionPerformed(ActionEvent e) {
                    long currentTime = System.nanoTime();
                    float deltaTime = (currentTime - lastTime) / 1_000_000_000.0f; // Convert to seconds
                    lastTime = currentTime;

                    playerController.update(deltaTime);
                    mainContainer.getPlayerView().repaint();
                    gunController.update(deltaTime);
                    mainContainer.getGunView().repaint();
                    enemyController.update(deltaTime);
                   mainContainer.getEnemyView().repaint();
                }
            });
            gameTimer.setRepeats(true);  // Ensure it keeps running
            gameTimer.start();
        });
    }
}