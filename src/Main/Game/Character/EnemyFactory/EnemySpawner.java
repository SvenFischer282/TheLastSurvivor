package Main.Game.Character.EnemyFactory;

import Main.Game.Character.Enemy;

import java.util.ArrayList;
import java.util.List;

public class EnemySpawner {
    private final List<Enemy> enemies = new ArrayList<>();
    private final BasicEnemyFactory basicEnemyFactory;

    public EnemySpawner() {
        this.basicEnemyFactory = new BasicEnemyFactory();
    }

    public void spawnBasicEnemies(int amount) {
        for (int i = 0; i < amount; i++) {
            // Spread them out horizontally, and vertically by a bit
            int x = 200 + (i * 100);
            int y = 200 + (i * 50);
            Enemy enemy = basicEnemyFactory.createEnemy(x, y);
            enemies.add(enemy);
        }
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public void removeDeadEnemies() {
        for (Enemy enemy : enemies) {
            if (enemy.getHealth() <= 0) {
                enemy.cleanup();
            }
        }
        enemies.removeIf(enemy -> enemy.getHealth() <= 0);
    }
}
