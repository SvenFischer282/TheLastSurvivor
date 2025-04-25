package Main.Game.Character;

import Main.Game.ScoreCounter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class PlayerTest {
    private Player player;

    @BeforeEach
    public void setUp() {
        player = new Player(100, 100);
    }

    @Test
    public void testConstructor() {
        assertEquals(10, player.getHealth());
        assertEquals(100, player.getX(), 0.001);
        assertEquals(100, player.getY(), 0.001);
        assertEquals(2, player.getDamage());
        assertEquals(500.0f, player.getSpeed(), 0.001);
        assertFalse(player.isRotation());
        assertEquals(Player.Direction.RIGHT, player.getDirection());
        assertNotNull(player.getGun());
        assertNotNull(player.getSword());
    }

    @Test
    public void testTakeDamage() {
        player.takeDamage(3);
        assertEquals(7, player.getHealth());
        player.takeDamage(8);
        assertEquals(0, player.getHealth());
    }

    @Test
    public void testHeal() {
        player.takeDamage(5);
        player.heal(3);
        assertEquals(8, player.getHealth());
        player.heal(5);
        assertEquals(10, player.getHealth()); // Should not exceed MAX_HEALTH
        player.heal(-1); // Should not change health
        assertEquals(10, player.getHealth());
    }

    @Test
    public void testAddScore() {
        ScoreCounter.getInstance().setScore(0);
        player.addScore(100);
        assertEquals(100, ScoreCounter.getInstance().getScore());
    }

    @Test
    public void testUpdateMovement() {
        player.setVelocity(100, 200);
        player.update(0.1f);
        assertEquals(110, player.getX(), 0.001);
        assertEquals(120, player.getY(), 0.001);
    }

    @Test
    public void testBoundaryConstraints() {
        // Test left boundary
        player.setPositionX(0);
        player.setVelocity(-100, 0);
        player.update(0.1f);
        assertEquals(0, player.getX(), 0.001);

        // Test right boundary
        player.setPositionX(1200 - 32);
        player.setVelocity(100, 0);
        player.update(0.1f);
        assertEquals(1200 - 32, player.getX(), 0.001);

        // Test top boundary
        player.setPositionY(0);
        player.setVelocity(0, -100);
        player.update(0.1f);
        assertEquals(0, player.getY(), 0.001);

        // Test bottom boundary
        player.setPositionY(700 - 32);
        player.setVelocity(0, 100);
        player.update(0.1f);
        assertEquals(700 - 32, player.getY(), 0.001);
    }

    @Test
    public void testGunShoot() {
        Player.Gun gun = player.getGun();
        gun.shoot(200, 200);
        assertFalse(gun.canShoot());
        assertEquals(1, gun.getBullets().size());
        Player.Gun.Bullet bullet = gun.getBullets().get(0);
        assertTrue(bullet.isBulletActive());
        assertEquals(player.getX(), bullet.getBulletPosX(), 0.001);
        assertEquals(player.getY(), bullet.getBulletPosY(), 0.001);
    }

    @Test
    public void testGunUpdate() {
        Player.Gun gun = player.getGun();
        gun.shoot(200, 200);
        gun.update(0.1f);
        Player.Gun.Bullet bullet = gun.getBullets().get(0);
        assertTrue(bullet.getBulletPosX() != player.getX());
        assertTrue(bullet.getBulletPosY() != player.getY());
    }

    @Test
    public void testSwordSwing() {
        Player.Sword sword = player.getSword();
        sword.swing();
        assertTrue(sword.isSwinging());
        // Note: Cannot easily test timing-based behavior without waiting
    }

    @Test
    public void testObserverNotification() {
        class TestObserver implements Main.Utils.Observer.GameStateObserver {
            boolean updated = false;
            @Override
            public void update() {
                updated = true;
            }
        }
        TestObserver observer = new TestObserver();
        player.addObserver(observer);
        player.takeDamage(1);
        assertTrue(observer.updated);
        player.removeObserver(observer);
        observer.updated = false;
        player.heal(1);
        assertFalse(observer.updated);
    }
}