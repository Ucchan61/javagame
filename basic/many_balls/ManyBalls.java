import java.awt.*;
import javax.swing.*;

public class ManyBalls extends JFrame {
    public ManyBalls() {
        // �^�C�g����ݒ�
        setTitle("�{�[��������������");
        // �T�C�Y�ύX�֎~
        setResizable(false);

        // �p�l�����쐬���ăt���[���ɒǉ�
        MainPanel panel = new MainPanel();
        Container contentPane = getContentPane();
        contentPane.add(panel);

        // �p�l���T�C�Y�ɍ��킹�ăt���[���T�C�Y�������ݒ�
        pack();
    }

    public static void main(String[] args) {
        ManyBalls frame = new ManyBalls();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}