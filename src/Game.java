import extern.CONSTANTS;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.*;
//@ing
import static extern.AnsiColors.*;

class Game {
    private static final int BOARD_SIZE = 20;
    final private Input input;

    final private ArrayList<Player> players = new ArrayList<>();
    private Square[] board;
    private int currentPlayerIndex;

    public boolean isFinished = false;

    private MonopolyBoard monopolyBoard;

    public Game(Scanner scanner) {
        this.input = new Input(scanner);
        this.init();
    }
    private void init() {

        this.currentPlayerIndex = 0;


        this.board = new Square[BOARD_SIZE]; // Define the array with 20 elements
        for(int i = 0; i < BOARD_SIZE; i++) {
            board[i] =  BEBUG.getCorrectSquare(i); //TODO: Move getCorrectSquare to game class (used to be for testing)
            //have to move to below player instansiation
//            if(board[i] instanceof LandPlot) {
//                if(i % 4 == 0) {
//                    ((LandPlot) board[i]).setOwner(players.get(0));
//                    ((LandPlot) board[i]).smallToiletCount++;
//                    ((LandPlot) board[i]).wormBreederCount++;
//                } else {
//                    ((LandPlot) board[i]).setOwner(players.get(1));
//                    ((LandPlot) board[i]).largeToiletCount++;
//                    ((LandPlot) board[i]).upgradedWormBreederCount++;
//                }
//            }
        }
        //}
        this.monopolyBoard = new MonopolyBoard(board);

        //TODO: Add logic to add each player; {
        players.add(new Player("PLAYER1", this.monopolyBoard));
        players.add(new Player("PLAYER2", this.monopolyBoard));
        players.add(new Player("PLAYER3", this.monopolyBoard));
        players.add(new Player("PLAYER4", this.monopolyBoard));
        // }
    }
    public void startNextTurn() {
        Player currentPlayer = players.get(currentPlayerIndex);
        Square currentSquare = board[currentPlayer.getCurrentPosition()];
        final String currentStatsString = " " + ANSI_RED + "(money: " + currentPlayer.getMoney() + ", wood: " + currentPlayer.getWood() + ", worms: " + currentPlayer.getWorms() + " )" + ANSI_RESET;
        System.out.println(ANSI_GREEN + "\nIts " + currentPlayer.name + " turn." + ANSI_RESET + currentStatsString);
        System.out.println("\tOn Square: " + ANSI_BLUE + currentSquare.name + ANSI_RESET + "\n");

        //TODO: If is owner of LandPlot then allow chance to build toilet
        //TODO: ADD OPTION TO FINISH GAME
        boolean actionSelected = false;

        while (!actionSelected) {
            var currentPlayerLandPlots = getPlayerLandPlots(currentPlayer);
            var currentPlayerBuildingSpaces = getLandPlotEmptySpaces(currentPlayerLandPlots);
            boolean manageLandPlotsOptionAvailable = !currentPlayerLandPlots.isEmpty();
            System.out.println(ANSI_CYAN + "1:" + ANSI_RESET + " Roll Dice");
            if (manageLandPlotsOptionAvailable) {
                System.out.println(ANSI_CYAN + "2:" + ANSI_RESET + " Manage Land Plots (" + ANSI_YELLOW + currentPlayerLandPlots.size() + ANSI_RESET + " LandPlots, " + ANSI_YELLOW + currentPlayerBuildingSpaces + ANSI_RESET + " Building Spaces)");
            }
            System.out.println(ANSI_CYAN + "3:" + ANSI_RESET + " Finish Game");

            final var userChoice = input.getInt("Enter Choice");
            switch (userChoice) {
                case 1:
                    input.getString("Press Enter to Roll");
                    //before move
                    int moveAmount = Dice.roll();
                    System.out.println(ANSI_PURPLE + "Rolled a " + moveAmount + ANSI_RESET + "\n");
                    movePlayer(currentPlayer, moveAmount);

                    //after move
                    Square newSquare = board[currentPlayer.getCurrentPosition()];
                    newSquare.landOnSquare(currentPlayer, this.input);

                    checkEndGame();
                    currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
                    actionSelected = true; // Proceed to next player
                    break;
                case 2:
                    if (manageLandPlotsOptionAvailable) {
                        managePlots(currentPlayer, currentPlayerLandPlots);
                        // Do not set actionSelected to true to allow repeated plot management
                    } else {
                        System.out.println("Invalid option, please try again.");
                    }
                    break;
                case 3:
                    isFinished = true;
                    actionSelected = true; // Exit game
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }

    private void managePlots(Player currentPlayer, ArrayList<LandPlot> currentPlayerLandPlots) {
        System.out.println(currentPlayer.name +" has " + currentPlayerLandPlots.size() + " LandPlots with " + getLandPlotEmptySpaces(currentPlayerLandPlots) + " empty building spaces:");
        //TODO: LANDPLOTS MAY BE FULL
        var wantToBuild = input.getBool("Would you like to build on your Land Plots?");
        if (wantToBuild) {
        for (int i = 0; i < currentPlayerLandPlots.size(); i++) {
            var plot = currentPlayerLandPlots.get(i);
            System.out.println((i + 1) + ": " + plot.name + " has " + (plot.buildingCapacity - plot.getBuildingCount()) + " spaces free.");
        }
            var buildChoice = input.getInt("Enter Plot Number to build on");
            //TODO: ADD PROPER VALIDATION
            assert (buildChoice > -1 && buildChoice <= currentPlayerLandPlots.size());

            LandPlot chosenPlot = currentPlayerLandPlots.get(buildChoice-1);
            if((chosenPlot.buildingCapacity - chosenPlot.getBuildingCount()) > 0) {
                //VALID CHOICE
                buildBuilding(chosenPlot);
            } else {
                //TODO: add while loop here to allow invalid choice
                throw new RuntimeException("CANT ADD BUILDING PLOT FULL");
            }
        }
    }
    private void buildBuilding(LandPlot chosenPlot) {
        System.out.println("Choose which building to add to your land plot:");
        System.out.println("1. Worm Breeder");
        System.out.println("2. Upgraded Worm Breeder");
        System.out.println("3. Small Toilet");
        System.out.println("4. Large Toilet");

        int choice = input.getInt("Enter your choice (1-4)");
        //TODO: ENSURE CHOICE IS VALID (1-4)
        assert choice < 5 && choice > 0;
        boolean buildingAttempt = false;
        switch (choice) {
            case 1:
                System.out.println("Successfully built a worm breeder on " + chosenPlot.name);
                break;
            case 2:
                chosenPlot.upgradedWormBreederCount++;
                System.out.println("Successfully built a upgraded worm breeder on " + chosenPlot.name);
                break;
            case 3:
                chosenPlot.smallToiletCount++;
                System.out.println("Successfully built a small toilet on " + chosenPlot.name);
                break;
            case 4:
                chosenPlot.largeToiletCount++;
                System.out.println("Successfully built a large toilet on " + chosenPlot.name);
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }
    }

    private boolean playerBuildOnPlot(Player player, LandPlot plot, BuildingType type) {
        int costMoney = 0;
        int costWood = 0;
        int costWorms = 0;

        // Determine costs based on building type
        switch (type) {
            case SMALL_TOILET:
                costMoney = CONSTANTS.smallToiletMoneyCost;
                costWood = CONSTANTS.smallToiletWoodCost;
                costWorms = CONSTANTS.smallToiletWormCost;
                break;
            case LARGE_TOILET:
                costMoney = CONSTANTS.largeToiletMoneyCost;
                costWood = CONSTANTS.largeToiletWoodCost;
                costWorms = CONSTANTS.largeToiletWormCost;
                break;
            case WORM_BREEDER:
                costMoney = CONSTANTS.wormBreederMoneyCost;
                costWood = CONSTANTS.wormBreederWoodCost;
                costWorms = CONSTANTS.wormBreederWormCost;
                break;
            case UPGRADED_WORM_BREEDER:
                costMoney = CONSTANTS.upgradedWormBreederMoneyCost;
                costWood = CONSTANTS.upgradedWormBreederWoodCost;
                costWorms = CONSTANTS.upgradedWormBreederWormCost;
                break;
            default:
                return false; // Invalid building type
        }

        // Validate if player has enough resources
        if (player.getMoney() < costMoney || player.getWood() < costWood || player.getWorms() < costWorms) {
            return false; // Not enough resources
        }

        // Check if there's capacity to build on the plot
        if (plot.getBuildingCount() >= plot.buildingCapacity) {
            return false; // No capacity
        }

        // Deduct costs from player resources
        player.setMoney(player.getMoney() - costMoney);
        player.setWood(player.getWood() - costWood);
        player.setWorms(player.getWorms() - costWorms);

        // Increment the appropriate building count on the plot
        switch (type) {
            case SMALL_TOILET:
                plot.smallToiletCount++;
                break;
            case LARGE_TOILET:
                plot.largeToiletCount++;
                break;
            case WORM_BREEDER:
                plot.wormBreederCount++;
                break;
            case UPGRADED_WORM_BREEDER:
                plot.upgradedWormBreederCount++;
                break;
        }

        return true; // Build successful
    }
    private void checkEndGame() {
        /*
        TODO: change logic for gameover
        TODO: IF ALL OBJECTIVES ARE FULLY COMPLETE FINISH GAME ALSO
        */
        for(Player player : this.players) {
            if(player.getMoney() <= 0) {
                this.isFinished = true;
                break;
            }
        }
    }
    private void movePlayer(Player player, int steps) {
        // have to increment and modulo in one function or the displayed value will be more than max for a single tick
        player.setCurrentPosition((player.getCurrentPosition() + steps) % BOARD_SIZE);
    }

    private ArrayList<LandPlot> getPlayerLandPlots(Player player) {
        var plots = new ArrayList<LandPlot>();
        for(Square s : this.board) {
            if(s instanceof LandPlot plot) {
                if(plot.getOwner() == player) {
                    plots.add(plot);
                }
            }
        }
        return plots;
    }
    private int getLandPlotEmptySpaces(ArrayList<LandPlot> plots) {
        int emptySpaces = 0;
        for(var plot : plots) {
            int buildingCapacityRemaining = plot.buildingCapacity - plot.getBuildingCount();
            emptySpaces += buildingCapacityRemaining;
        }
        return emptySpaces;
    }
    //DEBUG
    public void printBoard() {
        for(Square s : board) {
            System.out.println(s);
        }
    }

    public void tickGameLogic() {
        //TODO: THIS WILL BE FOR RUNNING PASSIVE PER TERM FARMS ETC
        for(Square s : this.board) {
            if(s instanceof LandPlot plot) {
                //LOGIC TO GIVE PLAYER WORMS PER FARM
                final var owner = plot.getOwner();
                if(owner == null) continue;

                owner.setWorms(owner.getWorms() + plot.wormBreederCount * CONSTANTS.wormBreederWormAmount);
                owner.setWorms(owner.getWorms() + plot.upgradedWormBreederCount * CONSTANTS.upgradedWormBreederWormAmount);
            }
        }
    }

    public void doWinnerMessage() {
        //TODO: FINISH WINNER MESSAGE
        List<Integer> toiletScores = new ArrayList<>(players.size());
        for(int i = 0; i < players.size(); i++) {
            toiletScores.add(0);
        }

        for(Square s : this.board) {
            if(s instanceof LandPlot plot) {
                final var owner = plot.getOwner();
                if(owner != null) {
                    for(int i = 0; i < players.size(); i++) {
                        if(players.get(i) == owner) {
                            //TODO: CHECK SCORES CORRECT
                            toiletScores.set(i, toiletScores.get(i) + plot.smallToiletCount + plot.largeToiletCount * 2);
                        }
                    }
                }
            }
        }
        int max = -1;
        List<Integer> maxIndexes = new ArrayList<>(); // To handle multiple indexes with max value
        for(int i = 0; i < toiletScores.size(); i++) {
            if(toiletScores.get(i) > max) {
                max = toiletScores.get(i);
                maxIndexes.clear(); // Clear previous indexes for a new max value
                maxIndexes.add(i);
            } else if (toiletScores.get(i) == max) { // Handle draw condition
                maxIndexes.add(i);
            }
        }

        // Handle output for draw or single winner
        if (maxIndexes.size() > 1) {
            System.out.println("Draw between:");
            for (int index : maxIndexes) {
                System.out.println(players.get(index));
            }
        } else if (!maxIndexes.isEmpty()) {
            System.out.println(players.get(maxIndexes.getFirst()));
        } else {
            System.out.println("No data available.");
        }
    }
}
