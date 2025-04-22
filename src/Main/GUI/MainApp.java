package Main.GUI;

import Main.GUI.Enemy.EnemiesController;
import Main.GUI.Player.PlayerGunController;
import Main.Game.Character.Enemy;
import Main.Game.Character.EnemyFactory.BasicEnemyFactory;
import Main.Game.Character.EnemyFactory.EnemyFactory;
import Main.Game.Character.EnemyFactory.EnemySpawner;
import Main.Game.Character.Player;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import Main.Game.Collectible.Potions.Potion;
import Main.Game.Collectible.Potions.StrengthPotion;
import Main.Game.Inventory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The main application class that initializes and runs the game.
 * Sets up all game components, controllers, and the game loop.
 */
public class MainApp {
    private static final Logger logger = LoggerFactory.getLogger(MainApp.class);

    public static void main(String[] args) {


        // Initialize game entities
        Player player = new Player(400, 300);
        Inventory inventory = new Inventory(player);

        EnemyFactory basicEnemyFactory = new BasicEnemyFactory();
        EnemySpawner enemySpawner = new EnemySpawner();

        // Spawn multiple enemies
        enemySpawner.spawnBasicEnemies(3);
        enemySpawner.spawnFastZombies(2);
        List<Enemy> enemyList = enemySpawner.getEnemies();

        // Set player position
        player.setPositionX(600);
        player.setPositionY(500);

        // Create main container with multiple enemies
        MainContainer mainContainer = new MainContainer(player, enemyList,inventory);
        Potion strenght = new StrengthPotion(10,10,2);
        inventory.addPotion(strenght);

        // Create the main window
        JFrame frame = new JFrame("The Last Survivor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(mainContainer);
        frame.setSize(1200, 750);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Set up player controls
        PlayerGunController playerController = new PlayerGunController(player);
        mainContainer.getPlayerGunView().addKeyListener(playerController);
        mainContainer.getPlayerGunView().addMouseListener(playerController);
        mainContainer.getPlayerGunView().requestFocusInWindow();

        // Set up enemies controller
        EnemiesController enemiesController = new EnemiesController(enemyList, player);

        // Set up game loop
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        final long[] lastTime = {System.nanoTime()};

        executor.scheduleAtFixedRate(() -> {
            long currentTime = System.nanoTime();
            float deltaTime = Math.min((currentTime - lastTime[0]) / 1_000_000_000.0f, 0.1f);
            lastTime[0] = currentTime;

            // Update player and enemies
            synchronized (player) {
                playerController.update(deltaTime);
                enemiesController.updateAll(deltaTime);
            }

            // Repaint views
            SwingUtilities.invokeLater(() -> {
                mainContainer.getPlayerGunView().repaint();
                mainContainer.getEnemiesView().repaint();
            });
        }, 0, 16, TimeUnit.MILLISECONDS);

        // Shut down executor when window closes
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                executor.shutdown();
                // Also shutdown any scheduler in enemies
                for (Enemy enemy : enemyList) {
                    enemy.cleanup();
                }
            }
        });
    }
}