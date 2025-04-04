package Main.GUI.Enemy;

import Main.Game.Character.Enemy;
import javax.swing.*;
import java.awt.*;

public class EnemyView extends JComponent {
    private final Enemy enemy;

    public EnemyView(Enemy enemy) {
        this.enemy = enemy;
        setOpaque(false); // Priehľadné pozadie
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.GREEN);
        int x = (int) enemy.getX();
        int y = (int) enemy.getY();
        g.fillRect(x, y, 32, 32);
        g.setColor(Color.WHITE);
        g.drawString(String.format("Health:%d", enemy.getHealth()), 10, 40);


    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1200, 750); // Rovnaká veľkosť ako kontajner
    }
}