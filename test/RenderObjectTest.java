import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class RenderObjectTest {


    private RenderObject renderObject;

    @BeforeEach
    public void setUp() {
        renderObject = new RenderObject();
    }

    @Test
    public void testIsActiveWhenMonopolyBoardIsNull() {
        assertFalse(renderObject.isActive(), "RenderObject should not be active when MonopolyBoard is null");
    }
    @Test
    public void testIsActiveWhenMonopolyBoardIsNotNull() {
        renderObject.setMonopolyBoard(Mockito.mock(MonopolyBoard.class));
        assertTrue(renderObject.isActive(), "RenderObject should be active when MonopolyBoard is not null");
    }
    @Test
    public void testGetPlayersWhenMonopolyBoardIsNull() {
        assertNull(renderObject.getPlayers());
    }
    @Test
    public void testGetPlayersWhenMonopolyBoardNotNull() {
        //Array with 1 TEST PLAYER
        var mockPlayers = new ArrayList<Player>();
        mockPlayers.add(new Player("TEST NAME", null)); // Add mock players as needed

        //Add Mocked MonopolyBoard to renderObject
        var monopolyBoard = Mockito.mock(MonopolyBoard.class);
        renderObject.setMonopolyBoard(monopolyBoard);
        //give mocked MonopolyBoard Return
        when(monopolyBoard.getPlayers()).thenReturn(mockPlayers);

        //Actual Test
        assertEquals(mockPlayers, renderObject.getPlayers());
    }

    @Test
    public void testSetMonopolyBoardWithNullArguement() {
        assertThrows(IllegalArgumentException.class, () -> renderObject.setMonopolyBoard(null));
    }

    @Test
    public void testUpdateWithActiveMonopolyBoard() {
        // Add Mocked MonopolyBoard to renderObject
        var monopolyBoard = Mockito.mock(MonopolyBoard.class);
        renderObject.setMonopolyBoard(monopolyBoard);

        // Execute the method under test
        assertDoesNotThrow(() -> renderObject.update());

        // Verify that refreshDisplay() is called exactly once
        Mockito.verify(monopolyBoard, Mockito.times(1)).refreshDisplay();
    }
}