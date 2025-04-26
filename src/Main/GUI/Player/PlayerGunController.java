package Main.GUI.Player;

import Main.Game.Character.Player;
import Main.Game.Collectible.Potions.Potion;
import Main.Game.Inventory;
import Main.Utils.Exceptions.GunNotReadyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Manages player input for movement, shooting, and potion usage.
 */
public class PlayerGunController implements KeyListener, MouseListener {
    private final Player player;
    private final Player.Gun gun;
    private final float playerSpeed = 200.0f;
    private final float bulletSpeed = 800.0f;
    private boolean up, down, left, right;
    private final Inventory inventory;
    private final static Logger logger = LoggerFactory.getLogger(Player.class);

    /**
     * Constructs a PlayerGunController for handling player input.
     * @param player The player character to control.
     * @param inventory The player's inventory for potion usage.
     */
    public PlayerGunController(Player player, Inventory inventory) {
        this.player = player;
        this.gun = player.getGun();
        this.inventory = inventory;
    }

    /**
     * Handles key press events for movement, sword swinging, and potion usage.
     * @param e The KeyEvent triggered by a key press.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> up = true;
            case KeyEvent.VK_S -> down = true;
            case KeyEvent.VK_A -> left = true;
            case KeyEvent.VK_D -> right = true;
            case KeyEvent.VK_SPACE -> player.getSword().swing();
            case KeyEvent.VK_1 -> inventory.usePotion(Potion.PotionType.HEAL);
            case KeyEvent.VK_2, KeyEvent.VK_NUMPAD2, 16777534 -> {
                logger.info("2 was pressed");
                inventory.usePotion(Potion.PotionType.STRENGTH);
            }
        }
        updateVelocity();
    }

    /**
     * Handles key release events to stop movement.
     * @param e The KeyEvent triggered by a key release.
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
     * Updates the player's velocity and direction based on active movement keys.
     */
    private void updateVelocity() {
        float vx = 0, vy = 0;
        if (up) {
            player.setDirection(Player.Direction.UP);
            vy -= playerSpeed;
        }
        if (down) {
            player.setDirection(Player.Direction.DOWN);
            vy += playerSpeed;
        }
        if (left) {
            player.setDirection(Player.Direction.LEFT);
            player.setRotation(true);
            vx -= playerSpeed;
        }
        if (right) {
            player.setDirection(Player.Direction.RIGHT);
            player.setRotation(false);
            vx += playerSpeed;
        }

        if (vx != 0 && vy != 0) {
            vx *= 0.7071f; // Diagonal movement normalization
            vy *= 0.7071f;
        }
        player.setVelocity(vx, vy);
    }

    /**
     * Empty implementation for key typed events.
     * @param e The KeyEvent triggered by a key typed.
     */
    @Override
    public void keyTyped(KeyEvent e) {}

    /**
     * Handles mouse click events to shoot the gun.
     * @param e The MouseEvent triggered by a mouse click.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (gun.canShoot()) {
            try {
                gun.shoot(e.getX(), e.getY());
            } catch (GunNotReadyException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * Empty implementation for mouse pressed events.
     * @param e The MouseEvent triggered by a mouse press.
     */
    @Override
    public void mousePressed(MouseEvent e) {}

    /**
     * Empty implementation for mouse released events.
     * @param e The MouseEvent triggered by a mouse release.
     */
    @Override
    public void mouseReleased(MouseEvent e) {}

    /**
     * Empty implementation for mouse entered events.
     * @param e The MouseEvent triggered by mouse entering the component.
     */
    @Override
    public void mouseEntered(MouseEvent e) {}

    /**
     * Empty implementation for mouse exited events.
     * @param e The MouseEvent triggered by mouse exiting the component.
     */
    @Override
    public void mouseExited(MouseEvent e) {}

    /**
     * Updates the player and gun states.
     * @param deltaTime Time elapsed since the last update.
     */
    public void update(float deltaTime) {
        player.update(deltaTime);
        gun.update(deltaTime);
    }
}