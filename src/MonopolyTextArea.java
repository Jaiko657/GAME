import java.awt.BorderLayout;
import java.io.PrintStream;
import javax.swing.*;

public class MonopolyTextArea extends JPanel {

    public MonopolyTextArea() {
        setLayout(new BorderLayout());
        JTextArea textArea = new JTextArea(15, 30);
        // Directly add the JTextArea without JScrollPane
        add(textArea, BorderLayout.CENTER);
        TextAreaOutputStream taOutputStream = new TextAreaOutputStream(textArea);
        System.setOut(new PrintStream(taOutputStream));
    }
//    public MonopolyTextArea() {
//        setLayout(new BorderLayout());
//        JTextArea textArea = new JTextArea(15, 30);
//        add(new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
//                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
//        TextAreaOutputStream taOutputStream = new TextAreaOutputStream(
//                textArea);
//        System.setOut(new PrintStream(taOutputStream));
//    }
}
