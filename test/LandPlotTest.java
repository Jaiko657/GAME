import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    void testLandOnSquareWithMoneyDonation() {
        // Mock dependencies
        var mockInput = Mockito.mock(Input.class);
        var mockCon = Mockito.mock(Console.class);
        var mockRenderObject = Mockito.mock(RenderObject.class); // Assuming RenderObject is an interface/abstract class
        var testPlayer = "TestPlayer";
        var player = new Player(testPlayer, null);
        var owner = new Player("OwnerPlayer", null);
        var plotName = "PLOTNAME";
        var plot = new LandPlot(plotName, mockRenderObject, BuildingType.TOILET); // Assuming Square is the class we're testing
        plot.setOwner(owner);

        // Setup mock behavior
        Mockito.when(mockInput.getCon()).thenReturn(mockCon);
        Mockito.when(mockInput.getBool(Mockito.anyString())).thenReturn(true); // Simulates agreeing to donate
        Mockito.when(mockInput.getInt(Mockito.anyString())).thenReturn(1); // Choice 1 for Money donation

        // Assume these setters exist for simulation purposes
        player.setMoney(1000); // Player has enough money to donate
        owner.setMoney(0); // Owner starts with 0 money for simplicity

        // Execute the method under test
        plot.landOnSquare(player, mockInput);

        // Verify interactions and state changes
        Mockito.verify(mockCon).println(plotName + " is owned already by OwnerPlayer");
        Mockito.verify(mockCon).print(testPlayer + " donates ");
        Mockito.verify(mockCon).print(750 + " Money ");
        Mockito.verify(mockCon).println(" to OwnerPlayer"); // Assuming 750 is the max donation amount
        assertEquals(750, owner.getMoney()); // Verify the owner's money increased as expected
    }
    @Test
    void testLandOnSquareWithWoodDonation() {
        // Mock dependencies
        var mockInput = Mockito.mock(Input.class);
        var mockCon = Mockito.mock(Console.class);
        var mockRenderObject = Mockito.mock(RenderObject.class); // Assuming RenderObject is an interface/abstract class
        var testPlayer = "TestPlayer";
        var player = new Player(testPlayer, null);
        var owner = new Player("OwnerPlayer", null);
        var plotName = "PLOTNAME";
        var plot = new LandPlot(plotName, mockRenderObject, BuildingType.TOILET); // Assuming Square is the class we're testing
        plot.setOwner(owner);

        // Setup mock behavior
        Mockito.when(mockInput.getCon()).thenReturn(mockCon);
        Mockito.when(mockInput.getBool(Mockito.anyString())).thenReturn(true); // Simulates agreeing to donate
        Mockito.when(mockInput.getInt(Mockito.anyString())).thenReturn(2); // Choice 2 for Wood donation

        // Assume these setters exist for simulation purposes
        player.setWood(1000); // Player has enough wood to donate
        owner.setWood(0); // Owner starts with 0 wood for simplicity

        // Execute the method under test
        plot.landOnSquare(player, mockInput);

        // Verify interactions and state changes
        Mockito.verify(mockCon).println(plotName + " is owned already by OwnerPlayer");
        Mockito.verify(mockCon).print(testPlayer + " donates ");
        Mockito.verify(mockCon).print(400 + " Wood ");
        Mockito.verify(mockCon).println(" to OwnerPlayer"); // Assuming 400 is the max donation amount
        assertEquals(400, owner.getWood()); // Verify the owner's wood increased as expected
    }
    @Test
    void testLandOnSquareWithWormDonation() {
        // Mock dependencies
        var mockInput = Mockito.mock(Input.class);
        var mockCon = Mockito.mock(Console.class);
        var mockRenderObject = Mockito.mock(RenderObject.class); // Assuming RenderObject is an interface/abstract class
        var testPlayer = "TestPlayer";
        var player = new Player(testPlayer, null);
        var owner = new Player("OwnerPlayer", null);
        var plotName = "PLOTNAME";
        var plot = new LandPlot(plotName, mockRenderObject, BuildingType.TOILET); // Assuming Square is the class we're testing
        plot.setOwner(owner);

        // Setup mock behavior
        Mockito.when(mockInput.getCon()).thenReturn(mockCon);
        Mockito.when(mockInput.getBool(Mockito.anyString())).thenReturn(true); // Simulates agreeing to donate
        Mockito.when(mockInput.getInt(Mockito.anyString())).thenReturn(3); // Choice 3 for Worm donation

        // Assume these setters exist for simulation purposes
        player.setWorms(1000); // Player has enough money to donate
        owner.setWorms(0); // Owner starts with 0 money for simplicity

        // Execute the method under test
        plot.landOnSquare(player, mockInput);

        // Verify interactions and state changes
        Mockito.verify(mockCon).println(plotName + " is owned already by OwnerPlayer");
        Mockito.verify(mockCon).print(testPlayer + " donates ");
        Mockito.verify(mockCon).print(20 + " Worms ");
        Mockito.verify(mockCon).println(" to OwnerPlayer"); // Assuming 20 is the max donation amount
        assertEquals(20, owner.getWorms()); // Verify the owner's Worms increased as expected
    }
    @Test
    void testLandOnSquareWithInitialInvalidChoice() {
        // Mock dependencies
        var mockInput = Mockito.mock(Input.class);
        var mockCon = Mockito.mock(Console.class);
        var mockRenderObject = Mockito.mock(RenderObject.class); // Assuming RenderObject is an interface/abstract class
        var testPlayer = "TestPlayer";
        var player = new Player(testPlayer, null);
        var owner = new Player("OwnerPlayer", null);
        var plotName = "PLOTNAME";
        var plot = new LandPlot(plotName, mockRenderObject, BuildingType.TOILET); // Assuming Square is the class we're testing
        plot.setOwner(owner);

        // Setup mock behavior
        Mockito.when(mockInput.getCon()).thenReturn(mockCon);
        Mockito.when(mockInput.getBool(Mockito.anyString())).thenReturn(true); // Simulates agreeing to donate
        Mockito.when(mockInput.getInt(Mockito.anyString())).thenReturn(100,3); // Choice 100 then 3 for Worm donation

        // Assume these setters exist for simulation purposes
        player.setWorms(1000); // Player has enough money to donate
        owner.setWorms(0); // Owner starts with 0 money for simplicity

        // Execute the method under test
        plot.landOnSquare(player, mockInput);

        // Verify interactions and state changes
        Mockito.verify(mockCon).println(plotName + " is owned already by OwnerPlayer");
        Mockito.verify(mockCon).print(testPlayer + " donates ");
        Mockito.verify(mockCon).print(20 + " Worms ");
        Mockito.verify(mockCon).println(" to OwnerPlayer"); // Assuming 20 is the max donation amount
        assertEquals(20, owner.getWorms()); // Verify the owner's Worms increased as expected
    }
    @Test
    void testLandOnSquareOwnedByLandingPlayerWithoutBuilding() {
        // Mock dependencies
        var mockInput = Mockito.mock(Input.class);
        var mockCon = Mockito.mock(Console.class);
        var mockRenderObject = Mockito.mock(RenderObject.class); // Assuming RenderObject is an interface/abstract class
        var testPlayer = "TestPlayer";
        var player = new Player(testPlayer, null);
        var plotName = "PLOTNAME";
        var plot = new LandPlot(plotName, mockRenderObject, BuildingType.TOILET); // Assuming Square is the class we're testing
        plot.setOwner(player);

        // Setup mock behavior
        Mockito.when(mockInput.getCon()).thenReturn(mockCon);
        Mockito.when(mockInput.getBool(Mockito.anyString())).thenReturn(true); // Simulates agreeing to donate

        // Execute the method under test
        plot.landOnSquare(player, mockInput);

        // Verify interactions and state changes
        Mockito.verify(mockCon).println(plotName + " is owned by you.");
    }
    @Test
    void testLandOnSquareOwnedByLandingPlayerWithBuilding() {
        // Mock dependencies
        var mockInput = Mockito.mock(Input.class);
        var mockCon = Mockito.mock(Console.class);
        var mockRenderObject = Mockito.mock(RenderObject.class); // Assuming RenderObject is an interface/abstract class
        var testPlayer = "TestPlayer";
        var player = new Player(testPlayer, null);
        var initialMoney = player.getMoney();
        var plotName = "PLOTNAME";
        var plot = new LandPlot(plotName, mockRenderObject, BuildingType.TOILET); // Assuming Square is the class we're testing
        plot.setOwner(player);
        plot.buildBuilding();

        // Setup mock behavior
        Mockito.when(mockInput.getCon()).thenReturn(mockCon);
        Mockito.when(mockInput.getBool(Mockito.anyString())).thenReturn(true); // Simulates agreeing to donate

        // Execute the method under test
        plot.landOnSquare(player, mockInput);

        // Verify interactions and state changes
        Mockito.verify(mockCon).println(plotName + " is owned by you.");
    }
    @Test
    void testLandOnSquareOwnedByLandingPlayerWithEmptyWormBuilding() {
        // Mock dependencies
        var mockInput = Mockito.mock(Input.class);
        var mockCon = Mockito.mock(Console.class);
        var mockRenderObject = Mockito.mock(RenderObject.class); // Assuming RenderObject is an interface/abstract class
        var testPlayer = "TestPlayer";
        var player = new Player(testPlayer, null);
        var initialMoney = player.getMoney();
        var plotName = "PLOTNAME";
        var plot = new LandPlot(plotName, mockRenderObject, BuildingType.WORM_BREEDER); // Assuming Square is the class we're testing
        plot.setOwner(player);
        plot.buildBuilding();
        plot.getBuilding().takeContent();

        // Setup mock behavior
        Mockito.when(mockInput.getCon()).thenReturn(mockCon);
        Mockito.when(mockInput.getBool(Mockito.anyString())).thenReturn(true); // Simulates agreeing to donate

        // Execute the method under test
        plot.landOnSquare(player, mockInput);

        // Verify interactions and state changes
        Mockito.verify(mockCon).println(plotName + " is owned by you.");
        assertEquals(player.getMoney(), initialMoney);
    }
    @Test
    void testLandOnSquareOwnedByLandingPlayerWithEmptyToiletBuilding() {
        // Mock dependencies
        var mockInput = Mockito.mock(Input.class);
        var mockCon = Mockito.mock(Console.class);
        var mockRenderObject = Mockito.mock(RenderObject.class); // Assuming RenderObject is an interface/abstract class
        var testPlayer = "TestPlayer";
        var player = new Player(testPlayer, null);
        var initialMoney = player.getMoney();
        var plotName = "PLOTNAME";
        var plot = new LandPlot(plotName, mockRenderObject, BuildingType.TOILET); // Assuming Square is the class we're testing
        plot.setOwner(player);
        plot.buildBuilding();
        plot.getBuilding().takeContent();

        // Setup mock behavior
        Mockito.when(mockInput.getCon()).thenReturn(mockCon);
        Mockito.when(mockInput.getBool(Mockito.anyString())).thenReturn(true); // Simulates agreeing to donate

        // Execute the method under test
        plot.landOnSquare(player, mockInput);

        // Verify interactions and state changes
        Mockito.verify(mockCon).println(plotName + " is owned by you.");
    }
    @Test
    void testLandOnLandPlotToiletNotOwnedAndTakeOwnership() {
        // Mock dependencies
        var mockInput = Mockito.mock(Input.class);
        var mockCon = Mockito.mock(Console.class);
        var mockRenderObject = Mockito.mock(RenderObject.class); // Assuming RenderObject is an interface/abstract class you're using
        var player = new Player("TestPlayer", null);
        var plotName = "Community Land";
        var plot = new LandPlot(plotName, mockRenderObject, BuildingType.TOILET); // Assuming Square is the class we're testing

        // Setup mock behavior
        Mockito.when(mockInput.getCon()).thenReturn(mockCon);
        Mockito.when(mockInput.getBool(Mockito.anyString())).thenReturn(true); // Simulates agreeing to take ownership

        // Execute the method under test
        plot.landOnSquare(player, mockInput);

        // Verify interactions
        Mockito.verify(mockCon).println("Community Land is not owned");
        Mockito.verify(mockCon).println("\nWould you like to take ownership this community land plot?");
        Mockito.verify(mockCon).println("It can have a Toilet for the community built on it!");
        Mockito.verify(mockCon).println("TestPlayer now owns Community Land");

        // Verify state changes
        assertEquals(player, plot.getOwner()); // Verify the player is now the owner of the square
        Mockito.verify(mockRenderObject, Mockito.times(2)).update(); // Assuming update is called when ownership changes
    }
    @Test
    void testLandOnLandPlotBreederNotOwnedAndTakeOwnership() {
        // Mock dependencies
        var mockInput = Mockito.mock(Input.class);
        var mockCon = Mockito.mock(Console.class);
        var mockRenderObject = Mockito.mock(RenderObject.class); // Assuming RenderObject is an interface/abstract class you're using

        var player = new Player("TestPlayer", null);
        var plotName = "Community Land";
        var plot = new LandPlot(plotName, mockRenderObject, BuildingType.WORM_BREEDER); // Assuming Square is the class we're testing

        // Setup mock behavior
        Mockito.when(mockInput.getCon()).thenReturn(mockCon);
        Mockito.when(mockInput.getBool(Mockito.anyString())).thenReturn(true); // Simulates agreeing to take ownership

        // Execute the method under test
        plot.landOnSquare(player, mockInput);

        // Verify interactions
        Mockito.verify(mockCon).println("Community Land is not owned");
        Mockito.verify(mockCon).println("\nWould you like to take ownership this community land plot?");
        Mockito.verify(mockCon).println("It can have a worm breeder to help you build more toilets!");
        Mockito.verify(mockCon).println("TestPlayer now owns Community Land");

        // Verify state changes
        assertEquals(player, plot.getOwner()); // Verify the player is now the owner of the square
        Mockito.verify(mockRenderObject, Mockito.times(2)).update(); // Assuming update is called when ownership changes
    }
    @Test
    void testLandOnLandPlotToiletNotOwnedAndOtherPlayerTakesOwnership() {
        // Mock dependencies
        var mockInput = Mockito.mock(Input.class);
        var mockCon = Mockito.mock(Console.class);
        var mockRenderObject = Mockito.mock(RenderObject.class); // Assuming RenderObject is an interface/abstract class you're using
        var players = new ArrayList<Player>();
        var player = new Player("TestPlayer", null);
        var player2 = new Player("TestPlayer2", null);
        players.add(player);
        players.add(player2);
        var plotName = "Community Land";
        var plot = new LandPlot(plotName, mockRenderObject, BuildingType.TOILET); // Assuming Square is the class we're testing

        // Setup mock behavior
        Mockito.when(mockInput.getCon()).thenReturn(mockCon);
        Mockito.when(mockInput.getBool(Mockito.anyString())).thenReturn(false); // Simulates disagreeing to take ownership
        Mockito.when(mockInput.getInt(Mockito.anyString())).thenReturn(2); // Simulates disagreeing to take ownership
        Mockito.when(mockRenderObject.getPlayers()).thenReturn(players); // Simulates players from game object

        // Execute the method under test
        plot.landOnSquare(player, mockInput);

        // Verify interactions
        Mockito.verify(mockCon).println("You don't want to take control of Land Plot.");
        Mockito.verify(mockCon).println("\nWould Anyone Else Like to take control of the Plot");
        Mockito.verify(mockCon).println("\nNew Owner is TestPlayer2");

        // Verify state changes
        assertEquals(player2, plot.getOwner()); // Verify the player2 is now the owner of the square
        Mockito.verify(mockRenderObject, Mockito.times(1)).getPlayers(); // Assuming update is called when ownership changes
        Mockito.verify(mockRenderObject, Mockito.times(2)).update(); // Assuming update is called when ownership changes
    }
