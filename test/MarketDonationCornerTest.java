import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class MarketDonationCornerTest {

    private MarketDonationCorner marketDonationCorner;
    private Player mockPlayer;
    private Input mockInput;
    private Console mockConsole;

    @BeforeEach
    void setUp() {
        RenderObject mockRenderObject = mock(RenderObject.class);
        marketDonationCorner = new MarketDonationCorner(mockRenderObject);
        mockPlayer = mock(Player.class);
        mockInput = mock(Input.class);
        mockConsole = mock(Console.class);

        when(mockInput.getCon()).thenReturn(mockConsole);

    }

    @Test
    void testLandOnSquareWithAuction() {
        when(mockInput.getInt("Choice")).thenReturn(1);

        var N = 10;
        for(int i = 0; i < N; i++) {
            marketDonationCorner.landOnSquare(mockPlayer, mockInput);
        }

        verify(mockConsole, times(N)).println(contains(" bid "));
        verify(mockPlayer, times(N)).setMoney(anyInt());
    }

    @Test
    void testInvalidChoiceAndVendors() {
        when(mockInput.getInt("Choice")).thenReturn(3, 2); // First input invalid, second valid

        marketDonationCorner.landOnSquare(mockPlayer, mockInput);

        verify(mockConsole, times(1)).println("Invalid Choice Please type 1 or 2");
        verify(mockConsole).println(contains("Approaching the vendors paid off with a guaranteed donation of"));
        verify(mockPlayer).setMoney(anyInt());
    }
}