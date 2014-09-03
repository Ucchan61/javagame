import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

/*
 * Created on 2005/04/16
 *
 */

/**
 * @author mori
 *  
 */
public class MainPanel extends JPanel implements Runnable {
    // �p�l���T�C�Y
    private static final int WIDTH = 480;
    private static final int HEIGHT = 320;

    // �ǐՎ�
    private Predator predator;
    // �}�b�v
    private Map map;

    // �X���b�h
    private Thread thread;

    public MainPanel() {
        // �p�l���̐����T�C�Y��ݒ�Apack()����Ƃ��ɕK�v
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);

        // �}�b�v
        map = new Map("map.dat");

        // �ǐՎ�
        predator = new Predator(1, 1, map);

        thread = new Thread(this);
        thread.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // �}�b�v��`��
        map.draw(g);

        // �ǐՎ҂�`��
        predator.draw(g);
    }

    public void run() {
        while (true) {
            // �}�b�v�����񂷂�
            predator.patrol();

            repaint();

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}