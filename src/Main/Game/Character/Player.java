package Main.Game.Character;

import Main.Game.ScoreCounter;
import Main.Game.Weapons.Weapon;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Represents a player character in the game.
 */
public class Player extends Character {
    private float vx, vy;
    private int health;
    private float speed;
    private boolean rotation;
    private final Gun gun; // Inštancia vnorenej triedy Gun
    private static final int MAX_HEALTH = 10;
    private final ScoreCounter scoreCounter = ScoreCounter.getInstance();

    /**
     * Constructs a new Player instance.
     * @param x Initial x-coordinate
     * @param y Initial y-coordinate
     */
    public Player(int x, int y) {
        super(10, x, y,5);
        this.vx = 0;
        this.vy = 0;
        this.speed = 500.0f; // Default speed
        this.rotation = false;
        this.gun = new Gun(10); // Inicializácia Gun s damage 10
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
        gun.update(deltaTime); // Aktualizácia zbrane
    }
    public void heal(int value){

        if (value + this.getHealth() > MAX_HEALTH) {
            this.setHealth(MAX_HEALTH);
        }
        else{
            this.setHealth(this.getHealth() + value);
        }
    }
    public void addScore(int value){
        scoreCounter.setScore(scoreCounter.getScore() + value);
    }

    @Override
    public void update(float deltaTime, Player player) {
        // Nie je potrebné, ponechané prázdne
    }

    public void setVelocity(float vx, float vy) {
        this.vx = vx;
        this.vy = vy;
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


        public Gun(int damage) {
            super(damage);
            canShoot = true;
            bulletActive = false;
            bulletPosX = Player.this.getX(); // Prístup k vonkajšej triede
            bulletPosY = Player.this.getY();
            dx = 0;
            dy = 0;
        }

        public void shoot(int target_x, int target_y) {
            if (canShoot && !bulletActive) {
                canShoot = false;
                bulletActive = true;
                bulletPosX = Player.this.getX();
                bulletPosY = Player.this.getY();

                scheduler.schedule(() -> canShoot = true, 1000, TimeUnit.MILLISECONDS);
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

                if (bulletPosX < 0 || bulletPosX > SCREEN_WIDTH ||
                        bulletPosY < 0 || bulletPosY > SCREEN_HEIGHT ) {
                    resetBullet();
                }

            }
        }

        private void resetBullet() {
            bulletActive = false;
            dx = 0;
            dy = 0;
            bulletPosX = Player.this.getX();
            bulletPosY = Player.this.getY();
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
}