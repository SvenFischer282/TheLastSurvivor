package Main.Game.Character;

import Main.GUI.MainApp;
import org.slf4j.Logger;

import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents an enemy character in the game.
 * Enemies move towards the player (to be implemented)
 * and attack when within range. They have a cooldown period after attacking.
 * Extends the base {@link Character} class.
 */
public class Enemy extends Character {
    private final static Logger logger = LoggerFactory.getLogger(MainApp.class);
    /** The movement speed of the enemy (currently unused ). */
   private float speed;

    /** Flag indicating if the enemy is currently capable of attacking. */
   private boolean ableToHit;
   private boolean canBeHitByBullet ;
   private boolean canBeHitBySword;
   private Color color;

    /** Scheduler service to handle the attack cooldown timing. */
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    /**
     * Constructs a new Enemy instance.
     *
     * @param health     The initial health points of the enemy.
     * @param positionX  The initial X coordinate of the enemy.
     * @param positionY  The initial Y coordinate of the enemy.
     * @param damage     The amount of damage the enemy inflicts per attack.
     */
    public Enemy(int health, int positionX, int positionY, int damage,float speed) {
        super(health, positionX, positionY, damage);
        this.speed = speed;
        this.ableToHit = true;
        this.canBeHitByBullet = true;
        this.canBeHitBySword = true;
        this.color = Color.BLUE;
    }
    public void takeDamage(int damage){
        if (this.getHealth() - damage > 0){
            this.setHealth(this.getHealth()-damage);
        }else {
            this.setHealth(0);
            logger.info("Ennemy died");

        }
    }


    /**
     * Updates the enemy's state, primarily checking for player proximity to attack.
     * @param deltaTime The time elapsed since the last update .
     * @param player    A reference to the player character for interaction.
     */
    @Override
    public void update(float deltaTime, Player player) {

        moveToPlayer(player,deltaTime);
        hitByBullet(player);
        hitBySword(player);

    }

    /**
     * Move the enemy towards the player
     * Checks the distance to the player and initiates an attack if within range and ready.
     * @param player The Player object to check the distance against.
     * @param deltaTime The time elapsed from the last update
     */
    void moveToPlayer(Player player, float deltaTime) {
        float hitboxRadius = 32;
         // Movement speed in units per second

        // Calculate direction vector
        float dx = player.getX() - this.getX();
        float dy = player.getY() - this.getY();

        // Normalize the direction vector (convert to unit vector)
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        if (distance > 0) {
            dx /= distance;
            dy /= distance;
        }

        // Move toward player
        this.setPositionX(this.getX() + (dx * this.speed * deltaTime));
        this.setPositionY(this.getY() + (dy * this.speed * deltaTime));

        // Check collision after movement
        float newDistanceX = Math.abs(this.getX() - player.getX());
        float newDistanceY = Math.abs(this.getY() - player.getY());
        float newDistance = (float) Math.sqrt(newDistanceX * newDistanceX + newDistanceY * newDistanceY);

        if (newDistance < hitboxRadius && ableToHit) {
            attackPlayer(player);
        }
    }

    /**
     * Performs an attack on the specified player and starts the attack cooldown.
     * Inflicts damage, prevents immediate re-attacking, and schedules the attack
     * availability to be restored after a delay.
     *
     * @param player The Player object to attack.
     */
    void attackPlayer(Player player) {
        logger.info("Player was hit by enemy");
        player.takeDamage(this.getDamage());
        ableToHit = false;


        scheduler.schedule(() -> {
            ableToHit = true;

        }, 2, TimeUnit.SECONDS);
    }
    void hitByBullet(Player player) {
        if (!canBeHitByBullet) return;  // prevent multiple hits

        for (Player.Gun.Bullet bullet : player.getGun().getBullets()) {
            if (!bullet.isBulletActive()) continue;

            float bulletX = bullet.getBulletPosX();
            float bulletY = bullet.getBulletPosY();

            if (bulletX > this.getX() - 16 && bulletX < this.getX() + 16 &&
                    bulletY > this.getY() - 16 && bulletY < this.getY() + 16) {

                this.takeDamage(player.getDamage());
                logger.info("Player hit enemy with bullet");
                bullet.deactivate();  // prevent multiple hits from same bullet
                canBeHitByBullet = false;

                // Allow the enemy to be hit by the next bullet after a short delay
                scheduler.schedule(() -> canBeHitByBullet = true,
                        300, TimeUnit.MILLISECONDS);
                break; // exit after one successful hit
            }
        }
    }


    void hitBySword(Player player) {
        if (!canBeHitBySword || !player.getSword().isSwinging()) return; // Prevent multiple hits and check if sword is swinging

        int centerX = (int) player.getX() + 32; // Player center X
        int centerY = (int) player.getY() + 32; // Player center Y
        int hitboxX, hitboxY, hitboxWidth, hitboxHeight;

        switch (player.getDirection()) {
            case RIGHT:
                hitboxX = centerX; // Start at player’s center
                hitboxY = centerY - 32; // Centered vertically
                hitboxWidth = 64; // Extend right
                hitboxHeight = 64; // Wide enough to cover player height
                break;
            case LEFT:
                hitboxX = centerX - 64; // Extend left
                hitboxY = centerY - 32; // Centered vertically
                hitboxWidth = 64; // Wide hitbox
                hitboxHeight = 64; // Wide enough to cover player height
                break;
            case UP:
                hitboxX = centerX - 16; // Centered horizontally
                hitboxY = centerY - 96; // Extend upward
                hitboxWidth = 32; // Narrow to match player width
                hitboxHeight = 96; // Tall hitbox
                break;
            case DOWN:
                hitboxX = centerX - 16; // Centered horizontally
                hitboxY = centerY; // Extend downward
                hitboxWidth = 32; // Narrow to match player width
                hitboxHeight = 96; // Tall hitbox
                break;
            default:
                return;
        }

        // Check if enemy is within the sword's hitbox
        if (this.getX() + 16 > hitboxX && this.getX() - 16 < hitboxX + hitboxWidth &&
                this.getY() + 16 > hitboxY && this.getY() - 16 < hitboxY + hitboxHeight) {

            this.takeDamage(player.getSword().getDamage());

            canBeHitBySword = false;

            // Allow the enemy to be hit by the next sword swing
            scheduler.schedule(() -> {
                canBeHitBySword = true;
            }, 300, TimeUnit.MILLISECONDS);
        }
    }

    /**
     * Function to shut down the scheduler when the enemy is killed
     */
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

    @Override
    public String toString() {
        return "Enemy{" +
                "speed=" + speed +
                ", ableToHit=" + ableToHit +
                ", canBeHitByBullet=" + canBeHitByBullet +
                '}';
    }
}