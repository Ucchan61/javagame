/*
 * �쐬��: 2005/02/15
 *
 */
import java.util.*;

/**
 * �G�[�W�F���g�N���X �����w�K�ŗ��p����e���\�b�h������
 * 
 * @author mori
 *  
 */
public class Agent {
    // �����w�K�p�����[�^
    private static final double ALPHA = 0.1; // �w�K��
    private static final double EPSILON = 0; // �T����
    private static final double GAMMA = 1.0; // ������

    // ��Ԑ��E�s������������擾
    // ��������Environment.������̂͂߂�ǂ���������
    private static final int NUM_STATE = Environment.NUM_STATE;
    private static final int NUM_ACTION = Environment.NUM_ACTION;

    // ���l�֐��i���l�֐��̍X�V���w�K�ł��j
    private double[][] Q;
    // ����������
    private Random rand;
    // ���ւ̎Q��
    private Environment env;

    /**
     * �R���X�g���N�^
     * 
     * @param env ���ւ̎Q��
     */
    public Agent(Environment env) {
        this.env = env;

        // ���l�֐����쐬
        Q = new double[NUM_STATE][NUM_ACTION];
        // ���l�֐���������
        initQ();
        rand = new Random();
    }

    /**
     * ���l�֐�������������
     */
    public void initQ() {
        for (int s = 0; s < NUM_STATE; s++) {
            for (int a = 0; a < NUM_ACTION; a++) {
                Q[s][a] = 0.0;
            }
        }
    }

    /**
     * ���l�֐��ɂ��������čs����I������B�ő�̉��l�֐��l�����s����I���×~�igreedy�j�@��p����
     * 
     * @return �I�������s��
     */
    public int selectBestAction() {
        // ���݂̊��̏�Ԃ��擾
        int s = env.getStateNum();
        // �ő�̉��l�֐��l�����s��
        int bestAction = 0;

        // �ő�̉��l�֐��l�����s����T��
        for (int a = 1; a < NUM_ACTION; a++) {
            if (Q[s][a] > Q[s][bestAction]) {
                bestAction = a;
            }
        }

        return bestAction;
    }

    /**
     * ���l�֐��ɂ��������čs����I������Bepsilon�̊m���Ń����_���s�������e-greedy�@��p����
     * 
     * @return �I�������s��
     */
    public int selectAction() {
        int action = 0;

        // �Ƃ肠����greedy�@�ōs���I��
        action = selectBestAction();
        // EPSILON�̊m���Ń����_���s����I��
        if (rand.nextDouble() < EPSILON) {
            action = rand.nextInt(NUM_ACTION);
        }

        return action;
    }

    /**
     * Q-Learning���g���ĉ��l�֐����X�V����
     * 
     * @param s ���
     * @param a �s��
     * @param r ��V
     * @param next_s ���̏��
     * @param next_a ���̍s��
     */
    public void updateQ(int s, int a, double r, int next_s, int next_a) {
        Q[s][a] += ALPHA * (r + GAMMA * Q[next_s][next_a] - Q[s][a]);
    }
}