package Main.GUI.Enemy;

import Main.Game.Character.Enemy;

public class EnemyController {
    private final Enemy enemy;


    public EnemyController(Enemy enemy) {
        this.enemy = enemy;
    }

    public void update(float deltaTime) {
        enemy.update(deltaTime);
    }
}
