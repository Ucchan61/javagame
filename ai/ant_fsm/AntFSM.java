import java.awt.Container;

import javax.swing.JFrame;

/*
 * Created on 2005/04/24
 *
 */

/**
 * @author mori
 *
 */
public class AntFSM extends JFrame {
    public AntFSM() {
        // �^�C�g����ݒ�
        setTitle("�L����ԋ@�B�i�a�̗�j");

        // ���C���p�l�����쐬���ăt���[���ɒǉ�
        MainPanel panel = new MainPanel();
        Container contentPane = getContentPane();
        contentPane.add(panel);

        // �p�l���T�C�Y�ɍ��킹�ăt���[���T�C�Y�������ݒ�
        pack();
    }

    public static void main(String[] args) {
        AntFSM frame = new AntFSM();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
