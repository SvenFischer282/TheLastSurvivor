package Main.GUI;

import Main.GUI.Enemy.EnemiesController;
import Main.GUI.Player.PlayerGunController;
import Main.Game.Character.Enemy;
import Main.Game.Character.EnemyFactory.EnemySpawner;
import Main.Game.Character.Player;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import Main.Game.Collectible.Potions.HealPotion;
import Main.Game.Collectible.Potions.Potion;
import Main.Game.Collectible.Potions.PotionFactory.PotionSpawner;
import Main.Game.Collectible.Potions.StrengthPotion;
import Main.Game.Inventory;
import Main.Game.Collectible.Potions.PotionCollisionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The main application class that initializes and runs the game.
 * Sets up all game components, controllers, and the game loop.
 */
public class MainApp {
    private static final Logger logger = LoggerFactory.getLogger(MainApp.class);

    public static void main(String[] args) {
        // Initialize player and inventory
        Player player = new Player(400, 300);
        Inventory inventory = new Inventory(player);

        // Spawn enemies and potions
        EnemySpawner enemySpawner = new EnemySpawner();
        PotionSpawner potionSpawner = new PotionSpawner();

        potionSpawner.spawnStrengthPotion(1);
        potionSpawner.spawnHealPotion(1);
        List<Potion> potionList = potionSpawner.getPotions();

        enemySpawner.spawnBasicEnemies(3);
        enemySpawner.spawnFastZombies(2);
        List<Enemy> enemyList = enemySpawner.getEnemies();

        player.setPositionX(600);
        player.setPositionY(500);

        // Main container with game components
        MainContainer mainContainer = new MainContainer(player, enemyList, inventory, potionList);

        // (Optional debug inventory additions)
        Potion strength = new StrengthPotion(10, 10, 2);
        Potion heal = new HealPotion(10, 10, 2);
        inventory.addPotion(strength);
        inventory.addPotion(heal);

        // Setup game window
        JFrame frame = new JFrame("The Last Survivor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(mainContainer);
        frame.setSize(1200, 750);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Setup player controller
        PlayerGunController playerController = new PlayerGunController(player, inventory);
        mainContainer.getPlayerGunView().addKeyListener(playerController);
        mainContainer.getPlayerGunView().addMouseListener(playerController);
        mainContainer.getPlayerGunView().requestFocusInWindow();

        // Setup enemies controller
        EnemiesController enemiesController = new EnemiesController(enemyList, player);

        // Setup game loop for player + enemies
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        final long[] lastTime = {System.nanoTime()};

        executor.scheduleAtFixedRate(() -> {
            long currentTime = System.nanoTime();
            float deltaTime = Math.min((currentTime - lastTime[0]) / 1_000_000_000.0f, 0.1f);
            lastTime[0] = currentTime;

            synchronized (player) {
                playerController.update(deltaTime);
                enemiesController.updateAll(deltaTime);
            }

            SwingUtilities.invokeLater(() -> {
                mainContainer.getPlayerGunView().repaint();
                mainContainer.getEnemiesView().repaint();
            });
        }, 0, 16, TimeUnit.MILLISECONDS); // ~60 FPS

        // Repaint loop for potions view
        ScheduledExecutorService potionExecutor = Executors.newSingleThreadScheduledExecutor();
        potionExecutor.scheduleAtFixedRate(() -> {
            SwingUtilities.invokeLater(() -> {
                mainContainer.getPotionsView().repaint();
            });
        }, 0, 100, TimeUnit.MILLISECONDS); // ~10 FPS

        // Setup potion collision handling
        PotionCollisionHandler potionCollisionHandler = new PotionCollisionHandler(potionList, player, inventory);
        ScheduledExecutorService collisionExecutor = Executors.newSingleThreadScheduledExecutor();
        collisionExecutor.scheduleAtFixedRate(() -> {
            potionCollisionHandler.checkCollisions();
        }, 0, 50, TimeUnit.MILLISECONDS); // check 20x za sekundu

        // Shutdown on window close
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                executor.shutdown();
                potionExecutor.shutdown();
                collisionExecutor.shutdown();
                for (Enemy enemy : enemyList) {
                    enemy.cleanup();
                }
            }
        });
    }
}
