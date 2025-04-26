package Main.Game.Character.EnemyFactory;

import Main.Game.Character.Enemy;
import Main.Game.Character.Zombie.BigZombie;

/**
 * Factory class for creating BigZombie enemy instances.
 */
public class BigZombieFactory implements EnemyFactory {

    /**
     * Constructs a BigZombieFactory.
     */
    public BigZombieFactory() {
    }

    /**
     * Creates a new BigZombie at the specified coordinates.
     * @param x The x-coordinate for the BigZombie's position.
     * @param y The y-coordinate for the BigZombie's position.
     * @return A new BigZombie instance.
     */
    @Override
    public BigZombie createEnemy(int x, int y) {
        BigZombie bigZombie = new BigZombie(x, y);
        return bigZombie;
    }
}