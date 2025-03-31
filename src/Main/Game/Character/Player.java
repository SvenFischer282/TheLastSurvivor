package Main.Game.Character;

/**
 * Represents a player character in the game.
 */
public class Player extends Character {
    private float vx, vy;
    private int health;
    private float speed;
    private boolean rotation;

    /**
     * Constructs a new Player instance.
     * @param x Initial x-coordinate
     * @param y Initial y-coordinate
     */
    public Player(int x, int y) {
        super(10, x, y);
        this.vx = 0;
        this.vy = 0;
        this.speed = 500.0f; // Default speed
        this.rotation = false;
    }

    /**
     * Updates the player's position based on velocity and time elapsed.
     * @param deltaTime Time elapsed since last update
     */
    public void update(float deltaTime) {
        float changeX = getX() + vx * deltaTime;
        float changeY = getY() + vy * deltaTime;
        if (changeX < 0 ) {
            changeX = 0;
        }
        if (changeY < 0 ) {
            changeY = 0;
        }
        if (changeY>(700-32)){
            changeY = (700-32);
        }
        if (changeX>(1200-32)){
            changeX = (1200-32);
        }
        setPositionX(changeX);
        setPositionY(changeY);
    }

    /**
     * Sets the player's velocity.
     * @param vx Velocity in x direction
     * @param vy Velocity in y direction
     */
    public void setVelocity(float vx, float vy) {
        this.vx = vx;
        this.vy = vy;
    }

    /**
     * Gets the player's x velocity.
     * @return Current x velocity
     */
    public float getVx() {
        return vx;
    }

    /**
     * Gets the player's y velocity.
     * @return Current y velocity
     */
    public float getVy() {
        return vy;
    }



    /**
     * Gets the player's movement speed.
     * @return Current speed value
     */
    public float getSpeed() {
        return speed;
    }

    /**
     * Checks if the player is rotating.
     * @return true if rotating, false otherwise
     */
    public boolean isRotation() {
        return rotation;
    }

    /**
     * Sets the player's rotation state.
     * @param rotation true to enable rotation, false to disable
     */
    public void setRotation(boolean rotation) {
        this.rotation = rotation;
    }
}