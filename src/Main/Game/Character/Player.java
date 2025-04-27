package Main.Game.Character;

import Main.Game.Inventory;
import Main.Game.ScoreCounter;
import Main.Game.Weapons.Weapon;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import Main.Utils.Exceptions.GunNotReadyException;
import Main.Utils.Exceptions.NegativeValueException;
import Main.Utils.Observer.GameStateObserver;
import Main.Utils.Observer.GameStateSubject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

/**
 * Represents a player character in the game, extending Character and implementing GameStateSubject.
 */
public class Player extends Character implements GameStateSubject {
    private float vx, vy;
    private int health;
    private float speed;
    private boolean rotation;
    private final Gun gun;
    private final Sword sword;
    private static final int MAX_HEALTH = 10;
    private final ScoreCounter scoreCounter = ScoreCounter.getInstance();
    private final static Logger logger = LoggerFactory.getLogger(Player.class);
    private List<GameStateObserver> observers = new ArrayList<>();

    /**
     * Enum for player movement directions.
     */
    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    private Direction direction;

    /**
     * Constructs a new Player instance.
     *
     * @param x Initial x-coordinate
     * @param y Initial y-coordinate
     */
    public Player(int x, int y) {
        super(MAX_HEALTH, x, y, 2);
        this.health = super.getHealth();
        this.vx = 0;
        this.vy = 0;
        this.speed = 500.0f;
        this.rotation = false;
        this.gun = new Gun(this.getDamage());
        this.sword = new Sword(this.getDamage() / 2);
        this.direction = Direction.RIGHT;
        logger.info("Player was inicialized");
    }

    /**
     * Gets the player's score counter.
     * @return The score counter instance.
     */
    public ScoreCounter getScoreCounter() {
        return scoreCounter;
    }

    /**
     * Adds an observer for game state changes.
     * @param observer The observer to add.
     */
    @Override
    public void addObserver(GameStateObserver observer) {
        observers.add(observer);
    }

