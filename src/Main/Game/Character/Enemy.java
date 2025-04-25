package Main.Game.Character;

import Main.GUI.MainApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Enemy extends Character {
    private final static Logger logger = LoggerFactory.getLogger(MainApp.class);
    private float speed;
    private boolean ableToHit;
    private boolean canBeHitByBullet;
    private boolean canBeHitBySword;
    private Color color;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private List<Enemy> allEnemies; // Odkaz na všetkých nepriateľov

    public Enemy(int health, int positionX, int positionY, int damage, float speed) {
        super(health, positionX, positionY, damage);
        this.speed = speed;
        this.ableToHit = true;
        this.canBeHitByBullet = true;
        this.canBeHitBySword = true;
        this.color = Color.BLUE;
    }

    public void takeDamage(int damage) {
        if (this.getHealth() - damage > 0) {
            this.setHealth(this.getHealth() - damage);
        } else {
            this.setHealth(0);
            logger.info("Enemy died");
        }
    }

    public ScheduledExecutorService getScheduler() {
        return scheduler;
    }

    public List<Enemy> getAllEnemies() {
        return allEnemies;
    }

    public boolean isAlive() {
        return getHealth() > 0;
    }
    public boolean isDead(){
        if (getHealth() <= 0) {
            return true;
        }
        return false;
    }
    @Override
    public void update(float deltaTime, Player player) {
        moveToPlayer(player, deltaTime);
        hitByBullet(player);
        hitBySword(player);
    }

    void moveToPlayer(Player player, float deltaTime) {
        float hitboxRadius = 32;

        float dx = player.getX() - this.getX();
        float dy = player.getY() - this.getY();

        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        if (distance > 0) {
            dx /= distance;
            dy /= distance;
        }

        float nextX = this.getX() + (dx * this.speed * deltaTime);
        float nextY = this.getY() + (dy * this.speed * deltaTime);

        // Zamedzenie kolízii s inými nepriateľmi
        boolean canMove = true;
        if (allEnemies != null) {
            for (Enemy other : allEnemies) {
                if (other == this) continue;

                float dist = (float) Math.sqrt(
                        (nextX - other.getX()) * (nextX - other.getX()) +
                                (nextY - other.getY()) * (nextY - other.getY())
                );

                if (dist < 32) { // Zabránime prekrývaniu
                    canMove = false;
                    break;
                }
            }
        }

        if (canMove) {
            this.setPositionX(nextX);
            this.setPositionY(nextY);
        }

        float newDistanceX = Math.abs(this.getX() - player.getX());
        float newDistanceY = Math.abs(this.getY() - player.getY());
        float newDistance = (float) Math.sqrt(newDistanceX * newDistanceX + newDistanceY * newDistanceY);

        if (newDistance < hitboxRadius && ableToHit) {
            attackPlayer(player);
        }
    }

    void attackPlayer(Player player) {
        logger.info("Player was hit by enemy");
        player.takeDamage(this.getDamage());
        ableToHit = false;

        scheduler.schedule(() -> ableToHit = true, 2, TimeUnit.SECONDS);
    }

    void hitByBullet(Player player) {
        if (!canBeHitByBullet) return;

        for (Player.Gun.Bullet bullet : player.getGun().getBullets()) {
            if (!bullet.isBulletActive()) continue;

            float bulletX = bullet.getBulletPosX();
            float bulletY = bullet.getBulletPosY();

            if (bulletX > this.getX() - 16 && bulletX < this.getX() + 16 &&
                    bulletY > this.getY() - 16 && bulletY < this.getY() + 16) {

                this.takeDamage(player.getDamage());
                logger.info("Player hit enemy with bullet");
                bullet.deactivate();
                canBeHitByBullet = false;

                scheduler.schedule(() -> canBeHitByBullet = true, 300, TimeUnit.MILLISECONDS);
                break;
            }
        }
    }

    void hitBySword(Player player) {
        if (!canBeHitBySword || !player.getSword().isSwinging()) return;

        int centerX = (int) player.getX() + 32;
        int centerY = (int) player.getY() + 32;
        int hitboxX, hitboxY, hitboxWidth, hitboxHeight;

        switch (player.getDirection()) {
            case RIGHT:
                hitboxX = centerX;
                hitboxY = centerY - 32;
                hitboxWidth = 64;
                hitboxHeight = 64;
                break;
            case LEFT:
                hitboxX = centerX - 64;
                hitboxY = centerY - 32;
                hitboxWidth = 64;
                hitboxHeight = 64;
                break;
            case UP:
                hitboxX = centerX - 16;
                hitboxY = centerY - 96;
                hitboxWidth = 32;
                hitboxHeight = 96;
                break;
            case DOWN:
                hitboxX = centerX - 16;
                hitboxY = centerY;
                hitboxWidth = 32;
                hitboxHeight = 96;
                break;
            default:
                return;
        }

        if (this.getX() + 16 > hitboxX && this.getX() - 16 < hitboxX + hitboxWidth &&
                this.getY() + 16 > hitboxY && this.getY() - 16 < hitboxY + hitboxHeight) {

            this.takeDamage(player.getSword().getDamage());
            canBeHitBySword = false;

            scheduler.schedule(() -> canBeHitBySword = true, 300, TimeUnit.MILLISECONDS);
        }
    }

    public void cleanup() {
        scheduler.shutdown();
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public boolean isAbleToHit() {
        return ableToHit;
    }

    public void setAbleToHit(boolean ableToHit) {
        this.ableToHit = ableToHit;
    }

    public void setAllEnemies(List<Enemy> allEnemies) {
        this.allEnemies = allEnemies;
    }

    @Override
    public String toString() {
        return "Enemy{" +
                "speed=" + speed +
                ", ableToHit=" + ableToHit +
                ", canBeHitByBullet=" + canBeHitByBullet +
                '}';
    }
}
