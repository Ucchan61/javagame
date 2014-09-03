import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
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
    private static final int WIDTH = 640;
    private static final int HEIGHT = 480;

    // �ő唚����
    private static final int MAX_EXPLOSION = 128;

    private Explosion[] explosion;
    private Thread thread;

    public MainPanel() {
        // �p�l���̐����T�C�Y��ݒ�Apack()����Ƃ��ɕK�v
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        addMouseListener(this);
        addMouseMotionListener(this);

        // �����I�u�W�F�N�g���u���炩���߁v�p��
        // �N���b�N�����Ƃ��ɍ��ƒx������
        explosion = new Explosion[MAX_EXPLOSION];
        for (int i = 0; i < MAX_EXPLOSION; i++) {
            explosion[i] = new Explosion();
        }

        thread = new Thread(this);
        thread.start();
    }

    /**
     * �Q�[�����[�v
     */
    public void run() {
        while (true) {
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

        // ������`��
        for (int i = 0; i < MAX_EXPLOSION; i++) {
            explosion[i].draw(g);
        }
    }

    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        for (int i = 0; i < MAX_EXPLOSION; i++) {
            if (!explosion[i].isUsed()) { // �g���Ă��Ȃ�Explosion�I�u�W�F�N�g������
                explosion[i].play(x, y); // �����I
                break; // 1��������OK�Ȃ̂Ń��[�v������
            }
        }
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        for (int i = 0; i < MAX_EXPLOSION; i++) {
            if (!explosion[i].isUsed()) { // �g���Ă��Ȃ�Explosion�I�u�W�F�N�g������
                explosion[i].play(x, y); // �����I
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