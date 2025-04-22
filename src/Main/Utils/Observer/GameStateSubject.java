package Main.Utils.Observer;

public interface GameStateSubject {
    void addObserver(GameStateObserver observer);
    void removeObserver(GameStateObserver observer);
    void notifyObservers();
}