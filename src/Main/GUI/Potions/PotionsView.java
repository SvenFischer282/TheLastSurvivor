package Main.GUI.Potions;

import Main.Game.Collectible.Potions.Potion;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * A Swing component that renders a list of potions.
 */
public class PotionsView extends JComponent {
    private final List<Potion> potions;

    public PotionsView(List<Potion> potions) {
        this.potions = potions;
    }

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

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1200, 750);
    }
}
