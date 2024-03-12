import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

public class BaseCampSquareTest {

    @Test
    public void testLandOnSquareWithSpareWoodAvailable() {
        var mockInput = mock(Input.class);
        var mockConsole = mock(Console.class);
        var mockPlayer = mock(Player.class);
        var mockRenderObject = mock(RenderObject.class);
        // Setup
        when(mockInput.getCon()).thenReturn(mockConsole);
        when(mockInput.getInt(anyString())).thenReturn(100);
        when(mockPlayer.getWood()).thenReturn(0);

        BaseCampSquare baseCampSquare = new BaseCampSquare(mockRenderObject);

        // Action
        baseCampSquare.landOnSquare(mockPlayer, mockInput);

        // Verify
        verify(mockConsole).println("You arrive back to Base Camp. Luckily there is some backup materials!\nThere is 500 wood available how much do you want to take. Remember it is important to work together so if you don't need wood type 0.");
        verify(mockInput).getInt("Amount of Wood you want to take(less than 500)");
        verify(mockPlayer).setWood(100);
    }
    @Test
    public void testLandOnSquareWithSpareWoodAvailableInvalidChoice() {
        var mockInput = mock(Input.class);
        var mockConsole = mock(Console.class);
        var mockPlayer = mock(Player.class);
        var mockRenderObject = mock(RenderObject.class);
        // Setup
        when(mockInput.getCon()).thenReturn(mockConsole);
        when(mockInput.getInt(anyString())).thenReturn(-100, 100);
        when(mockPlayer.getWood()).thenReturn(0);

        BaseCampSquare baseCampSquare = new BaseCampSquare(mockRenderObject);

        // Action
        baseCampSquare.landOnSquare(mockPlayer, mockInput);

        // Verify
        verify(mockConsole).println("You arrive back to Base Camp. Luckily there is some backup materials!\nThere is 500 wood available how much do you want to take. Remember it is important to work together so if you don't need wood type 0.");
        verify(mockPlayer).setWood(100);
    }

    @Test
    public void testLandOnSquareWithNoSpareWood() {
        var mockInput = mock(Input.class);
        var mockConsole = mock(Console.class);
        var mockPlayer = mock(Player.class);
        var mockRenderObject = mock(RenderObject.class);
        // Setup
        when(mockInput.getCon()).thenReturn(mockConsole);
        BaseCampSquare baseCampSquare = new BaseCampSquare(mockRenderObject);

        // Simulate all wood taken
        baseCampSquare.landOnSquare(mockPlayer, mockInput);
        when(mockInput.getInt(anyString())).thenReturn(500, 100);
        baseCampSquare.landOnSquare(mockPlayer, mockInput);

        // Reset mock to simulate interaction after all wood is taken
        reset(mockConsole);

        // Action
        baseCampSquare.landOnSquare(mockPlayer, mockInput);

        // Verify
        verify(mockConsole).println("You arrive back to Base Camp. Hope you all have enough wood as all the spare wood has been claimed!!!");
    }
}