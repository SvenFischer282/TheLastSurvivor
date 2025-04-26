package Main.GUI.Potions;

import Main.Game.Collectible.Potions.Potion;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * A Swing component for rendering a list of potions in the game.
 */
public class PotionsView extends JComponent {
    private final List<Potion> potions;

    /**
     * Constructs a PotionsView with a list of potions to render.
     * @param potions The list of potions to display.
     */
    public PotionsView(List<Potion> potions) {
        this.potions = potions;
    }

    /**
     * Renders each potion as a colored circle with a type label.
     * @param g The Graphics object used for drawing.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Potion potion : potions) {
            int x = (int) potion.getX();
            int y = (int) potion.getY();

            // Choose color based on potion type
            switch (potion.getType()) {
                case HEAL:
                    g.setColor(Color.RED);
                    break;
                case STRENGTH:
                    g.setColor(Color.BLUE);
                    break;
                case PLAIN:
                default:
                    g.setColor(Color.GRAY);
                    break;
            }

            // Draw the potion as a small circle
            g.fillOval(x - 8, y - 8, 16, 16);

            // Optional: Draw text label
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 10));
            g.drawString(potion.getType().toString(), x - 15, y - 12);
        }
    }

    /**
     * Gets the preferred size of the component.
     * @return A Dimension object with width 1200 and height 750.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1200, 750);
    }
}