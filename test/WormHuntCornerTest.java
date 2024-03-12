import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class WormHuntCornerTest {

    private WormHuntCorner wormHuntCorner;
    private Player mockPlayer;
    private Input mockInput;
    private Console mockConsole;

    @BeforeEach
    void setUp() {
        RenderObject mockRenderObject = mock(RenderObject.class);
        wormHuntCorner = new WormHuntCorner(mockRenderObject);
        mockPlayer = mock(Player.class);
        mockInput = mock(Input.class);
        mockConsole = mock(Console.class);

        when(mockInput.getCon()).thenReturn(mockConsole);

    }

    @Test
    void testLandOnSquareWithShortPath() {
        when(mockInput.getInt("Choice")).thenReturn(1);

        wormHuntCorner.landOnSquare(mockPlayer, mockInput);

        verify(mockConsole).println(contains("Digging "));
        verify(mockPlayer).setWorms(anyInt());
    }

    @Test
    void testLandOnSquareWithInvaldThenLongPath() {
        when(mockInput.getInt("Choice")).thenReturn(3,2);

        wormHuntCorner.landOnSquare(mockPlayer, mockInput);

        verify(mockConsole).println(contains("Foraging "));
        verify(mockPlayer).setWorms(anyInt());
    }
}
