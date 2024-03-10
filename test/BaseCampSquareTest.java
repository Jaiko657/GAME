import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class BaseCampSquareTest {
    private BaseCampSquare basecamp;
    private Player player;
    private Input input;
    private Console con;

    @Before
    public void setUp() {
        RenderObject renderObject = mock(RenderObject.class);
        basecamp = new BaseCampSquare(renderObject);
        player = mock(Player.class);
        input = mock(Input.class);
        con = mock(Console.class);
        when(input.getCon()).thenReturn(con);
    }

    @Test
    public void testLandOnSquareWithSpareWood() {
        when(input.getInt(anyString())).thenReturn(100);
        when(player.getWood()).thenReturn(0);

        basecamp.landOnSquare(player, input);

        verify(con).println("You arrive back to Base Camp. Luckily there is some backup materials!\\nThere is 500 wood available how much do you want to take. Remember it is important to work together so if you don't need wood type 0.");
        verify(player).setWood(100);
        assertEquals(400, basecamp.spareWood);
    }

    @Test
    public void testLandOnSquareWithNoSpareWood() {
        basecamp.spareWood = 0;

        basecamp.landOnSquare(player, input);

        verify(con).println("You arrive back to Base Camp. Hope you all have enough wood as all the spare wood has been claimed!!!");
        verify(player, never()).setWood(anyInt());
    }

    @Test
    public void testInvalidWoodAmount() {
        when(input.getInt(anyString())).thenReturn(600, 100);

        basecamp.landOnSquare(player, input);

        verify(con).println("Invalid Amount of wood!!!\\n");
        verify(player).setWood(100);
        assertEquals(400, basecamp.spareWood);
    }
}
