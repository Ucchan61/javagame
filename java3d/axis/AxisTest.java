import java.awt.Container;

import javax.swing.JFrame;

/*
 * Created on 2006/07/28
 */

public class AxisTest extends JFrame {
    public AxisTest() {
        super("���W��");
        setResizable(false);

        Container c = getContentPane();

        // ���C���p�l�����쐬
        MainPanel mainPanel = new MainPanel();
        c.add(mainPanel);

        pack();
    }

    public static void main(String[] args) {
        AxisTest frame = new AxisTest();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
