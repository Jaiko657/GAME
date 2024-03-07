public class WoodGatheringCorner extends CornerSquare {
    public WoodGatheringCorner(RenderObject renderObject) {
        super("Forest", renderObject);
    }

    @Override
    public void landOnSquare(Player player, Input input) {
        var con = input.getCon();
        con.println("You've entered a local forest to gather wood. Choose your path: 'short' for a quick but less rewarding route, or 'long' for a challenging but potentially more rewarding journey.");
        con.println("1. Short");
        con.println("2. Long");
        var choice = 1;
        while(true) {
            choice = input.getInt("Choice");
            if(choice == 1 || choice == 2) {
                break;
            }
            con.println("Invalid Choice Please type 1 or 2");
        }
        int woodCollected;
        if (choice == 1) {
            woodCollected = (int) (Math.random() * 15 + 5); // Generates between 5 to 20 units of wood
            con.println("You took the challenging path and collected " + woodCollected + " units of wood!");
        } else {
            woodCollected = (int) (Math.random() * 5 + 1); // Generates between 1 to 6 units of wood
            con.println("You took the short path and collected " + woodCollected + " units of wood.");
        }
        player.setWood(player.getWood() + woodCollected);
        input.getString("Press Enter to finish turn");
    }
}
