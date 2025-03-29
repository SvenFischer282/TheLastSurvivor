package Main.GUI.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import Main.Game.Character.Player;

public class PlayerController implements KeyListener {
    private Player player;
    private boolean up, down, left, right; // Movement flags

    public PlayerController(Player player) {
        this.player = player;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_W) up = true;
        if (key == KeyEvent.VK_S) down = true;
        if (key == KeyEvent.VK_A) left = true;
        if (key == KeyEvent.VK_D) right = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_W) up = false;
        if (key == KeyEvent.VK_S) down = false;
        if (key == KeyEvent.VK_A) left = false;
        if (key == KeyEvent.VK_D) right = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {} // Not needed for movement

    public void update(float deltaTime) {
        float speed = 100f; // Adjust speed as needed
        float vx = 0, vy = 0;

        if (up)    vy -= speed;
        if (down)  vy += speed;
        if (left)  vx -= speed;
        if (right) vx += speed;

        player.setVelocity(vx, vy);
    }
}