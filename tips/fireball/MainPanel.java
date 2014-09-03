import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

/*
 * Created on 2006/01/29
 */

/**
 * @author mori
 */
public class MainPanel extends JPanel
        implements
            Runnable,
            MouseListener,
            MouseMotionListener {
    // �p�l���T�C�Y
    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;

    // �ő�t�@�C�A�{�[����
    private static final int MAX_FIRES = 128;

    private FireBall[] fireball;
    private Thread thread;

    public MainPanel() {
        // �p�l���̐����T�C�Y��ݒ�Apack()����Ƃ��ɕK�v
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        addMouseListener(this);
        addMouseMotionListener(this);

        // �I�u�W�F�N�g���u���炩���߁v�p��
        // �N���b�N�����Ƃ��ɍ��ƒx������
        fireball = new FireBall[MAX_FIRES];
        for (int i = 0; i < MAX_FIRES; i++) {
            fireball[i] = new FireBall();
        }

        thread = new Thread(this);
        thread.start();
    }

    /**
     * �Q�[�����[�v
     */
    public void run() {
        while (true) {
            // �t�@�C�A�{�[���̈ړ�
            for (int i=0; i<MAX_FIRES; i++) {
                if (fireball[i].isUsed()) {
                    fireball[i].move();
                }
            }

            repaint();

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // �w�i�����œh��Ԃ�
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // ���S��`��
        g.setColor(Color.YELLOW);
        g.fillOval(320, 240, 2, 2);
        
        // �t�@�C�A�{�[����`��
        for (int i = 0; i < MAX_FIRES; i++) {
            if (fireball[i].isUsed()) {
                fireball[i].draw(g);
            }
        }
    }

    public void mouseDragged(MouseEvent e) {
        Point start = new Point(320, 240);
        Point target = new Point(e.getX(), e.getY());
        for (int i = 0; i < MAX_FIRES; i++) {
            if (!fireball[i].isUsed()) { // �g���Ă��Ȃ��I�u�W�F�N�g������
                fireball[i].shot(start, target); // �t�@�C�A�I
                break; // 1��������OK�Ȃ̂Ń��[�v������
            }
        }
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        Point start = new Point(320, 240);
        Point target = new Point(e.getX(), e.getY());
        for (int i = 0; i < MAX_FIRES; i++) {
            if (!fireball[i].isUsed()) { // �g���Ă��Ȃ��I�u�W�F�N�g������
                fireball[i].shot(start, target); // �t�@�C�A�I
                break; // 1��������OK�Ȃ̂Ń��[�v������
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}