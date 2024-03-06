import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

public class MonopolyBoard extends JFrame {
    private final Game game;
    private Map<String, JPanel> boardSections;

    private class SquarePanel extends JPanel {
        //board is drew in order right to left top to bottom so
        // top 6 inorder, then bottom 6 reverse, then left 4 reverse, then right 4 inorder
        private static final int[] guiToBoardTranslationIndexes = {0, 1, 2, 3, 4, 5, 15, 14, 13, 12, 11, 10, 19, 18, 17, 16, 6, 7, 8, 9};
        Square underlyingSquare;
        public SquarePanel(Game game, int boardIndex) {
            super();
            //TODO: New line for each player
            var board = game.getBoard();
            this.underlyingSquare = board[guiToBoardTranslationIndexes[boardIndex]];
            add(new JLabel(this.underlyingSquare.name));

            var players = game.getPlayers();
            for(int i = 0; i < players.size(); i++) {
                var player = players.get(i);
                if(player.getCurrentPosition() == guiToBoardTranslationIndexes[boardIndex]) {
                    var playerLabel = new JLabel(player.name);
                    playerLabel.setForeground(player.color);

                    if(underlyingSquare instanceof LandPlot) {
                        if(((LandPlot) this.underlyingSquare).getOwner() == player) {
                            playerLabel.setForeground(new Color(
                                    255 - player.color.getRed(),
                                    255 - player.color.getGreen(),
                                    255 - player.color.getBlue()));
                        }
                    }
                    add(playerLabel);
                }
            }
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if(underlyingSquare instanceof ChallengeSquare) {
                g.setColor(Color.RED);
            } else {
                g.setColor(Color.GREEN);
                var owner = ((LandPlot) underlyingSquare).getOwner();
                if(owner != null) {
                    g.setColor(owner.color);
                }
            }
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        @Override
        public Dimension getPreferredSize() {
            // Size of the square side in pixels
            int SQUARE_SIZE = 150;
            return new Dimension(SQUARE_SIZE, SQUARE_SIZE);
        }
    }
    public MonopolyBoard(Game game) {
        this.game = game;
        setTitle("Custom Monopoly Board");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 900);
        setResizable(false);
        setLayout(new BorderLayout());

        createBoardSections(game);

        setLocationRelativeTo(null); //centers game
        setVisible(true);
        game.con.setSize(590,570);
        Point gameLocation = this.getLocation();
        game.con.setLocation(gameLocation.x+155, gameLocation.y+180);
        game.con.setUndecorated(true);
        game.con.setType(Type.UTILITY);
        game.con.setBgColour(Color.GREEN);
        game.con.setFont(new Font("Arial", Font.PLAIN, 30));
        game.con.setVisible(true);
        game.con.gainFocus();

        // Listener to toggle alwaysOnTop for Console and Stats based on focus of monopoly game
        WindowAdapter focusAdapter = new WindowAdapter() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                game.con.setAlwaysOnTop(true);
                game.getPlayers().forEach(player -> player.getStatsGUI().setAlwaysOnTop(true));
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                game.con.setAlwaysOnTop(false);
                game.getPlayers().forEach(player -> player.getStatsGUI().setAlwaysOnTop(false));
            }
        };

        // Adding the same listener to gameboard frames
        this.addWindowFocusListener(focusAdapter);

        game.con.requestFocus();
    }

    private void createBoardSections(Game game) {
        // Initialize boardSections if null or clear existing entries
        if (this.boardSections == null) {
            this.boardSections = new HashMap<>();
        } else {
            // Remove old panels from the GUI
            for (JPanel panel : boardSections.values()) {
                remove(panel);
            }
            // Clear existing entries to start fresh
            boardSections.clear();
        }

        int TOP_BOTTOM_LENGTH = 6;
        int SIDE_LENGTH = 4;
        boardSections.put("North", createEdgePanel(TOP_BOTTOM_LENGTH, game, "North"));
        boardSections.put("South", createEdgePanel(TOP_BOTTOM_LENGTH, game, "South"));

        JPanel leftPanel = createEdgePanel(SIDE_LENGTH, game, "West");
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        boardSections.put("West", leftPanel);

        JPanel rightPanel = createEdgePanel(SIDE_LENGTH, game, "East");
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        boardSections.put("East", rightPanel);


        // Add new panels to the GUI
        add(boardSections.get("North"), BorderLayout.NORTH);
        add(boardSections.get("East"), BorderLayout.EAST);
        add(boardSections.get("South"), BorderLayout.SOUTH);
        add(boardSections.get("West"), BorderLayout.WEST);

        // Refresh the GUI to show the new panels
        validate();
        repaint();
    }
    private JPanel createEdgePanel(int length, Game game, String side) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, length));
        Integer sideIndex = null;
        switch(side) {
            case "North":
                sideIndex = 0;
                break;
            case "South":
                sideIndex = 6;
                break;
            case "West":
                sideIndex = 12;
                break;
            case "East":
                sideIndex = 16;
                break;
            default:
                //TODO: Could replace string side with enum but cba this error will never happen
                throw new RuntimeException("CHECK SIDE PROVIDED TO METHOD");
        }
        for (int i = 0; i < length; i++) {
            SquarePanel square = new SquarePanel(game, sideIndex+i);
            square.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            square.setBackground(Color.WHITE);
            panel.add(square);
        }
        return panel;
    }

    public void refreshDisplay() {
        createBoardSections(this.game);
    }
}