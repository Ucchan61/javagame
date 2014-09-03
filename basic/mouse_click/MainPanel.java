import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;

/*
 * Created on 2006/02/25
 */

public class MainPanel extends JPanel implements MouseListener {
    // �p�l���T�C�Y
    public static final int WIDTH = 240;
    public static final int HEIGHT = 240;
    // �_�̑傫��
    public static final int SIZE = 10;

    // �_�̈ʒu�����Ƃ����X�g
    private ArrayList pointList;

    public MainPanel() {
        // �p�l���̐����T�C�Y��ݒ�Apack()����Ƃ��ɕK�v
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        // 10�i�f�t�H���g�j�̗v�f������ArrayList���쐬
        pointList = new ArrayList();

        // MouseListener��o�^
        addMouseListener(this);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.RED);

        // pointList�ɓ����Ă���_�̏ꏊ�ɓ_��`��
        for (int i = 0; i < pointList.size(); i++) {
            Point p = (Point) pointList.get(i);
            g.fillOval(p.x - SIZE / 2, p.y - SIZE / 2, SIZE, SIZE);
        }
    }

    public void mouseClicked(MouseEvent e) {
        // �N���b�N�������W�𓾂�
        int x = e.getX();
        int y = e.getY();
        // pointList�ɓo�^
        pointList.add(new Point(x, y));

        repaint();
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }
}
