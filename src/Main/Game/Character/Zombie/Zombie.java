package Main.Game.Character.Zombie;

import Main.Game.Character.Enemy;

import java.awt.*;

/**
 * Class representing basic zombie
 */
public class Zombie extends Enemy {
    /**
     * Constructor for basic zombie
     * @param health health of zombie
     * @param positionX  positon on X-axis
     * @param positionY positon on Y-axis
     * @param damage  damage of zombie
     * @param speed speed of zombie
     */
    public Zombie(int health, int positionX, int positionY, int damage,float speed) {
        super(health, positionX, positionY,damage,speed);
        this.setColor(Color.RED);
    }


}
