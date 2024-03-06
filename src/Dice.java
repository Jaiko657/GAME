import java.util.Date;
import java.util.Random;

public class Dice {
    static public int roll() {
        Random r = new Random();

        return r.nextInt(6) + 1;
    }
}
