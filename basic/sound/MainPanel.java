import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

/*
 * Created on 2006/02/25
 */

public class MainPanel extends JPanel implements Runnable {
    // �p�l���T�C�Y
    public static final int WIDTH = 240;
    public static final int HEIGHT = 240;

    // ������{�[��
    private SoundBall ball;
    // �A�j���[�V�����p�X���b�h
    private Thread thread;

    public MainPanel() {
        // �p�l���̐����T�C�Y��ݒ�Apack()����Ƃ��ɕK�v
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setSize(WIDTH, HEIGHT);

        // �{�[�����쐬
        ball = new SoundBall(0, 0, 5, 4);

        // �X���b�h���N��
        thread = new Thread(this);
        thread.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // �{�[����`��
        ball.draw(g);
    }

    public void run() {
        // �v���O�������I������܂Ńt���[���������J��Ԃ�
        while (true) {
            // �e�{�[���𑬓x�������ړ�������
            ball.move();

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

