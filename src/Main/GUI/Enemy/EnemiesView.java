package Main.GUI.Enemy;

import Main.Game.Character.Enemy;
import Main.Game.Character.Zombie.BigZombie;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * A Swing component that renders a list of enemies.
 */
public class EnemiesView extends JComponent {
    private final List<Enemy> enemies;

    public EnemiesView(List<Enemy> enemies) {
        this.enemies = enemies;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        enemies.stream()
                .filter(Enemy::isAlive)
                .forEach(enemy -> {
                    if (enemy instanceof BigZombie) {
                        int x = (int) enemy.getX();
                        int y = (int) enemy.getY();
                        g.setColor(enemy.getColor());
                        g.fillRect(x - 30, y - 30, 60, 60);
                        g.setColor(Color.WHITE);
                        g.drawString("Health: " + enemy.getHealth(), x - 25, y - 25);
                    } else {
                        int x = (int) enemy.getX();
                        int y = (int) enemy.getY();
                        g.setColor(enemy.getColor());
                        g.fillRect(x - 16, y - 16, 32, 32);
                        g.setColor(Color.WHITE);
                        g.drawString("Health: " + enemy.getHealth(), x - 20, y - 20);
                    }
                });

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1200, 750);
    }
}
