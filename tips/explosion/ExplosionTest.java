import java.awt.Container;

import javax.swing.JFrame;

/*
 * Created on 2006/01/29
 */

/**
 * @author mori
 */
public class ExplosionTest extends JFrame {
    public ExplosionTest() {
        // �^�C�g����ݒ�
        setTitle("�����G�t�F�N�g");
        setResizable(false);

        // ���C���p�l�����쐬���ăt���[���ɒǉ�
        MainPanel panel = new MainPanel();
        Container contentPane = getContentPane();
        contentPane.add(panel);

        // �p�l���T�C�Y�ɍ��킹�ăt���[���T�C�Y�������ݒ�
        pack();
    }

    public static void main(String[] args) {
        ExplosionTest frame = new ExplosionTest();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}