import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Game g = new Game(scanner);
/*
        g.printBoard();
*/

        while(!g.isFinished) {
            g.startNextTurn();
            g.tickGameLogic();
        }
        scanner.close();

        System.out.println("\nGAME OVER");
    }
}