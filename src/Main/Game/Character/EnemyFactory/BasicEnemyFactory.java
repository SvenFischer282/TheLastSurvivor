package Main.Game.Character.EnemyFactory;

import Main.Game.Character.Enemy;

public class BasicEnemyFactory implements EnemyFactory{
    private int health;
    private int damage;
    public BasicEnemyFactory(){
        this.health = 4;
        this.damage = 1;
    }
    @Override
    public Enemy createEnemy(int x, int y) {
        return new Enemy(health,x, y , damage);
    }
}
