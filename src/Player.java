public class Player {
    public final int id;
    public final String name;
    private int currentPosition;

    private int money;
    private int wood;
    private int worms;

    static private int nextId;

    final private PlayerStatsGUI statsGUI;

    public Player(String name, MonopolyBoard monopolyBoard) {
        this.id = nextId++;
        this.name = name;
        this.currentPosition = 0;
        //TODO: CHOSE STARTING MONEY
        this.money = 5000;
        this.wood = 2000;
        this.worms = 250;
        this.statsGUI = new PlayerStatsGUI(this, monopolyBoard);
        this.statsGUI.setVisible(true);
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name +
                ", currentPosition='" + currentPosition +
                '}';
    }

    public int getCurrentPosition() {
        return this.currentPosition;
    }

    public void setCurrentPosition(int newPos) {
        this.currentPosition = newPos;
        this.statsGUI.refreshDisplay();
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
        statsGUI.refreshDisplay();
    }

    public int getWood() {
        return wood;
    }

    public void setWood(int wood) {
        this.wood = wood;
        statsGUI.refreshDisplay();
    }

    public int getWorms() {
        return worms;
    }

    public void setWorms(int worms) {
        this.worms = worms;
        statsGUI.refreshDisplay();
    }
}