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
public class BreadCrumb extends JFrame {
    public BreadCrumb() {
        // �^�C�g����ݒ�
        setTitle("�u���b�h�N�����o�H�T��");

        // ���C���p�l�����쐬���ăt���[���ɒǉ�
        MainPanel panel = new MainPanel();
        Container contentPane = getContentPane();
        contentPane.add(panel);

        // �p�l���T�C�Y�ɍ��킹�ăt���[���T�C�Y�������ݒ�
        pack();
    }

    public static void main(String[] args) {
        BreadCrumb frame = new BreadCrumb();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
