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
 * Represents a player character in the game.
 */
public class Player extends Character implements GameStateSubject {
    private float vx, vy;
    private int health;
    private float speed;
    private boolean rotation;
    private final Gun gun; // Inštancia vnorenej triedy Gun
    private final Sword sword;
    private static final int MAX_HEALTH = 10;
    private final ScoreCounter scoreCounter = ScoreCounter.getInstance();
    private final static Logger logger = LoggerFactory.getLogger(Player.class);
    private List<GameStateObserver> observers = new ArrayList<>();



    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    Direction direction;

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
        this.speed = 500.0f; // Default speed
        this.rotation = false;
        this.gun = new Gun(this.getDamage());
        this.sword = new Sword(this.getDamage() / 2);
        this.direction = Direction.RIGHT;
        logger.info("Player was inicialized");

    }

    public ScoreCounter getScoreCounter() {
        return scoreCounter;
    }


    @Override
    public void addObserver(GameStateObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(GameStateObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (GameStateObserver observer : observers) {
            observer.update();
        }
    }

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
     *
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

    public void heal(int value) throws NegativeValueException {
        if (value < 0) {

            throw new NegativeValueException("Cannot heal with negative value: " );

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

    public void addScore(int value) {
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
    public int getHealth() {
        return this.health;
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

    public Sword getSword() {
        return sword;
    }

    public class Gun extends Weapon {
        private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        private boolean canShoot;
        private static final int SCREEN_WIDTH = 1200;
        private static final int SCREEN_HEIGHT = 722;
        private final List<Bullet> bullets = new ArrayList<>();

        public Gun(int damage) {
            super(damage/2);
            canShoot = true;
        }

        public void shoot (int target_x, int target_y)  throws GunNotReadyException {
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

                // Povolenie ďalšieho výstrelu po oneskorení
                scheduler.schedule(() -> canShoot = true, 500, TimeUnit.MILLISECONDS);
            } else {
                throw new GunNotReadyException("Gun isn't ready yet");
            }
        }

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

        public List<Bullet> getBullets() {
            return bullets;
        }

        public boolean canShoot() {
            return canShoot;
        }

        public class Bullet {
            private float bulletPosX;
            private float bulletPosY;
            private final float dx;
            private final float dy;
            private boolean bulletActive;

            public Bullet(float bulletPosX, float bulletPosY, float dx, float dy, boolean bulletActive) {
                this.bulletPosX = bulletPosX;
                this.bulletPosY = bulletPosY;
                this.dx = dx;
                this.dy = dy;
                this.bulletActive = bulletActive;
            }

            public float getBulletPosX() {
                return bulletPosX;
            }

            public float getBulletPosY() {
                return bulletPosY;
            }

            public void setBulletPosX(float bulletPosX) {
                this.bulletPosX = bulletPosX;
            }
            public void setBulletPosY(float bulletPosY) {
                this.bulletPosY = bulletPosY;
            }
            public boolean isBulletActive() {
                return bulletActive;
            }
            public void deactivate() {
                this.bulletActive = false;
            }

            public float getDx() {
                return dx;
            }

            public float getDy() {
                return dy;
            }
        }
    }


    public class Sword extends Weapon {
        private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        private boolean isSwinging;
        private boolean swingAvalible;

        public Sword(int damage) {
            super(damage * 2); // Sword deals more damage than the gun
            this.isSwinging = false;
            swingAvalible = true;
        }

        /**
         * Starts a sword swing if it's not already swinging.
         * The swing lasts for 300ms and then ends automatically.
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
         * Returns true if the sword is currently swinging (can hit an enemy).
         */
        public boolean isSwinging() {
            return isSwinging;
        }


    }

}