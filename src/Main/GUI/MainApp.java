package Main.GUI;

import Main.GUI.Player.PlayerController;
import Main.GUI.Weapons.Gun.GunController;
import Main.Game.Character.Player;
import Main.Game.Weapons.Gun;

import javax.swing.*;
import java.awt.*;
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

            // Create main container with player view and gun view
            MainContainer mainContainer = new MainContainer(player, gun);
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

            frame.setSize( 1200, 750);

            frame.setVisible(true);
            SwingUtilities.invokeLater(() -> {
                System.out.println("POST-VISIBLE DEBUG:");
                System.out.println("Frame size: " + frame.getWidth() + "x" + frame.getHeight());
                System.out.println("Content size: " + frame.getContentPane().getSize());
            });

            // Game loop
            new Timer(16, e -> {
                playerController.update(0.016f);
                mainContainer.getPlayerView().repaint();
                gunController.update(0.016f);
                mainContainer.getGunView().repaint();
            }).start();
        });
    }
}