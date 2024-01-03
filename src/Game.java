import java.util.ArrayList;
import java.util.Scanner;

class Game {
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

        //TODO: Make each square different {
        this.board = new Square[BOARD_SIZE]; // Define the array with 20 elements
        for(int i = 0; i < BOARD_SIZE; i++) {
            board[i] =  BEBUG.getCorrectSquare(i);// Correctly create each square and assign to the array
        }
        //}
    }
    public void startNextTurn() {
        Player currentPlayer = players.get(currentPlayerIndex);
        Square currentSquare = board[currentPlayer.currentPosition];
        System.out.println("\nIts "  + currentPlayer.name + " turn.");
        System.out.println("On Square: " + currentSquare.name);

        input.getString("Press Enter to Roll");
        //before move
        int moveAmount = Dice.roll();
        System.out.println("Rolled a " + moveAmount);
        movePlayer(currentPlayer, moveAmount);

        //after move
        currentSquare = board[currentPlayer.currentPosition];
        System.out.println(currentPlayer.name + " Now on Square: " + currentSquare.name);
        currentSquare.landOnSquare(currentPlayer, this.input);

        nextPlayer();
    }
    private void nextPlayer() {
        checkEndGame();
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }
    private void checkEndGame() {
        //TODO: change logic for gameover
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
