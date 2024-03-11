import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    Player player;

    @BeforeEach
    void setUp() {
        this.player = new Player("Test Player", null);
    }

    @Test
    void testGetCurrentPosition() {
        assertEquals(0, player.getCurrentPosition());
    }
    @Test
    void testToString() {
        var newPlayer = new Player("Test Player", null);

        String expected = "Player{id=" + (newPlayer.id-1) + ", name='Test Player, currentPosition='0}";
        assertEquals(expected, player.toString());
    }

    @Test
    void testGetStatsGUIWhenNull() {
        assertNull(player.getStatsGUI());
    }

    @Test
    void testSetCurrentPosition() {
        player.setCurrentPosition(5);
        assertEquals(5, player.getCurrentPosition());
    }

    @Test
    void testGetMoney() {
        assertEquals(5000, player.getMoney());
    }

    @Test
    void testSetMoney() {
        player.setMoney(1000);
        assertEquals(1000, player.getMoney());
    }

    @Test
    void testGetWood() {
        assertEquals(2000, player.getWood());
    }

    @Test
    void testSetWood() {
        player.setWood(500);
        assertEquals(500, player.getWood());
    }

    @Test
    void testGetWorms() {
        player.setWorms(90);
        assertEquals(90, player.getWorms());
    }

    @Test
    void testSetWorms() {
        player.setWorms(50);
        assertEquals(50, player.getWorms());
    }

    @Test
    void testMockedGui() {

    }
}