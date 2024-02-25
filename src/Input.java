import java.util.InputMismatchException;
import java.util.Scanner;

public class Input {
        final private Scanner scanner;

        public Input(Scanner scanner) {
            this.scanner = scanner;
        }

        public String getString(String prompt) {
            System.out.print(prompt + ": ");
            return scanner.nextLine();
        }

        public int getInt(String prompt) {
            while (true) {
                System.out.print(prompt + ": ");
                try {
                    var ret = scanner.nextInt();
                    scanner.nextLine();
                    return ret;
                } catch (InputMismatchException e) {
                    System.out.println("That's not an integer. Please try again.");
                    scanner.nextLine(); // Clear the buffer
                }
            }
        }

        public double getDouble(String prompt) {
            while (true) {
                System.out.print(prompt + ": ");
                try {
                    var ret = scanner.nextDouble();
                    scanner.nextLine();
                    return ret;
                } catch (InputMismatchException e) {
                    System.out.println("That's not a double. Please try again.");
                    scanner.nextLine(); // Clear the buffer
                }
            }
        }

        public char getChar(String prompt) {
            while (true) {
                System.out.print(prompt + ": ");
                String input = scanner.nextLine();
                if (input.length() == 1) {
                    return input.charAt(0);
                } else {
                    System.out.println("Please enter a single character.");
                }
            }
        }
    public boolean getBool(String prompt) {
        while (true) {
            System.out.print(prompt + " (y/n): ");
            try {
                var ret = scanner.nextLine().trim();
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
                System.out.println("Please Use y/n. Please try again.");
            }
        }
    }
}
