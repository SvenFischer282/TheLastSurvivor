package Main.Game.Weapons;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import Main.Game.Character.Player;

/**
 * Represents a gun weapon in the game.
 */
public class Gun extends Weapon {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private Player player;
    private boolean canShoot;
    private float bulletPosX;
    private float bulletPosY;
    private float dx, dy;
    private boolean bulletActive;
    private static final int SCREEN_WIDTH = 1200;
    private static final int SCREEN_HEIGHT = 722;

    /**
     * Constructs a new Gun instance.
     * @param damage The damage value of the gun
     * @param player The player wielding the gun
     */
    public Gun(int damage, Player player) {
        super(damage);
        this.player = player;
        canShoot = true;
        bulletActive = false;
        bulletPosX = player.getX();
        bulletPosY = player.getY();
        dx = 0;
        dy = 0;
    }

    /**
     * Fires the gun towards target coordinates.
     * @param target_x The x-coordinate of the target
     * @param target_y The y-coordinate of the target
     */
    public void shoot(int target_x, int target_y) {
        if (canShoot && !bulletActive) {
            canShoot = false;
            bulletActive = true;
            bulletPosX = player.getX();
            bulletPosY = player.getY();

            scheduler.schedule(() -> {
                canShoot = true;
            }, 1000, TimeUnit.MILLISECONDS);
        }
    }

    /**
     * Sets the bullet's velocity.
     * @param dx The change in x direction
     * @param dy The change in y direction
     */
    public void setBulletVelocity(float dx, float dy) {
        if (bulletActive && this.dx == 0 && this.dy == 0) {
            this.dx = dx;
            this.dy = dy;
        }
    }

    /**
     * Updates the bullet's position based on time elapsed.
     * @param deltaTime Time elapsed since last update
     */
    public void update(float deltaTime) {
        if (bulletActive) {
            bulletPosX += dx * deltaTime;
            bulletPosY += dy * deltaTime;

            // Check if bullet is out of bounds
            if (bulletPosX < 0 || bulletPosX > SCREEN_WIDTH ||
                    bulletPosY < 0 || bulletPosY > SCREEN_HEIGHT) {
                resetBullet();
            }
        }
    }

    /**
     * Resets the bullet to its initial state.
     */
    private void resetBullet() {
        bulletActive = false;
        dx = 0;
        dy = 0;
        bulletPosX = player.getX();
        bulletPosY = player.getY();
    }

    /**
     * Gets the bullet's current x position.
     * @return The x-coordinate of the bullet
     */
    public float getBulletPosX() {
        return bulletPosX;
    }

    /**
     * Gets the bullet's current y position.
     * @return The y-coordinate of the bullet
     */
    public float getBulletPosY() {
        return bulletPosY;
    }

    /**
     * Checks if a bullet is currently active.
     * @return true if bullet is active, false otherwise
     */
    public boolean isBulletActive() {
        return bulletActive;
    }

    /**
     * Checks if the gun can shoot.
     * @return true if gun can shoot, false otherwise
     */
    public boolean canShoot() {
        return canShoot;
    }
}