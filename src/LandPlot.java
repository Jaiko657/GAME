public class LandPlot extends Square {
    private Player owner;
    final public int landPlotCost;
    final public int buildingCapacity;
    public int wormBreederCount;
    public int upgradedWormBreederCount;
    public int smallToiletCount;
    public int largeToiletCount;

    public LandPlot(String name, RenderObject renderObject, int landPlotCost, int buildingCapacity) {
        super(name, renderObject);

        this.owner = null;
        this.landPlotCost = landPlotCost;
        this.buildingCapacity = buildingCapacity;
        this.wormBreederCount = 0;
        this.upgradedWormBreederCount = 0;
        this.smallToiletCount = 0;
        this.largeToiletCount = 0;
    }
    public Player getOwner() {
        return this.owner;
    }

    public int getBuildingCount() {
        return wormBreederCount + upgradedWormBreederCount + smallToiletCount + largeToiletCount;
    }
    @Override
    public String toString() {
        return "LandPlot{" +
                "owner=" + owner +
                ", landPlotCost=" + landPlotCost +
                ", buildingCapacity=" + buildingCapacity +
                "} " + super.toString();
    }

    @Override
    public void landOnSquare(Player player, Input input) {
        if(this.renderObject.isActive()) {
            this.renderObject.update();
        }
        var con = input.getCon();
        if(this.owner != null) {
            if(this.owner == player) {
                con.println(this.name + " is owned already by you.");
                //TODO: MAYBE DO UPGRADE
            } else {
                con.println(this.name + " is owned already by " + this.owner.name);
            }
            return;
        }
            con.println(this.name + " is not owned");
            if(player.getMoney() >= this.landPlotCost) {
                con.println("Would you like to buy this land plot?");
                con.println("Stats:");
                con.println(" - " + this.buildingCapacity + " building spots this square.");
                con.println(" - Cost: $" + this.buildingCapacity);
                final boolean wantToBuy = input.getBool("Do you want to buy?");
                if(wantToBuy) {
                        player.setMoney(player.getMoney() - this.landPlotCost);
                        this.owner = player;
                        con.println(player.name + " now owns " + this.name);
                        if(this.renderObject.isActive()) {
                            this.renderObject.update();
                        }
                }
            } else {
                con.println(player.name + " cannot afford Land Plot");
            }
    }



    //TODO: REMOVE THIS METHOD USED IN DEBUGING
    public void setOwner(Player player) {
        this.owner = player;
    }
}
