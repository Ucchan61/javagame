/*
 * Created on 2005/01/03
 *
 */
import java.awt.*;
import javax.swing.*;
/**
 * @author mori
 *
 */
public class KeyTest extends JFrame {
    public KeyTest() {
        // �^�C�g����ݒ�
        setTitle("�L�[�{�[�h�e�X�g");

        // ���C���p�l�����쐬���ăt���[���ɒǉ�
        MainPanel panel = new MainPanel();
        Container contentPane = getContentPane();
        contentPane.add(panel);

        // �p�l���T�C�Y�ɍ��킹�ăt���[���T�C�Y�������ݒ�
        pack();
    }

    public static void main(String[] args) {
        KeyTest frame = new KeyTest();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
