package Main.Game;

public class ScoreCounter {
    private static ScoreCounter instance;
    private int score;
    private ScoreCounter() {
        this.score = 0;
    }

    public static ScoreCounter getInstance() {
        if (instance == null) {
            instance = new ScoreCounter();
        }
        return instance;
    }

    public static void setInstance(ScoreCounter instance) {
        ScoreCounter.instance = instance;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
