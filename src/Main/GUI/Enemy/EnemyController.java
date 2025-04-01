package Main.GUI.Enemy;

import Main.Game.Character.Enemy;
import Main.Game.Character.Player;

public class EnemyController {
    private final Enemy enemy;
    private final Player player;


    public EnemyController(Enemy enemy,Player player) {
        this.enemy = enemy;
        this.player = player;

    }

    public void update(float deltaTime) {
        enemy.update(deltaTime,player);
    }
}
