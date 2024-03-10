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

    public void buildBuilding(Input input) {
        var con = input.getCon();
        if(canPlayerAfford(owner)) {
            con.println(owner.name + " can afford to Build " + buildingType);
            if(input.getBool("Do You Want to Build?")) {
                owner.setMoney(owner.getMoney() - this.laborCost);
                owner.setWood(owner.getWood() - this.woodCost);
                owner.setWorms(owner.getWorms() - this.wormCost);
                con.println("You have now built " + buildingType + " on " + this.name);
                con.println("\tLabor Cost: " + laborCost + ", Wood Needed: " + woodCost + ", Worms Needed: " + wormCost);

                this.building = new Building(buildingType);
                renderObject.update();
            }
        }
    }
    //TODO: REMOVE AFTER DEBUG OVER
    public void forceBuildBuilding() {
        this.building = new Building(buildingType);
    }

    public Building getBuilding() {
        return this.building;
    }
    //TODO: Way to give away ownership of plot
    public void setOwner(Player owner) {
        this.owner = owner;
        if(renderObject.isActive()) {
            renderObject.update();
        }
    }
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
        }
        var ownershipChoice = input.getBool("Do you want to take ownership?");
        if(ownershipChoice) {
            this.owner = player;
            con.println(player.name + " now owns " + this.name);
            renderObject.update();
        } else {
            con.println("You don't want to take control of Land Plot.");
            con.println("\nWould Anyone Else Like to take control of the Plot");
            var players = renderObject.getPlayers();
            int invalidIndex = 0;
            for(int i = 0; i < players.size(); i++) {
                var pl = players.get(i);
                if(pl == player) {
                    invalidIndex = i;
                    continue;
                }
                con.println((i+1) + ": " + pl.name);
            }
            con.println("0: Nobody Takes Control");
            boolean validChoice = false;
            int ownershipIndex = 0;
            while(!validChoice) {
                ownershipIndex = input.getInt("Who Wants to take ownership");
                if(ownershipIndex == 0) {
                    con.println("No one wants Land Plot");
                    return;
                }
                if(ownershipIndex != invalidIndex) {
                    con.println("Invalid Choice");
                    continue;
                }
                if(ownershipIndex > 0 && ownershipIndex <= players.size()) {
                    validChoice = true;
                    ownershipIndex--;
                }

            }
            var newOwner = players.get(ownershipIndex);
            con.println("\nNew Owner is " + newOwner.name);
            setOwner(newOwner);
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
        //gets and emptys content of compost
        int compostAmount = building.takeContent();

        // Check if there is compost to sell
        if (compostAmount > 0) {
            int pricePerUnit = (int) Math.random()*4 +1; // rand between 1 - 5

            // Calculate the total sale amount
            int totalSaleAmount = compostAmount * pricePerUnit;
            // Update player's money
            player.setMoney(player.getMoney() + totalSaleAmount);

            // Inform the player
            con.println("You sold " + compostAmount + " units of compost to farmers for " + totalSaleAmount + " currency units.");
        } else {
            // Inform the player if there's no compost to sell
            con.println("There is no compost available to sell.");
        }
    }
}
