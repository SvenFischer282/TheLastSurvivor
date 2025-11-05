package Main.GUI.Potions;

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

import Main.Game.Collectible.Potions.Potion;

/**
 * A Swing component for rendering a list of potions in the game.
 */
public class PotionsView extends JComponent {
    private final List<Potion> potions;
    private Image healPotionImage;
    private Image strengthPotionImage;

    /**
     * Constructs a PotionsView with a list of potions to render.
     * @param potions The list of potions to display.
     */
    public PotionsView(List<Potion> potions) {
        this.potions = potions;
        setOpaque(false);
        loadPotionSprites();
    }

    /**
     * Loads potion sprite images from resources.
     */
    private void loadPotionSprites() {
        try {
            healPotionImage = ImageIO.read(getClass().getResource("/Images/Potion_of_Healing.png"));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error loading healing potion image:");
            e.printStackTrace();
            healPotionImage = createPlaceholderImage(Color.RED);
        }

        try {
            strengthPotionImage = ImageIO.read(getClass().getResource("/Images/Potion_of_Strength.png"));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error loading strength potion image:");
            e.printStackTrace();
            strengthPotionImage = createPlaceholderImage(Color.BLUE);
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
     * Renders each potion as a sprite image.
     * @param g The Graphics object used for drawing.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Potion potion : potions) {
            int x = (int) potion.getX();
            int y = (int) potion.getY();
            Image potionImage = null;

            // Select appropriate sprite based on potion type
            switch (potion.getType()) {
                case HEAL:
                    potionImage = healPotionImage;
                    break;
                case STRENGTH:
                    potionImage = strengthPotionImage;
                    break;
                case PLAIN:
                default:
                    // Fallback to colored circle for plain potions
                    g.setColor(Color.GRAY);
                    g.fillOval(x - 8, y - 8, 16, 16);
                    break;
            }

            // Draw the potion sprite
            if (potionImage != null) {
                g.drawImage(potionImage, x - 16, y - 16, 32, 32, this);
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