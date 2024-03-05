import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.text.*;

public class CustomConsolePanel extends JPanel implements KeyListener, CaretListener {
    private JTextPane textPane;
    private boolean bsDisabled = false;
    private int charCount = 0;
    private int lastLength = 0;

    public CustomConsolePanel() {
        this.setLayout(new BorderLayout());
        textPane = new JTextPane();
        textPane.addKeyListener(this);
        textPane.addCaretListener(this);
        JScrollPane scrollPane = new JScrollPane(textPane);
        this.add(scrollPane, BorderLayout.CENTER);
        textPane.getCaret().setVisible(true);
        enableBackspace(true);
    }

    private void enableBackspace(boolean enable) {
        if (enable) {
            this.textPane.getInputMap().put(KeyStroke.getKeyStroke("BACK_SPACE"), (Object)null);
            this.bsDisabled = false;
        } else {
            this.textPane.getInputMap().put(KeyStroke.getKeyStroke("BACK_SPACE"), "none");
            this.bsDisabled = true;
        }
    }


    public synchronized void clear() {
        this.textPane.setText("");
        this.notifyAll();
    }

    public synchronized void print(Object obj) {
        if (obj != null) {
            String str = obj.toString();
            StyledDocument doc = this.textPane.getStyledDocument();
            try {
                doc.insertString(doc.getLength(), str, null);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
        this.notifyAll();
    }

    public synchronized void println(Object obj) {
        if (obj != null) {
            print(obj.toString() + "\n");
        } else {
            print("\n");
        }
        this.notifyAll();
    }

    public synchronized void println() {
        print("\n");
    }

    public synchronized void print(String str) {
        print((Object) str);
    }

    public synchronized void println(String str) {
        println((Object) str);
    }

    public void keyPressed(KeyEvent evt) {
    }

    public void keyReleased(KeyEvent evt) {
    }

    public synchronized void keyTyped(KeyEvent evt) {
        char ch = evt.getKeyChar();
        if (ch != '\n' && ch != '\r') {
            if (ch == '\b') {
                if (!this.bsDisabled) {
                    --this.charCount;
                    if (this.charCount == 0) {
                        this.enableBackspace(false);
                    }
                }
            } else {
                if (this.bsDisabled) {
                    this.enableBackspace(true);
                }
                ++this.charCount;
                if (this.charCount == 1) {
                    this.enableBackspace(true);
                }
            }
        } else {
            this.lastLength = this.charCount;
            this.charCount = 0;
            this.notifyAll();
        }
    }

    public void caretUpdate(CaretEvent evt) {
        StyledDocument doc = this.textPane.getStyledDocument();
        int len = doc.getLength();
        int pos = this.textPane.getCaretPosition();
        if (len != pos) {
            this.textPane.setCaretPosition(len);
        }
    }

    // Additional methods to mimic Console functionality can be added here.
}
