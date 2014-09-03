import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

/*
 * Created on 2006/05/07
 */

public class MainPanel extends JPanel implements Runnable {
    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;

    private Ball ball; // �{�[���I�u�W�F�g

    // �_�u���o�b�t�@�����O�idb�j�p
    private Graphics dbg;
    private Image dbImage = null;

    private Thread gameLoop; // �Q�[�����[�v

    public MainPanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        ball = new Ball(320, 240, 9, 7);

        // �Q�[�����[�v�J�n
        gameLoop = new Thread(this);
        gameLoop.start();
    }

    /**
     * �Q�[�����[�v
     */
    public void run() {
        while (true) {
            gameUpdate(); // �Q�[����Ԃ��X�V�iex: �{�[���̈ړ��j
            gameRender(); // �o�b�t�@�Ƀ����_�����O�i�_�u���o�b�t�@�����O�j
            paintScreen(); // �o�b�t�@����ʂɕ`��irepaint()�������ł���I�j

            // Active Rendering�ł�repaint()���g��Ȃ��I

            // �x�~
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * �Q�[����Ԃ��X�V
     */
    private void gameUpdate() {
        // �{�[���̈ړ�
        ball.move();
    }

    /**
     * �o�b�t�@�Ƀ����_�����O�i�_�u���o�b�t�@�����O�j
     */
    private void gameRender() {
        // ����̌Ăяo�����Ƀ_�u���o�b�t�@�����O�p�I�u�W�F�N�g���쐬
        if (dbImage == null) {
            // �o�b�t�@�C���[�W
            dbImage = createImage(WIDTH, HEIGHT);
            if (dbImage == null) {
                System.out.println("dbImage is null");
                return;
            } else {
                // �o�b�t�@�C���[�W�̕`��I�u�W�F�N�g
                dbg = dbImage.getGraphics();
            }
        }

        // �o�b�t�@���N���A����
        dbg.setColor(Color.WHITE);
        dbg.fillRect(0, 0, WIDTH, HEIGHT);

        // �{�[�����o�b�t�@�֕`�悷��
        ball.draw(dbg);
    }

    /**
     * �o�b�t�@����ʂɕ`��
     */
    private void paintScreen() {
        try {
            Graphics g = getGraphics(); // �O���t�B�b�N�I�u�W�F�N�g���擾
            if ((g != null) && (dbImage != null)) {
                g.drawImage(dbImage, 0, 0, null); // �o�b�t�@�C���[�W����ʂɕ`��
            }
            Toolkit.getDefaultToolkit().sync();
            if (g != null) {
                g.dispose(); // �O���t�B�b�N�I�u�W�F�N�g��j��
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
