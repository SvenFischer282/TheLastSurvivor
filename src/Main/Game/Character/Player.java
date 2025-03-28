package Main.Game.Character;

public class Player extends Character {
    private int strength;
    public Player(int health, int positionX, int positionY) {
        super(health, positionX, positionY);
    }
    public int getStrength() {
        return strength;
    }
    public void setStrength(int strength) {
        strength = strength;
    }
    public void move(int dx, int dy) {
        this.setPositionX(this.getPositionX() + dx);
        this.setPositionY(this.getPositionY() + dy);
    }
}
