package Main.Game;

/**
 * A singleton class that manages and tracks the game score.
 * Provides global access to the score throughout the game.
 */
public class ScoreCounter {
    /**
     * The single instance of ScoreCounter.
     */
    private static ScoreCounter instance;

    /**
     * The current game score.
     */
    private int score;

    /**
     * Private constructor to prevent instantiation.
     * Initializes score to 0.
     */
    private ScoreCounter() {
        this.score = 0;
    }

    /**
     * Gets the singleton instance of ScoreCounter.
     * Creates a new instance if one doesn't exist.
     * @return The ScoreCounter instance
     */
    public static ScoreCounter getInstance() {
        if (instance == null) {
            instance = new ScoreCounter();
        }
        return instance;
    }

    /**
     * Sets the singleton instance (primarily for testing purposes).
     * @param instance The ScoreCounter instance to set
     */
    public static void setInstance(ScoreCounter instance) {
        ScoreCounter.instance = instance;
    }

    /**
     * Gets the current score.
     * @return The current score value
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the current score.
     * @param score The new score value to set
     */
    public void setScore(int score) {
        this.score = score;
    }
}