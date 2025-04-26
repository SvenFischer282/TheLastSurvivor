package Main.Game.Character.Zombie;

import java.awt.*;

/**
 * Class representing Big zombie
 */
public class BigZombie extends Zombie {

    /**
     * Big zombie constructor
     * @param positionX Big zombie X-axis position
     * @param positionY Big zombie Y-axis position
     */
    public BigZombie( int positionX, int positionY) {
        super(10, positionX, positionY, 4, 50f);
        this.setColor(Color.ORANGE);
    }
}
