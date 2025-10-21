package Main.GUI;

import Database.MongoManager;
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
import Main.Game.ScoreCounter;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {

        MongoDatabase database = null;
        try {
            database = MongoManager.getDatabase();
            // Optional: Log that you got the database, though MongoManager already logs success
            logger.info("Database instance acquired successfully.");

        } catch (Exception e) {
            // Handle fatal connection failure
            logger.error("FATAL: Could not initialize MongoDB connection. Exiting application.", e);
            // Optionally exit if the database is essential
            // System.exit(1);
        }
        MongoCollection<Document> playerCollection = database.getCollection("test");

//        for (Document playerDocument : playerCollection.find()) {
//            System.out.println(playerDocument.toJson());
//        }


        Player player = new Player(400, 300);
        Inventory inventory = new Inventory(player);

        // Spawn enemies and potions
        CoinSpawner coinSpawner = new CoinSpawner();
        EnemySpawner enemySpawner = new EnemySpawner(coinSpawner);
        PotionSpawner potionSpawner = new PotionSpawner();
        ScoreCounter scoreCounter = ScoreCounter.getInstance();

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
        frame.setSize(1200, 750);
        frame.setLocationRelativeTo(null);

        // Create StartPanel with button action
        // 1. Declare a final array to hold your StartPanel instance.
        final StartPanel[] panelHolder = new StartPanel[1];

// 2. Initialize the StartPanel, referencing the holder inside the lambda.
        StartPanel startPanel = new StartPanel(e -> {
            // Access the panel using the holder array.
            String playerName = panelHolder[0].getPlayerName();
            if (Objects.equals(playerName, "")) {
                player.setName("Unknown");
            } else {
                player.setName(playerName);
            }
            frame.setContentPane(mainContainer);
            frame.revalidate();
            frame.repaint();
            scoreCounter.setScore(0);
            // Setup controllers when the game actually starts
            setupControllers(mainContainer, player, inventory, enemyList, enemySpawner, potionSpawner, potionList, coins, frame, playerCollection, scoreCounter);
        });

// 3. Assign the newly created instance to the holder.
        panelHolder[0] = startPanel;

        frame.setContentPane(startPanel);
        frame.setVisible(true);
    }

    private static void setupControllers(
            MainContainer mainContainer,
            Player player,
            Inventory inventory,
            List<Enemy> enemyList,
            EnemySpawner enemySpawner,
            PotionSpawner potionSpawner,
            List<Potion> potionList,
            List<Coins> coins,
            JFrame frame,
            MongoCollection<Document> playerCollection, ScoreCounter scoreCounter) {
        PlayerGunController playerController = new PlayerGunController(player, inventory);
        mainContainer.getPlayerGunView().addKeyListener(playerController);
        mainContainer.getPlayerGunView().addMouseListener(playerController);
        mainContainer.getPlayerGunView().requestFocusInWindow();

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

                        String playerName = player.getName(); // Get name once
                        int currentScore = ScoreCounter.getInstance().getScore();

                        // 1. Define the FILTER using the FIELD NAME "player"
                        Bson filter_player = Filters.eq("player", playerName);

                        // 2. Attempt to FIND the player
                        Document db_player = playerCollection.find(filter_player).first();

                        if (db_player == null) {
                            // Player DOES NOT exist: Insert a NEW document
                            // NOTE: We use "player" as the key, matching the filter above.
                            Document newPlayerDocument = new Document()
                                    .append("player", playerName)
                                    .append("score", currentScore);

                            playerCollection.insertOne(newPlayerDocument);
                            logger.info("Player {} added to database with initial score: {}", playerName, currentScore);

                        } else {
                            // Player EXISTS: Check and potentially UPDATE the score
                            Integer db_Score = db_player.getInteger("score");

                            // Check if the current score is higher (handling null/missing score gracefully)
                            if (db_Score == null || currentScore > db_Score) {

                                // 3. Define the UPDATE operation using the FIELD NAME "score"
                                Bson updateOperation = new Document("$set", new Document("score", currentScore));

                                // Perform the update using the filter
                                playerCollection.updateOne(filter_player, updateOperation);
                                logger.info("High score updated for {}: New score is {}", playerName, currentScore);

                            } else {
                                logger.info("Player {} already exists. Current score {} is not a new high score (current best: {}).",
                                        playerName, currentScore, db_Score);
                            }
                        }
                        // Display the Game Over Panel
                        frame.setContentPane(new GameOverPanel(frame, player, playerCollection));
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
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
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
