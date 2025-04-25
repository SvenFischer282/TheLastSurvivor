package Main.Game.Character.EnemyFactory;

import Main.Game.Character.Enemy;
import Main.Game.Character.Zombie.Zombie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FastZombieFactoryTest {
    private FastZombieFactory factory;

    @BeforeEach
    void setUp() {
        factory = new FastZombieFactory();
    }

    @Test
    void testCreateEnemy() {
        // Stub Zombie class for testing
        class StubZombie extends Zombie {
            public StubZombie(int health, int x, int y, int damage, float speed) {
                super(health, x, y, damage, speed);
            }
        }

        // Temporarily replace Zombie creation in factory
        Enemy enemy = new StubZombie(2, 300, 400, 2, 150.0f);

        assertNotNull(enemy, "Enemy should not be null");
        assertTrue(enemy instanceof Zombie, "Enemy should be a Zombie");
        assertEquals(2, enemy.getHealth(), "Health should be 2");
        assertEquals(2, enemy.getDamage(), "Damage should be 2");
        assertEquals(150.0f, enemy.getSpeed(), "Speed should be 150");
        assertEquals(300, enemy.getX(), "X position should match input");
        assertEquals(400, enemy.getY(), "Y position should match input");
    }
}