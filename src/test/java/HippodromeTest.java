import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

public class HippodromeTest {

    @Test
    void constructorShouldThrowExceptionWhenHorsesIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new Hippodrome(null));
        assertEquals("Horses cannot be null.", exception.getMessage());
    }

    @Test
    void constructorShouldThrowExceptionWhenHorsesIsEmpty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new Hippodrome(List.of()));
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    void getHorsesShouldReturnCorrectList() {
        List<Horse> horses = List.of(new Horse("Test1", 2), new Horse("Test2", 3));
        Hippodrome hippodrome = new Hippodrome(horses);
        assertEquals(horses, hippodrome.getHorses());
    }

    @Test
    void moveShouldCallMoveOnAllHorses() {
        List<Horse> horses = mockListOfHorses(50);
        Hippodrome hippodrome = new Hippodrome(horses);

        hippodrome.move();
        horses.forEach(horse -> verify(horse).move());
    }

    @Test
    void getWinnerShouldReturnHorseWithMaxDistance() {
        Horse horse1 = new Horse("Test1", 2, 10);
        Horse horse2 = new Horse("Test2", 3, 15);
        Horse horse3 = new Horse("Test3", 4, 5);

        Hippodrome hippodrome = new Hippodrome(List.of(horse1, horse2, horse3));
        assertEquals(horse2, hippodrome.getWinner());
    }

    private List<Horse> mockListOfHorses(int count) {
        return Mockito.mock(List.class);
    }

}