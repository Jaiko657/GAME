public class WormHuntChallenge extends ChallengeSquare {
    public WormHuntChallenge(RenderObject renderObject) {
        super("River Edge", renderObject);
    }

    @Override
    public void landOnSquare(Player player, Input input) {
        super.landOnSquare(player, input);
        var con = input.getCon();
        con.println("You arrive at the bank of the river! Do you want to 'dig' or 'forage'?");
        String choice = input.getString("Enter 'dig' or 'forage': ");
        int wormsCollected;
        if ("dig".equalsIgnoreCase(choice)) {
            wormsCollected = (int) (Math.random() * 25 + 10); // Generates between 10 to 35 worms
            con.println("Digging deep, you've collected " + wormsCollected + " worms!");
        } else {
            wormsCollected = (int) (Math.random() * 10 + 5); // Generates between 5 to 15 worms
            con.println("Foraging around, you've collected " + wormsCollected + " worms.");
        }
        player.setWorms(player.getWorms() + wormsCollected);
    }
}

