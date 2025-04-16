package Main.GUI.Enemy;

import Main.Game.Character.Enemy;
import Main.Game.Character.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Controls a list of enemies, updating each one based on game logic.
 */
public class EnemiesController {
    private final List<Enemy> enemies;
    private final List<EnemyController> controllers;

    public EnemiesController(List<Enemy> enemies, Player player) {
        this.enemies = enemies;
        this.controllers = new ArrayList<>();

        for (Enemy enemy : enemies) {
            controllers.add(new EnemyController(enemy, player));
        }
    }

    /**
     * Updates all enemies via their individual controllers.
     * Removes dead enemies from the list.
     *
     * @param deltaTime Elapsed time since last update
     */
    public void updateAll(float deltaTime) {
        Iterator<Enemy> enemyIterator = enemies.iterator();
        Iterator<EnemyController> controllerIterator = controllers.iterator();

        while (enemyIterator.hasNext() && controllerIterator.hasNext()) {
            Enemy enemy = enemyIterator.next();
            EnemyController controller = controllerIterator.next();

            if (enemy.getHealth() > 0) {
                controller.update(deltaTime);
            } else {
                enemy.cleanup(); // shut down the scheduler
                enemyIterator.remove();
                controllerIterator.remove();
            }
        }
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }
}
