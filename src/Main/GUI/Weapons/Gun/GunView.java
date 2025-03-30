package Main.GUI.Weapons.Gun;

import Main.Game.Weapons.Gun;
import Main.Game.Character.Player;
import javax.swing.*;
import java.awt.*;

public class GunView extends JPanel {
    private final Gun gun;
    private final Player player;
    private static final int BULLET_SIZE = 10;

    public GunView(Player player, Gun gun) {
        this.player = player;
        this.gun = gun;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        if (gun.isBulletActive()) {
            g2d.setColor(Color.RED);
            int bulletX = (int) gun.getBulletPosX() - BULLET_SIZE / 2;
            int bulletY = (int) gun.getBulletPosY() - BULLET_SIZE / 2;
            g2d.fillOval(bulletX, bulletY, BULLET_SIZE, BULLET_SIZE);
            // Debug info
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 600);
    }
}