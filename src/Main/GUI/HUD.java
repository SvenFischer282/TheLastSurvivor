package Main.GUI;

import javax.swing.*;

import Main.Game.Character.Player;
import Main.Game.Inventory;
import Main.Game.ScoreCounter;
import Main.Game.Collectible.Potions.Potion;
import Main.Utils.Observer.GameStateObserver;
import Main.Utils.Observer.GameStateSubject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

/**
 * A Swing component that displays the player's heads-up display (HUD) with health, score, and potion counts.
 */
public class HUD extends JComponent implements GameStateObserver {
    private final Player player;
    private final ScoreCounter scoreCounter;
    private final Inventory inventory;
    private final Logger logger = LoggerFactory.getLogger(HUD.class);

    /**
     * Constructs a HUD component to display player stats and inventory.
     * @param player The player whose stats are displayed.
     * @param scoreCounter The score counter for displaying the player's score.
     * @param inventory The inventory for displaying potion counts.
     */
    public HUD(Player player, ScoreCounter scoreCounter, Inventory inventory) {
        this.player = player;
        this.scoreCounter = scoreCounter;
        this.inventory = inventory;

        // Register HUD as an observer
        if (player instanceof GameStateSubject) {
            ((GameStateSubject) player).addObserver(this);
        }
        if (scoreCounter instanceof GameStateSubject) {
            ((GameStateSubject) scoreCounter).addObserver(this);
        }
        if (inventory instanceof GameStateSubject) {
            inventory.addObserver(this);
        }
    }

    /**
     * Renders the HUD with player health, score, and potion counts.
     * @param g The Graphics object used for drawing.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        g.drawString("Health: " + player.getHealth(), 10, 20);
        g.drawString("Score: " + scoreCounter.getScore(), 10, 40);

        // Display potion counts
        int count = inventory.getPotionCount(Potion.PotionType.HEAL);
        g.drawString(Potion.PotionType.HEAL.name() + " potions: " + count, 10, 60);
        count = inventory.getPotionCount(Potion.PotionType.STRENGTH);
        g.drawString(Potion.PotionType.STRENGTH.name() + " potions: " + count, 10, 80);
    }

    /**
     * Gets the preferred size of the HUD component.
     * @return A Dimension object with width 1200 and height 750.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1200, 750);
    }

    /**
     * Updates the HUD display when notified of a game state change.
     */
    @Override
    public void update() {
        this.repaint();
        logger.info("HUD update");
    }

    /**
     * Removes this HUD as an observer from the player, score counter, and inventory.
     */
    public void cleanup() {
        if (player instanceof GameStateSubject) {
            ((GameStateSubject) player).removeObserver(this);
        }
        if (scoreCounter instanceof GameStateSubject) {
            ((GameStateSubject) scoreCounter).removeObserver(this);
        }
        if (inventory instanceof GameStateSubject) {
            inventory.removeObserver(this);
        }
    }
}