package Main.GUI.Enemy;

import Main.Game.Character.Enemy;
import Main.Game.Character.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Manages a list of enemies, updating their states and handling their controllers.
 */
public class EnemiesController {
    private static final Logger logger = LoggerFactory.getLogger(EnemiesController.class);
    private final List<Enemy> enemies;
    private final List<EnemyController> controllers;
    private final Player player;
    private static final float MIN_DISTANCE = 20.0f; // Minimum distance between enemies
    private static final float REPULSION_FORCE = 100.0f; // Strength of repulsion force

    /**
     * Constructs an EnemiesController to manage enemy updates and interactions.
     * @param enemies The list of enemies to control.
     * @param player The player character for enemy interactions.
     */
    public EnemiesController(List<Enemy> enemies, Player player) {
        this.enemies = enemies;
        this.controllers = new ArrayList<>();
        this.player = player;

        // Initialize controllers for initial enemies
        for (Enemy enemy : enemies) {
            controllers.add(new EnemyController(enemy, player));
        }
    }

    /**
     * Updates all enemies, removes dead ones, adds controllers for new enemies, and applies repulsion.
     * @param deltaTime Time elapsed since the last update.
     */
    public void updateAll(float deltaTime) {
        // Remove dead enemies and their controllers
        Iterator<Enemy> enemyIterator = enemies.iterator();
        Iterator<EnemyController> controllerIterator = controllers.iterator();

        while (enemyIterator.hasNext() && controllerIterator.hasNext()) {
            Enemy enemy = enemyIterator.next();
            EnemyController controller = controllerIterator.next();

            if (enemy.getHealth() <= 0) {
                enemy.cleanup(); // Shut down the scheduler
                enemyIterator.remove();
                controllerIterator.remove();
            }
        }

        // Add controllers for new enemies
        for (Enemy enemy : enemies) {
            boolean hasController = controllers.stream().anyMatch(c -> c.getEnemy() == enemy);
            if (!hasController) {
                controllers.add(new EnemyController(enemy, player));
                logger.info("Added EnemyController for enemy at ({}, {})", enemy.getX(), enemy.getY());
            }
        }

        // Update all living enemies
        for (EnemyController controller : controllers) {
            if (controller.getEnemy().getHealth() > 0) {
                controller.update(deltaTime);
            }
        }
    }

    /**
     * Gets the list of managed enemies.
     * @return The list of enemies.
     */
    public List<Enemy> getEnemies() {
        return enemies;
    }
}