public class BEBUG {
    static public Square getCorrectSquare(int i) {
        //ChallengeSquare
        if((i % 5) == 0) {
            String name;
            switch (i) {
                case 0 -> name = "Go Square";
                case 5 -> name = "WORMS";
                case 10 -> name = "BRIDGE";
                case 15 -> name = "MONEY";
                default -> throw new RuntimeException();
            }
            return new ChallengeSquare(name);
        }
        //Land Plot
        int cost;
        int capacity;
        int plotNumber = i;
        if(i < 5) {
            cost = 100;
            capacity = 2;
        } else if(i < 10) {
            cost = 150;
            capacity = 3;
            plotNumber -= 1;
        } else if(i < 15) {
            cost = 200;
            capacity = 4;
            plotNumber -= 2;
        } else if(i < 20) {
            cost = 250;
            capacity = 5;
            plotNumber -= 3;
        } else {
            throw new RuntimeException("SHOULDNT OCCUR");
        }
        return new LandPlot("Plot " + plotNumber, cost, capacity);
    }
}
