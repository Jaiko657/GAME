import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class InputTest {

    @Test
    void testGetCon() {
        var con = Mockito.mock(Console.class);
        var input = new Input(con);
        assertEquals(input.getCon(), con);
    }

    @Test
    void testGetString() {
        var con = Mockito.mock(Console.class);
        Mockito.doNothing().when(con).print(Mockito.anyString());
        var testString = "TEST123";
        Mockito.when(con.readLn()).thenReturn(testString);
        var input = new Input(con);
        assertEquals(testString, input.getString("PROMPT"));
    }

    @Test
    void testGetIntValidInt() {
        var con = Mockito.mock(Console.class);
        Mockito.doNothing().when(con).print(Mockito.anyString());
        var testString = "123";
        Mockito.when(con.readLn()).thenReturn(testString);
        var input = new Input(con);
        assertEquals(Integer.parseInt(testString), input.getInt("PROMPT"));
    }
    @Test
    void testGetIntCatchClause() {
        var con = Mockito.mock(Console.class);
        Mockito.doNothing().when(con).println(Mockito.anyString());
        Mockito.doNothing().when(con).print(Mockito.anyString());
        // Simulate user entering a non-integer followed by an integer to break out of the loop.
        Mockito.when(con.readLn()).thenReturn("notAnInteger", "1");
        var input = new Input(con);
        assertEquals(1, input.getInt("PROMPT"));
        // Verify the catch clause was executed by checking if the error message was printed.
        Mockito.verify(con).println("That's not an integer. Please try again.");
    }
    @Test
    void testGetDoubleValidInt() {
        var con = Mockito.mock(Console.class);
        Mockito.doNothing().when(con).print(Mockito.anyString());
        var testString = "1.23";
        Mockito.when(con.readLn()).thenReturn(testString);
        var input = new Input(con);
        assertEquals(Double.parseDouble(testString), input.getDouble("PROMPT"));
    }
    @Test
    void testGetDoubleCatchClause() {
        var con = Mockito.mock(Console.class);
        Mockito.doNothing().when(con).println(Mockito.anyString());
        Mockito.doNothing().when(con).print(Mockito.anyString());
        // Simulate user entering a non-double followed by a double to break out of the loop.
        Mockito.when(con.readLn()).thenReturn("notAnInteger", "1.23");
        var input = new Input(con);
        assertEquals((double) 1.23, input.getDouble("PROMPT"));
        // Verify the catch clause was executed by checking if the error message was printed.
        Mockito.verify(con).println("That's not a Number. Please try again.");
    }
    @Test
    void testGetCharValidInput() {
        var con = Mockito.mock(Console.class);
        Mockito.doNothing().when(con).print(Mockito.anyString());
        var testChar = "a";
        Mockito.when(con.readLn()).thenReturn(testChar);
        var input = new Input(con);
        assertEquals(testChar.charAt(0), input.getChar("PROMPT"));
    }

    @Test
    void testGetCharCatchClause() {
        var con = Mockito.mock(Console.class);
        Mockito.doNothing().when(con).println(Mockito.anyString());
        Mockito.doNothing().when(con).print(Mockito.anyString());
        // Simulate user entering multiple characters followed by a single character to break out of the loop.
        Mockito.when(con.readLn()).thenReturn("abc", "a");
        var input = new Input(con);
        assertEquals('a', input.getChar("PROMPT"));
        // Verify the catch clause was executed by checking if the error message was printed.
        Mockito.verify(con).println("Please enter a single character.");
    }
    @Test
    void testGetBoolValidInputYes() {
        var con = Mockito.mock(Console.class);
        Mockito.doNothing().when(con).print(Mockito.anyString());
        var testInput = "y";
        Mockito.when(con.readLn()).thenReturn(testInput);
        var input = new Input(con);
        assertTrue(input.getBool("PROMPT"));
    }

    @Test
    void testGetBoolValidInputNo() {
        var con = Mockito.mock(Console.class);
        Mockito.doNothing().when(con).print(Mockito.anyString());
        var testInput = "n";
        Mockito.when(con.readLn()).thenReturn(testInput);
        var input = new Input(con);
        assertFalse(input.getBool("PROMPT"));
    }

    @Test
    void testGetBoolCatchClauseInvalidLength() {
        var con = Mockito.mock(Console.class);
        Mockito.doNothing().when(con).println(Mockito.anyString());
        Mockito.doNothing().when(con).print(Mockito.anyString());
        // Simulate user entering an invalid response (too long) followed by a valid 'y' response
        Mockito.when(con.readLn()).thenReturn("yes", "y");
        var input = new Input(con);
        assertTrue(input.getBool("PROMPT"));
        // Verify the error message was printed due to invalid input length
        Mockito.verify(con).println("Please Use y/n. Please try again.");
    }

    @Test
    void testGetBoolCatchClauseInvalidCharacter() {
        var con = Mockito.mock(Console.class);
        Mockito.doNothing().when(con).println(Mockito.anyString());
        Mockito.doNothing().when(con).print(Mockito.anyString());
        // Simulate user entering an invalid character followed by a valid 'n' response
        Mockito.when(con.readLn()).thenReturn("a", "n");
        var input = new Input(con);
        assertFalse(input.getBool("PROMPT"));
        // Verify the error message was printed due to an invalid character
        Mockito.verify(con).println("Please Use y/n. Please try again.");
    }

}