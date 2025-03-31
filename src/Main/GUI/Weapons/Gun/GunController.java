package Main.GUI.Weapons.Gun;

import Main.Game.Character.Player;
import Main.Game.Weapons.Gun;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Controls the gun's behavior based on mouse input.
 */
public class GunController implements MouseListener {
    private Player player;
    private float speed;
    private Gun gun;

    /**
     * Constructs a new GunController instance.
     * @param player The player wielding the gun
     * @param gun The gun to control
     */
    public GunController(Player player, Gun gun) {
        this.player = player;
        this.gun = gun;
        this.speed = 600f;
    }

    /**
     * Handles mouse click events to shoot the gun.
     * @param e The mouse event containing click coordinates
     */
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

    /**
     * Updates the gun's state based on time elapsed.
     * @param deltaTime Time elapsed since last update
     */
    public void update(float deltaTime) {
        gun.update(deltaTime);
    }

    /**
     * Handles mouse pressed events (not implemented).
     * @param e The mouse event
     */
    @Override public void mousePressed(MouseEvent e) {}

    /**
     * Handles mouse released events (not implemented).
     * @param e The mouse event
     */
    @Override public void mouseReleased(MouseEvent e) {}

    /**
     * Handles mouse entered events (not implemented).
     * @param e The mouse event
     */
    @Override public void mouseEntered(MouseEvent e) {}

    /**
     * Handles mouse exited events (not implemented).
     * @param e The mouse event
     */
    @Override public void mouseExited(MouseEvent e) {}
}