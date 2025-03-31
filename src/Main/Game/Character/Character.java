package Main.Game.Character;

public class Character {
    private int health;
    private float positionX;
    private float positionY;

    public Character(int health, int positionX, int positionY) {
        this.health = health;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public float getX() {
        return positionX;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public float getY() {
        return positionY;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }
}

