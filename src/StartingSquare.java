public class StartingSquare extends CornerSquare {
    private int spareWood = 500;
    public StartingSquare(RenderObject renderObject) {
        super("Starting Square", renderObject);
    }

    @Override
    public void landOnSquare(Player player, Input input) {
        var con = input.getCon();
        if(spareWood <= 0) {
            con.println("You arrive back where you began. Hope you all have enough wood as all the spare wood has been claimed!!!");
            return;
        }
        con.println("You arrive back where you began. Luckily there is some backup materials!\nThere is "+this.spareWood+" wood available how much do you want to take. Remember it is important to work together so if you don't need wood type 0.");
        int choice;
        while(true) {
            choice = input.getInt("Amount of Wood you want to take(less than "+this.spareWood+")");
            if(choice <= this.spareWood && choice >= 0) {
                break;
            }
            con.println("Invalid Amount of wood!!!\n");
        }
        this.spareWood -= choice;
        player.setWood(player.getWood() + choice);
        input.getString("Press Enter to finish turn");
    }
}
