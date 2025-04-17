package Main.GUI.Player;

import Main.Game.Character.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * A Swing component that renders both the player character and their gun.
 * Handles visualization of player sprites, bullets, and health information.
 */
public class PlayerGunView extends JPanel {
    /**
     * The player model being visualized.
     */
    private final Player player;

    /**
     * The gun model associated with the player.
     */
    private final Player.Gun gun;
    /**
     * The sword model associated with the player
     */
private final Player.Sword sword;
    /**
     * Image of the player facing right.
     */
    private Image playerImageR;

    /**
     * Image of the player facing left.
     */
    private Image playerImageL;

    /**
     * The diameter of bullet visualization in pixels.
     */
    private static final int BULLET_SIZE = 10;

    /**
     * Creates a new PlayerGunView for the specified player.
     *
     * @param player The player to visualize
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
     * Creates a placeholder image when player sprites cannot be loaded.
     *
     * @param color The color for the placeholder
     * @return A generated placeholder image
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
     * Renders the player, gun, and debug information.
     * @param g The Graphics context to render to
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Render Player
        Image currentImage = player.isRotation() ? playerImageL : playerImageR;
        if (currentImage != null) {
            g.drawImage(currentImage, (int) player.getX(), (int) player.getY(), this);
        }

        // Render Gun (bullet)
        if (gun.isBulletActive()) {
            g.setColor(Color.RED);
            g.fillOval((int) gun.getBulletPosX() - BULLET_SIZE / 2,
                    (int) gun.getBulletPosY() - BULLET_SIZE / 2,
                    BULLET_SIZE, BULLET_SIZE);
        }
        if (sword.isSwinging()) {
            g.setColor(Color.GRAY); // Farba meča
            int swordLength = 50;
            int swordWidth = 5;
            int offset = 10; // Vzdialenosť od stredu postavy
            int centerX = (int) player.getX() + 32; // Stred postavy X
            int centerY = (int) player.getY()+32 ; // Stred postavy Y
            int x, y, width, height;

            switch (player.getDirection()) {
                case RIGHT:
                    x = centerX + offset;
                    y = centerY - swordWidth / 2;
                    width = swordLength;
                    height = swordWidth;
                    break;
                case LEFT:
                    x = centerX - offset - 70;
                    y = centerY - swordWidth / 2;
                    width = swordLength;
                    height = swordWidth;
                    break;
                case UP:
                    x = centerX + offset; // Pravá strana
                    y = centerY - offset - swordLength;
                    width = swordWidth;
                    height = swordLength;
                    break;
                case DOWN:
                    x = centerX + offset; // Pravá strana
                    y = centerY + offset;
                    width = swordWidth;
                    height = swordLength;
                    break;
                default:
                    return; // Bezpečnostná kontrola
            }

            g.fillRect(x, y, width, height);
        }
        // Debug info
        g.setColor(Color.WHITE);
        g.drawString(String.format("Health:%d", player.getHealth()), 10, 20);
    }
}