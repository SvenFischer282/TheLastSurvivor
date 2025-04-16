package Main.GUI.Enemy;

import Main.Game.Character.Enemy;

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
        for (Enemy enemy : enemies) {
            if (enemy.getHealth() <= 0) continue;

            int x = (int) enemy.getX();
            int y = (int) enemy.getY();
            g.setColor(Color.GREEN);
            g.fillRect(x - 16, y - 16, 32, 32);
            g.setColor(Color.RED);
            g.drawOval(x - 32, y - 32, 64, 64);
            g.setColor(Color.WHITE);
            g.drawString("Health: " + enemy.getHealth(), x - 20, y - 20);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1200, 750);
    }
}
