package Main.GUI.Enemy;

import Main.Game.Character.Enemy;
import Main.Game.Character.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controls a list of enemies, updating each one based on game logic.
 */
public class EnemiesController {
    private static final Logger logger = LoggerFactory.getLogger(EnemiesController.class);
    private final List<Enemy> enemies;
    private final List<EnemyController> controllers;
    private final Player player;
    private static final float MIN_DISTANCE = 20.0f; // Minimum distance between enemies
    private static final float REPULSION_FORCE = 100.0f; // Strength of repulsion

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
     * Updates all enemies via their individual controllers.
     * Removes dead enemies and their controllers.
     * Adds controllers for new enemies.
     * Applies repulsion to prevent enemies from overlapping.
     *
     * @param deltaTime Elapsed time since last update
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

    public List<Enemy> getEnemies() {
        return enemies;
    }
}