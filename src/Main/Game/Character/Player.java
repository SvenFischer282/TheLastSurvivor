package Main.Game.Character;

public class Player extends Character {
    private float vx, vy;
    private int health;
    private float speed;
    private boolean rotation;

    public Player(int x, int y) {
        super(10, x, y);
        this.vx = 0;
        this.vy = 0;
        this.speed = 500.0f; // Default speed
        this.rotation = false;
    }

    public void update(float deltaTime) {
        setPositionX(getPositionX() + vx * deltaTime);
        setPositionY(getPositionY() + vy * deltaTime);
    }

    public void setVelocity(float vx, float vy) {
        this.vx = vx;
        this.vy = vy;
    }

    // Add these getters
    public float getVx() {
        return vx;
    }

    public float getVy() {
        return vy;
    }

    public float getX() { return getPositionX(); }
    public float getY() { return getPositionY(); }
    public float getSpeed() { return speed; }

    public boolean isRotation() {
        return rotation;
    }

    public void setRotation(boolean rotation) {
        this.rotation = rotation;
    }
}