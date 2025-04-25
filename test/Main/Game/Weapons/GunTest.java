package Main.Game.Weapons;

import Main.Game.Character.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GunTest {
    private Gun gun;
    private TestPlayer player;

    // Minimal Player implementation for testing
    static class TestPlayer extends Player {
        private float x, y;

        public TestPlayer(float x, float y) {
            super(0, 0);
            this.x = x;
            this.y = y;
        }

        @Override
        public float getX() {
            return x;
        }

        @Override
        public float getY() {
            return y;
        }

        public void setPosition(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }

    @BeforeEach
    void setUp() {
        player = new TestPlayer(100, 100); // Player at (100, 100)
        gun = new Gun(10, player); // Damage = 10
    }

    @Test
    void constructor_initializesCorrectly() {
        assertEquals(10, gun.getDamage(), "Damage should be 10");
        assertSame(player, gun.getPlayer(), "Player should be set");
        assertTrue(gun.canShoot(), "Gun should be able to shoot");
        assertFalse(gun.isBulletActive(), "Bullet should not be active");
        assertEquals(player.getX(), gun.getBulletPosX(), "Bullet X should be at player's X");
        assertEquals(player.getY(), gun.getBulletPosY(), "Bullet Y should be at player's Y");
        assertEquals(0, gun.getDx(), "Bullet dx should be 0");
        assertEquals(0, gun.getDy(), "Bullet dy should be 0");
    }

    @Test
    void shoot_activatesBulletAndSetsCooldown() throws InterruptedException {
        gun.shoot(200, 200);

        assertTrue(gun.isBulletActive(), "Bullet should be active");
        assertFalse(gun.canShoot(), "Gun should not shoot again");
        assertEquals(player.getX(), gun.getBulletPosX(), "Bullet X should be at player's X");
        assertEquals(player.getY(), gun.getBulletPosY(), "Bullet Y should be at player's Y");

        Thread.sleep(1100); // Wait for 1-second cooldown
        assertTrue(gun.canShoot(), "Gun should shoot after cooldown");
    }

    @Test
    void shoot_whileBulletActive_doesNotShoot() {
        gun.shoot(200, 200);
        float initialX = gun.getBulletPosX();
        float initialY = gun.getBulletPosY();

        gun.shoot(300, 300);

        assertTrue(gun.isBulletActive(), "Bullet should stay active");
        assertFalse(gun.canShoot(), "Gun should not shoot");
        assertEquals(initialX, gun.getBulletPosX(), "Bullet X should not change");
        assertEquals(initialY, gun.getBulletPosY(), "Bullet Y should not change");
    }

    @Test
    void setBulletVelocity_setsVelocityWhenBulletActive() {
        gun.shoot(200, 200);
        gun.setBulletVelocity(50, 30);

        assertEquals(50, gun.getDx(), "Bullet dx should be 50");
        assertEquals(30, gun.getDy(), "Bullet dy should be 30");
    }

    @Test
    void setBulletVelocity_doesNotSetWhenBulletInactive() {
        gun.setBulletVelocity(50, 30);

        assertEquals(0, gun.getDx(), "Bullet dx should stay 0");
        assertEquals(0, gun.getDy(), "Bullet dy should stay 0");
    }

    @Test
    void update_bulletActive_movesBullet() {
        gun.shoot(200, 200);
        gun.setBulletVelocity(50, 30);
        gun.setBulletPosX(100);
        gun.setBulletPosY(100);

        gun.update(1.0f);

        assertEquals(150, gun.getBulletPosX(), "Bullet X should move");
        assertEquals(130, gun.getBulletPosY(), "Bullet Y should move");
        assertTrue(gun.isBulletActive(), "Bullet should stay active");
    }

    @Test
    void update_bulletInactive_doesNotMove() {
        gun.setDx(50);
        gun.setDy(30);
        gun.setBulletPosX(100);
        gun.setBulletPosY(100);

        gun.update(1.0f);

        assertEquals(100, gun.getBulletPosX(), "Bullet X should not move");
        assertEquals(100, gun.getBulletPosY(), "Bullet Y should not move");
        assertFalse(gun.isBulletActive(), "Bullet should stay inactive");
    }

    @Test
    void update_bulletOutOfBoundsLeft_resetsBullet() {
        gun.shoot(200, 200);
        gun.setBulletPosX(-1); // Left of screen
        gun.setBulletPosY(100);
        gun.setDx(-50);
        gun.setDy(0);

        gun.update(1.0f);

        assertEquals(player.getX(), gun.getBulletPosX(), "Bullet X should reset");
        assertEquals(player.getY(), gun.getBulletPosY(), "Bullet Y should reset");
        assertFalse(gun.isBulletActive(), "Bullet should be inactive");
        assertEquals(0, gun.getDx(), "Bullet dx should be 0");
        assertEquals(0, gun.getDy(), "Bullet dy should be 0");
    }

    @Test
    void update_bulletOutOfBoundsRight_resetsBullet() {
        gun.shoot(200, 200);
        gun.setBulletPosX(1201); // Right of screen
        gun.setBulletPosY(100);
        gun.setDx(50);
        gun.setDy(0);

        gun.update(1.0f);

        assertEquals(player.getX(), gun.getBulletPosX(), "Bullet X should reset");
        assertEquals(player.getY(), gun.getBulletPosY(), "Bullet Y should reset");
        assertFalse(gun.isBulletActive(), "Bullet should be inactive");
        assertEquals(0, gun.getDx(), "Bullet dx should be 0");
        assertEquals(0, gun.getDy(), "Bullet dy should be 0");
    }

    @Test
    void update_bulletOutOfBoundsTop_resetsBullet() {
        gun.shoot(200, 200);
        gun.setBulletPosX(100);
        gun.setBulletPosY(-1); // Above screen
        gun.setDx(0);
        gun.setDy(-30);

        gun.update(1.0f);

        assertEquals(player.getX(), gun.getBulletPosX(), "Bullet X should reset");
        assertEquals(player.getY(), gun.getBulletPosY(), "Bullet Y should reset");
        assertFalse(gun.isBulletActive(), "Bullet should be inactive");
        assertEquals(0, gun.getDx(), "Bullet dx should be 0");
        assertEquals(0, gun.getDy(), "Bullet dy should be 0");
    }

    @Test
    void update_bulletOutOfBoundsBottom_resetsBullet() {
        gun.shoot(200, 200);
        gun.setBulletPosX(100);
        gun.setBulletPosY(723); // Below screen
        gun.setDx(0);
        gun.setDy(30);

        gun.update(1.0f);

        assertEquals(player.getX(), gun.getBulletPosX(), "Bullet X should reset");
        assertEquals(player.getY(), gun.getBulletPosY(), "Bullet Y should reset");
        assertFalse(gun.isBulletActive(), "Bullet should be inactive");
        assertEquals(0, gun.getDx(), "Bullet dx should be 0");
        assertEquals(0, gun.getDy(), "Bullet dy should be 0");
    }

    @Test
    void update_playerMoves_bulletResetsToNewPlayerPosition() {
        gun.shoot(200, 200);
        gun.setBulletPosX(1201); // Out of bounds
        gun.setBulletPosY(100);
        gun.setDx(50);
        gun.setDy(0);

        player.setPosition(200, 200);
        gun.update(1.0f);

        assertEquals(200, gun.getBulletPosX(), "Bullet X should reset to new player X");
        assertEquals(200, gun.getBulletPosY(), "Bullet Y should reset to new player Y");
        assertFalse(gun.isBulletActive(), "Bullet should be inactive");
    }
}