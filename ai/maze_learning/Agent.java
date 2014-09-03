import java.util.*;

/**
 * �G�[�W�F���g�N���X�B�����w�K�ŗ��p����e���\�b�h�������B
 * 
 * @author mori
 *  
 */
public class Agent {
    // �����w�K�p�����[�^
    private static final double ALPHA = 0.1; // �w�K��
    private static final double EPSILON = 0.01; // �T����
    private static final double GAMMA = 0.95; // ������

    // ���H����擾������Ԑ��A�s����
    private int numState;
    private int numAction;

    // ���l�֐�
    private double[][] Q;

    // ���H�ւ̎Q��
    private Maze maze;

    // ����������
    private Random rand;

    /**
     * �R���X�g���N�^�B
     * 
     * @param maze ���H�ւ̎Q�ƁB
     */
    public Agent(Maze maze) {
        this.maze = maze;
        // ��Ԑ��E�s��������H������擾
        numState = maze.getNumState();
        numAction = maze.getNumAction();
        // ���l�֐����쐬
        Q = new double[numState][numAction];
        // ���l�֐���������
        initQ();
        rand = new Random();
    }

    /**
     * ���l�֐�������������B
     */
    public void initQ() {
        for (int s = 0; s < numState; s++) {
            for (int a = 0; a < numAction; a++) {
                Q[s][a] = 0.0;
            }
        }
    }

    /**
     * ���l�֐��ɂ��������čs����I������B�ő�̉��l�֐��l�����s����I���×~�igreedy�j�@��p����B
     * 
     * @return �I�������s���B
     */
    public int selectBestAction() {
        // ���݂̊��̏�Ԃ��擾
        int s = maze.getStateNum();
        // �ő�̉��l�֐��l�����s��
        int bestAction = 0;

        // �ő�̉��l�֐��l�����s����T��
        for (int a = 1; a < numAction; a++) {
            if (Q[s][a] > Q[s][bestAction]) {
                bestAction = a;
            }
        }

        return bestAction;
    }

    /**
     * ���l�֐��ɂ��������čs����I������Bepsilon�̊m���Ń����_���s�������e-greedy�@��p����B
     * 
     * @return �I�������s���B
     */
    public int selectAction() {
        int action = 0;

        // �Ƃ肠����greedy�@�ōs���I��
        action = selectBestAction();
        // EPSILON�̊m���Ń����_���s����I��
        if (rand.nextDouble() < EPSILON) {
            action = rand.nextInt(numAction);
        }

        return action;
    }

    /**
     * Q-Learning���g���ĉ��l�֐����X�V����B
     * 
     * @param s ��ԁB
     * @param a �s���B
     * @param r ��V�B
     * @param next_s ���̏�ԁB
     * @param next_a ���̍s���B
     */
    public void updateQ(int s, int a, double r, int next_s, int next_a) {
        Q[s][a] += ALPHA * (r + GAMMA * Q[next_s][next_a] - Q[s][a]);
    }
}