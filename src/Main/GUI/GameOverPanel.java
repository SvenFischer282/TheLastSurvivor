package Main.GUI;

import Main.Game.Character.Player;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * A panel displayed when the player's health reaches 0, showing a "Game Over" message,
 * the player's score, and a leaderboard for the top 10 players.
 */
public class GameOverPanel extends JPanel {

    // Use an executor to fetch data on a background thread to prevent GUI freeze
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    /**
     * Constructs a GameOverPanel with options to restart the game or exit.
     * @param frame The parent JFrame to be closed upon restart.
     * @param player The player whose score is displayed.
     * @param playerCollection The MongoDB collection holding player score data.
     */
    public GameOverPanel(JFrame frame, Player player, MongoCollection<Document> playerCollection) {
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);

        // --- UI Components Initialization ---
        JLabel gameOverLabel = new JLabel("Game Over", SwingConstants.CENTER);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 48));
        gameOverLabel.setForeground(Color.RED);

        JLabel playerScoreLabel = new JLabel("Your score was: " + player.getScoreCounter().getScore(), SwingConstants.CENTER);
        playerScoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        playerScoreLabel.setForeground(Color.WHITE);

        // Leaderboard Display Area
        JTextArea leaderboardArea = new JTextArea("Fetching Leaderboard...", 12, 30);
        leaderboardArea.setEditable(false);
        leaderboardArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
        leaderboardArea.setBackground(new Color(30, 30, 30)); // Dark background
        leaderboardArea.setForeground(Color.CYAN); // Bright text
        leaderboardArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JScrollPane scrollPane = new JScrollPane(leaderboardArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(450, 300));


        // Restart Button
        JButton restartButton = new JButton("Restart");
        restartButton.setFont(new Font("Arial", Font.PLAIN, 24));
        restartButton.addActionListener(e -> {
            // Shut down the executor before restarting
            executor.shutdownNow();
            frame.dispose();
            // In a real application, you might want to call a method in MainApp
            // that handles clean initialization instead of MainApp.main(null)
            MainApp.main(null);
        });

        // Exit Button
        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.PLAIN, 24));
        exitButton.addActionListener(e -> {
            executor.shutdownNow(); // Shut down the executor
            System.exit(0);
        });

        // --- Layout Components ---
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(10, 0, 10, 0);

        add(gameOverLabel, gbc);
        add(playerScoreLabel, gbc);

        gbc.insets = new Insets(20, 0, 20, 0);
        add(scrollPane, gbc); // Add the leaderboard display

        gbc.insets = new Insets(10, 0, 10, 0);
        add(restartButton, gbc);
        add(exitButton, gbc);

        // --- Leaderboard Data Fetching ---
        fetchLeaderboardData(playerCollection, leaderboardArea);
    }

    /**
     * Fetches the top 10 players from MongoDB on a background thread and updates the JTextArea.
     * @param playerCollection The MongoDB collection.
     * @param leaderboardArea The JTextArea to update.
     */
    /**
     * Fetches the top 10 players from MongoDB on a background thread and updates the JTextArea,
     * adding placeholders if fewer than 10 players exist.
     * @param playerCollection The MongoDB collection.
     * @param leaderboardArea The JTextArea to update.
     */
    private void fetchLeaderboardData(MongoCollection<Document> playerCollection, JTextArea leaderboardArea) {
        executor.schedule(() -> {
            StringBuilder leaderboardText = new StringBuilder();
            // Updated header to reflect the new field name, e.g., 'Player Name'
            leaderboardText.append(String.format("%-4s %-20s %s\n", "Rank", "Player Name", "Score"));
            leaderboardText.append("---------------------------------------\n");

            // Define the sorting order: descending score (-1)
            Document sortQuery = new Document("score", -1);

            int rank = 1;
            final int MAX_PLAYERS = 10;

            try (MongoCursor<Document> cursor = playerCollection.find()
                    .sort(sortQuery)
                    .limit(MAX_PLAYERS)
                    .iterator()) {

                // 1. Process all available players
                while (cursor.hasNext()) {
                    Document doc = cursor.next();

                    // --- FIX 1: Change field name for the player name (e.g., to 'name') ---
                    // You must change the field name "name" below to whatever field
                    // in your MongoDB document holds the player's name.
                    String name = doc.getString("player");
                    // Fallback to "Unknown" if the field is null or missing
                    if (name == null || name.isEmpty()) {
                        name = "Unknown Player";
                    }

                    // --- FIX 2: Handle Long/Integer score casting by using Number ---
                    Number scoreNumber = doc.get("score", Number.class);
                    int score = scoreNumber != null ? scoreNumber.intValue() : 0;

                    // Format the line for the leaderboard
                    leaderboardText.append(String.format("%-4d %-20s %d\n", rank++, name, score));
                }

                // 2. Add placeholders if we have less than MAX_PLAYERS
                int playersFound = rank - 1; // Number of players successfully added

                if (playersFound == 0) {
                    // Only display the "No scores" message if absolutely no documents were found
                    leaderboardText.append(String.format("%-4s %-20s %s\n", "-", "No scores recorded yet.", "-"));
                }

                // Fill the remaining spots with '---' or 'TBD'
                for (int i = Math.max(1, playersFound + 1); i <= MAX_PLAYERS; i++) {
                    // Use 'i' for rank, even if the first spot was taken by "No scores"
                    leaderboardText.append(String.format("%-4d %-20s %s\n", i, "---", "---"));
                }

            } catch (Exception e) {
                // Keep the error logging concise for the GUI
                leaderboardText.append("\nFATAL: Error fetching data from database.\n");
                leaderboardText.append(e.getClass().getSimpleName()).append(": ").append(e.getMessage());
            }

            // Update the GUI on the Event Dispatch Thread (EDT)
            SwingUtilities.invokeLater(() -> {
                leaderboardArea.setText(leaderboardText.toString());
            });

        }, 0, TimeUnit.MILLISECONDS);
    }
    // ... (paintComponent remains the same)
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}