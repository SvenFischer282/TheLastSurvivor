package Main.GUI.Enemy;

import Main.Game.Character.Enemy;

import javax.swing.*;
import java.awt.*;

public class EnemyView extends JPanel {
    private final Enemy enemy;

    public EnemyView(Enemy enemy) {
        this.enemy = enemy;
        setOpaque(false);

    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.GREEN);
        g2d.fillRect((int)enemy.getX(), (int)enemy.getY(), 100, 100);
    }

}
