/*
 * �쐬��: 2005/02/15
 *
 */
import java.awt.*;
/**
 * ���N���X �G�[�W�F���g�ȊO�̂��ׂĂ��Ǘ�����
 * 
 * @author mori
 *  
 */
public class Environment {

    private static final double G = 9.8; // �d�͉����x
    private static final double M1 = 1.0; // �_1�̎���
    private static final double M2 = 1.0; // �_2�̎���
    // State�N���X�ł��g���̂�public�w��
    public static final double L1 = 1.0; // �_1�̒���
    public static final double L2 = 1.0; // �_2�̒���
    private static final double LC1 = 0.5; // �_1�̏d�S�܂ł̋���
    private static final double LC2 = 0.5; // �_2�̏d�S�܂ł̋���
    private static final double I1 = 1.0; // �_1�̊������[�����g
    private static final double I2 = 1.0; // �_2�̊������[�����g
    private static final double T = 0.05; // ���ԃX�e�b�v

    // ��ԕ����Ŏg���p�x�̃��W�A���\��
    private static final double ONE_FORTH = 0.785394;
    private static final double TWO_FORTHS = 1.570788;
    private static final double THREE_FORTHS = 2.356182;
    private static final double ONE = 3.141592;
    private static final double FIVE_FORTHS = 3.92697;
    private static final double SIX_FORTHS = 4.712364;
    private static final double SEVEN_FORTHS = 5.497758;
    private static final double TWO = 6.283184;
    private static final double THREE = 9.424776;
    private static final double FOUR = 12.566368;
    private static final double SIX = 18.849552;
    private static final double NINE = 28.274328;
   
    // �o�[�̍��� State�N���X�ł��g���̂�public�w��
    public static final int BAR_HEIGHT = 30;

    // ��Ԑ��i8 * 4 * 8 * 6�j
    public static final int NUM_STATE = 1536;
    // �s�����i���̊��őI���ł���s���̐��j
    public static final int NUM_ACTION = 3;

    // ���̏��
    private State state;

    /**
     * �R���X�g���N�^
     * 
     * @param panel �p�l���ւ̎Q��
     */
    public Environment(MainPanel panel) {
        // ��Ԃ��쐬
        state = new State();
    }

    /**
     * ��������������
     */
    public void init() {
        state.init();
    }

    /**
     * ���̏�Ԃ�Ԃ�
     * 
     * @return ���̏��
     */
    public State getState() {
        return state;
    }

    /**
     * ���̏�Ԃ���ӂ̔ԍ��ɕϊ����ĕԂ�
     * 
     * @return ���̏�Ԕԍ�
     */
    public int getStateNum() {
        int s1, s2, s3, s4;

        // 8�����ɕ���
        if (state.theta1 < ONE_FORTH) {
            s1 = 0;
        } else if (state.theta1 < TWO_FORTHS) {
            s1 = 1;
        } else if (state.theta1 < THREE_FORTHS) {
            s1 = 2;
        } else if (state.theta1 < ONE) {
            s1 = 3;
        } else if (state.theta1 < FIVE_FORTHS) {
            s1 = 4;
        } else if (state.theta1 < SIX_FORTHS) {
            s1 = 5;
        } else if (state.theta1 < SEVEN_FORTHS) {
            s1 = 6;
        } else {
            s1 = 7;
        }

        // 4�����ɕ���
        if (state.thetaDot1 < -TWO) {
            s2 = 0;
        } else if (state.thetaDot1 < 0) {
            s2 = 1;
        } else if (state.thetaDot1 < TWO) {
            s2 = 2;
        } else {
            s2 = 3;
        }

        // 8�����ɕ���
        if (state.theta2 < ONE_FORTH) {
            s3 = 0;
        } else if (state.theta2 < TWO_FORTHS) {
            s3 = 1;
        } else if (state.theta2 < THREE_FORTHS) {
            s3 = 2;
        } else if (state.theta2 < ONE) {
            s3 = 3;
        } else if (state.theta2 < FIVE_FORTHS) {
            s3 = 4;
        } else if (state.theta2 < SIX_FORTHS) {
            s3 = 5;
        } else if (state.theta2 < SEVEN_FORTHS) {
            s3 = 6;
        } else {
            s3 = 7;
        }

        // 6�����ɕ���
        if (state.thetaDot2 < -SIX) {
            s4 = 0;
        } else if (state.thetaDot2 < -THREE) {
            s4 = 1;
        } else if (state.thetaDot2 < 0) {
            s4 = 2;
        } else if (state.thetaDot2 < THREE) {
            s4 = 3;
        } else if (state.thetaDot2 < SIX) {
            s4 = 4;
        } else {
            s4 = 5;
        }

        // ��Ԃ͑S����1536�ʂ�B0-1535��Ԃ�
        // ���̎��͏�Ԕԍ�����ӂɋ��߂邽�߂̂���
        return s1 * 4 * 8 * 6 + s2 * 8 * 6 + s3 * 6 + s4;
    }

