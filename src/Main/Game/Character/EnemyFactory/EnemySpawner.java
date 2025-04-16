package Main.Game.Character.EnemyFactory;

import Main.Game.Character.Enemy;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class EnemySpawner {
    private List<Enemy> enemies= new ArrayList<>();
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private EnemyFactory enemyFactory;
    public EnemySpawner(EnemyFactory enemyFactory){
        this.enemyFactory = enemyFactory;
    }
    public void spawnEnemies(int ammount){
        for (int i = 0; i < ammount; i++) {
            Enemy newEnemy = enemyFactory.createEnemy(100,100);
            enemies.add(newEnemy);

        }
         }

    // Get all spawned enemies
    public List<Enemy> getEnemies() {
        return enemies;
    }

    // Clean up dead enemies
    public void removeDeadEnemies() {
        for (Enemy enemy : enemies) {
            if (enemy.getHealth() <= 0) {
                enemy.cleanup(); // Shut down enemy scheduler
            }
        }
        enemies.removeIf(enemy -> enemy.getHealth() <= 0);
    }
}
