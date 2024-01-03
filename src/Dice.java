import java.util.Random;

public class Dice {
    static public int roll() {
        Random r = new Random();
        return r.nextInt(12) + 1;
    }
}
