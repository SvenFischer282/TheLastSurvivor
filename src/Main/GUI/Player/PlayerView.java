package Main.GUI.Player;

import Main.Game.Character.Player;
import javax.swing.*;
import java.awt.*;

public class PlayerView extends JPanel {
    private Player player;
    private ImageIcon playerImageR = new ImageIcon("/Users/svenfischer/IdeaProjects/LastSurvivor/src/Resources/Images/PlayerR.png");
    private ImageIcon playerImageL = new ImageIcon("/Users/svenfischer/IdeaProjects/LastSurvivor/src/Resources/Images/PlayerL.png");
    private ImageIcon playerImage;
    public PlayerView(Player player) {
        this.player = player;
        this.playerImage = playerImageR ;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Draw player
        if (player.isRotation()) {
            playerImage = playerImageL;
        }else playerImage = playerImageR;
        playerImage.paintIcon(this, g, (int)player.getX(), (int)player.getY());

        // Draw player info
        g.setColor(Color.WHITE);
        g.drawString("Player Position: (" + (int)player.getX() + ", " + (int)player.getY() + ")", 10, 20);
    }
}