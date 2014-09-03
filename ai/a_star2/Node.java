import java.awt.Point;
import java.util.LinkedList;

/*
 * Created on 2005/04/17
 *
 */

/**
 * �m�[�h�N���X
 * 
 * @author mori
 */
public class Node implements Comparable {
    // �m�[�h�̃}�b�v���ł̍��W
    public Point pos;
    // �X�^�[�g�n�_����̃R�X�g
    public int costFromStart;
    // �S�[���܂ł̃q���[���X�e�B�b�N�R�X�g
    public int heuristicCostToGoal;
    // �e�m�[�h
    public Node parentNode;

    public Node(Point pos) {
        this.pos = pos;
    }

    /**
     * �q���[���X�e�B�b�N�R�X�g���v�Z����
     * 
     * @param node �Ώۃm�[�h
     * @return �q���[���X�e�B�b�N�R�X�g
     */
    public int getHeuristicCost(Node node) {
        // �Ώۃm�[�h�܂ł̒����������q���[���X�e�B�b�N�R�X�g�Ƃ���
        // (x1, y1)-(x2, y2)�Ԃ̒��������́�(x2-x1)^2 + (y2-y1)^2
        int m = node.pos.x - pos.x;
        int n = node.pos.y - pos.y;

        return (int) Math.sqrt(m * m + n * n);
    }

    /**
     * �m�[�h�����������ׂ�i�I�[�o�[���C�h�j
     * 
     * @param �m�[�h
     * @return �����m�[�h��������true��Ԃ�
     */
    public boolean equals(Object node) {
        // �m�[�h�̍��W���ꏏ�������瓯���Ƃ݂Ȃ�
        if (pos.x == ((Node) node).pos.x && pos.y == ((Node) node).pos.y) {
            return true;
        }

        return false;
    }

    /**
     * �m�[�h�̑召���R�X�g�����ɔ�r����i�I�[�o�[���C�h�j
     */
    public int compareTo(Object node) {
        // �R�X�g=�X�^�[�g�m�[�h����̃R�X�g+�q���[���X�e�B�b�N�R�X�g
        int c1 = costFromStart + heuristicCostToGoal;
        int c2 = ((Node) node).costFromStart
                + ((Node) node).heuristicCostToGoal;

        if (c1 < c2)
            return -1;
        else if (c1 == c2)
            return 0;
        else
            return 1;
    }

    /**
     * �אڂ���m�[�h�̃��X�g��Ԃ�
     * 
     * @return �אڃm�[�h�̃��X�g
     */
    public LinkedList getNeighbors() {
        LinkedList neighbors = new LinkedList();
        int x = pos.x;
        int y = pos.y;

        // �㉺���E�݂̂����ړ��ł��Ȃ��悤�ɐ�������

        // ��
        neighbors.add(new Node(new Point(x, y - 1)));
        // �E��
        // neighbors.add(new Node(new Point(x+1, y-1)));
        // �E
        neighbors.add(new Node(new Point(x + 1, y)));
        // �E��
        // neighbors.add(new Node(new Point(x+1, y+1)));
        // ��
        neighbors.add(new Node(new Point(x, y + 1)));
        // ����
        // neighbors.add(new Node(new Point(x-1, y+1)));
        // ��
        neighbors.add(new Node(new Point(x - 1, y)));
        // ����
        // neighbors.add(new Node(new Point(x-1, y-1)));

        return neighbors;
    }
}