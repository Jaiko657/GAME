import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SquareTest {

    @Test
    void landOnSquare() {
        var square = new Square("TEST NAME", new RenderObject());
        assertThrows(RuntimeException.class, () -> square.landOnSquare(null, null));
    }

    @Test
    void testToString() {
        var square1 = new Square("TEST NAME1", new RenderObject());
        assertEquals("Square{id=0, name=\'TEST NAME1\'}", square1.toString());
        var square2 = new Square("TEST NAME2", new RenderObject());
        assertEquals("Square{id=1, name=\'TEST NAME2\'}", square2.toString());
    }
}