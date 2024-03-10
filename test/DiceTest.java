import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class DiceTest {

    @Test
    public void testRoll_WithinRange() {
        // Roll the dice 100 times to ensure randomness does handle well over multiple attempts
        for(int i = 0; i < 100; i++) {
            int result = Dice.roll();
            // Verify if the dice roll is between 1 and 6
            assertTrue(result >= 1 && result <= 6, "Dice roll should be between 1 and 6, but was " + result);
        }
    }
}

