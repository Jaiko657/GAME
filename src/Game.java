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
            board[i] = getCorrectSquare(i, renderObject);
        }

        this.monopolyBoard = new MonopolyBoard(this);
        this.renderObject.setMonopolyBoard(this.monopolyBoard);
        //BASICALLY AN ASSERTION TO ENSURE NO ERROR
        if (!this.renderObject.isActive()) {
            throw new RuntimeException("Renderer Not Set Up");
        }
        con.println("Add Players");
        con.gainFocus();
        int playerCount = 0;
        boolean isValid = false;
        while(!isValid) {
            playerCount = input.getInt("How Many Players (2-4)");
            //TODO: Use -1 as testing
            if(playerCount == -1) {
                players.add(new Player("PLAYER1", this.monopolyBoard));
                players.add(new Player("PLAYER2", this.monopolyBoard));
                ((LandPlot)this.board[14]).setOwner(players.get(0));
                ((LandPlot)this.board[14]).buildBuilding();
                ((LandPlot)this.board[8]).setOwner(players.get(0));
                ((LandPlot)this.board[8]).buildBuilding();
                break;
            }
            if(playerCount > 1 && playerCount < 5) {
                isValid = true;
            } else {
                con.println("Invalid Amount try again");
            }
        }
        for(int i = 0; i < playerCount; i++) {
            players.add(new Player(input.getString("Player " + (i+1) + " Name"), this.monopolyBoard));
            con.gainFocus();
        }

        this.monopolyBoard.refreshDisplay();
    }
    static public Square getCorrectSquare(int i, RenderObject renderObject) {
        if((i % 5) == 0) {
            switch (i) {
                case 0 -> {
                    return new BaseCampSquare(renderObject);
                }
                case 5 -> {
                    return new WormHuntCorner(renderObject);
                }
                case 10 -> {
                    return new WoodGatheringCorner(renderObject);
                }
                case 15 -> {
                    return new MarketDonationCorner(renderObject);
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
        con.gainFocus();
        Player currentPlayer = players.get(currentPlayerIndex);
        Square currentSquare = board[currentPlayer.getCurrentPosition()];
        con.println("\nIts " + currentPlayer.name + " turn.");
        con.println("\tOn Square: " + currentSquare.name + "\n");

        boolean actionSelected = false;

        while (!actionSelected) {
            var currentPlayerLandPlots = getPlayerLandPlots(currentPlayer);
            boolean manageLandPlotsOptionAvailable = !currentPlayerLandPlots.isEmpty();
            con.println("1:" + " Roll Dice");
            if (manageLandPlotsOptionAvailable) {
                con.println("2:" + " Complete Tasks (Own " + currentPlayerLandPlots.size() + " LandPlots)");
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
                    //finish game
                    isFinished = true;
                    //return to skip Press Enter
                    return;
                default:
                    con.println("Invalid option, please try again.");
            }
        }
        input.getString("Press Enter to finish turn");
    }

    private void managePlots(Player currentPlayer, ArrayList<LandPlot> currentPlayerLandPlots) {
        //TODO: IMPLEMENT THE EXTRA TASKS LIKE TRANSFERING OWNERSHIP

        boolean finishedManaging = false;
        while (!finishedManaging) {
            var unbuiltPlots = new ArrayList<LandPlot>();
            for (int i = 0; i < currentPlayerLandPlots.size(); i++) {
                var plot = currentPlayerLandPlots.get(i);
                if(!plot.getHasBuilding()) {
                    unbuiltPlots.add(plot);
                }
            }
            var breederPlots = new ArrayList<LandPlot>();
            for (int i = 0; i < currentPlayerLandPlots.size(); i++) {
                var plot = currentPlayerLandPlots.get(i);
                if(plot.getHasBuilding() && plot.buildingType == BuildingType.WORM_BREEDER) {
                    breederPlots.add(plot);
                }
            }
            var toiletPlots = new ArrayList<LandPlot>();
            for (int i = 0; i < currentPlayerLandPlots.size(); i++) {
                var plot = currentPlayerLandPlots.get(i);
                if(plot.getHasBuilding() && plot.buildingType == BuildingType.TOILET) {
                    toiletPlots.add(plot);
                }
            }
            con.clear();
            con.println("\nCompleting Tasks for: " + currentPlayer.name);
            if(!unbuiltPlots.isEmpty()) {
                con.println("1: Build on a plot");
            }
            if(!breederPlots.isEmpty()) {
                con.println("2: Collect worms");
            }
            if(!toiletPlots.isEmpty()) {
                con.println("3: Collect Compost");
            }
            con.println("0: Finish Managing Plots");

            final var userChoice = input.getInt("Enter Choice");
            switch (userChoice) {
                case 0:
                    finishedManaging = true;
                    break;
                case 1:
                    if(unbuiltPlots.isEmpty()) {
                        con.println("Invalid option, please try again.");
                        break;
                    }
                    buildBuildings(currentPlayer, unbuiltPlots);
                    break;
                case 2:
                    if(breederPlots.isEmpty()) {
                        con.println("Invalid option, please try again.");
                        break;
                    }
                    collectWorms(currentPlayer, breederPlots);
                    break;
                case 3:
                    if(toiletPlots.isEmpty()) {
                        con.println("Invalid option, please try again.");
                        break;
                    }
                    collectCompost(currentPlayer, toiletPlots);
                    break;
                case 4:
                    break;
                default:
                    con.println("Invalid option, please try again.");
            }
        }
        con.clear();
    }

    private void collectWorms(Player currentPlayer, ArrayList<LandPlot> wormPlots) {
        var con = this.input.getCon();
        con.println("Worm Breeders Build:");
        for(int i = 0; i < wormPlots.size();i++) {
            var plot = wormPlots.get(i);
            con.println(plot.name + " has " + plot.getBuilding().getContent() + " Worms");
        }

        var choice = input.getBool("Would you like to collect the worms that have been bred?");
        if(!choice) {
            input.getString("Press Enter to Continue");
            return;
        }
        for (int i = 0; i < wormPlots.size(); i++) {
            var plot = wormPlots.get(i);
            var worms = plot.getBuilding().takeContent();
            currentPlayer.setWorms(currentPlayer.getWorms() + worms);
            con.println(currentPlayer.name + " has just gained " + worms + "worms!");
        }
        input.getString("Press Enter to Continue");
    }

    private void collectCompost(Player currentPlayer, ArrayList<LandPlot> toiletPlots) {
        var con = this.input.getCon();
        con.println("Toilets with Compost:");
        for (int i = 0; i < toiletPlots.size(); i++) {
            var plot = toiletPlots.get(i);
            con.println(plot.name + " contains " + plot.getBuilding().getContent() + " compost");
        }

        var choice = input.getBool("Would you like to collect the compost from the toilets?");
        if (!choice) {
            input.getString("Press Enter to Continue");
            return;
        }
        int compost = 0;
        for (int i = 0; i < toiletPlots.size(); i++) {
            var plot = toiletPlots.get(i);
            var compostContent = plot.getBuilding().takeContent();
            compost += compostContent;
            con.println(currentPlayer.name + " has just gained " + compost + " compost!");
        }
        con.println("\nThis compost is the communities compost but the community provides wood in exchange for compost to help support the project!");

        con.println("The community gives what they can for the compost and there is no set price.");
        int woodDonationAmount = (int) ((int) compost * Math.random() * 2);
        woodDonationAmount /= 100;
        woodDonationAmount *= 100;

        con.println("\nToday the community can provide " + woodDonationAmount + " wood in exchange for the compost\n");

        if(woodDonationAmount > 0) {
            con.println(currentPlayer.name + " gained " + woodDonationAmount + " wood!!!");
            currentPlayer.setWood(currentPlayer.getWood() + woodDonationAmount);
        } else {
            con.println(currentPlayer.name + " has gained no wood as community has not enough spare resources to donate");
        }
        input.getString("Press Enter to Continue");
    }

    private void buildBuildings(Player currentPlayer, ArrayList<LandPlot> unbuiltPlots) {
        con.println(currentPlayer.name + " Owned Empty Land Plots:");
        for(int i = 0; i < unbuiltPlots.size(); i++) {
            var plot = unbuiltPlots.get(i);
            con.println((i+1) + ": " + plot.name + "\n\tLaborCost: " + plot.laborCost + ", Wood Needed: " + plot.woodCost + ", Worms Needed: " + plot.wormCost);
        }
        int choice = 0;
        boolean validChoice = false;
        while (!validChoice) {
            choice = input.getInt("Choice");
            if (choice >= 1 && choice <= unbuiltPlots.size()) {
                validChoice = true;
            } else {
                con.println("Invalid choice, please select a number between 1 and " + unbuiltPlots.size());
            }
        }
        buildBuilding(unbuiltPlots.get(choice-1));
        input.getString("Press Enter to Continue");
    }

    private void buildBuilding(LandPlot plot) {
        var owner = plot.getOwner();
        var con = input.getCon();
        var buildingType = plot.buildingType;
        if(canPlayerAfford(owner, plot)) {
            con.println(owner.name + " can afford to Build " + buildingType);
            if(input.getBool("Do You Want to Build?")) {
                owner.setMoney(owner.getMoney() - plot.laborCost);
                owner.setWood(owner.getWood() - plot.woodCost);
                owner.setWorms(owner.getWorms() - plot.wormCost);
                con.println("You have now built " + buildingType + " on " + plot.name);
                con.println("\tLabor Cost: " + plot.laborCost + ", Wood Needed: " + plot.woodCost + ", Worms Needed: " + plot.wormCost);

                plot.buildBuilding();
                renderObject.update();
            }
        }
    }
    private boolean canPlayerAfford(Player player, LandPlot plot) {
        return (
                player.getMoney() >= plot.laborCost &&
                player.getWood() >= plot.woodCost &&
                player.getWorms() >= plot.wormCost
        );
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
        //Runs every turn allowing toilets to create compost and worm breeders to create worms
        for(Square s : this.board) {
            if(s instanceof LandPlot plot) {
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
                if (plot.buildingType == BuildingType.TOILET) {
                    final var owner = plot.getOwner();
                    if (owner != null) {
                        for (int i = 0; i < players.size(); i++) {
                            if (players.get(i) == owner) {
                                //TODO: CHECK SCORES CORRECT
                                toiletScores.set(i, toiletScores.get(i) + 1);
                            }
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