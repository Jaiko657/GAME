public class LandPlot extends Square {
    private Player owner;
    final public int landPlotCost;
    final public int buildingCapacity;
    public int wormBreederCount;
    public int upgradedWormBreederCount;
    public int smallToiletCount;
    public int largeToiletCount;

    public LandPlot(String name, int landPlotCost, int buildingCapacity) {
        super(name);

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
        if(this.owner != null) {
            if(this.owner == player) {
                System.out.println(this.name + " is owned already by you.");
                //TODO: MAYBE DO UPGRADE
            } else {
                System.out.println(this.name + " is owned already by " + this.owner.name);
            }
            return;
        }
            System.out.println(this.name + " is not owned");
            if(player.getMoney() >= this.landPlotCost) {
                System.out.println("Would you like to buy this land plot?");
                System.out.println("Stats:");
                System.out.println(" - " + this.buildingCapacity + " building spots this square.");
                System.out.println(" - Cost: $" + this.buildingCapacity);
                final boolean wantToBuy = input.getBool("Do you want to buy?");
                if(wantToBuy) {
                        player.setMoney(player.getMoney() - this.landPlotCost);
                        this.owner = player;
                        System.out.println(player.name + " now owns " + this.name);
                }
            } else {
                System.out.println(player.name + " cannot afford Land Plot");
            }
    }



    //TODO: REMOVE THIS METHOD USED IN DEBUGING
    public void setOwner(Player player) {
        this.owner = player;
    }
}