    /**
     * Removes an observer for game state changes.
     * @param observer The observer to remove.
     */
    @Override
    public void removeObserver(GameStateObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all observers of game state changes.
     */
    @Override
    public void notifyObservers() {
        for (GameStateObserver observer : observers) {
            observer.update();
        }
    }

    /**
     * Reduces player's health and notifies observers.
     * @param damage Amount of damage to take.
     */
    @Override
    public void takeDamage(int damage) {
        if (this.health - damage > 0) {
            this.health -= damage;
        } else {
            this.health = 0;
        }
        notifyObservers();
        logger.info("Player damaged: " + this.health + this);
    }

    /**
     * Updates the player's position and gun based on time elapsed.
     * @param deltaTime Time elapsed since last update.
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

    /**
     * Heals the player by a specified amount, capped at MAX_HEALTH.
     * @param value Amount to heal.
     * @throws NegativeValueException If the value is negative.
     */
    public void heal(int value) throws NegativeValueException {
        if (value < 0) {
            throw new NegativeValueException("Cannot heal with negative value");
        }
        if (this.health + value > MAX_HEALTH) {
            this.health = MAX_HEALTH;
            logger.info("Player was healed to max health: " + this.health);
        } else {
            this.health += value;
            logger.info("Player was healed by " + value + ". New health: " + this.health);
        }
        notifyObservers();
        logger.debug("Heal function called with value: " + value);
    }

    /**
     * Adds a specified value to the player's score.
     * @param value Score to add.
     */
    public void addScore(int value) {
        logger.info("Adding score to player");
        scoreCounter.setScore(scoreCounter.getScore() + value);
    }

    /**
     * Empty implementation of abstract method from Character.
     * @param deltaTime Time elapsed since last update.
     * @param player Reference to self (not used).
     */
    @Override
    public void update(float deltaTime, Player player) {
    }

    /**
     * Sets the player's velocity.
     * @param vx Velocity in x direction.
     * @param vy Velocity in y direction.
     */
    public void setVelocity(float vx, float vy) {
        this.vx = vx;
        this.vy = vy;
    }

    /**
     * Gets the player's current health.
     * @return Current health value.
     */
    public int getHealth() {
        return this.health;
    }

    /**
     * Gets the player's current direction.
     * @return Current direction.
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Sets the player's direction.
     * @param direction New direction.
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Gets the player's x velocity.
     * @return X velocity.
     */
    public float getVx() {
        return vx;
    }

    /**
     * Gets the player's y velocity.
     * @return Y velocity.
     */
    public float getVy() {
        return vy;
    }

    /**
     * Gets the player's movement speed.
     * @return Speed value.
     */
    public float getSpeed() {
        return speed;
    }

    /**
     * Checks if the player is rotating.
     * @return True if rotating.
     */
    public boolean isRotation() {
        return rotation;
    }

    /**
     * Sets the player's rotation state.
     * @param rotation New rotation state.
     */
    public void setRotation(boolean rotation) {
        this.rotation = rotation;
    }

    /**
     * Gets the player's gun.
     * @return Gun instance.
     */
    public Gun getGun() {
        return gun;
    }

    /**
     * Gets the player's sword.
     * @return Sword instance.
     */
    public Sword getSword() {
        return sword;
    }

    /**
     * Represents the player's gun, handling shooting mechanics.
     */
    public class Gun extends Weapon {
        private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        private boolean canShoot;
        private static final int SCREEN_WIDTH = 1200;
        private static final int SCREEN_HEIGHT = 722;
        private final List<Bullet> bullets = new ArrayList<>();

        /**
         * Constructs a new Gun.
         * @param damage Damage value for the gun.
         */
        public Gun(int damage) {
            super(damage / 2);
            canShoot = true;
        }

        /**
         * Shoots a bullet toward the target coordinates.
         * @param target_x Target x-coordinate.
         * @param target_y Target y-coordinate.
         * @throws GunNotReadyException If the gun is not ready to shoot.
         */
        public void shoot(int target_x, int target_y) throws GunNotReadyException {
            if (canShoot) {
                canShoot = false;
                float bulletPosX = Player.this.getX();
                float bulletPosY = Player.this.getY();
                float dx = target_x - bulletPosX;
                float dy = target_y - bulletPosY;
                float length = (float) Math.sqrt(dx * dx + dy * dy);
                float speed = 1000f;
                float normDX = (dx / length) * speed;
                float normDY = (dy / length) * speed;
                bullets.add(new Bullet(bulletPosX, bulletPosY, normDX, normDY, true));
                scheduler.schedule(() -> canShoot = true, 500, TimeUnit.MILLISECONDS);
            } else {
                throw new GunNotReadyException("Gun isn't ready yet");
            }
        }

        /**
         * Updates bullet positions and removes off-screen bullets.
         * @param deltaTime Time elapsed since last update.
         */
        public void update(float deltaTime) {
            List<Bullet> toRemove = new ArrayList<>();
            for (Bullet bullet : bullets) {
                if (bullet.bulletActive) {
                    bullet.bulletPosX += bullet.dx * deltaTime;
                    bullet.bulletPosY += bullet.dy * deltaTime;
                    if (bullet.bulletPosX < 0 || bullet.bulletPosX > SCREEN_WIDTH ||
                            bullet.bulletPosY < 0 || bullet.bulletPosY > SCREEN_HEIGHT) {
                        toRemove.add(bullet);
                    }
                }
            }
            bullets.removeAll(toRemove);
        }

        /**
         * Gets the list of active bullets.
         * @return List of bullets.
         */
        public List<Bullet> getBullets() {
            return bullets;
        }

        /**
         * Checks if the gun can shoot.
         * @return True if the gun is ready to shoot.
         */
        public boolean canShoot() {
            return canShoot;
        }

        /**
         * Represents a single bullet fired by the gun.
         */
        public class Bullet {
            private float bulletPosX;
            private float bulletPosY;
            private final float dx;
            private final float dy;
            private boolean bulletActive;

            /**
             * Constructs a new Bullet.
             * @param bulletPosX Initial x position.
             * @param bulletPosY Initial y position.
             * @param dx X velocity.
             * @param dy Y velocity.
             * @param bulletActive Active state.
             */
            public Bullet(float bulletPosX, float bulletPosY, float dx, float dy, boolean bulletActive) {
                this.bulletPosX = bulletPosX;
                this.bulletPosY = bulletPosY;
                this.dx = dx;
                this.dy = dy;
                this.bulletActive = bulletActive;
            }

            /**
             * Gets the bullet's x position.
             * @return X position.
             */
            public float getBulletPosX() {
                return bulletPosX;
            }

            /**
             * Gets the bullet's y position.
             * @return Y position.
             */
            public float getBulletPosY() {
                return bulletPosY;
            }

            /**
             * Sets the bullet's x position.
             * @param bulletPosX New x position.
             */
            public void setBulletPosX(float bulletPosX) {
                this.bulletPosX = bulletPosX;
            }

            /**
             * Sets the bullet's y position.
             * @param bulletPosY New y position.
             */
            public void setBulletPosY(float bulletPosY) {
                this.bulletPosY = bulletPosY;
            }

            /**
             * Checks if the bullet is active.
             * @return True if the bullet is active.
             */
            public boolean isBulletActive() {
                return bulletActive;
            }

            /**
             * Deactivates the bullet.
             */
            public void deactivate() {
                this.bulletActive = false;
            }

            /**
             * Gets the bullet's x velocity.
             * @return X velocity.
             */
            public float getDx() {
                return dx;
            }

            /**
             * Gets the bullet's y velocity.
             * @return Y velocity.
             */
            public float getDy() {
                return dy;
            }
        }
    }

    /**
     * Represents the player's sword, handling melee attacks.
     */
    public class Sword extends Weapon {
        private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        private boolean isSwinging;
        private boolean swingAvalible;

        /**
         * Constructs a new Sword.
         * @param damage Damage value for the sword.
         */
        public Sword(int damage) {
            super(damage * 2);
            this.isSwinging = false;
            swingAvalible = true;
        }

        /**
         * Starts a sword swing if available, with a 200ms duration and 2000ms cooldown.
         */
        public void swing() {
            if (!isSwinging && swingAvalible) {
                swingAvalible = false;
                isSwinging = true;
                scheduler.schedule(() -> {
                    isSwinging = false;
                }, 200, TimeUnit.MILLISECONDS);
                scheduler.schedule(() -> {
                    swingAvalible = true;
                }, 2000, TimeUnit.MILLISECONDS);
            }
        }

        /**
         * Checks if the sword is currently swinging.
         * @return True if the sword is swinging.
         */
        public boolean isSwinging() {
            return isSwinging;
        }
    }
}