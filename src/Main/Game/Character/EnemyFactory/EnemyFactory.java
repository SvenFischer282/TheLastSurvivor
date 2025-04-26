package Main.Game.Character.EnemyFactory;

import Main.Game.Character.Enemy;

/**
 * Interface for enemy factories to create specific types of enemies.
 * @param <T> The type of Enemy to be created, extending Enemy.
 */
public interface EnemyFactory<T extends Enemy> {

    /**
     * Creates an enemy of type T at the specified coordinates.
     * @param x The x-coordinate for the enemy's position.
     * @param y The y-coordinate for the enemy's position.
     * @return A new enemy instance of type T.
     */
    T createEnemy(int x, int y);
}