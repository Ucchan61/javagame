import java.awt.Container;

import javax.swing.JFrame;

/*
 * Created on 2006/04/30
 */

public class Main extends JFrame {
    public Main() {
        // �^�C�g����ݒ�
        setTitle("���^�{�[��");
        // �T�C�Y�ύX�s��
        setResizable(false);

        // �p�l�����쐬
        MainPanel panel = new MainPanel();
        Container contentPane = getContentPane();
        contentPane.add(panel);

        // �p�l���T�C�Y�ɍ��킹�ăt���[���T�C�Y�������ݒ�
        pack();
    }

    public static void main(String[] args) {
        Main frame = new Main();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
