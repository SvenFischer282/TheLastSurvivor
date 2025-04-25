package Main.Game.Character.EnemyFactory;

import Main.Game.Character.Enemy;
import Main.Game.Character.Zombie.BigZombie;
import Main.Game.Character.Zombie.FastZombie;
import Main.Game.Collectible.Coins.CoinFactory.CoinSpawner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class EnemySpawnerTest {
    EnemySpawner spawner;
    @BeforeEach
    void setUp() {
        CoinSpawner coinSpawner = new CoinSpawner();
        spawner = new EnemySpawner(coinSpawner);
    }

    @Test
    void spawnBasicEnemies() {
        spawner.spawnBasicEnemies(2);
        assertEquals(2,spawner.getEnemies().size());
    }

    @Test
    void spawnFastZombies() {
        spawner.spawnFastZombies(2);
        for (int i = 0; i < 2; i++) {
            assertInstanceOf(FastZombie.class, spawner.getEnemies().get(i));
        }

    }

    @Test
    void getEnemies() {
        spawner.spawnBasicEnemies(10);
        assertEquals(10,spawner.getEnemies().size());

    }

    @Test
    void spawnRandomEnemies() {
        spawner.spawnRandomEnemies(2);
        assertEquals(2,spawner.getEnemies().size());
    }

    @Test
    void removeDeadEnemies() {
        spawner.spawnBasicEnemies(2);
        List<Enemy> enemies = spawner.getEnemies();
        enemies.get(0).setHealth(0);
        spawner.removeDeadEnemies();
        assertEquals(1,spawner.getEnemies().size());
    }

    @Test
    void spawnBigZombies() {
        spawner.spawnBigZombies(2);
        for (int i = 0; i < 2; i++) {
            assertInstanceOf(BigZombie.class, spawner.getEnemies().get(i));
        }
    }
}