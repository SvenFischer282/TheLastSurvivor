package Main.Game.Character;

import Main.Game.ScoreCounter;
import Main.Game.Weapons.Weapon;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Represents a player character in the game.
 */
public class Player extends Character {
    private float vx, vy;
    private int health;
    private float speed;
    private boolean rotation;
    private final Gun gun; // Inštancia vnorenej triedy Gun
    private final Sword sword;
    private static final int MAX_HEALTH = 10;
    private final ScoreCounter scoreCounter = ScoreCounter.getInstance();
    private final static Logger logger = LoggerFactory.getLogger(Player.class);
    public enum Direction{
        UP, DOWN, LEFT, RIGHT
    }
    Direction direction;

    /**
     * Constructs a new Player instance.
     * @param x Initial x-coordinate
     * @param y Initial y-coordinate
     */
    public Player(int x, int y) {
        super(10, x, y,2);
        this.vx = 0;
        this.vy = 0;
        this.speed = 500.0f; // Default speed
        this.rotation = false;
        this.gun = new Gun(this.getDamage());
        this.sword = new Sword(this.getDamage());
        this.direction = Direction.RIGHT;
        logger.info("Player was inicialized");

    }

    /**
     * Updates the player's position and gun based on time elapsed.
     * @param deltaTime Time elapsed since last update
     */
    @Override
    public void update(float deltaTime) {
        float changeX = getX() + vx * deltaTime;
        float changeY = getY() + vy * deltaTime;
        if (changeX < 0) changeX = 0;
        if (changeY < 0) changeY = 0;
        if (changeY > (700 - 32)) changeY = (700 - 32);
        if (changeX > (1200 - 32)) changeX = (1200 - 32);
        setPositionX(changeX);
        setPositionY(changeY);
        gun.update(deltaTime);

    }
    public void heal(int value){

        if (value + this.getHealth() > MAX_HEALTH) {
            this.setHealth(MAX_HEALTH);
            logger.info("Player was healed to max health");
        }
        else{
            this.setHealth(this.getHealth() + value);
            logger.info("Player was healed");
        }
    }
    public void addScore(int value){
        logger.info("Adding score to player");
        scoreCounter.setScore(scoreCounter.getScore() + value);
    }

    @Override
    public void update(float deltaTime, Player player) {
    }

    public void setVelocity(float vx, float vy) {
        this.vx = vx;
        this.vy = vy;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public float getVx() {
        return vx;
    }

    public float getVy() {
        return vy;
    }

    public float getSpeed() {
        return speed;
    }

    public boolean isRotation() {
        return rotation;
    }

    public void setRotation(boolean rotation) {
        this.rotation = rotation;
    }

    public Gun getGun() {
        return gun;
    }
    public Sword getSword() {return sword;}

    /**
     * Nested class representing a gun weapon wielded by the player.
     */
    public class Gun extends Weapon {
        private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        private boolean canShoot;
        private float bulletPosX;
        private float bulletPosY;
        private float dx, dy;
        private boolean bulletActive;
        private static final int SCREEN_WIDTH = 1200;
        private static final int SCREEN_HEIGHT = 722;

        /**
         * Constructs a new gun istance
         * @param damage Initial damage
         */
        public Gun(int damage) {
            super(damage);
            canShoot = true;
            bulletActive = false;
            bulletPosX = Player.this.getX();
            bulletPosY = Player.this.getY();
            dx = 0;
            dy = 0;
        }

        /**
         * Handles bullet shooting and reloading time
         * @param target_x Target`s X coordinate
         * @param target_y Target`s Y coordinate
         */
        public void shoot(int target_x, int target_y) {
            if (canShoot && !bulletActive) {
                canShoot = false;
                bulletActive = true;
                bulletPosX = Player.this.getX();
                bulletPosY = Player.this.getY();


            }else{
                logger.warn("Gun can not shot yet");
            }
        }
        /**
         * Setter for bullet velocity.
         * Only sets the velocity if the bullet is active and currently stationary.
         *
         * @param dx The desired horizontal velocity component.
         * @param dy The desired vertical velocity component.
         */
        public void setBulletVelocity(float dx, float dy) {
            if (bulletActive && this.dx == 0 && this.dy == 0) {
                this.dx = dx;
                this.dy = dy;
            }
        }

        /**
         * Updates the bullet's position based on its velocity and the elapsed time.
         * If the bullet moves off-screen, it is reset.
         *
         * @param deltaTime The time elapsed since the last update
         */
        public void update(float deltaTime) {
            if (bulletActive) {
                bulletPosX += dx * deltaTime;
                bulletPosY += dy * deltaTime;

                if (bulletPosX < 0 || bulletPosX > SCREEN_WIDTH ||
                        bulletPosY < 0 || bulletPosY > SCREEN_HEIGHT ) {
                    resetBullet();

                }
            }
        }

        /**
         * Resets the bullet's state to inactive.
         * This sets the bulletActive flag to false, zeroes out its velocity,
         * and repositions it at the player's current coordinates.
         */
        public void resetBullet() {
            bulletActive = false;
            canShoot = true;
            dx = 0;
            dy = 0;
            bulletPosX = Player.this.getX();
            bulletPosY = Player.this.getY();
            logger.info("Bullet was reseted");
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

    public class Sword extends Weapon{
        private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        private boolean isSwinging;
        public Sword(int damage) {
            super(damage * 2); // Sword deals more damage than the gun
            this.isSwinging = false;
        }

        /**
         * Starts a sword swing if it's not already swinging.
         * The swing lasts for 300ms and then ends automatically.
         */
        public void swing() {
            if (!isSwinging) {
                isSwinging = true;
                scheduler.schedule(() -> isSwinging = false, 300, TimeUnit.MILLISECONDS);
            }
        }

        /**
         * Returns true if the sword is currently swinging (can hit an enemy).
         */
        public boolean isSwinging() {
            return isSwinging;
        }


    }

}