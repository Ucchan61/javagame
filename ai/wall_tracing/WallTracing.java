import java.awt.Container;

import javax.swing.JFrame;

/*
 * Created on 2005/04/16
 *
 */

/**
 * @author mori
 *
 */
public class WallTracing extends JFrame {
    public WallTracing() {
        // �^�C�g����ݒ�
        setTitle("�E�H�[���g���[�V���O");

        // ���C���p�l�����쐬���ăt���[���ɒǉ�
        MainPanel panel = new MainPanel();
        Container contentPane = getContentPane();
        contentPane.add(panel);

        // �p�l���T�C�Y�ɍ��킹�ăt���[���T�C�Y�������ݒ�
        pack();
    }

    public static void main(String[] args) {
        WallTracing frame = new WallTracing();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
