import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

/*
 * Created on 2005/04/24
 *
 */

/**
 * @author mori
 *  
 */
public class MainPanel extends JPanel implements Runnable {
    // �p�l���T�C�Y
    private static final int WIDTH = 672;
    private static final int HEIGHT = 516;

    // �`�b�v�Z�b�g�̃T�C�Y�i�P�ʁF�s�N�Z���j
    private static final int CS = 16;

    // �a�̐�
    private static final int MAX_ANTS = 200;

    // �a
    private Ant[] ants;

    // �}�b�v
    private Map map;

    // �Q�[�����[�v�p�X���b�h
    private Thread thread;

    public MainPanel() {
        // �p�l���̐����T�C�Y��ݒ�Apack()����Ƃ��ɕK�v
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);

        // �}�b�v���쐬
        map = new Map();

        // �a���쐬
        ants = new Ant[MAX_ANTS];
        ants[0] = new Ant(Ant.RED_ANT, Ant.FORAGE, 5, 5, map, this);
        ants[1] = new Ant(Ant.RED_ANT, Ant.FORAGE, 5, 8, map, this);
        ants[2] = new Ant(Ant.BLACK_ANT, Ant.FORAGE, 36, 5, map, this);
        ants[3] = new Ant(Ant.BLACK_ANT, Ant.FORAGE, 36, 8, map, this);

        thread = new Thread(this);
        thread.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(153, 255, 153));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // �}�b�v��`��
        map.draw(g);

        // �a��`��
        for (int i = 0; i < MAX_ANTS; i++) {
            if (ants[i] != null) {
                ants[i].draw(g);
            }
        }
    }

    /**
     * �Q�[�����[�v
     */
    public void run() {
        while (true) {
            // �e�a�̍s��
            for (int i = 0; i < MAX_ANTS; i++) {
                if (ants[i] != null) {
                    ants[i].act();
                }
            }

            repaint();

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void birthAnt(int type, int x, int y) {
        for (int i = 0; i < MAX_ANTS; i++) {
            // �ŏ���null�̂Ƃ����1�C�����ǉ�����
            if (ants[i] == null) {
                ants[i] = new Ant(type, Ant.FORAGE, x, y, map, this);
                break;
            }
        }
    }
}