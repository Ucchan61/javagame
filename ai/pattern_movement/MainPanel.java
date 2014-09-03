import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

/*
 * Created on 2005/04/10
 *
 */

/**
 * @author mori
 *
 */
public class MainPanel extends JPanel implements Runnable {
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

    // �X���b�h
    private Thread thread;

    public MainPanel() {
        // �p�l���̐����T�C�Y��ݒ�Apack()����Ƃ��ɕK�v
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);

        // �ǐՎ�
        predator = new Predator(0, 0);

        // ���^�o�H���쐬
        // ���[�v������Ă���
        predator.buildPathSegment(new Point(40, 5), new Point(15, 70));
        predator.buildPathSegment(new Point(15, 70), new Point(75, 30));
        predator.buildPathSegment(new Point(75, 30), new Point(5, 30));
        predator.buildPathSegment(new Point(5, 30), new Point(65, 70));
        predator.buildPathSegment(new Point(65, 70), new Point(40, 5));

        // �쐬�����o�H��\��
        // predator.showPath();

        thread = new Thread(this);
        thread.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // �ǐՎ҂�`��
        predator.draw(g);
    }

    public void run() {
        while (true) {
            // �ǐՎ҂��ړ�
            predator.move();

            repaint();

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
