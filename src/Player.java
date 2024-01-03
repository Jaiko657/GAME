public class Player {
    public final int id;
    public final String name;
    public int currentPosition;

    static private int nextId;

    public Player(String name) {
        this.id = nextId++;
        this.name = name;
        this.currentPosition = 0;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name +
                ", currentPosition='" + currentPosition +
                '}';
    }
}