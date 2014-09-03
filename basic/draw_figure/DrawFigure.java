import java.awt.*;
import javax.swing.*;

public class DrawFigure extends JFrame {
    public DrawFigure() {
        // �^�C�g����ݒ�
        setTitle("�}�`��`��");

        // ���C���p�l�����쐬���ăt���[���ɒǉ�
        MainPanel panel = new MainPanel();
        Container contentPane = getContentPane();
        contentPane.add(panel);

        // �p�l���T�C�Y�ɍ��킹�ăt���[���T�C�Y�������ݒ�
        pack();
    }

    public static void main(String[] args) {
        DrawFigure frame = new DrawFigure();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

class MainPanel extends JPanel {
    // �p�l���T�C�Y
    private static final int WIDTH = 240;
    private static final int HEIGHT = 240;

    public MainPanel() {
        // �p�l���̐����T�C�Y��ݒ�
        // pack()����Ƃ��ɕK�v
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // ����`��
        g.drawLine(10, 10, 100, 10);
        // �ԂɕύX
        g.setColor(Color.RED);
        // �l�p�`��`��
        g.drawRect(10, 20, 40, 40);
        g.fillRect(60, 20, 40, 40);
        // �ɕύX
        g.setColor(Color.BLUE);
        // �~��`��
        g.drawOval(10, 70, 40, 40);
        g.fillOval(60, 70, 40, 40);
    }
}
