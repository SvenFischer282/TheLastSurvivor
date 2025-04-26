package Main.Utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RandomBorderCoordinatesTest {

    @Test
    void testRandomBorderCoordinateIsOnBorder() {
        int width = 1200;
        int height = 750;

        // Try multiple times to be safer (e.g., 100 iterations)
        for (int i = 0; i < 100; i++) {
            int[] coordinate = RandomBorderCoordinates.getRandomBorderCoordinate();

            int x = coordinate[0];
            int y = coordinate[1];

            // Check if the coordinate is actually on the border
            boolean isOnTopBorder = (y == 0) && (x >= 0 && x < width);
            boolean isOnBottomBorder = (y == height - 1) && (x >= 0 && x < width);
            boolean isOnLeftBorder = (x == 0) && (y >= 0 && y < height);
            boolean isOnRightBorder = (x == width - 1) && (y >= 0 && y < height);

            assertTrue(
                    isOnTopBorder || isOnBottomBorder || isOnLeftBorder || isOnRightBorder,
                    "Coordinate should be on one of the four borders, but got: (" + x + ", " + y + ")"
            );
        }
    }
}
