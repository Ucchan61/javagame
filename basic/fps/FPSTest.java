import javax.swing.JFrame;

/*
 * Created on 2007/04/26
 */

public class FPSTest extends JFrame {
    public FPSTest() {
        setTitle("FPS�̑���");
        MainPanel panel = new MainPanel();
        getContentPane().add(panel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new FPSTest();
    }
}
