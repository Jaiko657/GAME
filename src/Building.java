public class Building {
    final private BuildingType type;
    private int amountStored;

    public Building(BuildingType type) {
        this.type = type;
        if(this.type == BuildingType.TOILET) {
            this.amountStored = PRICES.TOILET_WORMCOST;
        } else {
            this.amountStored = PRICES.WORM_BREEDER_WORMCOST;
        }
    }

    public void tick() {
        if(this.type == BuildingType.TOILET) {
            this.amountStored += 10;
        } else {
            this.amountStored += 30;
        }
    }

    public int getAmountStored() {
        return amountStored;
    }
}