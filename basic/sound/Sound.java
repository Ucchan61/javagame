import java.awt.*;
import javax.swing.*;

public class Sound extends JFrame {
    public Sound() {
        // �^�C�g����ݒ�
        setTitle("���ʉ���炷");

        // �p�l�����쐬���ăt���[���ɒǉ�
        MainPanel panel = new MainPanel();
        Container contentPane = getContentPane();
        contentPane.add(panel);

        // �p�l���T�C�Y�ɍ��킹�ăt���[���T�C�Y�������ݒ�
        pack();
    }

    public static void main(String[] args) {
        Sound frame = new Sound();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}