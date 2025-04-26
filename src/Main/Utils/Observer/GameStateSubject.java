package Main.Utils.Observer;

/**
 * Interface for subjects in the observer pattern to manage game state observers.
 */
public interface GameStateSubject {

    /**
     * Adds an observer to the subject's observer list.
     * @param observer The observer to add.
     */
    void addObserver(GameStateObserver observer);

    /**
     * Removes an observer from the subject's observer list.
     * @param observer The observer to remove.
     */
    void removeObserver(GameStateObserver observer);

    /**
     * Notifies all registered observers of a state change.
     */
    void notifyObservers();
}