import java.awt.Container;

import javax.swing.JFrame;

/*
 * Created on 2005/04/23
 *
 */

/**
 * A*�o�H�T���̃T���v���v���O����
 * @author mori
 */
public class AstarTest3 extends JFrame {
    public AstarTest3() {
        // �^�C�g����ݒ�
        setTitle("A*�o�H�T��3");

        // ���C���p�l�����쐬���ăt���[���ɒǉ�
        MainPanel panel = new MainPanel();
        Container contentPane = getContentPane();
        contentPane.add(panel);

        // �p�l���T�C�Y�ɍ��킹�ăt���[���T�C�Y�������ݒ�
        pack();
    }

    public static void main(String[] args) {
        AstarTest3 frame = new AstarTest3();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
