import java.awt.*;
import javax.swing.*;

public class DrawImage extends JFrame {
    public DrawImage() {
        // �^�C�g����ݒ�
        setTitle("�C���[�W��\������");

        // �p�l�����쐬���ăt���[���ɒǉ�
        MainPanel panel = new MainPanel();
        Container contentPane = getContentPane();
        contentPane.add(panel);

        // �p�l���T�C�Y�ɍ��킹�ăt���[���T�C�Y�������ݒ�
        pack();
    }

    public static void main(String[] args) {
        DrawImage frame = new DrawImage();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}