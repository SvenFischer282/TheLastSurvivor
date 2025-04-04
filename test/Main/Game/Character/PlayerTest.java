package Main.Game.Character;

import Main.Game.ScoreCounter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    private Player player;

    @BeforeEach
    public void setUp() {
        // Reset ScoreCounter singleton and initialize Player
        ScoreCounter.getInstance().setScore(0);
        player = new Player(100, 100);
    }

    // Test Player initialization
    @Test
    public void testPlayerInitialization() {
        assertEquals(10, player.getHealth(), "Health should be 10");
        assertEquals(100, player.getX(), 0.001, "X position should be 100");
        assertEquals(100, player.getY(), 0.001, "Y position should be 100");
        assertEquals(500.0f, player.getSpeed(), "Speed should be 500");
        assertFalse(player.isRotation(), "Rotation should be false");
        assertNotNull(player.getGun(), "Gun should not be null");
    }

    // Test Player movement and boundaries
    @Test
    public void testPlayerMovementAndBoundaries() {
        player.setVelocity(500, 500);
        player.update(0.1f); // Move for 0.1 seconds
        assertEquals(150, player.getX(), 0.001, "X should be 150 after movement");
        assertEquals(150, player.getY(), 0.001, "Y should be 150 after movement");

        // Test lower boundary
        player.setVelocity(-2000, -2000);
        player.update(0.1f);
        assertEquals(0, player.getX(), 0.001, "X should not go below 0");
        assertEquals(0, player.getY(), 0.001, "Y should not go below 0");

        // Test upper boundary
        player.setPositionX(1100);
        player.setPositionY(600);
        player.setVelocity(2000, 2000);
        player.update(0.1f);
        assertEquals(1168, player.getX(), 0.001, "X should not exceed 1168 (1200-32)");
        assertEquals(668, player.getY(), 0.001, "Y should not exceed 668 (700-32)");
    }

    // Test healing
    @Test
    public void testHeal() {
        player.setHealth(5);
        player.heal(3);
        assertEquals(8, player.getHealth(), "Health should be 8 after healing by 3");

        player.heal(5); // Exceeding MAX_HEALTH
        assertEquals(10, player.getHealth(), "Health should not exceed 10 (MAX_HEALTH)");
    }

    // Test adding score
    @Test
    public void testAddScore() {
        player.addScore(50);
        assertEquals(50, ScoreCounter.getInstance().getScore(), "Score should be 50");
        player.addScore(25);
        assertEquals(75, ScoreCounter.getInstance().getScore(), "Score should be 75 after adding 25");
    }

    // Test Gun initialization
    @Test
    public void testGunInitialization() {
        Player.Gun gun = player.getGun();
        assertEquals(10, gun.getDamage(), "Gun damage should be 10");
        assertTrue(gun.canShoot(), "Gun should be able to shoot");
        assertFalse(gun.isBulletActive(), "Bullet should not be active");
        assertEquals(player.getX(), gun.getBulletPosX(), 0.001, "Bullet X position should match Player X");
        assertEquals(player.getY(), gun.getBulletPosY(), 0.001, "Bullet Y position should match Player Y");
    }

    // Test Gun shooting and updating
    @Test
    public void testGunShootAndUpdate() throws InterruptedException {
        Player.Gun gun = player.getGun();
        gun.shoot(200, 200);
        assertFalse(gun.canShoot(), "Gun should not be able to shoot after firing");
        assertTrue(gun.isBulletActive(), "Bullet should be active after shooting");

        // Set bullet velocity and update
        gun.setBulletVelocity(1000, 1000);
        gun.update(0.1f);
        assertEquals(200, gun.getBulletPosX(), 0.001, "Bullet X should be 200 after 0.1s");
        assertEquals(200, gun.getBulletPosY(), 0.001, "Bullet Y should be 200 after 0.1s");

        // Test bullet reset when out of bounds
        gun.update(1.2f); // Moves bullet beyond SCREEN_WIDTH (1200)
        assertFalse(gun.isBulletActive(), "Bullet should be inactive after going out of bounds");
        assertEquals(player.getX(), gun.getBulletPosX(), 0.001, "Bullet X should reset to Player X");
        assertEquals(player.getY(), gun.getBulletPosY(), 0.001, "Bullet Y should reset to Player Y");

        // Verify cooldown (wait 1.1s)
        Thread.sleep(1100);
        assertTrue(gun.canShoot(), "Gun should be able to shoot again after cooldown");
    }
}