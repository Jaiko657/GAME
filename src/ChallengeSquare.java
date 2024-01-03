public class ChallengeSquare extends Square {
    public ChallengeSquare(String name) {
        super(name);
    }

    @Override
    public void landOnSquare(Player player, Input input) {
        System.out.println(player.name + " landed on: " + this.name);
        input.getString("Press Enter");
    }
}
