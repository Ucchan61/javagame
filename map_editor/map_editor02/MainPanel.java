import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

/*
 * Created on 2005/12/17
 *
 */

/**
 * @author mori
 *  
 */
public class MainPanel extends JPanel
        implements
            MouseListener,
            MouseMotionListener {

    // �p�l���̃T�C�Y�i�P�ʁF�s�N�Z���j
    private static final int WIDTH = 512;
    private static final int HEIGHT = 512;

    // �`�b�v�Z�b�g�̃T�C�Y�i�P�ʁF�s�N�Z���j
    private static final int CHIP_SIZE = 32;

    // �}�b�v
    private int[][] map;
    // �}�b�v�̑傫���i�P�ʁF�}�X�j
    private int row;
    private int col;
    
    public MainPanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        addMouseListener(this);
        addMouseMotionListener(this);

        // �}�b�v��������
        initMap(16, 16);
    }

    /**
     * �}�b�v������������
     * @param row �s��
     * @param col ��
     */
    public void initMap(int r, int c) {
        row = r;
        col = c;
        map = new int[r][c];

        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                map[i][j] = 0;
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (map[i][j] == 0) {
                    g.setColor(Color.BLACK);
                } else {
                    g.setColor(Color.CYAN);
                }
                g.fillRect(j * CHIP_SIZE, i * CHIP_SIZE, CHIP_SIZE, CHIP_SIZE);
            }
        }
    }

    /**
     * �}�E�X�Ń}�b�v����N���b�N�����Ƃ�
     */
    public void mouseClicked(MouseEvent e) {
        // �}�E�X�|�C���^�̍��W������W�i�}�X�j�����߂�
        int x = e.getX() / CHIP_SIZE;
        int y = e.getY() / CHIP_SIZE;

        if (x >= 0 && x < col && y >= 0 && y < row) {
            map[y][x] = 1;
        }

        repaint();
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    /**
     * �}�b�v��Ń}�E�X���h���b�O�����Ƃ�
     */
    public void mouseDragged(MouseEvent e) {
        // �}�E�X�|�C���^�̍��W������W�i�}�X�j�����߂�
        int x = e.getX() / CHIP_SIZE;
        int y = e.getY() / CHIP_SIZE;

        if (x >= 0 && x < col && y >= 0 && y < row) {
            map[y][x] = 1;
        }

        repaint();
    }

    public void mouseMoved(MouseEvent e) {
    }
}