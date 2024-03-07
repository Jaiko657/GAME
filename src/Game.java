import java.util.*;

class Game {
    private static final int BOARD_SIZE = 20;
    final private Input input;

    final private ArrayList<Player> players = new ArrayList<>();
    private Square[] board;
    private int currentPlayerIndex;

    public boolean isFinished = false;

    private MonopolyBoard monopolyBoard;
    public final Console con;
    private RenderObject renderObject;

    public Game(Console con) {
        this.con = con;
        this.input = new Input(con);
        this.init();
    }

    public RenderObject getRenderObject() {
        return this.renderObject;
    }
    private void init() {
        this.currentPlayerIndex = 0;
        this.renderObject = new RenderObject();


        this.board = new Square[BOARD_SIZE]; // Define the array with 20 elements
        for (int i = 0; i < BOARD_SIZE; i++) {
            board[i] = getCorrectSquare(i, renderObject); //TODO: Move getCorrectSquare to game class (used to be for testing)
        }

        this.monopolyBoard = new MonopolyBoard(this);
        this.renderObject.setMonopolyBoard(this.monopolyBoard);
        //BASICALLY AN ASSERTION TO ENSURE NO ERROR
        if (this.renderObject.isActive() == false) {
            throw new RuntimeException("Renderer Not Set Up");
        }
            //TODO: Add logic to add each player; {
            players.add(new Player("PLAYER1", this.monopolyBoard));
            players.add(new Player("PLAYER2", this.monopolyBoard));
//            players.add(new Player("PLAYER3", this.monopolyBoard));
//            players.add(new Player("PLAYER4", this.monopolyBoard));
            // }

            this.monopolyBoard.refreshDisplay();
        }
    static public Square getCorrectSquare(int i, RenderObject renderObject) {
        //ChallengeSquare
        if((i % 5) == 0) {
            String name;
            switch (i) {
                case 0 -> {
                    return new StartingSquare(renderObject);
                }
                case 5 -> {
                    return new WormHuntChallenge(renderObject);
                }
                case 10 -> {
                    return new WoodGatheringChallenge(renderObject);
                }
                case 15 -> {
                    return new MarketDonationChallenge(renderObject);
                }
                default -> throw new RuntimeException();
            }
        }
        //Land Plot
        String name = null;
        BuildingType buildingType = null;
        if(i < 5) {
            name = "Worm Breeder Plot " + i;
            buildingType = BuildingType.WORM_BREEDER;
        } else if(i < 10) {
            i -= 1;
            name = "Worm Breeder Plot " + i;
            buildingType = BuildingType.WORM_BREEDER;
        } else if(i < 15) {
            i -= 10;
            name = "Toilet Plot " + i;
            buildingType = BuildingType.TOILET;
        } else if(i < 20) {
            i -= 11;
            name = "Toilet Plot " + i;
            buildingType = BuildingType.TOILET;
        } else {
            throw new RuntimeException("WONT OCCUR UNLESS ERROR IN CALLING LOOP");
        }
        return new LandPlot(name, renderObject, buildingType);
    }
    public void startNextTurn() {
        con.clear();
        Player currentPlayer = players.get(currentPlayerIndex);
        Square currentSquare = board[currentPlayer.getCurrentPosition()];
        con.println("\nIts " + currentPlayer.name + " turn.");
        con.println("\tOn Square: " + currentSquare.name + "\n");

        //TODO: If is owner of LandPlot then allow chance to build toilet
        //TODO: ADD OPTION TO FINISH GAME
        boolean actionSelected = false;

        while (!actionSelected) {
            var currentPlayerLandPlots = getPlayerLandPlots(currentPlayer);
            boolean manageLandPlotsOptionAvailable = !currentPlayerLandPlots.isEmpty();
            con.println("1:" + " Roll Dice");
            if (manageLandPlotsOptionAvailable) {
                con.println("2:" + " Complete Tasks on Land Plots (Own" + currentPlayerLandPlots.size() + " LandPlots");
            }
            con.println("3:" + " Finish Game");

            final var userChoice = input.getInt("Enter Choice");
            switch (userChoice) {
                case 1:
                    input.getString("\nPress Enter to Roll");
                    //before move
                    int diceRoll1 = Dice.roll();
                    int diceRoll2 = Dice.roll();
                    con.println();
                    con.println(currentPlayer.name + " Rolled a " + diceRoll1 + " and a " + diceRoll2);
                    int moveAmount = diceRoll1 + diceRoll2;
                    con.println();
                    con.println("Moving " + moveAmount + " Squares");
                    con.println();
                    movePlayer(currentPlayer, moveAmount);

                    //after move
                    Square landedSquare = board[currentPlayer.getCurrentPosition()];
                    landedSquare.landOnSquare(currentPlayer, this.input);

                    checkEndGame();
                    currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
                    actionSelected = true; // Proceed to next player
                    break;
                case 2:
                    if (manageLandPlotsOptionAvailable) {
                        managePlots(currentPlayer, currentPlayerLandPlots);
                        // Do not set actionSelected to true to allow repeated plot management
                    } else {
                        con.println("Invalid option, please try again.");
                    }
                    break;
                case 3:
                    isFinished = true;
                    actionSelected = true; // Exit game
                    break;
                default:
                    con.println("Invalid option, please try again.");
            }
        }
    }

