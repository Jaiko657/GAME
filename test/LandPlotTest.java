import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LandPlotTest {

    @Test
    void testGetHasBuildingWhenFalseWithInvalidType() {
        assertThrows(RuntimeException.class, () -> new LandPlot("TEST NAME", new RenderObject(), null));
    }
    @Test
    void testGetHasBuildingWhenFalseWithToilet() {
        var landPlot = new LandPlot("TEST NAME", new RenderObject(), BuildingType.TOILET);
        assertFalse(landPlot.getHasBuilding());
    }
    @Test
    void testBuildBuildingWithToilet() {
        var landPlot = new LandPlot("TEST NAME", new RenderObject(), BuildingType.TOILET);
        landPlot.buildBuilding();
        assertTrue(landPlot.getHasBuilding());
    }
    @Test
    void testGetHasBuildingWhenFalseWithWormBreeder() {
        var landPlot = new LandPlot("TEST NAME", new RenderObject(), BuildingType.WORM_BREEDER);
        assertFalse(landPlot.getHasBuilding());
    }
    @Test
    void testBuildBuildingWithWormBreeder() {
        var landPlot = new LandPlot("TEST NAME", new RenderObject(), BuildingType.WORM_BREEDER);
        landPlot.buildBuilding();
        assertTrue(landPlot.getHasBuilding());
    }

    @Test
    void forceBuildBuilding() {
    }

    @Test
    void testGetBuildingWithoutBuilding() {
        var landPlot = new LandPlot("TEST NAME", new RenderObject(), BuildingType.WORM_BREEDER);
        assertNull(landPlot.getBuilding());
    }

    @Test
    void setOwner() {
        var landPlot = new LandPlot("TEST NAME", new RenderObject(), BuildingType.WORM_BREEDER);
        var pl = new Player("TEST PLAYER", null);
        landPlot.setOwner(pl);
        assertEquals(pl, landPlot.getOwner());
    }

    @Test
    void testTickWithoutBuilding() {
        var landPlot = new LandPlot("TEST NAME", new RenderObject(), BuildingType.WORM_BREEDER);
        landPlot.tick();
    }
    @Test
    void testTickWithBuilding() {
        var landPlot = new LandPlot("TEST NAME", new RenderObject(), BuildingType.WORM_BREEDER);
        landPlot.buildBuilding();
        assertEquals(PRICES.WORM_BREEDER_WORMCOST, landPlot.getBuilding().getContent());
        landPlot.tick();
        assertEquals(PRICES.WORM_BREEDER_WORMCOST+30, landPlot.getBuilding().getContent());
    }

    @Test
    void landOnSquare() {
    }
}