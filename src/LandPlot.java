public class LandPlot extends Square {
    private Player owner;
    final public int laborCost;
    final public int woodCost;
    final public int wormCost;
    final public BuildingType buildingType;

    private boolean hasBuilding;

    public LandPlot(String name, RenderObject renderObject, BuildingType buildingType) {
        super(name, renderObject);
        switch(buildingType) {
            case WORM_BREEDER -> {
                this.laborCost = 500;
                this.woodCost = 500;
                this.wormCost = 50;
            }
            case TOILET -> {
                this.laborCost = 800;
                this.woodCost = 500;
                this.wormCost = 100;
            }
            default -> {
                throw new RuntimeException("Invalid Building Type");
            }
        }
        this.buildingType = buildingType;
    }
    public boolean getHasBuilding() {
        return this.hasBuilding;
    }

    //TODO: Way to give away ownership of plot
    public Player getOwner() {
        return this.owner;
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
        if(canPlayerAfford(player)) {
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
        } else {
            con.println(player.name + " cannot afford Land Plot");
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
        //FOR NOW JUST GIVE MONEY
        player.setMoney(player.getMoney()+ 100);
    }


    //TODO: REMOVE THIS METHOD USED IN DEBUGING
    public void setOwner(Player player) {
        this.owner = player;
    }
}
