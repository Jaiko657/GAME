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
        players.add(new Player("PLAYER3"));
        players.add(new Player("PLAYER4"));
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
        System.out.println(ANSI_GREEN + "\nIts "  + currentPlayer.name + " turn." + ANSI_RESET);
        System.out.println("On Square: " + ANSI_BLUE + currentSquare.name + ANSI_RESET);

        input.getString("Press Enter to Roll");
        //before move
        int moveAmount = Dice.roll();
        System.out.println("Rolled a " + ANSI_PURPLE + moveAmount + ANSI_RESET);
        movePlayer(currentPlayer, moveAmount);

        //after move
        currentSquare = board[currentPlayer.currentPosition];
        System.out.println(currentPlayer.name + " Now on Square: " + currentSquare.name);
        currentSquare.landOnSquare(currentPlayer, this.input);

        checkEndGame();
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }
    private void checkEndGame() {
        //TODO: change logic for gameover
        for(Player player : this.players) {
            if(player.money <= 0) {

            }
        }
        if(currentPlayerIndex == 3) {
            this.isFinished = true;
        }
    }
    private void movePlayer(Player player, int steps) {
        player.currentPosition += steps;
        player.currentPosition %= BOARD_SIZE;
    }

    //DEBUG
    public void printBoard() {
        for(Square s : board) {
            System.out.println(s);
        }
    }
}
