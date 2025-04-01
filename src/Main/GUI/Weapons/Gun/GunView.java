package Main.GUI.Weapons.Gun;

import Main.Game.Weapons.Gun;
import Main.Game.Character.Player;
import javax.swing.*;
import java.awt.*;

public class GunView extends JComponent { // JComponent namiesto JPanel
    private final Gun gun;
    private static final int BULLET_SIZE = 10;

    public GunView(Player player, Gun gun) {
        this.gun = gun;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (gun.isBulletActive()) {
            g.setColor(Color.RED);
            g.fillOval((int) gun.getBulletPosX() - BULLET_SIZE / 2,
                    (int) gun.getBulletPosY() - BULLET_SIZE / 2,
                    BULLET_SIZE, BULLET_SIZE);
        }
    }
}