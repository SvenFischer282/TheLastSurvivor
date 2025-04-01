package Main.GUI.Enemy;

import Main.Game.Character.Enemy;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;

public class EnemyView extends JComponent { // JComponent namiesto JPanel pre jednoduchosť
    private final Enemy enemy;
    private Image image;

    public EnemyView(Enemy enemy)  {
        this.enemy = enemy;
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect((int) enemy.getX(), (int) enemy.getY(), 32 , 32);
    }
}