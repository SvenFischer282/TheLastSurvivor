package Main.Game.Character.EnemyFactory;

import Main.Game.Character.Enemy;
import Main.Game.Character.Zombie.FastZombie;
import Main.Game.Character.Zombie.Zombie;

/**
 * Factory class for creating FastZombie enemy instances.
 */
public class FastZombieFactory implements EnemyFactory<FastZombie> {

    /**
     * Constructs a FastZombieFactory.
     */
    FastZombieFactory() {
    }

    /**
     * Creates a new FastZombie at the specified coordinates.
     * @param x The x-coordinate for the FastZombie's position.
     * @param y The y-coordinate for the FastZombie's position.
     * @return A new FastZombie instance.
     */
    @Override
    public FastZombie createEnemy(int x, int y) {
        FastZombie zombie = new FastZombie(x, y);
        return zombie;
    }
}