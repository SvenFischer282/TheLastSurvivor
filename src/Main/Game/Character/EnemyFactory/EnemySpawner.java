package Main.Game.Character.EnemyFactory;

import Main.Game.Character.Enemy;
import Main.Game.Character.Zombie.BigZombie;
import Main.Game.Collectible.Coins.CoinFactory.CoinSpawner;
import Main.Utils.RandomBorderCoordinates;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Spawns enemies at random border coordinates, ensuring they do not overlap with each other.
 */
public class EnemySpawner {
    private static final Logger logger = LoggerFactory.getLogger(EnemySpawner.class);
    private final List<Enemy> enemies = new ArrayList<>();
    private final BasicEnemyFactory basicEnemyFactory;
    private final FastZombieFactory fastZombieFactory;
    private final BigZombieFactory bigZombieFactory;
    private final CoinSpawner coinSpawner;
    private static final float MIN_SPAWN_DISTANCE = 64.0f; // Minimum distance between enemies
    private static final int MAX_SPAWN_ATTEMPTS = 10; // Maximum attempts to find a non-overlapping position
    private final Random random = new Random();

    /**
     * Enum for different enemy variants.
     */
    enum EnemyVariants {
        BASIC, FAST_ZOMBIE, BIG_ZOMBIE
    }

    /**
     * Constructs an EnemySpawner with a reference to a CoinSpawner.
     * @param coinSpawner The CoinSpawner for spawning coins when enemies die.
     */
    public EnemySpawner(CoinSpawner coinSpawner) {
        this.basicEnemyFactory = new BasicEnemyFactory();
        this.fastZombieFactory = new FastZombieFactory();
        this.bigZombieFactory = new BigZombieFactory();
        this.coinSpawner = coinSpawner;
    }

    /**
     * Spawns basic enemies, ensuring they don't overlap with existing enemies.
     * @param amount Number of enemies to spawn.
     */
    public void spawnBasicEnemies(int amount) {
        for (int i = 0; i < amount; i++) {
            boolean spawned = false;
            int attempts = 0;

            while (!spawned && attempts < MAX_SPAWN_ATTEMPTS) {
                int[] coordinates = RandomBorderCoordinates.getRandomBorderCoordinate();
                int x = coordinates[0];
                int y = coordinates[1];

                if (isPositionValid(x, y)) {
                    Enemy enemy = basicEnemyFactory.createEnemy(x, y);
                    enemy.setAllEnemies(enemies);
                    enemies.add(enemy);
                    spawned = true;
                }
                attempts++;
            }

            if (!spawned) {
                logger.warn("Failed to spawn basic enemy after {} attempts", MAX_SPAWN_ATTEMPTS);
            }
        }
    }

    /**
     * Spawns fast zombies, ensuring they don't overlap with existing enemies.
     * @param amount Number of zombies to spawn.
     */
    public void spawnFastZombies(int amount) {
        for (int i = 0; i < amount; i++) {
            boolean spawned = false;
            int attempts = 0;

            while (!spawned && attempts < MAX_SPAWN_ATTEMPTS) {
                int[] coordinates = RandomBorderCoordinates.getRandomBorderCoordinate();
                int x = coordinates[0];
                int y = coordinates[1];

                if (isPositionValid(x, y)) {
                    Enemy enemy = fastZombieFactory.createEnemy(x, y);
                    enemy.setAllEnemies(enemies);
                    enemies.add(enemy);
                    spawned = true;
                }
                attempts++;
            }

            if (!spawned) {
                logger.warn("Failed to spawn fast zombie after {} attempts", MAX_SPAWN_ATTEMPTS);
            }
        }
    }

    /**
     * Spawns big zombies, ensuring they don't overlap with existing enemies.
     * @param amount Number of zombies to spawn.
     */
    public void spawnBigZombies(int amount) {
        for (int i = 0; i < amount; i++) {
            boolean spawned = false;
            int attempts = 0;

            while (!spawned && attempts < MAX_SPAWN_ATTEMPTS) {
                int[] coordinates = RandomBorderCoordinates.getRandomBorderCoordinate();
                int x = coordinates[0];
                int y = coordinates[1];

                if (isPositionValid(x, y)) {
                    BigZombie enemy = bigZombieFactory.createEnemy(x, y);
                    enemy.setAllEnemies(enemies);
                    enemies.add(enemy);
                    spawned = true;
                }
                attempts++;
            }

            if (!spawned) {
                logger.warn("Failed to spawn fast zombie after {} attempts", MAX_SPAWN_ATTEMPTS);
            }
        }
    }

    /**
     * Checks if the given position is valid (no overlap with existing enemies).
     * @param x X-coordinate of the proposed spawn position.
     * @param y Y-coordinate of the proposed spawn position.
     * @return True if the position is valid, false if it overlaps with an existing enemy.
     */
    private boolean isPositionValid(int x, int y) {
        for (Enemy existingEnemy : enemies) {
            float dx = x - existingEnemy.getX();
            float dy = y - existingEnemy.getY();
            float distance = (float) Math.sqrt(dx * dx + dy * dy);

            if (distance < MIN_SPAWN_DISTANCE) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the list of spawned enemies.
     * @return List of enemies.
     */
    public List<Enemy> getEnemies() {
        return enemies;
    }

    /**
     * Spawns a random mix of basic enemies, fast zombies, and big zombies.
     * @param amount Number of enemies to spawn.
     */
    public void spawnRandomEnemies(int amount) {
        EnemyVariants[] variants = EnemyVariants.values();
        for (int i = 0; i < amount; i++) {
            EnemyVariants variant = variants[random.nextInt(variants.length)];
            if (variant == EnemyVariants.BASIC) {
                spawnBasicEnemies(1);
            } else if (variant == EnemyVariants.FAST_ZOMBIE) {
                spawnFastZombies(1);
            } else if (variant == EnemyVariants.BIG_ZOMBIE) {
                spawnBigZombies(1);
            }
        }
    }

    /**
     * Removes dead enemies and spawns a coin at their position.
     */
    public void removeDeadEnemies() {
        enemies.removeIf(enemy -> {
            if (enemy.getHealth() <= 0) {
                int x = (int) enemy.getX();
                int y = (int) enemy.getY();
                if (random.nextBoolean()) {
                    coinSpawner.spawnGoldCoin(x, y);
                    logger.debug("Spawned GoldCoin at ({}, {}) for dead enemy", x, y);
                } else {
                    coinSpawner.spawnSilverCoin(x, y);
                    logger.debug("Spawned SilverCoin at ({}, {}) for dead enemy", x, y);
                }
                enemy.cleanup();
                return true;
            }
            return false;
        });
    }
}