package Main.GUI.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import Main.Game.Character.Player;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class PlayerView extends JPanel {
    Player player;
    BufferedImage sprite;

    public PlayerView(Player player) {
        this.player = player;
        try {
            sprite = ImageIO.read(getClass().getResource("/Resources/Images/Basic Undead 4x.png"));        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(sprite != null) {
            int x = (int) player.getX();
            int y = (int) player.getY();
            g.drawImage(sprite, x, y, null);
        }
    }
    public void update(){
        repaint();
    }
}

