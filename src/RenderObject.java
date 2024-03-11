import java.util.ArrayList;

public class RenderObject {
   private MonopolyBoard monopolyBoard;
    public RenderObject() {}
    public boolean isActive() {
        var isActive = (this.monopolyBoard != null);
        return isActive;
    }
    public ArrayList<Player> getPlayers() {
        if(this.monopolyBoard == null) return null;
        return this.monopolyBoard.getPlayers();
    }
    public void setMonopolyBoard(MonopolyBoard monopolyBoard) {
        if(monopolyBoard == null) throw new IllegalArgumentException("Cannot set monopolyBoard as null");
        this.monopolyBoard = monopolyBoard;
    }
    public void update() {
        if(isActive()) {
            monopolyBoard.refreshDisplay();
        }
    }
}
