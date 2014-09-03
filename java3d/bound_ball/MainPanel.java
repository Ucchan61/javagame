import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

/*
 * Created on 2006/02/24
 */

public class MainPanel extends JPanel implements Runnable {
    // �p�l���T�C�Y
    public static final int WIDTH = 240;
    public static final int HEIGHT = 240;
    // �{�[���̑傫��
    private static final int SIZE = 10;
    // �{�[���̈ʒu (x, y)�A�~�̍���̍��W
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

        // �{�[���̈ʒu��������
        x = 0;
        y = 0;

        // �{�[���̑��x��������
        vx = 2;
        vy = 1;

        // �X���b�h���N��
        thread = new Thread(this);
        thread.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // �Ԃ��{�[����`��
        g.setColor(Color.RED);
        g.fillOval(x, y, SIZE, SIZE);
    }

    // ���C�����[�v
    public void run() {
        // �v���O�������I������܂ŌJ��Ԃ�
        while (true) {
            // �{�[���𑬓x�������ړ�������
            x += vx;
            y += vy;

            // ���܂��͉E�ɓ���������x�������x�̕����𔽓]������
            if (x < 0 || x > WIDTH - SIZE) {
                vx = -vx;
            }

            // ��܂��͉��ɓ���������y�������x�̕����𔽓]������
            if (y < 0 || y > HEIGHT - SIZE) {
                vy = -vy;
            }

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

