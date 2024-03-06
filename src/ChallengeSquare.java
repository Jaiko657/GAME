public class ChallengeSquare extends Square {
    public ChallengeSquare(String name, RenderObject renderObject) {
        super(name, renderObject);

    }

    @Override
    public void landOnSquare(Player player, Input input) {
        var con = input.getCon();
        con.println(player.name + " landed on: " + this.name);
        //TODO: CREATE MINIGAMES
        input.getString("Press Enter to skip");
    }
}
