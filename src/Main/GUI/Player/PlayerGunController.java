package Main.GUI.Player;

import Main.Game.Character.Player;
import Main.Game.Collectible.Potions.Potion;
import Main.Game.Inventory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.EventRecordingLogger;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Handles input controls for both player movement and gun shooting.
 * Implements KeyListener for movement and MouseListener for shooting.
 */
public class PlayerGunController implements KeyListener, MouseListener {
    private final Player player;
    private final Player.Gun gun;
    private final float playerSpeed = 200.0f;
    private final float bulletSpeed = 800.0f;
    private boolean up, down, left, right;
    private final Inventory inventory;
    private final static Logger logger = LoggerFactory.getLogger(Player.class);

    public PlayerGunController(Player player, Inventory inventory) {
        this.player = player;
        this.gun = player.getGun();
        this.inventory = inventory;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> up = true;
            case KeyEvent.VK_S -> down = true;
            case KeyEvent.VK_A -> left = true;
            case KeyEvent.VK_D -> right = true;
            case KeyEvent.VK_SPACE -> player.getSword().swing();
            case KeyEvent.VK_1 -> {
                inventory.usePotion(Potion.PotionType.HEAL);
            }
            case KeyEvent.VK_2, KeyEvent.VK_NUMPAD2, 16777534-> {
                logger.info("2 was pressed");
                inventory.usePotion(Potion.PotionType.STRENGTH);


            }
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
            vx *= 0.7071f;
            vy *= 0.7071f;
        }
        player.setVelocity(vx, vy);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {
        if (gun.canShoot()) {
            gun.shoot(e.getX(), e.getY());
        }
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    public void update(float deltaTime) {
        player.update(deltaTime);
        gun.update(deltaTime);
    }
}
