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

    @Test
    public void testRoll_Distribution() {
        final int numberOfRolls = 10000; // Increase for a larger sample size
        int[] counts = new int[6]; // Count occurrences of each number (1-6)

        // Roll the dice many times and count the occurrences of each result
        for(int i = 0; i < numberOfRolls; i++) {
            int result = Dice.roll();
            assertTrue(result >= 1 && result <= 6, "Dice roll should be between 1 and 6, but was " + result);
            counts[result - 1]++; // Increment the count for the rolled number
        }

        // Check that each number was rolled at least once
        for(int i = 0; i < counts.length; i++) {
            assertTrue(counts[i] > 0, "Number " + (i + 1) + " was never rolled.");
        }

        // Optional: Check the distribution - This part depends on your requirements for "randomness"
        // For a very rough check, ensure no number is rolled an unexpectedly high or low number of times
        // This is a basic and not statistically rigorous approach!
        int expectedCount = numberOfRolls / 6;
        int tolerance = (int) (expectedCount * 0.1); // Allow 10% deviation, adjust as necessary
        for(int count : counts) {
            assertTrue(Math.abs(count - expectedCount) <= tolerance, "Distribution is not uniform: expected around " + expectedCount + ", but got " + count);
        }
    }
}

