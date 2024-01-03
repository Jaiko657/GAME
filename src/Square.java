public abstract class Square {
    public final int id;
    public final String name;

    private static int nextId;

    public Square(String name) {
        this.id = nextId++;
        this.name = name;
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
