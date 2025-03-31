package Main.GUI.Player;

import Main.Game.Character.Player;
import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class PlayerView extends JPanel {
    private final Player player;
    private Image playerImageR;
    private Image playerImageL;

    public PlayerView(Player player) {
        this.player = player;
//        setPreferredSize(new Dimension(800, 600));
        setOpaque(true);
        setBackground(Color.BLACK);

        // Load both player images
        try {
            playerImageR = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Images/PlayerR.png")));
            playerImageL = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Images/PlayerL.png")));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error loading player images:");
            e.printStackTrace();
            // Create placeholders if images fail to load
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

        // Draw appropriate player image
        Image currentImage = player.isRotation() ? playerImageL : playerImageR;
        if (currentImage != null) {
            g.drawImage(currentImage, (int)player.getX(), (int)player.getY(), this);
        }

        // Draw debug info
        g.setColor(Color.WHITE);
        g.drawString(String.format("Player:%.0f  %.0f",player.getX(),  player.getY()), 10, 20);
        g.drawString(String.format("Health:%d", player.getHealth()), 10, 40);
    }
}