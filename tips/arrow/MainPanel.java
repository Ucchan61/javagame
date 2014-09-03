import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;

/*
 * Created on 2006/06/10
 */

public class MainPanel extends JPanel
        implements
            Runnable,
            MouseListener,
            MouseMotionListener {
    // �p�l���T�C�Y
    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;

    // ��̐�
    public static final int NUM_ARROW = 10;

    private double direction; // �|�̌���
    private Point cannonPos = new Point(0, 480); // �|�̈ʒu
    private Point mousePos = new Point(); // �}�E�X�̈ʒu

    private Arrow[] arrows = new Arrow[NUM_ARROW];

    // �_�u���o�b�t�@�����O�p
    private Graphics dbg;
    private Image dbImage = null;

    private long beforeTime;
    private Thread gameLoop; // �Q�[�����[�v

    public MainPanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        // �|���쐬
        for (int i = 0; i < NUM_ARROW; i++) {
            arrows[i] = new Arrow();
        }

        // ���ˉ������[�h
        try {
            WaveEngine.load("arrow01.wav");
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

        addMouseListener(this);
        addMouseMotionListener(this);

        gameLoop = new Thread(this);
        gameLoop.start();
    }

    public void run() {
        beforeTime = System.currentTimeMillis();

        while (true) {
            gameUpdate(); // �Q�[����Ԃ��X�V
            gameRender(); // �o�b�t�@�Ƀ����_�����O
            paintScreen(); // �o�b�t�@�̓��e����ʂɕ`��

            WaveEngine.render();

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
        double timeDiff;

        // �O�񂩂�̎��ԍ������߂�
        timeDiff = (System.currentTimeMillis() - beforeTime) / 1000.0; // �b�P��
        // System.out.println(timeDiff);

        // ��������ԕ������ړ�
        if (arrows != null) {
            for (int i = 0; i < NUM_ARROW; i++) {
                arrows[i].move(timeDiff);
            }
        }

        beforeTime = System.currentTimeMillis();
    }

    /**
     * �o�b�t�@�Ƀ����_�����O
     */
    private void gameRender() {
        // �o�b�t�@�̍쐬
        if (dbImage == null) {
            dbImage = createImage(WIDTH, HEIGHT);
            if (dbImage == null) {
                System.out.println("dbImage is null");
                return;
            } else {
                dbg = dbImage.getGraphics();
            }
        }

        // �w�i��h��Ԃ�
        dbg.setColor(Color.WHITE);
        dbg.fillRect(0, 0, WIDTH, HEIGHT);

        // ���`��
        if (arrows != null) {
            for (int i = 0; i < NUM_ARROW; i++) {
                arrows[i].draw(dbg);
            }
        }
    }

    /**
     * �o�b�t�@�̓��e����ʂɕ`��
     */
    private void paintScreen() {
        Graphics g = getGraphics();
        if ((g != null) && (dbImage != null)) {
            g.drawImage(dbImage, 0, 0, null);
        }
        Toolkit.getDefaultToolkit().sync();
        if (g != null) {
            g.dispose();
        }
    }

    public void mouseClicked(MouseEvent e) {
        // ���ˉ�
        WaveEngine.play(0);

        if (e.isControlDown()) {  // CTRL�������ƃ}�E�X�ΏۂɎw�莞�Ԍ�ɖ���
            for (int i = 0; i < NUM_ARROW; i++) {
                if (!arrows[i].isUsed()) { // �g���Ă��Ȃ����T���Ĕ��ˁI�I�I
                    // �}�E�X���W��3.0�b��ɖ���
                    arrows[i].fire(cannonPos.x, cannonPos.y, mousePos.x,
                            mousePos.y, 3.0);
                    break;
                }
            }
        } else {  // �����Ɗp�x���w��
            // ���ˊp
            direction = Math.atan2(mousePos.y - cannonPos.y, mousePos.x
                    - cannonPos.x);
            for (int i = 0; i < NUM_ARROW; i++) {
                if (!arrows[i].isUsed()) { // �g���Ă��Ȃ����T���Ĕ��ˁI�I�I
                    arrows[i].fire(cannonPos.x, cannonPos.y, 400, direction);
                    break; // 1�N���b�N��1���������˂������̂Ŕ�����
                }
            }
        }
    }

    public void mouseMoved(MouseEvent e) {
        mousePos = new Point(e.getX(), e.getY());
        // System.out.println(mousePos.x + " " + mousePos.y);
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
    }
}
