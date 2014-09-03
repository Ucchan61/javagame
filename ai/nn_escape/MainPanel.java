import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

/*
 * Created on 2005/05/13
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
    public static final int GS = 32;

    // �s���A��
    public static final int ROW = HEIGHT / GS;
    public static final int COL = WIDTH / GS;

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
        predator = new Predator(0, 0);
        // �l��
        prey = new Prey(10, 10, predator);

        thread = new Thread(this);
        thread.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // �i�q��`��
        g.setColor(Color.BLACK);
        for (int i=1; i<COL; i++) {  // �c��
            g.drawLine(i*GS, 0, i*GS, HEIGHT);
        }
        for (int i=1; i<ROW; i++) {  // ����
            g.drawLine(0, i*GS, WIDTH, i*GS);
        }
        g.drawRect(0, 0, WIDTH, HEIGHT);  // �O�g
        
        // �ǐՎҁE�l����`��
        predator.draw(g);
        prey.draw(g);
    }

    public void run() {
        while (true) {
            // ������
            prey.escape();
            
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
     * 012
     * 345
     * 678
     */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP :
                predator.move(1);
                break;
            case KeyEvent.VK_DOWN :
                predator.move(7);
                break;
            case KeyEvent.VK_LEFT :
                predator.move(3);
                break;
            case KeyEvent.VK_RIGHT :
                predator.move(5);
                break;
        }
        repaint();
    }

    public void keyReleased(KeyEvent e) {
    }
}
