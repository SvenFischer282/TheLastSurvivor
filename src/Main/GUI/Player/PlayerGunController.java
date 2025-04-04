package Main.GUI.Player;

import Main.Game.Character.Player;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Handles input controls for both player movement and gun shooting.
 * Implements KeyListener for movement and MouseListener for shooting.
 */
public class PlayerGunController implements KeyListener, MouseListener {
    /**
     * The player being controlled.
     */
    private final Player player;

    /**
     * The gun associated with the player.
     */
    private final Player.Gun gun;

    /**
     * The movement speed of the player in pixels per second.
     */
    private final float playerSpeed = 200.0f;

    /**
     * The speed of bullets when fired in pixels per second.
     */
    private final float bulletSpeed = 800.0f;

    /**
     * Movement flags tracking which directions are currently active.
     */
    private boolean up, down, left, right;

    /**
     * Creates a new controller for the specified player.
     * @param player The player to control
     */
    public PlayerGunController(Player player) {
        this.player = player;
        this.gun = player.getGun();
    }

    /**
     * Handles key press events for player movement.
     * @param e The key event
     */
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

    /**
     * Handles key release events for player movement.
     * @param e The key event
     */
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

    /**
     * Updates the player's velocity based on current movement flags.
     * Handles diagonal movement normalization and rotation.
     */
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

    /**
     * Handles mouse click events for shooting.
     * Calculates bullet trajectory toward click position.
     * @param e The mouse event
     */
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

    /**
     * Updates the player's state.
     * @param deltaTime Time elapsed since last update in seconds
     */
    public void update(float deltaTime) {
        player.update(deltaTime);
    }
}