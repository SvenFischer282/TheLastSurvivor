package Main.GUI.Player;

import Main.Game.Character.Player;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayerController implements KeyListener {
    private final Player player;
    private final float speed = 200.0f; // pixels per second
    private boolean up, down, left, right;

    public PlayerController(Player player) {
        this.player = player;
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
        if (up) vy -= speed;
        if (down) vy += speed;
        if (left){
            player.setRotation(true);
            vx -= speed;
        }
        if (right) {
            player.setRotation(false);
            vx += speed;
        }

        if (vx!=0 && vy!=0){
            //noramlise diagonal movement
            vy*=0.7071f;
            vy*=0.7071f;
        }


        player.setVelocity(vx, vy);
    }

    @Override
    public void keyTyped(KeyEvent e) {}  // Not used

    public void update(float deltaTime) {
        player.update(deltaTime);
    }
}