
package Main.GUI.Enemy;

import Main.Game.Character.Enemy;
import Main.Game.Character.Zombie.BigZombie;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * A Swing component for rendering a list of enemies in the game.
 */
public class EnemiesView extends JComponent {
    private final List<Enemy> enemies;

    /**
     * Constructs an EnemiesView with a list of enemies to render.
     *
     * @param enemies The list of enemies to display.
     */
    public EnemiesView(List<Enemy> enemies) {
        this.enemies = enemies;
        setOpaque(false);
    }

    /**
     * Renders each alive enemy as a colored rectangle with a health label.
     * BigZombies are drawn larger than other enemies.
     *
     * @param g The Graphics object used for drawing.
     */
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
                        g.drawString("Health: " + enemy.getHealth(), x - 30, y - 30);
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

    /**
     * Gets the preferred size of the component.
     *
     * @return A Dimension object with width 1200 and height 750.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1200, 750);
    }
}
