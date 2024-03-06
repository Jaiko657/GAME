import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class Console extends JFrame implements KeyListener, CaretListener {
    private static String version = "Console";
    private JTextPane text;
    private int charCount = 0;
    private int lastLength = 0;
    private boolean bsDisabled;

    public void gainFocus() {
        this.requestFocus();
        this.text.requestFocusInWindow();
    }
    private void init(boolean editable) {
        Container c = this.getContentPane();
        c.setLayout(new BorderLayout());
        this.text = new JTextPane();
        this.text.setFont(new Font("Arial", Font.PLAIN, 20));
        this.text.addKeyListener(this);
        this.text.addCaretListener(this);
        c.add(new JScrollPane(this.text));
        if (!editable) {
            this.text.setEditable(false);
        } else {
            this.text.setEditable(true);
        }

        this.text.getCaret().setVisible(false);
        this.addWindowListener(new Console.WindowHandler());
        this.setSize(500, 500);
    }

    private void enableBackspace(boolean yes) {
        if (yes) {
            this.text.getInputMap().put(KeyStroke.getKeyStroke("BACK_SPACE"), (Object)null);
            this.bsDisabled = false;
        } else {
            this.text.getInputMap().put(KeyStroke.getKeyStroke("BACK_SPACE"), "none");
            this.bsDisabled = true;
        }
    }

    public void caretUpdate(CaretEvent evt) {
        StyledDocument doc = this.text.getStyledDocument();
        int len = doc.getLength();
        int pos = this.text.getCaretPosition();
        if (len != pos) {
            this.text.setCaretPosition(len);
        }

    }

    public Console(boolean editable) {
        super(version);
        this.init(editable);
    }

    public synchronized void clear() {
        this.text.setText((String)null);
        this.notifyAll();
    }

    public synchronized void println(Object obj) {
        if (obj != null && obj instanceof ImageIcon) {
            StyledDocument doc2 = (StyledDocument)this.text.getDocument();
            Style style2 = doc2.addStyle("StyleName", (Style)null);
            StyleConstants.setIcon(style2, (ImageIcon)obj);

            try {
                doc2.insertString(doc2.getLength(), "invisible text", style2);
                this.println();
            } catch (Exception var5) {
            }
        } else {
            this.println(obj.toString());
        }

        this.notifyAll();
    }

    public synchronized void print(Object obj) {
        if (obj != null && obj instanceof ImageIcon) {
            StyledDocument doc2 = (StyledDocument)this.text.getDocument();
            Style style2 = doc2.addStyle("StyleName", (Style)null);
            StyleConstants.setIcon(style2, (ImageIcon)obj);

            try {
                doc2.insertString(doc2.getLength(), "invisible text", style2);
            } catch (Exception var5) {
            }
        } else {
            this.println(obj.toString());
        }

        this.notifyAll();
    }

    public synchronized void println() {
        StyledDocument doc = this.text.getStyledDocument();

        try {
            doc.insertString(doc.getLength(), "\n", doc.getStyle("default"));
        } catch (Exception var3) {
        }

        this.notifyAll();
    }

    public synchronized void print(String str) {
        StyledDocument doc = this.text.getStyledDocument();

        try {
            doc.insertString(doc.getLength(), str, doc.getStyle("default"));
        } catch (Exception var4) {
        }

        this.notifyAll();
    }

    public synchronized void println(String str) {
        StyledDocument doc = this.text.getStyledDocument();

        try {
            doc.insertString(doc.getLength(), str + "\n", doc.getStyle("default"));
        } catch (Exception var4) {
        }

        this.notifyAll();
    }

    public synchronized void setFont(Font ft) {
        if (ft != null) {
            this.text.setFont(ft);
            this.notifyAll();
        }

    }

    public Font getFont() {
        return this.text.getFont();
    }

    public synchronized void setBgColour(Color col) {
        if (col != null) {
            this.text.setBackground(col);
            this.notifyAll();
        }

    }

    public synchronized void setColour(Color col) {
        if (col != null) {
            this.text.setForeground(col);
            this.notifyAll();
        }

    }

    public Color getColour() {
        return this.text.getForeground();
    }

    private void enableInput(boolean value) {
        this.text.setEditable(value);
    }

    public synchronized String readLn() {
        if (!this.text.isEditable()) {
            return null;
        } else {
            StyledDocument doc = this.text.getStyledDocument();
            int len = doc.getLength();
            String str = null;
            this.enableBackspace(false);
            this.text.setCaretPosition(len);

            try {
                this.wait();
                String data = this.text.getText();
                if (this.lastLength == 0) {
                    str = "";
                } else {
                    str = data.substring(data.length() - this.lastLength - 2);
                }
            } catch (Exception var5) {
                System.out.println("My ERROR " + var5);
            }

            this.notifyAll();
            return str.trim();
        }
    }

    public void keyPressed(KeyEvent evt) {
    }

    public void keyReleased(KeyEvent evt) {
    }

    public synchronized void keyTyped(KeyEvent arg0) {
        char ch = arg0.getKeyChar();
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

    private class WindowHandler extends WindowAdapter implements WindowListener {
        public void windowClosing(WindowEvent e) {
            System.exit(1);
        }

        public void windowDeiconified(WindowEvent e) {
        }

        public void windowActivated(WindowEvent e) {
        }

        public void windowDeactivated(WindowEvent e) {
        }
    }
}
