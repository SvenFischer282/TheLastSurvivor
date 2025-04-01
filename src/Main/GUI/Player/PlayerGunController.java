package Main.GUI.Player;

import Main.Game.Character.Player;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Controls both the Player and its Gun.
 */
public class PlayerGunController implements KeyListener, MouseListener {
    private final Player player;
    private final Player.Gun gun;
    private final float playerSpeed = 200.0f;
    private final float bulletSpeed = 800.0f;
    private boolean up, down, left, right;

    public PlayerGunController(Player player) {
        this.player = player;
        this.gun = player.getGun();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> up = true;
            case KeyEvent.VK_S -> down = true;
            case KeyEvent.VK_A -> left = true;
            case KeyEvent.VK_D -> right = true;
        }
        updateVelocity();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> up = false;
            case KeyEvent.VK_S -> down = false;
            case KeyEvent.VK_A -> left = false;
            case KeyEvent.VK_D -> right = false;
        }
        updateVelocity();
    }

    private void updateVelocity() {
        float vx = 0, vy = 0;
        if (up) vy -= playerSpeed;
        if (down) vy += playerSpeed;
        if (left) {
            player.setRotation(true);
            vx -= playerSpeed;
        }
        if (right) {
            player.setRotation(false);
            vx += playerSpeed;
        }

        if (vx != 0 && vy != 0) {
            vy *= 0.7071f;
            vx *= 0.7071f;
        }
        player.setVelocity(vx, vy);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {
        if (gun.canShoot() && !gun.isBulletActive()) {
            float start_x = player.getX();
            float start_y = player.getY();
            float target_x = e.getX();
            float target_y = e.getY();

            float dx = target_x - start_x;
            float dy = target_y - start_y;
            float len = (float) Math.sqrt(dx * dx + dy * dy);
            if (len != 0) {
                dx = dx / len * bulletSpeed;
                dy = dy / len * bulletSpeed;
            }

            gun.shoot((int) target_x, (int) target_y);
            gun.setBulletVelocity(dx, dy);
        }
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    public void update(float deltaTime) {
        player.update(deltaTime);
    }
}