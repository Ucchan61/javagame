/*
 * �쐬��: 2004/12/17
 *
 */
import java.awt.*;
import javax.swing.*;
/**
 * �I�Z���A�v���P�[�V�����ł��B
 * @author mori
 *
 */
public class Othello extends JFrame {
    public Othello() {
        // �^�C�g����ݒ�
        setTitle("�΂��Ђ�����Ԃ�");
        // �T�C�Y�ύX���ł��Ȃ�����
        setResizable(false);
        // ���C���p�l�����쐬���ăt���[���ɒǉ�
        MainPanel panel = new MainPanel();
        Container contentPane = getContentPane();
        contentPane.add(panel);

        // �p�l���T�C�Y�ɍ��킹�ăt���[���T�C�Y�������ݒ�
        pack();
    }

    public static void main(String[] args) {
        Othello frame = new Othello();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
