package Main.Game.Character.EnemyFactory;

import Main.Game.Character.Enemy;
import Main.Game.Character.Zombie;

public class FastZombieFactory implements EnemyFactory {
    float speed;
    int damage;
    int health;
    FastZombieFactory(){
        this.speed = 800f;
        this.damage = 2;
        this.health = 4;
    }

    @Override
    public Enemy createEnemy(int x, int y) {
        return new Zombie(health,x,y,damage);
    }
}
