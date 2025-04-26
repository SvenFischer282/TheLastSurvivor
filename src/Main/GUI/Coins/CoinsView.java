package Main.GUI.Coins;

import Main.Game.Collectible.Coins.Coins;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * A JComponent for rendering coins in the game.
 */
public class CoinsView extends JComponent {
    private final List<Coins> coins;

    /**
     * Constructs a CoinsView with a list of coins to render.
     * @param coins The list of coins to display.
     */
    public CoinsView(List<Coins> coins) {
        this.coins = coins;
    }

    /**
     * Renders the coins on the component.
     * @param g The Graphics object used for drawing.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Coins coin : coins) {
            int x = coin.getX();
            int y = coin.getY();

            // Set color based on coin type
            if (coin.getValue() == 5) {
                g.setColor(Color.YELLOW);
            } else if (coin.getValue() == 2) {
                g.setColor(Color.LIGHT_GRAY);
            } else {
                g.setColor(Color.WHITE);
            }

            // Draw the coin as a circle
            g.fillOval(x - 8, y - 8, 16, 16);

            // Optional label
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.PLAIN, 10));
            g.drawString(coin.getValue() + "", x - 2, y + 2);
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