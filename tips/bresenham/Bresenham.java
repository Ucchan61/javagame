import java.awt.Container;

import javax.swing.JFrame;

/*
 * Created on 2005/03/30
 *
 */

/**
 * �u���[���n���A���S���Y���Œ�����`�悷��
 * 
 * @author mori
 *
 */
public class Bresenham extends JFrame {
    public Bresenham() {
        // �^�C�g����ݒ�
        setTitle("�u���[���n���A���S���Y��");

        // ���C���p�l�����쐬���ăt���[���ɒǉ�
        MainPanel panel = new MainPanel();
        Container contentPane = getContentPane();
        contentPane.add(panel);

        // �p�l���T�C�Y�ɍ��킹�ăt���[���T�C�Y�������ݒ�
        pack();
    }

    public static void main(String[] args) {
        Bresenham frame = new Bresenham();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
