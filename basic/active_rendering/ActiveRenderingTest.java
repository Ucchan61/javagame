import javax.swing.JFrame;

/*
 * Created on 2006/05/07
 */

public class ActiveRenderingTest extends JFrame {
    public ActiveRenderingTest() {
        setTitle("�A�N�e�B�u�����_�����O");

        // ���C���p�l����ǉ�
        MainPanel panel = new MainPanel();
        getContentPane().add(panel);

        pack();
    }

    public static void main(String[] args) {
        ActiveRenderingTest frame = new ActiveRenderingTest();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
