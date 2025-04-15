package Main.Game.Character.EnemyFactory;

import Main.Game.Character.Enemy;

public interface EnemyFactory {
    Enemy createEnemy(int x, int y);
}
