public class MarketDonationCorner extends CornerSquare {
    public MarketDonationCorner(RenderObject renderObject) {
        super("Market", renderObject);
    }

    @Override
    public void landOnSquare(Player player, Input input) {
        var con = input.getCon();
        con.println("A local market is willing to donate resources. Do you approach 'vendors' for small guaranteed donations or 'auction' to potentially receive a large donation?");
        con.println("1. Vendors");
        con.println("2. Auction");
        var choice = 1;
        while(true) {
            choice = input.getInt("Choice");
            if(choice == 1 || choice == 2) {
                break;
            }
            con.println("Invalid Choice Please type 1 or 2");
        }
        int donation;
        if (choice == 1) {
            // Simulate a 50% chance to win big at the auction
            if (Math.random() < 0.5) {
                donation = (int) (Math.random() * 100 + 50); // Wins big, between 50 to 150 units
                con.println("Your bid was successful at the auction! You received a donation of " + donation + " currency units.");
            } else {
                donation = 0; // Loses the bid
                con.println("Unfortunately, your bid at the auction was not successful. You received no donations.");
            }
        } else {
            donation = (int) (Math.random() * 20 + 10); // Guaranteed small donation, between 10 to 30 units
            con.println("Approaching the vendors paid off with a guaranteed donation of " + donation + " currency units.");
        }
        player.setMoney(player.getMoney() + donation);
        input.getString("Press Enter to finish turn");
    }
}
