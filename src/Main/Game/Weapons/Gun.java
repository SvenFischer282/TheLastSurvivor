package Main.Game.Weapons;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import Main.Game.Character.Player;

public class Gun extends Weapon {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private Player player;
    private boolean canShoot;
    private float bulletPosX;
    private float bulletPosY;
    private float dx, dy;
    private boolean bulletActive;
    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 600;

    public Gun(int damage, Player player) {
        super(damage);
        this.player = player;
        canShoot = true;
        bulletActive = false;
        bulletPosX = player.getPositionX();
        bulletPosY = player.getPositionY();
        dx = 0;
        dy = 0;
    }

    public void shoot(int target_x, int target_y) {
        if (canShoot && !bulletActive) {
            canShoot = false;
            bulletActive = true;
            bulletPosX = player.getPositionX();
            bulletPosY = player.getPositionY();
            scheduler.schedule(() -> {
                canShoot = true;

            }, 1000, TimeUnit.MILLISECONDS);
        }
    }

    public void setBulletVelocity(float dx, float dy) {
        if (bulletActive && this.dx == 0 && this.dy == 0) {
            this.dx = dx;
            this.dy = dy;
        }
    }

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

    private void resetBullet() {
        bulletActive = false;
        dx = 0;
        dy = 0;
        bulletPosX = player.getPositionX();
        bulletPosY = player.getPositionY();

    }

    public float getBulletPosX() {
        return bulletPosX;
    }

    public float getBulletPosY() {
        return bulletPosY;
    }

    public boolean isBulletActive() {
        return bulletActive;
    }

    public boolean canShoot() {
        return canShoot;
    }
}