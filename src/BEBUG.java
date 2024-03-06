public class BEBUG {
    static public Square getCorrectSquare(int i, RenderObject renderObject) {
        //ChallengeSquare
        if((i % 5) == 0) {
            String name;
            switch (i) {
                case 0 -> {
                    return new StartingSquare(renderObject);
                }
                case 5 -> {
                    return new WormHuntChallenge(renderObject);
                }
                case 10 -> {
                    return new WoodGatheringChallenge(renderObject);
                }
                case 15 -> {
                    return new MarketDonationChallenge(renderObject);
                }
                default -> throw new RuntimeException();
            }
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
        return new LandPlot("Plot " + plotNumber, renderObject, cost, capacity);
    }
}
