package Main.GUI.Enemy;

import Main.Game.Character.Enemy;
import javax.swing.*;
import java.awt.*;

/**
 * A Swing component that visually represents an enemy character in the game.
 * Handles the rendering of enemy graphics and health display.
 */
public class EnemyView extends JComponent {
    /**
     * The enemy model being visualized.
     */
    private final Enemy enemy;

    /**
     * Creates a new EnemyView for the specified enemy.
     * @param enemy The enemy to visualize
     */
    public EnemyView(Enemy enemy) {
        this.enemy = enemy;
        setOpaque(false); // Transparent background
    }

    /**
     * Renders the enemy representation including:
     * - A green rectangle for the enemy body
     * - Health display text
     * @param g The Graphics context to render to
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.GREEN);
        int x = (int) enemy.getX();
        int y = (int) enemy.getY();
        g.fillRect(x-16, y-16, 32, 32);
        g.setColor(Color.RED);
        g.drawOval(x-32, y-32, 64, 64); //debug hitbox
        g.setColor(Color.WHITE);
        g.drawString(String.format("Health:%d", enemy.getHealth()), 10, 40);
    }

    /**
     * Specifies the preferred size for this component.
     * @return A Dimension representing 1200x750 pixels
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1200, 750); // Same size as container
    }
}