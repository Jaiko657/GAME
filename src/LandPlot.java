public class LandPlot extends Square {
    private Player owner;
    final public int laborCost;
    final public int woodCost;
    final public int wormCost;
    final public BuildingType buildingType;
    private Building building;

    public LandPlot(String name, RenderObject renderObject, BuildingType buildingType) {
        super(name, renderObject);
        if(buildingType == null) {
            throw new RuntimeException("Cant have Null BuildingType");
        }
        if(buildingType == BuildingType.WORM_BREEDER) {
            this.laborCost = PRICES.WORM_BREEDER_LABORCOST;
            this.woodCost = PRICES.WORM_BREEDER_WOODCOST;
            this.wormCost = PRICES.WORM_BREEDER_WORMCOST;
        } else {
            this.laborCost = PRICES.TOILET_LABORCOST;
            this.woodCost = PRICES.TOILET_WOODCOST;
            this.wormCost = PRICES.TOILET_WORMCOST;
        }
        this.buildingType = buildingType;
        this.building = null;
    }
    public boolean getHasBuilding() {
        return building != null;
    }

    public void buildBuilding() {
        this.building = new Building(buildingType);
        renderObject.update();
    }

    public Building getBuilding() {
        return this.building;
    }
    //TODO: Way to give away ownership of plot
    public void setOwner(Player owner) {
        this.owner = owner;
        renderObject.update();
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
        this.renderObject.update();
        var con = input.getCon();
        if(this.owner != null) {
            if(this.owner == player) {
                con.println(this.name + " is owned by you.");
                //TODO: MAYBE
            } else {
                con.println(this.name + " is owned already by " + this.owner.name);
                var donate = input.getBool("To help this community project would you like to donate anything the the owner of this plot");
                if(donate) {
                    con.println("What would you like to donate");
                    con.println("1: Money");
                    con.println("2: Wood");
                    con.println("3: Worms");

                    boolean validChoice = false;
                    int donationChoice = -1;
                    while(!validChoice) {
                        donationChoice = input.getInt("Choice (1-3)");
                        if(donationChoice < 1 || donationChoice > 3) {
                            con.println("Invalid Choice");
                            continue;
                        }
                        validChoice = true;
                    }
                    int donationAmount = -1;
                    con.print(player.name + " donates ");
                    switch(donationChoice) {
                        case 1:
                            donationAmount = Integer.min(player.getMoney(), 750);
                            con.print(donationAmount + " Money ");
                            this.owner.setMoney(donationAmount);
                            break;
                        case 2:
                            donationAmount = Integer.min(player.getWood(), 400);
                            con.print(donationAmount + " Wood ");
                            this.owner.setWood(donationAmount);
                            break;
                        case 3:
                            donationAmount = Integer.min(player.getWorms(), 20);
                            con.print(donationAmount + " Worms ");
                            this.owner.setWorms(donationAmount);
                            break;
                    }
                    con.println(" to " + this.owner.name);
                }
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
                con.println("It can have a worm breeder to help you build more toilets!");
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
            int invalidIndex = -1; // Use -1 to indicate no invalid index initially
            for(int i = 0; i < players.size(); i++) {
                var pl = players.get(i);
                if(pl == player) {
                    invalidIndex = i; // Store the index of the player to exclude
                    continue;
                }
                con.println((i + 1) + ": " + pl.name); // Display player names, starting from 1
            }
            con.println("0: Nobody Takes Control");

            boolean validChoice = false;
            int ownershipIndex = -1;
            while(!validChoice) {
                ownershipIndex = input.getInt("Who Wants to take ownership") - 1; // Adjust for 0-based index
                if(ownershipIndex == -2) {
                    con.println("No one wants the Land Plot");
                    return;
                }
                if(ownershipIndex == invalidIndex) {
                    con.println("Invalid Choice, this player cannot take ownership.");
                    continue;
                }
                if(ownershipIndex >= 0 && ownershipIndex < players.size() && ownershipIndex != invalidIndex) {
                    validChoice = true;
                } else {
                    con.println("Invalid Choice");
                }
            }
            var newOwner = players.get(ownershipIndex);
            con.println("\nNew Owner is " + newOwner.name);
            setOwner(newOwner);
        }
    }
//
//    private void sellComposite(Player player, Input input) {
//        //TODO: Method should allow player to exchange the composite from toilet to farmers in exchange for money
//        var con = input.getCon();
//        //gets and emptys content of compost
//        if(building == null) {
//            return;
//        }
//        int compostAmount = building.takeContent();
//
//        // Check if there is compost to sell
//        if (compostAmount > 0) {
//            int pricePerUnit = (int) Math.random()*4 +1; // rand between 1 - 5
//
//            // Calculate the total sale amount
//            int totalSaleAmount = compostAmount * pricePerUnit;
//            // Update player's money
//            player.setMoney(player.getMoney() + totalSaleAmount);
//
//            // Inform the player
//            //TODO: search for currency units and replacec
//            con.println("You sold " + compostAmount + " units of compost to farmers for " + totalSaleAmount + " currency units.");
//        } else {
//            // Inform the player if there's no compost to sell
//            con.println("There is no compost available to sell.");
//        }
//    }
}
