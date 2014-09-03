import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

/*
 * Created on 2005/03/30
 *
 */

/**
 * @author mori
 *  
 */
public class MainPanel extends JPanel
        implements MouseListener, MouseMotionListener {

    // �p�l���T�C�Y
    private static final int WIDTH = 480;
    private static final int HEIGHT = 480;
    // �O���b�h�T�C�Y
    private static final int GS = 16;
    // �s���A��
    public static final int ROW = HEIGHT / GS;
    public static final int COL = WIDTH / GS;
    // �����̍ő�̒���
    private static final int MAX_LINE_LENGTH = 256;

    // �����̎n�_
    private Point start;

    // �u���[���n���A���S���Y���ŋ��߂������̍��W
    private Point[] line;

    public MainPanel() {
        // �p�l���̐����T�C�Y��ݒ�Apack()����Ƃ��ɕK�v
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        line = new Point[MAX_LINE_LENGTH];

        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // �u���[���n���A���S���Y���ɂ���ċ��߂�ꂽ�����̍��W��
        // line[]�ɓ����Ă�̂ł�����h��Ԃ�
        for (int i = 0; i < MAX_LINE_LENGTH; i++) {
            if (line[i] == null) {
                break;
            }
            g.fillRect(line[i].x * GS, line[i].y * GS, GS, GS);
        }
    }

    /**
     * �u���[���n���A���S���Y���Œ���������
     * 
     * @param start �����̎n�_
     * @param end �����̏I�_
     */
    private void buildLine(Point start, Point end) {
        int nextX = start.x;
        int nextY = start.y;
        int deltaX = end.x - start.x;
        int deltaY = end.y - start.y;
        int stepX, stepY;
        int step;
        int fraction;

        for (step = 0; step < MAX_LINE_LENGTH; step++) {
            line[step] = null;
        }

        step = 0;

        if (deltaX < 0)
            stepX = -1;
        else
            stepX = 1;
        if (deltaY < 0)
            stepY = -1;
        else
            stepY = 1;

        deltaX = Math.abs(deltaX * 2);
        deltaY = Math.abs(deltaY * 2);

        line[step] = new Point(nextX, nextY);
        step++;

        if (deltaX > deltaY) {
            fraction = deltaY - deltaX / 2;
            while (nextX != end.x) {
                if (fraction >= 0) {
                    nextY += stepY;
                    fraction -= deltaX;
                }
                nextX += stepX;
                fraction += deltaY;
                line[step] = new Point(nextX, nextY);
                step++;
            }
        } else {
            fraction = deltaX - deltaY / 2;
            while (nextY != end.y) {
                if (fraction >= 0) {
                    nextX += stepX;
                    fraction -= deltaY;
                }
                nextY += stepY;
                fraction += deltaX;
                line[step] = new Point(nextX, nextY);
                step++;
            }
        }
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        // �n�_��ݒ�
        start = new Point(e.getPoint().x / GS, e.getPoint().y / GS);
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
        // �I�_��ݒ�
        Point end = new Point(e.getPoint().x / GS, e.getPoint().y / GS);
        // �u���[���n���A���S���Y���Œ��������߂�
        buildLine(start, end);
        // �ĕ`��
        repaint();
    }

    public void mouseMoved(MouseEvent e) {
    }
}