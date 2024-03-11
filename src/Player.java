import java.awt.*;

public class Player {
    public final int id;
    public final String name;
    public final Color color;
    private int currentPosition;

    private int money;
    private int wood;
    private int worms;

    static private int nextId;

    private PlayerStatsGUI statsGUI;

    private final static Color[] colorArray = {Color.BLUE, Color.RED, Color.MAGENTA, Color.ORANGE};

    public Player(String name, MonopolyBoard monopolyBoard) {
        this.id = nextId++;
        this.color = colorArray[this.id % 4];
        this.name = name;
        this.currentPosition = 0;
        //TODO: CHOSE STARTING MONEY
        this.money = 5000;
        this.wood = 2000;
        this.worms = 90;
        if(monopolyBoard != null) {
            this.statsGUI = new PlayerStatsGUI(this, monopolyBoard);
            this.statsGUI.setVisible(true);
        }
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name +
                ", currentPosition='" + currentPosition +
                '}';
    }

    public PlayerStatsGUI getStatsGUI() {
        return this.statsGUI;
    }
    public int getCurrentPosition() {
        return this.currentPosition;
    }

    public void setCurrentPosition(int newPos) {
        this.currentPosition = newPos;
        refreshStatsGUI();
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
        refreshStatsGUI();
    }

    public int getWood() {
        return wood;
    }

    public void setWood(int wood) {
        this.wood = wood;
        refreshStatsGUI();
    }

    public int getWorms() {
        return worms;
    }

    public void setWorms(int worms) {
        this.worms = worms;
        refreshStatsGUI();
    }

    private void refreshStatsGUI() {
        if(this.statsGUI != null) {
            this.statsGUI.refreshDisplay();
        }
    }
}