import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerStatsGUI extends JFrame {
    private Player player;
    private JLabel nameLabel, moneyLabel, woodLabel, wormsLabel;
    private Font statsFont = new Font("Arial", Font.BOLD, 14);
    private Color backgroundColor = new Color(33, 37, 41);
    private Color textColor = new Color(255, 255, 255);

    public PlayerStatsGUI(Player player) {
        this.player = player;
        initComponents();
        styleComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 300);
        setLocationRelativeTo(null);

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
        panel.add(Box.createVerticalGlue()); // Add space between labels and button
        panel.add(refreshButton);
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