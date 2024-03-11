import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuildingTest {

    @Test
    void testGetTypeWithToilet() {
        var building = new Building(BuildingType.TOILET);
        assertEquals(building.getType(), BuildingType.TOILET);
    }
    @Test
    void testGetTypeWithWormBreeder() {
        var building = new Building(BuildingType.WORM_BREEDER);
        assertEquals(building.getType(), BuildingType.WORM_BREEDER);
    }
    @Test
    void testGetContentWithToilet() {
        var building = new Building(BuildingType.TOILET);
        assertEquals(building.getContent(), PRICES.TOILET_WORMCOST);
    }
    @Test
    void testGetContentWithWormBreeder() {
        var building = new Building(BuildingType.WORM_BREEDER);
        assertEquals(building.getContent(), PRICES.WORM_BREEDER_WORMCOST);
    }

    @Test
    void testTickWithToilet() {
        var building = new Building(BuildingType.TOILET);
        assertEquals(building.getContent(), PRICES.TOILET_WORMCOST);
        building.tick();
        assertEquals(building.getContent(), PRICES.TOILET_WORMCOST + 10);
    }
    @Test
    void testTakeContentWithWormBreeder() {
        var building = new Building(BuildingType.WORM_BREEDER);
        assertEquals(building.getContent(), PRICES.WORM_BREEDER_WORMCOST);
        var takenAmount = building.takeContent();
        assertEquals(building.getContent(), 0);
        assertEquals(takenAmount, 30);
    }

    @Test
    void testTakeContentWithToilet() {
        var building = new Building(BuildingType.TOILET);
        assertEquals(building.getContent(), PRICES.TOILET_WORMCOST);
        var takenAmount = building.takeContent();
        assertEquals(building.getContent(), 0);
        assertEquals(takenAmount, 100);
    }
    @Test
    void testTickWithWormBreeder() {
        var building = new Building(BuildingType.WORM_BREEDER);
        assertEquals(building.getContent(), PRICES.WORM_BREEDER_WORMCOST);
        building.tick();
        assertEquals(building.getContent(), PRICES.WORM_BREEDER_WORMCOST + 30);
    }
}