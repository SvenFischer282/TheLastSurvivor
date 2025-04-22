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

public class HUD extends JComponent implements GameStateObserver {
    private final Player player;
    private final ScoreCounter scoreCounter;
    private final Inventory inventory;
    private final Logger logger = LoggerFactory.getLogger(HUD.class);

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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        g.drawString("Health: " + player.getHealth(), 10, 20);
        g.drawString("Score: " + scoreCounter.getScore(), 10, 40);

        // Display potion counts
        int y = 60;
        for (Potion.PotionType type : Potion.PotionType.values()) {
            int count = inventory.getPotionCount(type);
            g.drawString(type.name() + " potions: " + count, 10, y);
            y += 20;
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1200, 750);
    }

    @Override
    public void update() {
        this.repaint();
        logger.info("HUD update");
    }

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
