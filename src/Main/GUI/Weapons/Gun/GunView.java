package Main.GUI.Weapons.Gun;

import Main.Game.Weapons.Gun;
import Main.Game.Character.Player;
import javax.swing.*;
import java.awt.*;

/**
 * Visual representation of the gun and its bullet.
 */
public class GunView extends JPanel {
    private final Gun gun;
    private final Player player;
    private static final int BULLET_SIZE = 10;

    /**
     * Constructs a new GunView instance.
     * @param player The player associated with the gun
     * @param gun The gun to visualize
     */
    public GunView(Player player, Gun gun) {
        this.player = player;
        this.gun = gun;
        setOpaque(false);
    }

    /**
     * Paints the bullet when active.
     * @param g The graphics context to draw on
     */
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

    /**
     * Returns the preferred size of the panel.
     * @return Dimension object with width 800 and height 600
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 600);
    }
}