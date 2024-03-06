public class WoodGatheringChallenge extends ChallengeSquare {
    public WoodGatheringChallenge(RenderObject renderObject) {
        super("Forest", renderObject);
    }

    @Override
    public void landOnSquare(Player player, Input input) {
        super.landOnSquare(player, input);
        var con = input.getCon();
        con.println("You've entered a local forest to gather wood. Choose your path: 'short' for a quick but less rewarding route, or 'long' for a challenging but potentially more rewarding journey.");
        String choice = input.getString("Enter 'short' or 'long': ");
        int woodCollected;
        if ("long".equalsIgnoreCase(choice)) {
            woodCollected = (int) (Math.random() * 15 + 5); // Generates between 5 to 20 units of wood
            con.println("You took the challenging path and collected " + woodCollected + " units of wood!");
        } else {
            woodCollected = (int) (Math.random() * 5 + 1); // Generates between 1 to 6 units of wood
            con.println("You took the short path and collected " + woodCollected + " units of wood.");
        }
        player.setWood(player.getWood() + woodCollected);
    }
}
