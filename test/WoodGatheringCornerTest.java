import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class WoodGatheringCornerTest {

    private WoodGatheringCorner woodGatheringCorner;
    private Player mockPlayer;
    private Input mockInput;
    private Console mockConsole;

    @BeforeEach
    void setUp() {
        RenderObject mockRenderObject = mock(RenderObject.class);
        woodGatheringCorner = new WoodGatheringCorner(mockRenderObject);
        mockPlayer = mock(Player.class);
        mockInput = mock(Input.class);
        mockConsole = mock(Console.class);

        when(mockInput.getCon()).thenReturn(mockConsole);

    }

    @Test
    void testLandOnSquareWithShortPath() {
        when(mockInput.getInt("Choice")).thenReturn(1);

        woodGatheringCorner.landOnSquare(mockPlayer, mockInput);

        verify(mockConsole).println(contains(" took the challenging "));
        verify(mockPlayer).setWood(anyInt());
    }

    @Test
    void testLandOnSquareWithInvaldThenLongPath() {
        when(mockInput.getInt("Choice")).thenReturn(3,2);

        woodGatheringCorner.landOnSquare(mockPlayer, mockInput);

        verify(mockConsole).println(contains(" took the short "));
        verify(mockPlayer).setWood(anyInt());
    }
}
