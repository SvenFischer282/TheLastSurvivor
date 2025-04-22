package Main.GUI;

import javax.swing.*;

import Main.Game.Character.Player;
import Main.Game.ScoreCounter;
import Main.Utils.Observer.GameStateObserver;
import Main.Utils.Observer.GameStateSubject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;


/**
 * A Swing component that renders a HUD for the player and observes state changes.
 */
public class HUD extends JComponent implements GameStateObserver {
    private Player player;
    private ScoreCounter scoreCounter;
    private final Logger logger = LoggerFactory.getLogger(HUD.class);

    public HUD(Player player, ScoreCounter scoreCounter) {
        this.player = player;
        this.scoreCounter = scoreCounter;
        // Register HUD as an observer of Player and ScoreCounter
        if (player instanceof GameStateSubject) {
            ((GameStateSubject) player).addObserver(this);
        }
        if (scoreCounter instanceof GameStateSubject) {
            ((GameStateSubject) scoreCounter).addObserver(this);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


        int health = player.getHealth();
        g.setColor(Color.WHITE);
        g.drawString("Health: " + health, 10, 20);
        g.drawString("Score: " + scoreCounter.getScore(), 10, 40);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1200, 750);
    }

    @Override
    public void update() {
        this.repaint(); // Trigger repaint when notified of a state change
        logger.info("health: " + player.getHealth() + player);

    }

    // Cleanup method to remove HUD as an observer when no longer needed
    public void cleanup() {
        if (player instanceof GameStateSubject) {
            ((GameStateSubject) player).removeObserver(this);
        }
        if (scoreCounter instanceof GameStateSubject) {
            ((GameStateSubject) scoreCounter).removeObserver(this);
        }
    }
}