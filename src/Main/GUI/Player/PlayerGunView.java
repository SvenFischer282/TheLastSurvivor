package Main.GUI.Player;

import Main.Game.Character.Player;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Renders both the Player and its Gun.
 */
public class PlayerGunView extends JPanel {
    private final Player player;
    private final Player.Gun gun;
    private Image playerImageR;
    private Image playerImageL;
    private static final int BULLET_SIZE = 10;

    public PlayerGunView(Player player) {
        this.player = player;
        this.gun = player.getGun();
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

    private Image createPlaceholderImage(Color color) {
        BufferedImage img = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setColor(color);
        g2d.fillRect(0, 0, 50, 50);
        g2d.dispose();
        return img;
    }

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

        // Debug info
        g.setColor(Color.WHITE);
        g.drawString(String.format("Health:%d", player.getHealth()), 10, 20);
    }
}