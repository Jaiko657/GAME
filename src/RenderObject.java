import java.util.ArrayList;

public class RenderObject {
   private MonopolyBoard monopolyBoard;
    public RenderObject() {

    }
    public boolean isActive() {
        return (this.monopolyBoard != null);
    }
    public ArrayList<Player> getPlayers() {
        return this.monopolyBoard.getPlayers();
    }
    public void setMonopolyBoard(MonopolyBoard monopolyBoard) {
        this.monopolyBoard = monopolyBoard;
    }
    public void update() {
        monopolyBoard.refreshDisplay();
    }
}
