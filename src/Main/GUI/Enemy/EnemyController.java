package Main.GUI.Enemy;

import Main.Game.Character.Enemy;
import Main.Game.Character.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Updates the Enemy model based on elapsed time and player
 */
public class EnemyController {
    private static final Logger logger = LoggerFactory.getLogger(EnemyController.class);
    private final Enemy enemy;
    private final Player player;

    /**
     * @param enemy enemy which it controls
     * @param player targeted player
     */
    public EnemyController(Enemy enemy, Player player) {
        this.enemy = enemy;
        this.player = player;
    }

    /**
     * Returns the enemy controlled by this controller.
     *
     * @return The Enemy instance
     */
    public Enemy getEnemy() {
        return enemy;
    }

    /**
     * Updates the Enemy model based on elapsed time and player
     * @param deltaTime elapsed time
     */
    public void update(float deltaTime) {
        float oldX = enemy.getX();
        float oldY = enemy.getY();
        enemy.update(deltaTime, player);

    }
}