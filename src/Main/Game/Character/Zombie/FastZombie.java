package Main.Game.Character.Zombie;

/**
 * Class representing Fast zombie
 */
public class FastZombie extends Zombie {
    /**
     * Constructor for fast zombie
     * @param positionX positon on X-axis
     * @param positionY positon on Y-axis
     */
    public FastZombie( int positionX, int positionY) {
        super(2, positionX, positionY, 2, 150f);
    }
}
