package Main.Game.Character.EnemyFactory;

import Main.Game.Character.Enemy;
import Main.Game.Collectible.Coins.CoinFactory.CoinSpawner;
import Main.Utils.RandomBorderCoordinates;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Spawns enemies at random border coordinates, ensuring they do not overlap with each other.
 */
public class EnemySpawner {
    private final List<Enemy> enemies = new ArrayList<>();
    private final BasicEnemyFactory basicEnemyFactory;
    private final FastZombieFactory fastZombieFactory;
    private final CoinSpawner coinSpawner;
    private static final float MIN_SPAWN_DISTANCE = 64.0f; // Minimum distance between enemies (based on enemy size)
    private static final int MAX_SPAWN_ATTEMPTS = 10; // Maximum attempts to find a non-overlapping position
    private final Random random = new Random();
    enum enemyVariants{
        BASIC,FAST_ZOMBIE,
    }
    public EnemySpawner(CoinSpawner coinSpawner) {
        this.basicEnemyFactory = new BasicEnemyFactory();
        this.fastZombieFactory = new FastZombieFactory();
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
                // Log a warning or handle failure (e.g., skip this enemy)
                System.err.println("Failed to spawn basic enemy after " + MAX_SPAWN_ATTEMPTS + " attempts");
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
                // Log a warning or handle failure (e.g., skip this enemy)
                System.err.println("Failed to spawn fast zombie after " + MAX_SPAWN_ATTEMPTS + " attempts");
            }
        }
    }

    /**
     * Checks if the given position is valid (i.e., doesn't overlap with existing enemies).
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
                return false; // Overlaps with an existing enemy
            }
        }
        return true; // No overlaps
    }

    /**
     * Returns the list of spawned enemies.
     * @return List of enemies.
     */
    public List<Enemy> getEnemies() {
        return enemies;
    }
    public void spawnRandomEnemies(int amount) {
        enemyVariants[] variants = enemyVariants.values();
        for (int i = 0; i < amount; i++) {
            enemyVariants variant  = variants[random.nextInt(enemyVariants.values().length)];
            if (variant == enemyVariants.BASIC){
                spawnBasicEnemies(1);
            } else if (variant == enemyVariants.FAST_ZOMBIE) {
                spawnFastZombies(1);
            }
        }

    }

    /**
     * Removes enemies with zero or less health from the list.
     */
    public void removeDeadEnemies() {
        for (Enemy enemy : enemies) {
            if (enemy.getHealth() <= 0) {
                coinSpawner.spawnGoldCoin((int)enemy.getX(),(int)enemy.getY());
                enemy.cleanup();
            }
        }
        enemies.removeIf(enemy -> enemy.getHealth() <= 0);
    }
}