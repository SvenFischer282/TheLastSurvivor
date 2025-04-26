package Main.Game.Character;

import Main.GUI.MainApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Represents an enemy character in the game.
 */
public class Enemy extends Character {
    private final static Logger logger = LoggerFactory.getLogger(MainApp.class);
    private float speed;
    private boolean ableToHit;
    private boolean canBeHitByBullet;
    private boolean canBeHitBySword;
    private Color color;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private List<Enemy> allEnemies;

    /**
     * Constructor for Enemy.
     * @param health Initial health.
     * @param positionX Initial X position.
     * @param positionY Initial Y position.
     * @param damage Damage dealt.
     * @param speed Movement speed.
     */
    public Enemy(int health, int positionX, int positionY, int damage, float speed) {
        super(health, positionX, positionY, damage);
        this.speed = speed;
        this.ableToHit = true;
        this.canBeHitByBullet = true;
        this.canBeHitBySword = true;
        this.color = Color.BLUE;
    }

    /**
     * Reduces enemy health, logs death if health reaches 0.
     * @param damage Amount of damage taken.
     */
    @Override
    public void takeDamage(int damage) {
        if (this.getHealth() - damage > 0) {
            this.setHealth(this.getHealth() - damage);
        } else {
            this.setHealth(0);
            logger.info("Enemy died");
        }
    }

    /**
     * Gets the scheduler for delayed tasks.
     * @return The scheduler.
     */
    public ScheduledExecutorService getScheduler() {
        return scheduler;
    }

    /**
     * Gets the list of all enemies.
     * @return List of enemies.
     */
    public List<Enemy> getAllEnemies() {
        return allEnemies;
    }

    /**
     * Checks if enemy is alive.
     * @return True if health > 0.
     */
    public boolean isAlive() {
        return getHealth() > 0;
    }

    /**
     * Checks if enemy is dead.
     * @return True if health <= 0.
     */
    public boolean isDead() {
        return getHealth() <= 0;
    }

    /**
     * Updates enemy state, including movement and attacks.
     * @param deltaTime Time since last update.
     * @param player Reference to the player.
     */
    @Override
    public void update(float deltaTime, Player player) {
        moveToPlayer(player, deltaTime);
        hitByBullet(player);
        hitBySword(player);
    }

    /**
     * Moves enemy toward player, avoiding collisions with other enemies.
     * @param player The player to move toward.
     * @param deltaTime Time since last update.
     */
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

        boolean canMove = true;
        if (allEnemies != null) {
            for (Enemy other : allEnemies) {
                if (other == this) continue;
                float dist = (float) Math.sqrt(
                        (nextX - other.getX()) * (nextX - other.getX()) +
                                (nextY - other.getY()) * (nextY - other.getY())
                );
                if (dist < 32) {
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

    /**
     * Attacks the player, dealing damage with a cooldown.
     * @param player The player to attack.
     */
    void attackPlayer(Player player) {
        logger.info("Player was hit by enemy");
        player.takeDamage(this.getDamage());
        ableToHit = false;
        scheduler.schedule(() -> ableToHit = true, 2, TimeUnit.SECONDS);
    }

    /**
     * Checks if enemy is hit by player's bullets.
     * @param player The player firing bullets.
     */
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

    /**
     * Checks if enemy is hit by player's sword.
     * @param player The player swinging the sword.
     */
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

    /**
     * Shuts down the scheduler.
     */
    public void cleanup() {
        scheduler.shutdown();
    }

    /**
     * Gets the enemy's color.
     * @return The color.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the enemy's color.
     * @param color New color.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Gets the enemy's speed.
     * @return The speed.
     */
    public float getSpeed() {
        return speed;
    }

    /**
     * Sets the enemy's speed.
     * @param speed New speed.
     */
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    /**
     * Checks if enemy can attack.
     * @return True if able to hit.
     */
    public boolean isAbleToHit() {
        return ableToHit;
    }

    /**
     * Sets whether enemy can attack.
     * @param ableToHit Ability to hit.
     */
    public void setAbleToHit(boolean ableToHit) {
        this.ableToHit = ableToHit;
    }

    /**
     * Sets the list of all enemies.
     * @param allEnemies List of enemies.
     */
    public void setAllEnemies(List<Enemy> allEnemies) {
        this.allEnemies = allEnemies;
    }

    /**
     * Returns a string representation of the enemy.
     * @return String with enemy details.
     */
    @Override
    public String toString() {
        return "Enemy{" +
                "speed=" + speed +
                ", ableToHit=" + ableToHit +
                ", canBeHitByBullet=" + canBeHitByBullet +
                '}';
    }
}