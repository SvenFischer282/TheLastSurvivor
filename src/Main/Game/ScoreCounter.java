package Main.Game;

import Main.Utils.Observer.GameStateObserver;
import Main.Utils.Observer.GameStateSubject;

import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;

/**
 * A singleton class that manages and tracks the game score.
 * Provides global access to the score throughout the game and notifies observers of changes.
 */
public class ScoreCounter implements GameStateSubject {
    /**
     * The single instance of ScoreCounter.
     */
    private static ScoreCounter instance;

    /**
     * The current game score.
     */
    private int score;

    /**
     * List of observers to notify when the score changes.
     */
    private List<GameStateObserver> observers;

    /**
     * Private constructor to prevent instantiation.
     * Initializes score to 0 and observer list.
     */
    private ScoreCounter() {
        this.score = 0;
        this.observers = new ArrayList<>();
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
     * Sets the current score and notifies observers.
     * @param score The new score value to set
     */
    public void setScore(int score) {
        this.score = score;
        notifyObservers();
    }

    /**
     * Adds points to the current score and notifies observers.
     * @param points The points to add to the score
     */
    public void addScore(int points) {
        this.score += points;
        notifyObservers();
    }

    @Override
    public void addObserver(GameStateObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(GameStateObserver observer) {
        observers.remove(observer);
    }



    @Override
    public void notifyObservers() {
        SwingUtilities.invokeLater(() -> {
            for (GameStateObserver observer : observers) {
                observer.update();
            }
        });
    }
}