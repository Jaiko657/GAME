public class Player {
    public final int id;
    public final String name;
    public int currentPosition;

    public int money;
    public int wood;
    public int worms;

    static private int nextId;

    public Player(String name) {
        this.id = nextId++;
        this.name = name;
        this.currentPosition = 0;
        this.money = 5000;
        this.wood = 2000;
        this.worms = 250;
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