import javax.swing.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        var con = new Console(true);
        Game g = new Game(con);
//        g.printBoard();

        while(!g.isFinished) {
            g.startNextTurn();
            g.tickGameLogic();
        }
        g.doWinnerMessage();

        con.println("\nGAME OVER");

        con.print("Press Enter To Close Game: ");
        con.readLn();
        //to close GUI
        System.exit(0);
    }
}