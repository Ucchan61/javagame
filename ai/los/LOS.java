import java.awt.Container;

import javax.swing.JFrame;

/*
 * Created on 2005/03/15
 *
 */

/**
 * @author mori
 *  
 */
public class LOS extends JFrame {
    public LOS() {
        // �^�C�g����ݒ�
        setTitle("LOS�ǐ�");

        // ���C���p�l�����쐬���ăt���[���ɒǉ�
        MainPanel panel = new MainPanel();
        Container contentPane = getContentPane();
        contentPane.add(panel);

        // �p�l���T�C�Y�ɍ��킹�ăt���[���T�C�Y�������ݒ�
        pack();
    }

    public static void main(String[] args) {
        LOS frame = new LOS();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}