import java.awt.*;
import javax.swing.*;

public class BoundBall extends JFrame {
    public BoundBall() {
        // �^�C�g����ݒ�
        setTitle("�{�[�������˕Ԃ鏈��");

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
        BoundBall frame = new BoundBall();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}