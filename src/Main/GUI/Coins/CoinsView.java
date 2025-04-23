package Main.GUI.Coins;

import Main.Game.Collectible.Coins.Coins;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CoinsView extends JComponent {
    private final List<Coins> coins;

    public CoinsView(List<Coins> coins) {
        this.coins = coins;
    }

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
            g.drawString(coin.getValue()+"", x-2 , y+2 );
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1200, 750);
    }
}
