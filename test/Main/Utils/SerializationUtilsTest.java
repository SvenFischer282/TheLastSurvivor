package Main.Utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SerializationUtilsTest {

    private static final String TEST_FILE_PATH = "test_health.ser";

    @BeforeEach
    void setUp() {
        // Clean up before each test
        File file = new File(TEST_FILE_PATH);
        if (file.exists()) {
            file.delete();
        }
    }

    @AfterEach
    void tearDown() {
        // Clean up after each test
        File file = new File(TEST_FILE_PATH);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testSaveAndLoadHealth() {
        int originalHealth = 7;

        // Save health to file
        SerializationUtils.saveHealth(originalHealth, TEST_FILE_PATH);

        // Check if file was created
        File file = new File(TEST_FILE_PATH);
        assertTrue(file.exists(), "Health save file should exist after saving.");

        // Load health from file
        int loadedHealth = SerializationUtils.loadHealth(TEST_FILE_PATH);

        // Check if loaded health matches
        assertEquals(originalHealth, loadedHealth, "Loaded health should match saved health.");
    }

    @Test
    void testLoadHealth_FileDoesNotExist() {
        // Make sure the file doesn't exist
        File file = new File(TEST_FILE_PATH);
        if (file.exists()) {
            file.delete();
        }

        // Try loading health from non-existing file
        int loadedHealth = SerializationUtils.loadHealth(TEST_FILE_PATH);

        // Should return -1 according to your loadHealth method
        assertEquals(-1, loadedHealth, "Loading from non-existent file should return -1.");
    }
}
