package Main.GUI;

import Main.Game.Character.Player;
import Main.GUI.Player.PlayerView;
import Main.GUI.Weapons.Gun.GunView;
import Main.Game.Weapons.Gun;

import javax.swing.*;
import java.awt.*;

public class MainContainer extends JPanel {
    private PlayerView playerView;
    private GunView gunView;

    public MainContainer(Player player, Gun gun) {
        setLayout(new OverlayLayout(this)); // Changed to OverlayLayout

        // Initialize views
        playerView = new PlayerView(player);
        gunView = new GunView(player, gun);

        // Add views (gunView on top of playerView)
        add(gunView);
        add(playerView);

        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(800, 600));
    }

    public PlayerView getPlayerView() {
        return playerView;
    }

    public GunView getGunView() {
        return gunView;
    }
}