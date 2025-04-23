package Main.Game.Character.EnemyFactory;

import Main.Game.Character.Enemy;

public interface EnemyFactory<T extends Enemy> {
    T createEnemy(int x, int y);
}