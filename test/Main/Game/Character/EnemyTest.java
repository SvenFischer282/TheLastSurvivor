package Main.Game.Character;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnemyTest {

    // Simple Player implementation for testing
    private static class TestPlayer extends Character {
        public TestPlayer(int health, int positionX, int positionY, int damage) {
            super(health, positionX, positionY, damage);
        }

        @Override
        public void update(float deltaTime, Player player) {
            // Not needed for testing Enemy
        }
    }

    private Enemy enemy;
    private Player player;

    @BeforeEach
    void setUp() {
        enemy = new Enemy(100, 0, 0, 20);
        player = new Player(100, 100);
    }

    @Test
    void testConstructor() {
        assertEquals(100, enemy.getHealth());
        assertEquals(0f, enemy.getX());
        assertEquals(0f, enemy.getY());
        assertEquals(20, enemy.getDamage());
        assertEquals(500f, enemy.speed);
        assertTrue(enemy.ableToHit);
    }

    @Test
    void testMoveToPlayerMovesCloser() {
        float initialX = enemy.getX();
        float initialY = enemy.getY();

        enemy.moveToPlayer(player, 0.1f);

        assertTrue(enemy.getX() > initialX);
        assertTrue(enemy.getY() > initialY);
    }

    @Test
    void testMoveToPlayerDistanceCalculation() {
        // Move for 0.1 seconds with speed 100
        enemy.moveToPlayer(player, 0.1f);

        // Should move approximately 10 units (speed * deltaTime) in each direction
        // Accounting for normalization
        float expectedDistanceMoved = 10f;
        float newX = enemy.getX();
        float newY = enemy.getY();

        assertTrue(Math.abs(newX - expectedDistanceMoved / Math.sqrt(2)) < 0.1f);
        assertTrue(Math.abs(newY - expectedDistanceMoved / Math.sqrt(2)) < 0.1f);
    }



    @Test
    void testNoAttackWhenOutOfRange() {
        int initialPlayerHealth = player.getHealth();
        enemy.moveToPlayer(player, 0.1f);

        assertEquals(initialPlayerHealth, player.getHealth());
        assertTrue(enemy.ableToHit);
    }

    @Test
    void testUpdateCallsMoveToPlayer() {
        float initialX = enemy.getX();
        float initialY = enemy.getY();

        enemy.update(0.1f, player);

        assertNotEquals(initialX, enemy.getX());
        assertNotEquals(initialY, enemy.getY());
    }
    @Test
    void testPlayerAttack(){
        enemy.moveToPlayer(player, 1f);
        enemy.attackPlayer(player);
    }


}