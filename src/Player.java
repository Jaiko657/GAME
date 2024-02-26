public class Player {
    public final int id;
    public final String name;
    public int currentPosition;

    public int money;
    public int wood;
    public int worms;

    static private int nextId;

    final private PlayerStatsGUI statsGUI;

    public Player(String name) {
        this.id = nextId++;
        this.name = name;
        this.currentPosition = 0;
        //TODO: CHOSE STARTING MONEY
        this.money = 5000;
        this.wood = 2000;
        this.worms = 250;
        this.statsGUI = new PlayerStatsGUI(this);
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

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        statsGUI.refreshDisplay();
        this.money = money;
    }

    public int getWood() {
        return wood;
    }

    public void setWood(int wood) {
        statsGUI.refreshDisplay();
        this.wood = wood;
    }

    public int getWorms() {
        return worms;
    }

    public void setWorms(int worms) {
        statsGUI.refreshDisplay();
        this.worms = worms;
    }
}