    private void managePlots(Player currentPlayer, ArrayList<LandPlot> currentPlayerLandPlots) {
        //TODO: IMPLEMENT THE EXTRA TASKS LIKE TRANSFERING OWNERSHIP AND TAKING OUT COMPOSITE
        //use input.getInt(prompt) to get choice between 3 choices
        //choice 1, collect compost from toilets that are built
        //choice 2, collect worms from breeder
        //choice 3, Begin Construction of Building on plot

        con.println("MANAGE PLOTS TODO");
    }
    private void buildBuilding(LandPlot chosenPlot) {
        //TODO: GO THROUGH PAYING FOR EACH STEP
        /*
            1: Pay Workers
            2: Provide Wood
            3: Provide Worms
        */
    }
    private void checkEndGame() {
        for(Player player : this.players) {
            if(player.getMoney() <= 0) {
                this.isFinished = true;
                return;
            }
        }
        int buildingCount = 0;
        for(Square square : this.board) {
            if(square instanceof LandPlot && ((LandPlot) square).getHasBuilding()) {
                buildingCount++;
            }
        }
        if(buildingCount == BOARD_SIZE) {
            this.isFinished = true;
        }
    }
    private void movePlayer(Player player, int steps) {
        try {
            Thread.sleep(600);
            for (int i = 0; i < steps; i++) {
                // Sleep for 300ms before moving to the next position
                Thread.sleep(300);
                // Increment position by 1 and modulo with BOARD_SIZE to ensure it doesn't exceed board size
                player.setCurrentPosition((player.getCurrentPosition() + 1) % BOARD_SIZE);
                // Refresh the display to show the new position
                monopolyBoard.refreshDisplay();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Movement interrupted");
        }
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

    public void tickGameLogic() {
        //TODO: THIS WILL BE FOR RUNNING PASSIVE PER TERM FARMS ETC
        for(Square s : this.board) {
            if(s instanceof LandPlot plot) {
                var landPlot = (LandPlot) plot;
                plot.tick();
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
                            toiletScores.set(i, toiletScores.get(i) + 1);
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
            con.println("Draw between:");
            for (int index : maxIndexes) {
                con.println(players.get(index));
            }
        } else if (!maxIndexes.isEmpty()) {
            con.println(players.get(maxIndexes.getFirst()));
        } else {
            con.println("No data available.");
        }
    }

    public Square[] getBoard() {
        return this.board;
    }

    public ArrayList<Player> getPlayers() {
        return this.players;
    }
    //TODO: DEBUG REMOVE
    public void printBoard() {
        for(Square s : board) {
            con.println(s);
        }
    }
}
