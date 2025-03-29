package Main.GUI;

import Main.Game.Character.Player;
import Main.GUI.Player.PlayerController;
import Main.GUI.Player.PlayerView;

import javax.swing.JFrame;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainApp {
    private static final int FPS = 60; // Target frames per second
    private static final float DELTA_TIME = 1.0f / FPS; // Time per frame in seconds

    public static void main(String[] args) {
        // Initialize components
        Player player = new Player(100, 100); // x, y,
        PlayerView view = new PlayerView(player);
        PlayerController controller = new PlayerController(player);

        // Set up JFrame
        JFrame frame = new JFrame("LastSurvivor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(view);
        frame.addKeyListener(controller);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setFocusable(true); // Ensure frame can receive key events
        frame.requestFocusInWindow();
        // Game loop using Timer
        Timer timer = new Timer(1000 / FPS, new ActionListener() {
            private long lastTime = System.nanoTime();

            @Override
            public void actionPerformed(ActionEvent e) {
                long currentTime = System.nanoTime();
                float deltaTime = (currentTime - lastTime) / 1_000_000_000.0f; // Convert to seconds
                lastTime = currentTime;

                // Update game state
                player.update(deltaTime);
                view.update();
            }
        });
        timer.start();
    }
}