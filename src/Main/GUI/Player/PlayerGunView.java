package Main.GUI.Player;

import Main.Game.Character.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.List;

/**
 * A Swing component that renders the player character, their gun, and sword.
 * Visualizes player sprites, bullets, and sword swings.
 */
public class PlayerGunView extends JPanel {
    private final Player player;
    private final Player.Gun gun;
    private final Player.Sword sword;
    private Image playerImageR;
    private Image playerImageL;
    private static final int BULLET_SIZE = 10;
    
    // Hit effect tracking
    private int lastKnownHealth;
    private long hitEffectStartTime;
    private static final long HIT_EFFECT_DURATION_MS = 300; // 300ms flash effect
    private boolean isHitEffectActive;

    /**
     * Constructs a PlayerGunView for rendering the player and their weapons.
     * @param player The player character to visualize.
     */
    public PlayerGunView(Player player) {
        this.player = player;
        this.gun = player.getGun();
        this.sword = player.getSword();
        setOpaque(true);
        setBackground(Color.BLACK);
        this.lastKnownHealth = player.getHealth();
        this.hitEffectStartTime = 0;
        this.isHitEffectActive = false;

        try {
            playerImageR = ImageIO.read(getClass().getResource("/Images/PlayerR.png"));
            playerImageL = ImageIO.read(getClass().getResource("/Images/PlayerL.png"));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error loading player images:");
            e.printStackTrace();
            playerImageR = createPlaceholderImage(Color.RED);
            playerImageL = createPlaceholderImage(Color.BLUE);
        }
    }

    /**
     * Creates a placeholder image for when the player sprite fails to load.
     * @param color The color of the placeholder image.
     * @return A 50x50 BufferedImage filled with the specified color.
     */
    private Image createPlaceholderImage(Color color) {
        BufferedImage img = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setColor(color);
        g2d.fillRect(0, 0, 50, 50);
        g2d.dispose();
        return img;
    }

    /**
     * Checks if player was hit and activates hit effect.
     */
    private void checkForHit() {
        int currentHealth = player.getHealth();
        if (currentHealth < lastKnownHealth) {
            // Player took damage, activate hit effect
            hitEffectStartTime = System.currentTimeMillis();
            isHitEffectActive = true;
        }
        lastKnownHealth = currentHealth;
        
        // Update hit effect state
        if (isHitEffectActive) {
            long elapsed = System.currentTimeMillis() - hitEffectStartTime;
            if (elapsed >= HIT_EFFECT_DURATION_MS) {
                isHitEffectActive = false;
            }
        }
    }

    /**
     * Renders the player sprite, active bullets, and sword swings based on player state.
     * @param g The Graphics object used for drawing.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Check for hit and update hit effect
        checkForHit();

        // Render player with hit effect if active
        Image currentImage = player.isRotation() ? playerImageL : playerImageR;
        if (currentImage != null) {
            int playerX = (int) player.getX();
            int playerY = (int) player.getY();
            
            if (isHitEffectActive) {
                // Flash red tint on player sprite
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
                g2d.setColor(Color.RED);
                g2d.fillRect(playerX, playerY, 50, 50);
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                g2d.drawImage(currentImage, playerX, playerY, this);
                g2d.dispose();
            } else {
                g.drawImage(currentImage, playerX, playerY, this);
            }
        }

        // Render all bullets
        List<Player.Gun.Bullet> bullets = gun.getBullets();
        g.setColor(Color.RED);
        for (Player.Gun.Bullet bullet : bullets) {
            if (bullet.isBulletActive()) {
                g.fillOval((int) bullet.getBulletPosX() - BULLET_SIZE / 2,
                        (int) bullet.getBulletPosY() - BULLET_SIZE / 2,
                        BULLET_SIZE, BULLET_SIZE);
            }
        }

        // Render sword
        if (sword.isSwinging()) {
            g.setColor(Color.GRAY);
            int x, y, width, height;
            switch (player.getDirection()) {
                case RIGHT -> {
                    x = (int) player.getX() + 42;
                    y = (int) player.getY() + 29;
                    width = 50;
                    height = 5;
                }
                case LEFT -> {
                    x = (int) player.getX() - 43;
                    y = (int) player.getY() + 29;
                    width = 50;
                    height = 5;
                }
                case UP -> {
                    x = (int) player.getX() + 42;
                    y = (int) player.getY() - 25;
                    width = 5;
                    height = 50;
                }
                case DOWN -> {
                    x = (int) player.getX() + 42;
                    y = (int) player.getY() + 42;
                    width = 5;
                    height = 50;
                }
                default -> {
                    return;
                }
            }
            g.fillRect(x, y, width, height);
        }
        
        // Render red flash overlay when hit
        if (isHitEffectActive) {
            long elapsed = System.currentTimeMillis() - hitEffectStartTime;
            float alpha = 1.0f - ((float) elapsed / HIT_EFFECT_DURATION_MS);
            alpha = Math.max(0.0f, Math.min(1.0f, alpha)); // Clamp between 0 and 1
            
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha * 0.3f));
            g2d.setColor(Color.RED);
            g2d.fillRect(0, 0, getWidth(), getHeight());
            g2d.dispose();
        }
    }
}