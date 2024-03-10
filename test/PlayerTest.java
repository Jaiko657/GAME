import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    static Player player = new Player("Test Player", null);
    MonopolyBoard monopolyBoard; // Assuming MonopolyBoard is a class you have defined

    @BeforeEach
    void setUp() {
    }

    @Test
    void getCurrentPosition() {
        assertEquals(0, player.getCurrentPosition());
    }
    @Test
    void testToString() {
        String expected = "Player{id=0, name='Test Player, currentPosition='0}";
        assertEquals(expected, player.toString());
    }

    @Test
    void setCurrentPosition() {
        player.setCurrentPosition(5);
        assertEquals(5, player.getCurrentPosition());
    }

    @Test
    void getMoney() {
        assertEquals(1000, player.getMoney());
    }

    @Test
    void setMoney() {
        player.setMoney(1000);
        assertEquals(1000, player.getMoney());
    }

    @Test
    void getWood() {
        assertEquals(2000, player.getWood());
    }

    @Test
    void setWood() {
        player.setWood(500);
        assertEquals(500, player.getWood());
    }

    @Test
    void getWorms() {
        player.setWorms(90);
        assertEquals(90, player.getWorms());
    }

    @Test
    void setWorms() {
        player.setWorms(50);
        assertEquals(50, player.getWorms());
    }
}