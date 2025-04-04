package Main.GUI.Enemy;

import Main.Game.Character.Enemy;
import Main.Game.Character.Player;

/**
 * Updates the Enemy model based on elapsed time and player
 */
public class EnemyController {
    private final Enemy enemy;
    private final Player player;

    /**
     *
     * @param enemy enemy which it controlls
     * @param player targeted player
     */
    public EnemyController(Enemy enemy,Player player) {
        this.enemy = enemy;
        this.player = player;

    }

    /**
     * Updates the Enemy model based on elapsed time and player
     * @param deltaTime elapsed time
     */
    public void update(float deltaTime) {
        enemy.update(deltaTime,player);
    }
}
