package Main.Game.Collectible;

import Main.Game.Character.Player;
import Main.Game.ScoreCounter;

public class Coins extends Item implements Collectible {
    private int value;
    private int x;
    private int y;

    public Coins(int x, int y, String name) {
        super(x, y, name);
    }

@Override
    public void collect( Player player ) {
        System.out.println("Coins collected");
    }



    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
