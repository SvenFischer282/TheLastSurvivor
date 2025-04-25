package Main.Game.Character.Zombie;

import Main.Game.Character.Enemy;

import java.awt.*;

public class Zombie extends Enemy {
    public Zombie(int health, int positionX, int positionY, int damage,float speed) {
        super(health, positionX, positionY,damage,speed);
        this.setColor(Color.RED);
    }


}
