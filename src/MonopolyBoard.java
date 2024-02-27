import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MonopolyBoard extends JFrame {
    private Map<String, JPanel> boardSections;

    private class SquarePanel extends JPanel {
        public static int currentSquareIndex = 0;
        //board is drew in order right to left top to bottom so
        // top 6 inorder, then bottom 6 reverse, then left 4 reverse, then right 4 inorder
        private static int[] guiToBoardTranslationIndexs = {0, 1, 2, 3, 4, 5, 15, 14, 13, 12, 11, 10, 19, 18, 17, 16, 6, 7, 8, 9};
        Square underlyingSquare;
        public SquarePanel(Square[] board) {
            super();
            this.underlyingSquare = board[guiToBoardTranslationIndexs[currentSquareIndex++]];
            add(new JLabel(this.underlyingSquare.name));
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
    public MonopolyBoard(Square[] board) {
        setTitle("Custom Monopoly Board");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 900);
        setResizable(false);
        setLayout(new BorderLayout());

        createBoardSections(board);

        setLocationRelativeTo(null); //centers game
        setVisible(true);
    }

    private void createBoardSections(Square[] board) {
        this.boardSections = new HashMap<>();

        int TOP_BOTTOM_LENGTH = 6;
        int SIDE_LENGTH = 4;
        boardSections.put("North", createEdgePanel(TOP_BOTTOM_LENGTH, board));
        boardSections.put("South", createEdgePanel(TOP_BOTTOM_LENGTH, board));

        JPanel leftPanel = createEdgePanel(SIDE_LENGTH, board);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        boardSections.put("West", leftPanel);

        JPanel rightPanel = createEdgePanel(SIDE_LENGTH, board);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        boardSections.put("East", rightPanel);

        boardSections.put("Center", createCenterPanel());

        add(boardSections.get("North"), BorderLayout.NORTH);
        add(boardSections.get("East"), BorderLayout.EAST);
        add(boardSections.get("South"), BorderLayout.SOUTH);
        add(boardSections.get("West"), BorderLayout.WEST);
        add(boardSections.get("Center"), BorderLayout.CENTER);
    }

    private JPanel createEdgePanel(int length, Square[] board) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, length));
        for (int i = 0; i < length; i++) {
            SquarePanel square = new SquarePanel(board);
            square.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            square.setBackground(Color.WHITE);
            panel.add(square);
        }
        return panel;
    }

    private JPanel createCenterPanel() {
        var centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        JTextArea textArea = new JTextArea("Monopoly Game Board");
        textArea.setEditable(true);
        centerPanel.add(textArea, BorderLayout.CENTER);
        return centerPanel;
    }
}