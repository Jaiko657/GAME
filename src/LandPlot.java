public class LandPlot extends Square {
    private Player owner;
    final public int laborCost;
    final public int woodCost;
    final public int wormCost;
    final public BuildingType buildingType;
    private Building building;

    public LandPlot(String name, RenderObject renderObject, BuildingType buildingType) {
        super(name, renderObject);
        switch(buildingType) {
            case WORM_BREEDER -> {
                this.laborCost = PRICES.WORM_BREEDER_LABORCOST;
                this.woodCost = PRICES.WORM_BREEDER_WOODCOST;
                this.wormCost = PRICES.WORM_BREEDER_WORMCOST;
            }
            case TOILET -> {
                this.laborCost = PRICES.TOILET_LABORCOST;
                this.woodCost = PRICES.TOILET_WOODCOST;
                this.wormCost = PRICES.TOILET_WORMCOST;
            }
            default -> {
                throw new RuntimeException("Invalid Building Type");
            }
        }
        this.buildingType = buildingType;
        this.building = null;
    }
    public boolean getHasBuilding() {
        return building != null;
    }

    public void buildBuilding() {
        this.building = new Building(buildingType);
    }

    //TODO: Way to give away ownership of plot
    public Player getOwner() {
        return this.owner;
    }
    public void tick() {
        if(building == null) return;
        building.tick();
    }

    @Override
    public void landOnSquare(Player player, Input input) {
        if(this.renderObject.isActive()) {
            this.renderObject.update();
        }
        var con = input.getCon();
        if(this.owner != null) {
            if(this.owner == player) {
                con.println(this.name + " is owned by you.");
                sellComposite(player, input);
                //TODO: MAYBE DO UPGRADE
            } else {
                con.println(this.name + " is owned already by " + this.owner.name);
                //TODO: SELLING OWNERSHIP OF PLOT
            }
            return;
        }
        con.println(this.name + " is not owned");

        con.println("\nWould you like to take ownership this community land plot?");
        switch(this.buildingType) {
            case TOILET:
                con.println("It can have a Toilet for the community built on it!");
                break;
            case WORM_BREEDER:
                con.println("It can have a worm breeder to help you build more toilets built on it!");
                break;
//                case TOILET_OR_BREEDER:
//                    con.println("It can have a Toilet for the community built on it or a worm breeder to help you build more toilets built on it!");
//                    break;
        }
        var ownershipChoice = input.getBool("Do you want to take ownership?");
        if(ownershipChoice) {
            this.owner = player;
            con.println(player.name + " now owns " + this.name);
            renderObject.update();
        } else {
            con.println("You don't want to take control of Land Plot. Next Players Turn!!!");
        }
    }

    private boolean canPlayerAfford(Player player) {
        return (
            player.getMoney() >= this.laborCost &&
            player.getWood() >= this.woodCost &&
            player.getWorms() >= this.wormCost
        );
    }
    private void sellComposite(Player player, Input input) {
        //TODO: Method should allow player to exchange the composite from toilet to farmers in exchange for money
        var con = input.getCon();
        // Assume there is a method to get the total compost amount stored in the building
        int compostAmount = building.getAmountStored();

        // Check if there is compost to sell
        if (compostAmount > 0) {
            // Assuming a fixed price per unit for simplicity. Could be dynamic based on game conditions.
            int pricePerUnit = 5; // This price can be adjusted as needed

            // Calculate the total sale amount
            int totalSaleAmount = compostAmount * pricePerUnit;

            // Update player's money
            player.setMoney(player.getMoney() + totalSaleAmount);

            // Reset the compost storage in the building to 0 after selling
            building.empty();

            // Inform the player
            con.println("You sold " + compostAmount + " units of compost to farmers for " + totalSaleAmount + " currency units.");
        } else {
            // Inform the player if there's no compost to sell
            con.println("There is no compost available to sell.");
        }

        // Wait for the player to acknowledge before proceeding
        input.getString("Press Enter to finish.");
    }
}
