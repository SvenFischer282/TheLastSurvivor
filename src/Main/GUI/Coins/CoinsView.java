package Main.GUI.Coins;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import Main.Game.Collectible.Coins.Coins;

/**
 * A JComponent for rendering coins in the game.
 */
public class CoinsView extends JComponent {
    private final List<Coins> coins;
    private Image goldCoinImage;
    private Image silverCoinImage;

    /**
     * Constructs a CoinsView with a list of coins to render.
     * @param coins The list of coins to display.
     */
    public CoinsView(List<Coins> coins) {
        this.coins = coins;
        setOpaque(false);
        loadCoinSprites();
    }

    /**
     * Loads coin sprite images from resources.
     */
    private void loadCoinSprites() {
        try {
            goldCoinImage = ImageIO.read(getClass().getResource("/Images/gold_coin.png"));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error loading gold coin image:");
            e.printStackTrace();
            goldCoinImage = createPlaceholderImage(Color.YELLOW);
        }

        try {
            
            silverCoinImage = ImageIO.read(getClass().getResource("/Images/silver_coin.png"));
        } catch (IOException | IllegalArgumentException e) {
            // If WEBP fails, try PNG
            
                System.err.println("Error loading silver coin image:");
                e.printStackTrace();
                silverCoinImage = createPlaceholderImage(Color.LIGHT_GRAY);
            
        }
    }

    /**
     * Creates a placeholder image for when sprites fail to load.
     * @param color The color of the placeholder image.
     * @return A 32x32 BufferedImage filled with the specified color.
     */
    private Image createPlaceholderImage(Color color) {
        BufferedImage img = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setColor(color);
        g2d.fillOval(0, 0, 32, 32);
        g2d.dispose();
        return img;
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
            Image coinImage = null;

            // Select appropriate sprite based on coin value
            if (coin.getValue() == 5) {
                coinImage = goldCoinImage;
            } else if (coin.getValue() == 2) {
                coinImage = silverCoinImage;
            }

            // Draw the coin sprite
            if (coinImage != null) {
                g.drawImage(coinImage, x - 16, y - 16, 32, 32, this);
            } else {
                // Fallback to colored circle if image not found
                g.setColor(Color.WHITE);
                g.fillOval(x - 8, y - 8, 16, 16);
            }
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