import java.awt.Container;

import javax.swing.JFrame;

/*
 * Created on 2005/12/10
 *
 */

/**
 * @author mori
 *
 */
public class SoundEngineTest extends JFrame {
    public SoundEngineTest() {
        // �^�C�g����ݒ�
        setTitle("�T�E���h�G���W���e�X�g");
        // �T�C�Y�ύX�s��
        setResizable(false);

        // ���C���p�l�����쐬���ăt���[���ɒǉ�
        MainPanel panel = new MainPanel();
        Container contentPane = getContentPane();
        contentPane.add(panel);

        // �p�l���T�C�Y�ɍ��킹�ăt���[���T�C�Y�������ݒ�
        pack();
    }

    public static void main(String[] args) {
        SoundEngineTest frame = new SoundEngineTest();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
