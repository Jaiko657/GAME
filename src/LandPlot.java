public class LandPlot extends Square {
    final private Player owner;
    final public int landPlotCost;
    final public int buildingCapacity;

    public LandPlot(String name, int landPlotCost, int buildingCapacity) {
        super(name);

        this.owner = null;
        this.landPlotCost = landPlotCost;
        this.buildingCapacity = buildingCapacity;
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

    }
}
