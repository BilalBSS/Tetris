import javax.swing.*;

public class TetrisFrame extends JFrame {
    public static final int WIDTH = 156;    //10 blocks wide (10 * 15 = 150 + 6 from window)
    public static final int HEIGHT = 628;   //40 blocks high (40 * 15 = 600 + 28 from window)

    TetrisPanel panel = new TetrisPanel();

    TetrisFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setVisible(true);
        add(panel);
    }
}
