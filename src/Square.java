public abstract class Square {
    public final int id;
    public final String name;
    protected final RenderObject renderObject;

    private static int nextId;

    public Square(String name, RenderObject renderObject) {
        this.id = nextId++;
        this.name = name;
        this.renderObject = renderObject;
    }
    public void landOnSquare(Player player, Input input) {
        throw new RuntimeException("SHOULD BE OVERRIDE");
    }

    @Override
    public String toString() {
        return "Square{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
