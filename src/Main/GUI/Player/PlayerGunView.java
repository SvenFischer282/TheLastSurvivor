package Main.GUI.Player;

import Main.Game.Character.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.List;

/**
 * A Swing component that renders the player character, their gun, and sword.
 * Visualizes player sprites, bullets, and sword swings.
 */
public class PlayerGunView extends JPanel {
    private final Player player;
    private final Player.Gun gun;
    private final Player.Sword sword;
    private Image playerImageR;
    private Image playerImageL;
    private static final int BULLET_SIZE = 10;

    /**
     * Constructs a PlayerGunView for rendering the player and their weapons.
     * @param player The player character to visualize.
     */
    public PlayerGunView(Player player) {
        this.player = player;
        this.gun = player.getGun();
        this.sword = player.getSword();
        setOpaque(true);
        setBackground(Color.BLACK);

        try {
            playerImageR = ImageIO.read(getClass().getResource("/Images/PlayerR.png"));
            playerImageL = ImageIO.read(getClass().getResource("/Images/PlayerL.png"));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error loading player images:");
            e.printStackTrace();
            playerImageR = createPlaceholderImage(Color.RED);
            playerImageL = createPlaceholderImage(Color.BLUE);
        }
    }

    /**
     * Creates a placeholder image for when the player sprite fails to load.
     * @param color The color of the placeholder image.
     * @return A 50x50 BufferedImage filled with the specified color.
     */
    private Image createPlaceholderImage(Color color) {
        BufferedImage img = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setColor(color);
        g2d.fillRect(0, 0, 50, 50);
        g2d.dispose();
        return img;
    }

    /**
     * Renders the player sprite, active bullets, and sword swings based on player state.
     * @param g The Graphics object used for drawing.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Render player
        Image currentImage = player.isRotation() ? playerImageL : playerImageR;
        if (currentImage != null) {
            g.drawImage(currentImage, (int) player.getX(), (int) player.getY(), this);
        }

        // Render all bullets
        List<Player.Gun.Bullet> bullets = gun.getBullets();
        g.setColor(Color.RED);
        for (Player.Gun.Bullet bullet : bullets) {
            if (bullet.isBulletActive()) {
                g.fillOval((int) bullet.getBulletPosX() - BULLET_SIZE / 2,
                        (int) bullet.getBulletPosY() - BULLET_SIZE / 2,
                        BULLET_SIZE, BULLET_SIZE);
            }
        }

        // Render sword
        if (sword.isSwinging()) {
            g.setColor(Color.GRAY);
            int x, y, width, height;
            switch (player.getDirection()) {
                case RIGHT -> {
                    x = (int) player.getX() + 42;
                    y = (int) player.getY() + 29;
                    width = 50;
                    height = 5;
                }
                case LEFT -> {
                    x = (int) player.getX() - 43;
                    y = (int) player.getY() + 29;
                    width = 50;
                    height = 5;
                }
                case UP -> {
                    x = (int) player.getX() + 42;
                    y = (int) player.getY() - 25;
                    width = 5;
                    height = 50;
                }
                case DOWN -> {
                    x = (int) player.getX() + 42;
                    y = (int) player.getY() + 42;
                    width = 5;
                    height = 50;
                }
                default -> {
                    return;
                }
            }
            g.fillRect(x, y, width, height);
        }
    }
}