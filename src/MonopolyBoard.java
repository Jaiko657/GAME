import javax.swing.*;
import java.awt.*;
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
//            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setAlignmentX(Component.CENTER_ALIGNMENT);
            var board = game.getBoard();
            this.underlyingSquare = board[guiToBoardTranslationIndexes[boardIndex]];
            add(new JLabel(this.underlyingSquare.name));

            var players = game.getPlayers();
            for(int i = 0; i < players.size(); i++) {
                var player = players.get(i);
                if(player.getCurrentPosition() == guiToBoardTranslationIndexes[boardIndex]) {
                    add(new JLabel(player.name));
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

        boardSections.put("Center", createCenterPanel());

        // Add new panels to the GUI
        add(boardSections.get("North"), BorderLayout.NORTH);
        add(boardSections.get("East"), BorderLayout.EAST);
        add(boardSections.get("South"), BorderLayout.SOUTH);
        add(boardSections.get("West"), BorderLayout.WEST);
        add(boardSections.get("Center"), BorderLayout.CENTER);

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

    private JPanel createCenterPanel() {
/*
        var centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        JTextArea textArea = new JTextArea("Monopoly Game Board");
        textArea.setEditable(true);
        centerPanel.add(textArea, BorderLayout.CENTER);
*/

//        var centerPanel = new CustomConsolePanel();

        var centerPanel = new MonopolyTextArea();
        return centerPanel;
    }

    public void refreshDisplay() {
        createBoardSections(this.game);
    }
}