package Main.Utils;

import java.util.Random;

public class RandomBorderCoordinates {
    public static int[] getRandomBorderCoordinate() {
        Random random = new Random();
        int width = 1200;
        int height = 750;

        // Choose one of the four borders randomly
        int border = random.nextInt(4);

        // Return [x, y] coordinates based on the selected border
        switch (border) {
            case 0: // Top border (y = 0)
                return new int[]{random.nextInt(width), 0};
            case 1: // Bottom border (y = height - 1)
                return new int[]{random.nextInt(width), height - 1};
            case 2: // Left border (x = 0)
                return new int[]{0, random.nextInt(height)};
            case 3: // Right border (x = width - 1)
                return new int[]{width - 1, random.nextInt(height)};
            default:
                return new int[]{0, 0}; // Fallback, should not occur
        }
    }

}