    /**
     * ���̏�Ԃ֑J�ڂ���
     * 
     * @param action �͂����������
     */
    public void nextState(int action) {
        double thetaAcc1, thetaAcc2, d1, d2, phi1, phi2;
        double tau = 0;

        switch (action) {
            case (0) :
                tau = 1.0;
                break;
            case (1) :
                tau = -1.0;
                break;
            case (2) :
                tau = 0.0;
                break;
        }

        // �A�N���o�b�g�̉^��������
        d1 = M1 * LC1 * LC1 + M2
                * (L1 * L1 + LC2 * LC2 + 2 * L1 * LC2 * Math.cos(state.theta2))
                + I1 + I2;
        d2 = M2 * (LC2 * LC2 + L1 * LC2 * Math.cos(state.theta2)) + I2;
        phi1 = -M2 * L1 * LC2 * state.thetaDot2 * state.thetaDot2 * Math.sin(state.theta2)
                - 2 * M2 * L1 * LC2 * state.thetaDot2 * state.thetaDot1
                * Math.sin(state.theta2) + (M1 * LC1 + M2 * L1) * G
                * Math.cos(state.theta1 - Math.PI / 2) + state.theta2;
        phi2 = M2 * LC2 * G * Math.cos(state.theta1 + state.theta2 - Math.PI / 2);

        thetaAcc2 = 1
                / (M2 * LC2 * LC2 + I2 - (d2 * d2 / d1))
                * (tau + (d2 / d1) * phi1 - M2 * L1 * LC2 * state.thetaDot1
                        * state.thetaDot1 * Math.sin(state.theta2) - phi2);
        thetaAcc1 = -(1 / d1) * (d2 * thetaAcc2 + phi1);

        state.thetaDot1 += T * thetaAcc1;
        // �͈͓����`�F�b�N
        if (state.thetaDot1 < -FOUR) {
            state.thetaDot1 = -FOUR;
        }
        if (state.thetaDot1 > FOUR) {
            state.thetaDot1 = FOUR;
        }
        
        state.theta1 += T * state.thetaDot1;

        state.thetaDot2 += T * thetaAcc2;
        // �͈͓����`�F�b�N
        if (state.thetaDot2 < -NINE) {
            state.thetaDot2 = -NINE;
        }
        if (state.thetaDot2 > NINE) {
            state.thetaDot2 = NINE;
        }
        
        state.theta2 += T * state.thetaDot2;
    }

    /**
     * ��Ԃɉ����ĕ�V��Ԃ��B
     * 
     * @return �S�[����Ԃł�0�A����ȊO�ł�-1
     */
    public double getReward() {
        if (state.isTouchBar()) {
            return 0;
        }
        return -1;
    }

    /**
     * 1�G�s�\�[�h���I��������true��Ԃ�
     * 
     * @return ���悪�_�ɓ͂��Ă�����true
     */
    public boolean isEnd() {
        return state.isTouchBar();
    }

    /**
     * �ԂƎR��`��
     * 
     * @param g �O���t�B�b�N�X�I�u�W�F�N�g
     */
    public void draw(Graphics g) {
        int x1, y1;  // �_1�̐�[���W
        int x2, y2;  // �_2�̐�[���W
        
        int width = MainPanel.WIDTH;
        int height = MainPanel.HEIGHT;
        
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        
        // 100�̓X�P�[��
        x1 = (int) ((L1 * 100) * Math.sin(state.theta1) + width/2);
        y1 = (int) ((L1 * 100) * Math.cos(state.theta1) + height/2);

        x2 = (int) ((L1 * 100) * Math.sin(state.theta1) + (L2 * 100)
                * Math.sin(state.theta1 + state.theta2) + width/2);
        y2 = (int) ((L1 * 100) * Math.cos(state.theta1) + (L2 * 100)
                * Math.cos(state.theta1 + state.theta2) + 200); 
        
        g.setColor(Color.RED);
        // ���S����_1�̐�[���W�܂Œ�����`��
        g.drawLine(width/2, height/2, x1, y1);
        
        g.setColor(Color.BLUE);
        // �_1�̐�[���W����_2�̐�[���W�܂Œ�����`��
        g.drawLine(x1, y1, x2, y2);
        
        g.setColor(Color.DARK_GRAY);
        g.drawLine(0, BAR_HEIGHT, width, BAR_HEIGHT);
    }
}