package Main.Game.Character.EnemyFactory;

import Main.Game.Character.Enemy;
import Main.Utils.RandomBorderCoordinates;

import java.util.ArrayList;
import java.util.List;

public class EnemySpawner {
    private final List<Enemy> enemies = new ArrayList<>();
    private final BasicEnemyFactory basicEnemyFactory;
    private final FastZombieFactory fastZombieFactory;

    public EnemySpawner() {
        this.basicEnemyFactory = new BasicEnemyFactory();
        this.fastZombieFactory = new FastZombieFactory();
    }

    public void spawnBasicEnemies(int amount) {
        for (int i = 0; i < amount; i++) {
            int[] coordinates = RandomBorderCoordinates.getRandomBorderCoordinate();
            int x = coordinates[0];
            int y = coordinates[1];
            Enemy enemy = basicEnemyFactory.createEnemy(x, y);
            enemy.setAllEnemies(enemies);
            enemies.add(enemy);
        }
    }

    public void spawnFastZombies(int amount) {
        for (int i = 0; i < amount; i++) {
            int[] coordinates = RandomBorderCoordinates.getRandomBorderCoordinate();
            int x = coordinates[0];
            int y = coordinates[1];
            Enemy enemy = fastZombieFactory.createEnemy(x, y);
            enemies.add(enemy);
            enemy.setAllEnemies(enemies);

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
