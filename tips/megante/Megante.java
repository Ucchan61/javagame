import java.awt.Container;

import javax.swing.JFrame;

/*
 * Created on 2005/12/03
 *
 */

/**
 * @author mori
 *  
 */
public class Megante extends JFrame {
    public Megante() {
        // �^�C�g����ݒ�
        setTitle("���K���e�I");
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
        Megante frame = new Megante();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}