import java.util.InputMismatchException;
import java.util.Scanner;

public class Input {
        final private Console con;

        public Console getCon() {return this.con;}
        public Input(Console con) {
            this.con = con;
        }

        public String getString(String prompt) {
            con.print(prompt + ": ");
            return con.readLn();
        }

        public int getInt(String prompt) {
            while (true) {
                con.print(prompt + ": ");
                try {
                    var txt = con.readLn();
                    int ret = Integer.parseInt(txt);
                    return ret;
                } catch (Exception e) {
                    con.println("That's not an integer. Please try again.");
                }
            }
        }

        public double getDouble(String prompt) {
            while (true) {
                con.print(prompt + ": ");
                try {
                    var txt = con.readLn();
                    double ret = Double.parseDouble(txt);
                    return ret;
                } catch (Exception e) {
                    con.println("That's not a Number. Please try again.");
                }
            }
        }

        public char getChar(String prompt) {
            while (true) {
                con.print(prompt + ": ");
                String input = con.readLn();
                if (input.length() == 1) {
                    return input.charAt(0);
                } else {
                    con.println("Please enter a single character.");
                }
            }
        }
    public boolean getBool(String prompt) {
        while (true) {
            con.print(prompt + " (y/n): ");
            try {
                var ret = con.readLn().trim();
                if(ret.length() != 1) {
                    throw new InputMismatchException("");
                }
                final char value = ret.charAt(0);
                if (value == 'y' || value == 'Y') {
                    return true;
                }
                else if (value == 'n' || value == 'N') {
                    return false;
                } else {
                    throw new InputMismatchException("");
                }
            } catch (InputMismatchException e) {
                con.println("Please Use y/n. Please try again.");
            }
        }
    }
}
