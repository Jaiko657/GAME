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
                    return scanner.nextInt();
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
                    return scanner.nextDouble();
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
}
