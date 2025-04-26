package Main.GUI;

import Main.GUI.Enemy.EnemiesController;
import Main.GUI.Player.PlayerGunController;
import Main.Game.Character.Enemy;
import Main.Game.Character.EnemyFactory.EnemySpawner;
import Main.Game.Character.Player;
import Main.Game.Collectible.Coins.CoinCollisionHandler;
import Main.Game.Collectible.Coins.CoinFactory.CoinSpawner;
import Main.Game.Collectible.Coins.Coins;
import Main.Game.Collectible.Potions.HealPotion;
import Main.Game.Collectible.Potions.Potion;
import Main.Game.Collectible.Potions.PotionFactory.PotionSpawner;
import Main.Game.Collectible.Potions.StrengthPotion;
import Main.Game.Inventory;
import Main.Game.Collectible.Potions.PotionCollisionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The main application class that initializes and runs the game, managing components, controllers, and the game loop with a wave system.
 */
public class MainApp {
    private static final Logger logger = LoggerFactory.getLogger(MainApp.class);

    /**
     * Entry point for the game application.
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        // Initialize player and inventory
        Player player = new Player(400, 300);
        Inventory inventory = new Inventory(player);

        // Spawn enemies and potions
        CoinSpawner coinSpawner = new CoinSpawner();
        EnemySpawner enemySpawner = new EnemySpawner(coinSpawner);
        PotionSpawner potionSpawner = new PotionSpawner();

        // Add initial potion to inventory for testing
        Potion potion = new Potion(10, 10, 1);
        inventory.addPotion(potion);
        List<Potion> potionList = potionSpawner.getPotions();

        inventory.showInventory();

        // Initialize enemies for the first wave
        enemySpawner.spawnRandomEnemies(3);
        List<Enemy> enemyList = new ArrayList<>(enemySpawner.getEnemies());
        logger.debug("Initial wave spawned with {} enemies", enemyList.size());

        // Set initial player position
        player.setPositionX(600);
        player.setPositionY(500);
        List<Coins> coins = coinSpawner.getCoins();

        // Main container with game components
        MainContainer mainContainer = new MainContainer(player, enemyList, inventory, potionList, coins);

        // Add test potions to inventory
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

        // Wave system variables
        final int[] waveNumber = {1}; // Current wave
        final int[] enemiesToSpawn = {3}; // Enemies per wave
        final boolean[] waveTransition = {false}; // Wave transition state
        final long[] waveTransitionStart = {0}; // Transition start time

        // Setup game loop for player and enemies
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        final long[] lastTime = {System.nanoTime()};

        executor.scheduleAtFixedRate(() -> {
            long currentTime = System.nanoTime();
            float deltaTime = Math.min((currentTime - lastTime[0]) / 1_000_000_000.0f, 0.1f);
            lastTime[0] = currentTime;

            synchronized (enemyList) {
                // Check if player is dead
                if (player.getHealth() <= 0) {
                    executor.shutdown();
                    for (Enemy enemy : enemyList) {
                        enemy.cleanup();
                    }
                    SwingUtilities.invokeLater(() -> {
                        frame.setContentPane(new GameOverPanel(frame, player));
                        frame.revalidate();
                        frame.repaint();
                    });
                    return;
                }

                playerController.update(deltaTime);
                enemiesController.updateAll(deltaTime);
                enemySpawner.removeDeadEnemies();

                // Handle wave transition
                if (waveTransition[0]) {
                    long elapsed = (currentTime - waveTransitionStart[0]) / 1_000_000;
                    if (elapsed >= 2000) {
                        waveTransition[0] = false;
                        waveNumber[0]++;
                        enemiesToSpawn[0] += 2;
                        logger.info("Spawning Wave {} with {} enemies", waveNumber[0], enemiesToSpawn[0]);
                        potionSpawner.spawnRandomPotion(1);
                        enemyList.clear();
                        enemySpawner.spawnRandomEnemies(enemiesToSpawn[0]);
                        enemyList.addAll(enemySpawner.getEnemies());
                        logger.debug("Spawned {} new enemies for Wave {}", enemyList.size(), waveNumber[0]);
                        mainContainer.getEnemiesView().repaint();
                    }
                } else if (enemyList.isEmpty()) {
                    logger.debug("All enemies dead for wave {}", waveNumber[0]);
                    logger.info("Initiating transition for Wave {}", waveNumber[0] + 1);
                    waveTransition[0] = true;
                    waveTransitionStart[0] = currentTime;
                    mainContainer.getEnemiesView().repaint();
                }
            }

            SwingUtilities.invokeLater(() -> {
                mainContainer.getPlayerGunView().repaint();
                mainContainer.getEnemiesView().repaint();
                mainContainer.getCoinsView().repaint();
            });
        }, 0, 16, TimeUnit.MILLISECONDS); // ~60 FPS

        // Repaint loop for potions view
        ScheduledExecutorService potionExecutor = Executors.newSingleThreadScheduledExecutor();
        potionExecutor.scheduleAtFixedRate(() -> {
            SwingUtilities.invokeLater(() -> {
                mainContainer.getPotionsView().repaint();
            });
        }, 0, 100, TimeUnit.MILLISECONDS); // ~10 FPS

        // Repaint loop for coins view
        ScheduledExecutorService coinRepaintExecutor = Executors.newSingleThreadScheduledExecutor();
        coinRepaintExecutor.scheduleAtFixedRate(() -> {
            SwingUtilities.invokeLater(() -> {
                mainContainer.getCoinsView().repaint();
            });
        }, 0, 100, TimeUnit.MILLISECONDS); // ~10 FPS

        // Setup potion collision handling
        PotionCollisionHandler potionCollisionHandler = new PotionCollisionHandler(potionList, player, inventory);
        ScheduledExecutorService collisionExecutor = Executors.newSingleThreadScheduledExecutor();
        collisionExecutor.scheduleAtFixedRate(() -> {
            potionCollisionHandler.checkCollisions();
        }, 0, 50, TimeUnit.MILLISECONDS); // Check 20x per second

        // Setup coin collision handling
        CoinCollisionHandler coinCollisionHandler = new CoinCollisionHandler(coins, player);
        ScheduledExecutorService coinCollisionExecutor = Executors.newSingleThreadScheduledExecutor();
        coinCollisionExecutor.scheduleAtFixedRate(() -> {
            coinCollisionHandler.checkCollisions();
        }, 0, 50, TimeUnit.MILLISECONDS); // Check 20x per second

        // Shutdown on window close
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                executor.shutdown();
                potionExecutor.shutdown();
                collisionExecutor.shutdown();
                coinCollisionExecutor.shutdown();
                coinRepaintExecutor.shutdown();
                for (Enemy enemy : enemyList) {
                    enemy.cleanup();
                }
            }
        });
    }
}