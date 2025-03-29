package Main.Game.Character;

public class Player extends Character {

            // Movement speed (pixels per second)
    private float vx, vy;
    private int health;// Velocity in x and y directions
    private float speed;

    public Player(int x, int y) {
      super(10, x, y);
        this.vx = 0;
        this.vy = 0;
    }

    // Update position based on velocity and delta time
    public void update(float deltaTime) {
        setPositionX(vx * deltaTime);
        setPositionY(vy * deltaTime);
    }

    // Setters for velocity based on input
    public void setVelocity(float vx, float vy) {
        this.vx = vx;
        this.vy = vy;
    }

    // Getters
    public float getX() { return getPositionX(); }
    public float getY() { return getPositionY(); }
    public float getSpeed() { return speed; }

}