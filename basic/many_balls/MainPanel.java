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
    // �{�[���̐�
    private static final int NUM_BALL = 4;
    // �{�[�����i�[����z��
    private Ball[] ball;
    // �A�j���[�V�����p�X���b�h
    private Thread thread;

    public MainPanel() {
        // �p�l���̐����T�C�Y��ݒ�Apack()����Ƃ��ɕK�v
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setSize(WIDTH, HEIGHT);

        // �{�[�����i�[����z����쐬
        ball = new Ball[NUM_BALL];
        // �{�[�����쐬
        ball[0] = new Ball(0, 0, 1, 2);
        ball[1] = new Ball(10, 10, 3, -2);
        ball[2] = new Ball(50, 0, -2, 3);
        ball[3] = new Ball(0, 0, 12, 8);

        // �X���b�h���N��
        thread = new Thread(this);
        thread.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // �e�{�[����`��
        for (int i = 0; i < NUM_BALL; i++) {
            ball[i].draw(g);
        }
    }

    // ���C�����[�v
    public void run() {
        // �v���O�������I������܂Ńt���[���������J��Ԃ�
        while (true) {
            // �e�{�[���𑬓x�������ړ�������
            for (int i = 0; i < NUM_BALL; i++) {
                ball[i].move();
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
