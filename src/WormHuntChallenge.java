public class WormHuntChallenge extends ChallengeSquare {
    public WormHuntChallenge(RenderObject renderObject) {
        super("River Edge", renderObject);
    }

    @Override
    public void landOnSquare(Player player, Input input) {
        var con = input.getCon();
        con.println("You arrive at the bank of the river! Do you want to 'dig' or 'forage'?");

        con.println("1. Dig");
        con.println("2. Forage");
        var choice = 1;
        while(true) {
            choice = input.getInt("Choice");
            if(choice == 1 || choice == 2) {
                break;
            }
            con.println("Invalid Choice Please type 1 or 2");
        }
        int wormsCollected;
        if (choice == 1) {
            wormsCollected = (int) (Math.random() * 25 + 10); // Generates between 10 to 35 worms
            con.println("Digging deep, you've collected " + wormsCollected + " worms!");
        } else {
            wormsCollected = (int) (Math.random() * 10 + 5); // Generates between 5 to 15 worms
            con.println("Foraging around, you've collected " + wormsCollected + " worms.");
        }
        player.setWorms(player.getWorms() + wormsCollected);
    }
}