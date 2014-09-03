import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

/*
 * Created on 2005/04/16
 *
 */

/**
 * @author mori
 *  
 */
public class MainPanel extends JPanel implements Runnable, KeyListener {
    // �p�l���T�C�Y
    private static final int WIDTH = 640;
    private static final int HEIGHT = 640;
    // �O���b�h�T�C�Y
    private static final int GS = 8;
    // �s���A��
    public static final int ROW = HEIGHT / GS;
    public static final int COL = WIDTH / GS;
    // �����萔
    private static final int UP = 0;
    private static final int DOWN = 1;
    private static final int LEFT = 2;
    private static final int RIGHT = 3;

    // �ǐՎ�
    private Predator predator;
    // �l��
    private Prey prey;

    // �X���b�h
    private Thread thread;

    public MainPanel() {
        // �p�l���̐����T�C�Y��ݒ�Apack()����Ƃ��ɕK�v
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        addKeyListener(this);

        // �ǐՎ�
        predator = new Predator(50, 70);
        // �l���A���Ȃ��ł���i�΁j
        prey = new Prey(53, 10);

        thread = new Thread(this);
        thread.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // �ǐՎҁE�l����`��
        prey.draw(g);
        predator.draw(g);
    }

    public void run() {
        while (true) {
            predator.chase(prey);

            repaint();

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    /**
     * �L�[����Ŋl���𓮂���
     */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP :
                prey.move(UP);
                break;
            case KeyEvent.VK_DOWN :
                prey.move(DOWN);
                break;
            case KeyEvent.VK_LEFT :
                prey.move(LEFT);
                break;
            case KeyEvent.VK_RIGHT :
                prey.move(RIGHT);
                break;
        }

        repaint();
    }

    public void keyReleased(KeyEvent e) {
    }
}