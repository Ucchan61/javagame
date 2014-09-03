import java.awt.Point;
import java.util.LinkedList;

/*
 * Created on 2005/04/17
 *
 */

/**
 * A*�N���X
 * 
 * @author mori
 */
public class Astar {
    // �I�[�v�����X�g
    private PriorityList openList;
    // �N���[�Y�h���X�g
    private LinkedList closedList;
    // �}�b�v�ւ̎Q��
    private Map map;

    public Astar(Map map) {
        this.map = map;
        // PriorityList�͎���N���X�iAstar�̓����N���X�Ƃ��Ē�`�j
        openList = new PriorityList();
        closedList = new LinkedList();
    }

    /**
     * A*�ŋ��߂��X�^�[�g����S�[���܂ł̃p�X��Ԃ�
     * 
     * @param startPos �X�^�[�g�n�_
     * @param goalPos �S�[���n�_
     * @return A*�ŋ��߂��p�X
     */
    public LinkedList searchPath(Point startPos, Point goalPos) {
        int cnt = 0;
        // �X�^�[�g�m�[�h�ƃS�[���m�[�h���쐬
        Node startNode = new Node(startPos);
        Node goalNode = new Node(goalPos);

        // �X�^�[�g�m�[�h��ݒ�
        startNode.costFromStart = 0;
        startNode.heuristicCostToGoal = startNode.getHeuristicCost(goalNode);
        startNode.parentNode = null;

        // �X�^�[�g�m�[�h���I�[�v�����X�g�ɒǉ�
        openList.add(startNode);

        // �I�[�v�����X�g����ɂȂ�܂ł܂킷
        while (!openList.isEmpty()) {
            // openList�̓R�X�g�����������ɕ���ł��邽��
            // �ŏ��R�X�g�m�[�h�͈�Ԗڂɂ���
            // ���̃m�[�h�����o��
            Node curNode = (Node) openList.removeFirst();

            // ���̃m�[�h���S�[���m�[�h�ƈ�v���Ă��邩
            // ��v�iequals�j�̒�`��Node�N���X�Œ�`
            if (curNode.equals(goalNode)) {
                // �S�[���m�[�h����p�X�𐶐�
                // goalNode�̓R�X�g�Ȃǂ��ݒ肳��ĂȂ��̂�
                // �����Ƃ���curNode��n���Ƃ���ɒ���
                return constructPath(curNode);
            } else { // ��v���ĂȂ��ꍇ
                // ���݂̃m�[�h���N���[�Y�h���X�g�Ɉڂ�
                closedList.add(curNode);
                // ���݂̃m�[�h�ɗאڂ���e�m�[�h�𒲂ׂ�
                LinkedList neighbors = curNode.getNeighbors();
                for (int i = 0; i < neighbors.size(); i++) { // �e�אڃm�[�h�ɑ΂���
                    // �m�[�h��1�擾
                    Node neighborNode = (Node) neighbors.get(i);
                    // ���������p�����擾
                    // �I�[�v�����X�g�Ɋ܂܂�Ă��邩�H
                    boolean isOpen = openList.contains(neighborNode);
                    // �N���[�Y�h���X�g�Ɋ܂܂�Ă��邩�H
                    boolean isClosed = closedList.contains(neighborNode);
                    // ��Q���łȂ����H
                    boolean isHit = map.isHit(neighborNode.pos.x,
                            neighborNode.pos.y);

                    if (!isOpen && !isClosed && !isHit) {
                        // �I�[�v�����X�g�Ɉڂ��ăR�X�g���v�Z����
                        // �X�^�[�g����̃R�X�g�icostFromStart�j�͐e�̃R�X�g�ׂ̗Ȃ̂�+1����
                        neighborNode.costFromStart = curNode.costFromStart + 1;
                        // �q���[���X�e�B�b�N�R�X�g
                        neighborNode.heuristicCostToGoal = neighborNode
                                .getHeuristicCost(goalNode);
                        // �e�m�[�h
                        neighborNode.parentNode = curNode;
                        // �I�[�v�����X�g�ɒǉ�
                        openList.add(neighborNode);
                    }
                }
            }
        }

        // �ČĂяo�������邩������Ȃ��̂ŏ������Ă���
        openList.clear();
        closedList.clear();

        // ���[�v���܂킵�ăp�X��������Ȃ������ꍇ��null��Ԃ�
        return null;
    }

    /**
     * �S�[���m�[�h�܂ł̃p�X���\�z����
     * 
     * @param node �S�[���m�[�h
     * @return �X�^�[�g�m�[�h����S�[���m�[�h�܂ł̃p�X
     */
    private LinkedList constructPath(Node node) {
        LinkedList path = new LinkedList();

        // �e�m�[�h�����X���ǂ�
        while (node.parentNode != null) {
            // �ŏ��ɒǉ�����̂��~�\
            // �X�^�[�g�m�[�h��LinkedList�̐擪
            // �S�[���m�[�h��LinkedList�̍Ō�ɂȂ�悤�ɂ���
            path.addFirst(node);
            node = node.parentNode;
        }

        // �X�^�[�g�m�[�h�inode.parentNode == null�ƂȂ�m�[�h�j
        // ���ǉ����Ă���
        path.addFirst(node);

        return path;
    }

    /**
     * �I�[�v�����X�g�̒��g��\������⏕���\�b�h
     */
    private void showOpenList() {
        System.out.println("�y�I�[�v�����X�g�z");
        for (int i = 0; i < openList.size(); i++) {
            Node node = (Node) openList.get(i);
            System.out.print("(" + node.pos.x + ", " + node.pos.y + ")");
        }
        System.out.println();
    }

    /**
     * �N���[�Y�h���X�g�̒��g��\������⏕���\�b�h
     */
    private void showClosedList() {
        System.out.println("�y�N���[�Y�h���X�g�z");
        for (int i = 0; i < closedList.size(); i++) {
            Node node = (Node) closedList.get(i);
            System.out.print("(" + node.pos.x + ", " + node.pos.y + ")");
        }
        System.out.println();
    }

    /**
     * �����I�ɃR�X�g�̏��������Ƀm�[�h�����Ԃ悤�Ɋg���������X�g
     */
    private class PriorityList extends LinkedList {
        // ���X�g�ɒǉ�����add���\�b�h���I�[�o�[���C�h
        public void add(Node node) {
            for (int i = 0; i < size(); i++) {
                // �m�[�h�̑召�𒲂ׂď��������ɕ��Ԃ悤�ɒǉ�����
                // �m�[�h�̑召�𒲂ׂ�compareTo��Node�N���X�Œ�`
                if (node.compareTo(get(i)) <= 0) {
                    add(i, node);
                    return;
                }
            }
            addLast(node);
        }
    }
}