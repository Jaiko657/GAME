public class CornerSquare extends Square {
    public CornerSquare(String name, RenderObject renderObject) {
        super(name, renderObject);
    }

    @Override
    public void landOnSquare(Player player, Input input) {
        throw new RuntimeException("Should not use Challenge Square");
    }
}
