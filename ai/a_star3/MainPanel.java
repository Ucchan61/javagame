import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.LinkedList;

import javax.swing.JPanel;

/*
 * Created on 2005/04/23
 *
 */

/**
 * @author mori
 *  
 */
public class MainPanel extends JPanel {
    // �p�l���T�C�Y
    private static final int WIDTH = 240;
    private static final int HEIGHT = 240;

    // �`�b�v�Z�b�g�̃T�C�Y�i�P�ʁF�s�N�Z���j
    private static final int CS = 16;

    // �}�b�v
    private Map map;

    // A*�N���X
    private Astar astar;

    // �X�^�[�g�n�_�ƃS�[���n�_��ݒ�
    // �w�Q�[���J���҂̂��߂�AI����x��p.129�Ɠ���
    private static Point START_POS = new Point(1, 1);
    private static Point GOAL_POS = new Point(13, 13);

    // A*�ŋ��߂��ŒZ�o�H�̃p�X
    private LinkedList path;

    public MainPanel() {
        // �p�l���̐����T�C�Y��ݒ�Apack()����Ƃ��ɕK�v
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);

        // �}�b�v
        map = new Map("maze.dat");

        astar = new Astar(map);
        path = astar.searchPath(START_POS, GOAL_POS);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // �}�b�v��`��
        map.draw(g);

        // �X�^�[�g�n�_�ƃS�[���n�_��`��
        g.setColor(Color.RED);
        g.fillRect(START_POS.x * CS, START_POS.y * CS, CS, CS);

        g.setColor(Color.BLUE);
        g.fillRect(GOAL_POS.x * CS, GOAL_POS.y * CS, CS, CS);

        // �ŒZ�o�H�p�X��`��
        if (path != null) {
            g.setColor(Color.YELLOW);
            for (int i = 0; i < path.size(); i++) {
                Node node = (Node) path.get(i);
                Point pos = node.pos;
                g.fillRect(pos.x * CS + 7, pos.y * CS + 7, CS - 14, CS - 14);
            }
        }
    }
}