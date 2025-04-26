package Main.Utils;

import java.io.*;

public class    SerializationUtils {

    public static void saveHealth(int health, String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeInt(health);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int loadHealth(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return ois.readInt();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
