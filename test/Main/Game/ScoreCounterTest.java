package Main.Game;

import Main.Utils.Observer.GameStateObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.SwingUtilities;

import static org.junit.jupiter.api.Assertions.*;

class ScoreCounterTest {

    private ScoreCounter scoreCounter;

    @BeforeEach
    void setUp() {
        // Reset singleton before each test
        ScoreCounter.setInstance(null);
        scoreCounter = ScoreCounter.getInstance();
    }

    @Test
    void testSingletonInstanceReturnsSameObject() {
        ScoreCounter anotherInstance = ScoreCounter.getInstance();
        assertSame(scoreCounter, anotherInstance);
    }

    @Test
    void testInitialScoreIsZero() {
        assertEquals(0, scoreCounter.getScore());
    }

    @Test
    void testSetScoreChangesValue() {
        scoreCounter.setScore(42);
        assertEquals(42, scoreCounter.getScore());
    }

    @Test
    void testAddScoreIncreasesScore() {
        scoreCounter.setScore(10);
        scoreCounter.addScore(15);
        assertEquals(25, scoreCounter.getScore());
    }

    @Test
    void testObserverIsNotifiedOnSetScore() throws Exception {
        TestObserver observer = new TestObserver();
        scoreCounter.addObserver(observer);
        scoreCounter.setScore(100);

        // Wait for SwingUtilities.invokeLater to run
        waitForSwing();

        assertTrue(observer.wasUpdated);
    }

    @Test
    void testObserverIsNotifiedOnAddScore() throws Exception {
        TestObserver observer = new TestObserver();
        scoreCounter.addObserver(observer);
        scoreCounter.addScore(50);

        waitForSwing();

        assertTrue(observer.wasUpdated);
    }

    @Test
    void testRemovedObserverDoesNotGetUpdate() throws Exception {
        TestObserver observer = new TestObserver();
        scoreCounter.addObserver(observer);
        scoreCounter.removeObserver(observer);
        scoreCounter.setScore(99);

        waitForSwing();

        assertFalse(observer.wasUpdated);
    }

    // --- Helper classes & methods ---

    static class TestObserver implements GameStateObserver {
        boolean wasUpdated = false;

        @Override
        public void update() {
            wasUpdated = true;
        }
    }

    /**
     * Waits briefly to allow SwingUtilities.invokeLater() to complete.
     */
    private void waitForSwing() throws Exception {
        // Forces EDT tasks to complete
        final Object lock = new Object();
        final boolean[] done = {false};

        SwingUtilities.invokeLater(() -> {
            synchronized (lock) {
                done[0] = true;
                lock.notifyAll();
            }
        });

        synchronized (lock) {
            while (!done[0]) {
                lock.wait();
            }
        }
    }
}
