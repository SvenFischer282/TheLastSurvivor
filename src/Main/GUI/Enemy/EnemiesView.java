
package Main.GUI.Enemy;

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

import Main.Game.Character.Enemy;
import Main.Game.Character.Player;
import Main.Game.Character.Zombie.BigZombie;
import Main.Game.Character.Zombie.FastZombie;
import Main.Game.Character.Zombie.Zombie;

/**
 * A Swing component for rendering a list of enemies in the game.
 */
public class EnemiesView extends JComponent {
    private final List<Enemy> enemies;
    private final Player player;
    
    // Sprite images for different zombie types and directions
    private Image basicZombieL;
    private Image basicZombieR;
    private Image fastZombieL;
    private Image fastZombieR;
    private Image bigZombieL;
    private Image bigZombieR;

    /**
     * Constructs an EnemiesView with a list of enemies to render.
     *
     * @param enemies The list of enemies to display.
     * @param player The player reference to determine zombie facing direction.
     */
    public EnemiesView(List<Enemy> enemies, Player player) {
        this.enemies = enemies;
        this.player = player;
        setOpaque(false);
        loadZombieSprites();
    }

    /**
     * Loads all zombie sprite images from resources.
     */
    private void loadZombieSprites() {
        try {
            basicZombieL = ImageIO.read(getClass().getResource("/Images/Basic_zombie_L.png"));
            basicZombieR = ImageIO.read(getClass().getResource("/Images/Basic_zombie_R.png"));
            fastZombieL = ImageIO.read(getClass().getResource("/Images/Fast_zombie_L.png"));
            fastZombieR = ImageIO.read(getClass().getResource("/Images/Fast_zombie_R.png"));
            bigZombieL = ImageIO.read(getClass().getResource("/Images/Zombie_tank_L.png"));
            bigZombieR = ImageIO.read(getClass().getResource("/Images/Tank_zombie_r.png"));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error loading zombie images:");
            e.printStackTrace();
            // Create placeholder images if loading fails
            basicZombieL = createPlaceholderImage(Color.RED);
            basicZombieR = createPlaceholderImage(Color.RED);
            fastZombieL = createPlaceholderImage(Color.BLUE);
            fastZombieR = createPlaceholderImage(Color.BLUE);
            bigZombieL = createPlaceholderImage(Color.ORANGE, 100, 100);
            bigZombieR = createPlaceholderImage(Color.ORANGE, 100, 100);
        }
    }

    /**
     * Creates a placeholder image for when sprites fail to load.
     * @param color The color of the placeholder image.
     * @return A 64x64 BufferedImage filled with the specified color.
     */
    private Image createPlaceholderImage(Color color) {
        return createPlaceholderImage(color, 64, 64);
    }

    /**
     * Creates a placeholder image for when sprites fail to load.
     * @param color The color of the placeholder image.
     * @param width The width of the placeholder image.
     * @param height The height of the placeholder image.
     * @return A BufferedImage filled with the specified color.
     */
    private Image createPlaceholderImage(Color color, int width, int height) {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setColor(color);
        g2d.fillRect(0, 0, width, height);
        g2d.dispose();
        return img;
    }

    /**
     * Gets the appropriate sprite image for an enemy based on its type and facing direction.
     * @param enemy The enemy to get the sprite for.
     * @return The Image sprite for the enemy.
     */
    private Image getEnemySprite(Enemy enemy) {
        // Determine facing direction: true if facing left (enemy is to the right of player)
        boolean facingLeft = enemy.getX() > player.getX();
        
        if (enemy instanceof BigZombie) {
            return facingLeft ? bigZombieL : bigZombieR;
        } else if (enemy instanceof FastZombie) {
            return facingLeft ? fastZombieL : fastZombieR;
        } else if (enemy instanceof Zombie) {
            return facingLeft ? basicZombieL : basicZombieR;
        } else {
            // Fallback for unknown enemy types
            return facingLeft ? basicZombieL : basicZombieR;
        }
    }

    /**
     * Renders each alive enemy as a sprite with a health label.
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
                    Image sprite = getEnemySprite(enemy);
                    int x = (int) enemy.getX();
                    int y = (int) enemy.getY();
                    
                    if (enemy instanceof BigZombie) {
                        // Big zombies are larger
                        if (sprite != null) {
                            g.drawImage(sprite, x - 50, y - 50, 100, 100, this);
                        }
                        g.setColor(Color.WHITE);
                        g.drawString("Health: " + enemy.getHealth(), x - 50, y - 50);
                    } else {
                        // Regular zombies
                        if (sprite != null) {
                            g.drawImage(sprite, x - 32, y - 32, 64, 64, this);
                        }
                        g.setColor(Color.WHITE);
                        g.drawString("Health: " + enemy.getHealth(), x - 32, y - 32);
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
