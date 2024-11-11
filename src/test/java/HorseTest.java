import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;

public class HorseTest {

    @Test
    void constructorShouldThrowExceptionWhenNameIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new Horse(null, 2, 5));
        assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n"})
    void constructorShouldThrowExceptionWhenNameIsBlank(String name) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new Horse(name, 2, 5));
        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    void constructorShouldThrowExceptionWhenSpeedIsNegative() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new Horse("Test", -1));
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    void constructorShouldThrowExceptionWhenDistanceIsNegative() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new Horse("Test", 2, -1));
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    void moveShouldCallGetRandomDouble() {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            Horse horse = new Horse("Test", 2, 10);
            horse.move();
            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.2, 0.5, 0.9})
    void moveShouldCalculateDistanceCorrectly(double randomFactor) {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(randomFactor);
            Horse horse = new Horse("Test", 2, 10);
            horse.move();
            assertEquals(10 + 2 * randomFactor, horse.getDistance());
        }
    }

}