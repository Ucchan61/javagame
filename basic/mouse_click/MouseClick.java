import java.awt.*;
import javax.swing.*;

public class MouseClick extends JFrame {
    public MouseClick() {
        // �^�C�g����ݒ�
        setTitle("�}�E�X�N���b�N");

        // �p�l�����쐬
        MainPanel panel = new MainPanel();
        Container contentPane = getContentPane();
        contentPane.add(panel);

        // �p�l���T�C�Y�ɍ��킹�ăt���[���T�C�Y�������ݒ�
        pack();
    }

    public static void main(String[] args) {
        MouseClick frame = new MouseClick();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
