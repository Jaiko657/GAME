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

    public BuildingType getType() {
        return this.type;
    }
    public int getContent() {
        var ret = this.amountStored;
        return ret;
    }
    public int takeContent() {
        var ret = this.amountStored;
        this.amountStored = 0;
        return ret;
    }
}