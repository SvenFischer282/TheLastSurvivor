package Main.GUI.Enemy;

import Main.Game.Character.Enemy;
import Main.Game.Character.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Manages updates for a single enemy based on elapsed time and player interactions.
 */
public class EnemyController {
    private static final Logger logger = LoggerFactory.getLogger(EnemyController.class);
    private final Enemy enemy;
    private final Player player;

    /**
     * Constructs an EnemyController for a specific enemy and player.
     * @param enemy The enemy to control.
     * @param player The player targeted by the enemy.
     */
    public EnemyController(Enemy enemy, Player player) {
        this.enemy = enemy;
        this.player = player;
    }

    /**
     * Gets the enemy managed by this controller.
     * @return The Enemy instance.
     */
    public Enemy getEnemy() {
        return enemy;
    }

    /**
     * Updates the enemy's state based on elapsed time and player interaction.
     * @param deltaTime Time elapsed since the last update.
     */
    public void update(float deltaTime) {
        float oldX = enemy.getX();
        float oldY = enemy.getY();
        enemy.update(deltaTime, player);
    }
}