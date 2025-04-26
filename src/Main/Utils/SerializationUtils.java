
package Main.Utils;

import java.io.*;

/**
 * Utility class for serializing and deserializing health data to and from files.
 */
public class SerializationUtils {

    /**
     * Saves the health value to a file.
     *
     * @param health   The health value to save.
     * @param filePath The path to the file where health will be saved.
     */
    public static void saveHealth(int health, String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeInt(health);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the health value from a file.
     *
     * @param filePath The path to the file containing the health value.
     * @return The loaded health value, or -1 if an error occurs.
     */
    public static int loadHealth(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return ois.readInt();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
