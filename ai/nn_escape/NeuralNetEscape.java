import java.awt.Container;

import javax.swing.JFrame;

/*
 * Created on 2005/05/13
 *
 */

/**
 * �j���[�����l�b�g���g���������s���̊w�K
 * @author mori
 *
 */
public class NeuralNetEscape extends JFrame {
    public NeuralNetEscape() {
        // �^�C�g����ݒ�
        setTitle("NN�ɂ�铦���s���̊w�K");
        setResizable(false);

        // ���C���p�l�����쐬���ăt���[���ɒǉ�
        MainPanel panel = new MainPanel();
        Container contentPane = getContentPane();
        contentPane.add(panel);

        // �p�l���T�C�Y�ɍ��킹�ăt���[���T�C�Y�������ݒ�
        pack();
    }

    public static void main(String[] args) {
        NeuralNetEscape frame = new NeuralNetEscape();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
