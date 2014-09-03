import java.awt.*;
import javax.swing.*;

public class MoveBall extends JFrame {
    public MoveBall() {
        // �^�C�g����ݒ�
        setTitle("�{�[���𓮂���");

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
        MoveBall frame = new MoveBall();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

class MainPanel extends JPanel implements Runnable {
    // �p�l���T�C�Y
    private static final int WIDTH = 240;
    private static final int HEIGHT = 240;
    // �{�[���̑傫��
    private static final int SIZE = 10;
    // �{�[���̈ʒu (x, y)�A�~�̒��S�̍��W
    private int x;
    private int y;
    // �{�[���̑��x (vx, vy)
    private int vx;
    private int vy;
    // �A�j���[�V�����p�X���b�h
    private Thread thread;

    public MainPanel() {
        // �p�l���̐����T�C�Y��ݒ�Apack()����Ƃ��ɕK�v
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        // �ϐ���������
        // �{�[���͉�ʂ̍���ɔz�u
        x = 100;
        y = 100;
        vx = 1;
        vy = 1;

        // �X���b�h���N��
        thread = new Thread(this);
        thread.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // ���{�[����`��
        g.setColor(Color.BLUE);
        g.fillOval(x - SIZE / 2, y - SIZE / 2, SIZE, SIZE);
    }

    // ���C�����[�v
    public void run() {
        // �v���O�������I������܂Ńt���[���������J��Ԃ�
        while (true) {
            // �{�[���𑬓x�������ړ�������
            x += vx;
            y += vy;

            // �{�[�����ĕ`��
            repaint();

            // 20�~���b�����x�~
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
