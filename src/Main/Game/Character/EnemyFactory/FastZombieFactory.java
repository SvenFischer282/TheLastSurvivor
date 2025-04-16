package Main.Game.Character.EnemyFactory;

import Main.Game.Character.Enemy;
import Main.Game.Character.Zombie;

public class FastZombieFactory implements EnemyFactory {
    float speed;
    static int damage;
    static int health;
    FastZombieFactory(){
        this.speed = 150f;
        this.damage = 2;
        this.health = 2;
    }

    public Enemy createEnemy(int x, int y) {
        Zombie zombie =  new Zombie(health,x,y,damage,speed);
        return zombie;


    }
}
