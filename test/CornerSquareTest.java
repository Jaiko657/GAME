import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CornerSquareTest {

    @Test
    void landOnSquare() {
        var cornerSquare = new CornerSquare("TEST NAME", null);
        assertThrows(RuntimeException.class, () -> cornerSquare.landOnSquare(null,null));
    }
}