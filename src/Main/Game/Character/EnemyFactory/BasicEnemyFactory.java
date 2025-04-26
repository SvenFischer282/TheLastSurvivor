package Main.Game.Character.EnemyFactory;

import Main.Game.Character.Enemy;
import Main.Game.Character.Zombie.Zombie;

/**
 * Factory class for creating basic enemy instances.
 */
public class BasicEnemyFactory implements EnemyFactory {
    private int health;
    private int damage;
    private float speed;

    /**
     * Constructs a BasicEnemyFactory with default enemy attributes.
     */
    public BasicEnemyFactory() {
        this.health = 4;
        this.damage = 1;
        this.speed = 100;
    }

    /**
     * Creates a new basic enemy at the specified coordinates.
     * @param x The x-coordinate for the enemy's position.
     * @param y The y-coordinate for the enemy's position.
     * @return A new Enemy instance.
     */
    @Override
    public Enemy createEnemy(int x, int y) {
        return new Enemy(health, x, y, damage, speed);
    }
}