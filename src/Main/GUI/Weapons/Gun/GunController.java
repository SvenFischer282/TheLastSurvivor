package Main.GUI.Weapons.Gun;

import Main.Game.Character.Player;
import Main.Game.Weapons.Gun;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GunController implements MouseListener {
    private Player player;
    private float speed;
    private Gun gun;

    public GunController(Player player, Gun gun) {
        this.player = player;
        this.gun = gun;
        this.speed = 600f;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (gun.canShoot() && !gun.isBulletActive()) {
            float start_x = player.getX();
            float start_y = player.getY();
            float target_x = e.getX();
            float target_y = e.getY();

            // Calculate velocity
            float dx = target_x - start_x;
            float dy = target_y - start_y;
            float len = (float)Math.sqrt(dx*dx + dy*dy);
            if (len != 0) {
                dx = dx/len * speed;
                dy = dy/len * speed;
            }

            gun.shoot((int)target_x, (int)target_y);
            gun.setBulletVelocity(dx, dy);
        }
    }

    public void update(float deltaTime) {
        gun.update(deltaTime);
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}