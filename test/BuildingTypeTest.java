import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuildingTypeTest {

    @Test
    void testToStringToilet() {
        var buildingType = BuildingType.TOILET;
        assertEquals("Toilet", buildingType.toString());
    }
    @Test
    void testToStringWormBreeder() {
        var buildingType = BuildingType.WORM_BREEDER;
        assertEquals("Worm Breeder", buildingType.toString());
    }
}