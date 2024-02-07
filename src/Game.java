import java.util.ArrayList;
import java.util.Scanner;

class Game {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    private static final int BOARD_SIZE = 20;
    final private Input input;

    final private ArrayList<Player> players = new ArrayList<>();
    private Square[] board;
    private int currentPlayerIndex;

    public boolean isFinished = false;

    public Game(Scanner scanner) {
        this.input = new Input(scanner);
        this.init();
    }
    private void init() {
        currentPlayerIndex = 0;

        //TODO: Add logic to add each player; {
        players.add(new Player("PLAYER1"));
        players.add(new Player("PLAYER2"));
//        players.add(new Player("PLAYER3"));
//        players.add(new Player("PLAYER4"));
        // }

        this.board = new Square[BOARD_SIZE]; // Define the array with 20 elements
        for(int i = 0; i < BOARD_SIZE; i++) {
            board[i] =  BEBUG.getCorrectSquare(i); //TODO: Move getCorrectSquare to game class (used to be for testing)
        }
        //}
    }
    public void startNextTurn() {
        Player currentPlayer = players.get(currentPlayerIndex);
        Square currentSquare = board[currentPlayer.currentPosition];
        System.out.println(ANSI_GREEN + "\nIts " + currentPlayer.name + " turn." + ANSI_RESET);
        System.out.println("On Square: " + ANSI_BLUE + currentSquare.name + ANSI_RESET);

        //TODO: If is owner of LandPlot then allow chance to build toilet
        var currentPlayerLandPlots = getPlayerLandPlots(currentPlayer);
        if(!currentPlayerLandPlots.isEmpty()) {
            managePlots(currentPlayer, currentPlayerLandPlots);
        }

        input.getString("Press Enter to Roll");
        //before move
        int moveAmount = Dice.roll();
        System.out.println(ANSI_PURPLE + "Rolled a " + moveAmount + ANSI_RESET + "\n");
        movePlayer(currentPlayer, moveAmount);

        //after move
        Square newSquare = board[currentPlayer.currentPosition];
        newSquare.landOnSquare(currentPlayer, this.input);

        checkEndGame();
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    private void managePlots(Player currentPlayer, ArrayList<LandPlot> currentPlayerLandPlots) {
        System.out.println(String.format("%s has %d LandPlots:", currentPlayer.name, currentPlayerLandPlots.size()));
        for (int i = 0; i < currentPlayerLandPlots.size(); i++) {
            var plot = currentPlayerLandPlots.get(i);
            System.out.println((i + 1) + ": " + plot.name + " has " + (plot.buildingCapacity - plot.getBuildingCount()) + " spaces free.");
        }
        var wantToBuild = input.getChar("Would you like to build on your Land Plots (y/n)");
        if (wantToBuild == 'y' || wantToBuild == 'Y') {
            var buildChoice = input.getInt("Enter Plot Number");
            if(buildChoice > -1 && buildChoice <= currentPlayerLandPlots.size()) {
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
        switch (choice) {
            case 1:
                chosenPlot.wormBreederCount++;
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
    private void checkEndGame() {
        //TODO: change logic for gameover
        for(Player player : this.players) {
            if(player.money <= 0) {
                this.isFinished = true;
                break;
            }
        }
    }
    private void movePlayer(Player player, int steps) {
        player.currentPosition += steps;
        player.currentPosition %= BOARD_SIZE;
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
            }
        }
    }
}
