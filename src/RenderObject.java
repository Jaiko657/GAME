public class RenderObject {
   private MonopolyBoard monopolyBoard;
    public RenderObject() {

    }
    public boolean isActive() {
        return (this.monopolyBoard != null);
    }
    public void setMonopolyBoard(MonopolyBoard monopolyBoard) {
        this.monopolyBoard = monopolyBoard;
    }
    public void update() {
        monopolyBoard.refreshDisplay();
    }
}
