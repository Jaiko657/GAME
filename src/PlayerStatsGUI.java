import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerStatsGUI extends JFrame {
    private Player player;
    private JLabel nameLabel, moneyLabel, woodLabel, wormsLabel;
    private Font statsFont = new Font("Arial", Font.BOLD, 14);
    private Color backgroundColor = new Color(33, 37, 241);
    private Color textColor = new Color(255, 255, 255);

    public PlayerStatsGUI(Player player, MonopolyBoard monopolyBoard) {
        this.player = player;
        initComponents(monopolyBoard);
        styleComponents();
    }

    private void initComponents(MonopolyBoard monopolyBoard) {
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
        moneyLabel = new JLabel("Money: $" + player.money);
        woodLabel = new JLabel("Wood: " + player.wood + " units");
        wormsLabel = new JLabel("Worms: " + player.worms + " units");

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshDisplay();
            }
        });

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
        // Apply styles to labels
        for (JLabel label : new JLabel[]{nameLabel, moneyLabel, woodLabel, wormsLabel}) {
            label.setForeground(textColor);
            label.setFont(statsFont);
        }

        // Style the refresh button
/*
        JButton refreshButton = (JButton) getContentPane().getComponent(getContentPane().getComponentCount() - 1);
        refreshButton.setFont(statsFont);
        refreshButton.setBackground(new Color(0, 123, 255));
        refreshButton.setForeground(textColor);
*/
    }

    public void refreshDisplay() {
        nameLabel.setText("Name: " + player.name);
        moneyLabel.setText("Money: $" + player.money);
        woodLabel.setText("Wood: " + player.wood + " units");
        wormsLabel.setText("Worms: " + player.worms + " units");
    }
}