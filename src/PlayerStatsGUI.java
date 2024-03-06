import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerStatsGUI extends JFrame {
    private Player player;
    private JLabel nameLabel, moneyLabel, woodLabel, wormsLabel;
    private Font statsFont = new Font("Arial", Font.BOLD, 14);
    private Color backgroundColor;
    private Color textColor = new Color(255, 255, 255);

    public PlayerStatsGUI(Player player, MonopolyBoard monopolyBoard) {
        this.player = player;
        this.backgroundColor = player.color;
        this.textColor =  new Color(
                255 - this.backgroundColor.getRed(),
                255 - this.backgroundColor.getGreen(),
                255 - this.backgroundColor.getBlue());
        initComponents(monopolyBoard);
        styleComponents();
    }

    private void initComponents(MonopolyBoard monopolyBoard) {
        setUndecorated(true);
        setType(Type.UTILITY);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        // Get the location and dimensions of the monopolyBoard frame
        Point monopolyBoardLocation = monopolyBoard.getLocation();
        int width, depth, newX, newY;
        switch(this.player.id) {
            case 0:
                newX = monopolyBoardLocation.x - this.getWidth();
                newY = monopolyBoardLocation.y;
                setLocation(newX, newY);
                break;
            case 1:
                newX = monopolyBoardLocation.x + monopolyBoard.getWidth();
                newY = monopolyBoardLocation.y;
                setLocation(newX, newY);
                break;
            case 2:
                newX = monopolyBoardLocation.x - this.getWidth();
                newY = monopolyBoardLocation.y + monopolyBoard.getHeight() - getHeight();
                setLocation(newX, newY);
                break;
            case 3:
                newX = monopolyBoardLocation.x + monopolyBoard.getWidth();
                newY = monopolyBoardLocation.y + monopolyBoard.getHeight() - getHeight();
                setLocation(newX, newY);
                break;
            default:
                throw new RuntimeException("TODO");
        }

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(backgroundColor);
        add(panel);

        nameLabel = new JLabel("Name: " + player.name);
        moneyLabel = new JLabel("Money: $" + player.getMoney());
        woodLabel = new JLabel("Wood: " + player.getWood() + " units");
        wormsLabel = new JLabel("Worms: " + player.getWorms() + " units");

        panel.add(Box.createVerticalStrut(10)); // Add some space at the top
        panel.add(nameLabel);
        panel.add(moneyLabel);
        panel.add(woodLabel);
        panel.add(wormsLabel);
/*
        panel.add(Box.createVerticalGlue()); // Add space between labels and button
        panel.add(refreshButton);
*/
    }

    private void styleComponents() {
        // Apply Fonts to labels
        for (JLabel label : new JLabel[]{nameLabel, moneyLabel, woodLabel, wormsLabel}) {
            label.setFont(statsFont);
        }
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
    }

    public void refreshDisplay() {
        nameLabel.setText("Name: " + player.name);
        moneyLabel.setText("Money: $" + player.getMoney());
        woodLabel.setText("Wood: " + player.getWood() + " units");
        wormsLabel.setText("Worms: " + player.getWorms() + " units");
    }
}