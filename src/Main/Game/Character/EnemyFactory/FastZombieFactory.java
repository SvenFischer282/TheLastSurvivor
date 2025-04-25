package Main.Game.Character.EnemyFactory;

import Main.Game.Character.Enemy;
import Main.Game.Character.Zombie.FastZombie;
import Main.Game.Character.Zombie.Zombie;

public class FastZombieFactory implements EnemyFactory {

    FastZombieFactory(){

    }

    public FastZombie createEnemy(int x, int y) {
        FastZombie zombie =  new FastZombie(x,y);
        return zombie;


    }
}
