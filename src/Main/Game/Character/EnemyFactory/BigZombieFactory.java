package Main.Game.Character.EnemyFactory;

import Main.Game.Character.Enemy;
import Main.Game.Character.Zombie.BigZombie;

public class BigZombieFactory implements EnemyFactory{

    public BigZombieFactory() {

    }

    public  BigZombie createEnemy(int x, int y) {
        BigZombie bigZombie = new BigZombie(x, y);
        return bigZombie;
    }
}
