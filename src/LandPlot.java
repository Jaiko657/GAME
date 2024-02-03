public class LandPlot extends Square {
    private Player owner;
    final public int landPlotCost;
    final public int buildingCapacity;

    public LandPlot(String name, int landPlotCost, int buildingCapacity) {
        super(name);

        this.owner = null;
        this.landPlotCost = landPlotCost;
        this.buildingCapacity = buildingCapacity;
    }
    public Player getOwner() {
        return this.owner;
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
        if(this.owner == null) {
            System.out.println(this.name + " is not owned");
            if(player.money >= this.landPlotCost) {
                System.out.println("Would you like to buy this land plot?");
                System.out.println("Stats:");
                System.out.println(" - " + this.buildingCapacity + " building spots this square.");
                System.out.println(" - Cost: $" + this.buildingCapacity);
                var choice = input.getString("Type YES to purchase");
                if(choice.equals("YES")) {
                        player.money -= this.landPlotCost;
                        this.owner = player;
                        System.out.println(player.name + " now owns " + this.name);
                }
            } else {
                System.out.println(player.name + " cannot afford Land Plot");
            }
        }
    }
}
