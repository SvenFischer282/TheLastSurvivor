package Main.Game.Character;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EnemyTest {
    private Enemy enemy;
    private Player player;
    private final float deltaTime = 0.016f; // ~60 FPS

    // Minimal stub for Character superclass
    static abstract class Character {
        private int health;
        private float x, y;
        private int damage;

        public Character(int health, float x, float y, int damage) {
            this.health = health;
            this.x = x;
            this.y = y;
            this.damage = damage;
        }

        public int getHealth() {
            return health;
        }

        public void setHealth(int health) {
            this.health = health;
        }

        public float getX() {
            return x;
        }

        public void setPositionX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setPositionY(float y) {
            this.y = y;
        }

        public int getDamage() {
            return damage;
        }
    }

    // Minimal stub for Weapon superclass
    static abstract class Weapon {
        private int damage;

        public Weapon(int damage) {
            this.damage = damage;
        }

        public int getDamage() {
            return damage;
        }
    }

    @BeforeEach
    void setUp() {
        enemy = new Enemy(100, 0, 0, 10, 100.0f);
        player = new Player(100, 100);
    }

    @Test
    void testConstructor() {
        assertEquals(100, enemy.getHealth());
        assertEquals(0, enemy.getX(), 0.001);
        assertEquals(0, enemy.getY(), 0.001);
        assertEquals(10, enemy.getDamage());
        assertEquals(100.0f, enemy.getSpeed());
        assertTrue(enemy.isAbleToHit());
        assertTrue(enemy.isAlive());
        assertFalse(enemy.isDead());
        assertEquals(Color.BLUE, enemy.getColor());
    }

    @Test
    void testTakeDamage() {
        enemy.takeDamage(30);
        assertEquals(70, enemy.getHealth());
        assertTrue(enemy.isAlive());
        assertFalse(enemy.isDead());
    }

    @Test
    void testTakeDamageUntilDeath() {
        enemy.takeDamage(100);
        assertEquals(0, enemy.getHealth());
        assertFalse(enemy.isAlive());
        assertTrue(enemy.isDead());
    }

    @Test
    void testMoveToPlayerWithoutCollision() {
        List<Enemy> enemies = new ArrayList<>();
        enemies.add(enemy);
        enemy.setAllEnemies(enemies);

        enemy.moveToPlayer(player, deltaTime);

        // Expected movement towards player at (100,100) with speed 100
        float distance = (float) Math.sqrt(100 * 100 + 100 * 100);
        float dx = 100.0f / distance;
        float dy = 100.0f / distance;
        float expectedX = dx * 100.0f * deltaTime;
        float expectedY = dy * 100.0f * deltaTime;

        assertEquals(expectedX, enemy.getX(), 0.1);
        assertEquals(expectedY, enemy.getY(), 0.1);
    }

    @Test
    void testMoveToPlayerWithCollision() {
        List<Enemy> enemies = new ArrayList<>();
        Enemy otherEnemy = new Enemy(100, 10, 10, 10, 100.0f);
        enemies.add(enemy);
        enemies.add(otherEnemy);
        enemy.setAllEnemies(enemies);

        enemy.moveToPlayer(player, deltaTime);

        // Should not move due to collision with otherEnemy at (10,10)
        assertEquals(0, enemy.getX(), 0.001);
        assertEquals(0, enemy.getY(), 0.001);
    }

    @Test
    void testAttackPlayer() {
        // Move enemy close to player (within hitbox radius of 32)
        enemy = new Enemy(100, 80, 80, 10, 100.0f);
        enemy.attackPlayer(player);

        assertEquals(0, player.getHealth());
        assertFalse(enemy.isAbleToHit());

        // Simulate scheduler completion
        enemy.setAbleToHit(true);
        assertTrue(enemy.isAbleToHit());
    }

    @Test
    void testHitByBullet() {
        // Shoot a bullet close to the enemy
        player.getGun().shoot(0, 0); // Shoot towards enemy position
        Player.Gun.Bullet bullet = player.getGun().getBullets().get(0);
        // Move bullet to enemy's position
        bullet.setBulletPosX(0) ;
        bullet.setBulletPosY(0);

        enemy.hitByBullet(player);

        assertEquals(98, enemy.getHealth());
        assertFalse(bullet.isBulletActive());
        assertTrue(enemy.isAbleToHit());

        // Simulate scheduler completion
        enemy.setAbleToHit(true);
        assertTrue(enemy.isAbleToHit());
    }

    @Test
    void testHitByBulletMiss() {
        // Shoot a bullet far from the enemy
        player.getGun().shoot(1000, 1000); // Shoot far away
        Player.Gun.Bullet bullet = player.getGun().getBullets().get(0);
       bullet.setBulletPosX(1000);
        bullet.setBulletPosY(1000);

        enemy.hitByBullet(player);

        assertEquals(100, enemy.getHealth());
        assertTrue(bullet.isBulletActive());
        assertTrue(enemy.isAbleToHit());
    }

    @Test
    void testHitBySword() {
        // Position enemy within sword hitbox (RIGHT direction)
        enemy = new Enemy(100, 150, 100, 10, 100.0f); // Within hitbox for RIGHT
        player.getSword().swing(); // Start swinging

        enemy.hitBySword(player);

        assertEquals(98, enemy.getHealth()); // 100 - 4 (sword damage = player damage / 2 * 2 = 2 * 2)
        assertTrue(enemy.isAbleToHit());

        // Simulate scheduler completion
        enemy.setAbleToHit(true);
        assertTrue(enemy.isAbleToHit());
    }

    @Test
    void testHitBySwordMiss() {
        // Position enemy outside sword hitbox
        enemy = new Enemy(100, 300, 300, 10, 100.0f); // Far away
        player.getSword().swing(); // Start swinging

        enemy.hitBySword(player);

        assertEquals(100, enemy.getHealth());
        assertTrue(enemy.isAbleToHit());
    }

    @Test
    void testCleanup() {
        enemy.cleanup();
        assertTrue(enemy.getScheduler().isShutdown());
    }

    @Test
    void testSettersAndGetters() {
        enemy.setSpeed(200.0f);
        assertEquals(200.0f, enemy.getSpeed());

        enemy.setColor(Color.RED);
        assertEquals(Color.RED, enemy.getColor());

        enemy.setAbleToHit(false);
        assertFalse(enemy.isAbleToHit());

        List<Enemy> enemies = new ArrayList<>();
        enemy.setAllEnemies(enemies);
        assertEquals(enemies, enemy.getAllEnemies());
    }

    @Test
    void testToString() {
        String expected = "Enemy{speed=100.0, ableToHit=true, canBeHitByBullet=true}";
        assertEquals(expected, enemy.toString());
    }
}