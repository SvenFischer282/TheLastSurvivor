package Main.Game.Character.Zombie;

import java.awt.*;

public class BigZombie extends Zombie {

    public BigZombie( int positionX, int positionY) {
        super(10, positionX, positionY, 4, 50f);
        this.setColor(Color.ORANGE);
    }
}