//    @Test
    void testLandOnLandPlotToiletNotOwnedAndNoOneTakesOwnership() {
        // Mock dependencies
        var mockInput = Mockito.mock(Input.class);
        var mockCon = Mockito.mock(Console.class);
        var mockRenderObject = Mockito.mock(RenderObject.class); // Assuming RenderObject is an interface/abstract class you're using
        var players = new ArrayList<Player>();
        var player = new Player("TestPlayer", null);
        var player2 = new Player("TestPlayer2", null);
        players.add(player);
        players.add(player2);
        var plotName = "Community Land";
        var plot = new LandPlot(plotName, mockRenderObject, BuildingType.TOILET); // Assuming Square is the class we're testing

        // Setup mock behavior
        Mockito.when(mockInput.getCon()).thenReturn(mockCon);
        Mockito.when(mockInput.getBool(Mockito.anyString())).thenReturn(false); // Simulates disagreeing to take ownership
        Mockito.when(mockInput.getInt(Mockito.anyString())).thenReturn(0); // Simulates disagreeing to take ownership
        Mockito.when(mockRenderObject.getPlayers()).thenReturn(players); // Simulates players from game object

        // Execute the method under test
        plot.landOnSquare(player, mockInput);

        // Verify interactions
        Mockito.verify(mockCon).println("You don't want to take control of Land Plot.");
        Mockito.verify(mockCon).println("\nWould Anyone Else Like to take control of the Plot");
        Mockito.verify(mockCon).println("No one wants the Land Plot");

        // Verify state changes
        assertNull(plot.getOwner()); // Verify the player2 is now the owner of the square
        Mockito.verify(mockRenderObject, Mockito.times(1)).getPlayers(); // Assuming update is called when ownership changes
        Mockito.verify(mockRenderObject, Mockito.times(2)).update(); // Assuming update is called when ownership changes
    }
    //TODO: IMPORTANT FINISH THIS TESTING
}