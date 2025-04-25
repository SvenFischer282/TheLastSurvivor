package Main.Game.Character.EnemyFactory;

import Main.Game.Character.Enemy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BasicEnemyFactoryTest {
    private BasicEnemyFactory factory;

    @BeforeEach
    void setUp() {
        factory = new BasicEnemyFactory();
    }

    @Test
    void testCreateEnemy() {
        Enemy enemy = factory.createEnemy(100, 200);

        assertNotNull(enemy, "Enemy should not be null");
        assertEquals(4, enemy.getHealth(), "Health should be 4");
        assertEquals(1, enemy.getDamage(), "Damage should be 1");
        assertEquals(100.0f, enemy.getSpeed(), "Speed should be 100");
        assertEquals(100, enemy.getX(), "X position should match input");
        assertEquals(200, enemy.getY(), "Y position should match input");
    }
}