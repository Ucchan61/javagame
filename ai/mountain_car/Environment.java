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

    // ��Ԑ�
    public static final int NUM_STATE = 320;
    // �s�����i���̊��őI���ł���s���̐��j
    public static final int NUM_ACTION = 3;

    // ���̏��
    private State state;

    /**
     * �R���X�g���N�^�B
     * 
     * @param panel �p�l���ւ̎Q�ƁB
     */
    public Environment(MainPanel panel) {
        // ��Ԃ��쐬
        state = new State();
    }

    /**
     * ��������������B
     */
    public void init() {
        state.init();
    }

    /**
     * ���̏�Ԃ�Ԃ��B
     * 
     * @return ���̏�ԁB
     */
    public State getState() {
        return state;
    }

    /**
     * ���̏�Ԃ���ӂ̔ԍ��ɕϊ����ĕԂ��B
     * 
     * @return ���̏�Ԕԍ��B
     */
    public int getStateNum() {
        int s1, s2;

        // �Ԃ̈ʒu�A���x�����Ԕԍ������߂�
        // �ʒu��20����
        if (state.pos < -1.2) {
            s1 = 0;
        } else if (state.pos < -1.1) {
            s1 = 1;
        } else if (state.pos < -1.0) {
            s1 = 2;
        } else if (state.pos < -0.9) {
            s1 = 3;
        } else if (state.pos < -0.8) {
            s1 = 4;
        } else if (state.pos < -0.7) {
            s1 = 5;
        } else if (state.pos < -0.6) {
            s1 = 6;
        } else if (state.pos < -0.5) {
            s1 = 7;
        } else if (state.pos < -0.4) {
            s1 = 8;
        } else if (state.pos < -0.3) {
            s1 = 9;
        } else if (state.pos < -0.2) {
            s1 = 10;
        } else if (state.pos < -0.1) {
            s1 = 11;
        } else if (state.pos < 0) {
            s1 = 12;
        } else if (state.pos < 0.1) {
            s1 = 13;
        } else if (state.pos < 0.2) {
            s1 = 14;
        } else if (state.pos < 0.3) {
            s1 = 15;
        } else if (state.pos < 0.4) {
            s1 = 16;
        } else if (state.pos < 0.5) {
            s1 = 17;
        } else if (state.pos < 0.6) {
            s1 = 18;
        } else {
            s1 = 19;
        }

        // ���x��16����
        if (state.vel < -0.07) {
            s2 = 0;
        } else if (state.vel < -0.06) {
            s2 = 1;
        } else if (state.vel < -0.05) {
            s2 = 2;
        } else if (state.vel < -0.04) {
            s2 = 3;
        } else if (state.vel < -0.03) {
            s2 = 4;
        } else if (state.vel < -0.02) {
            s2 = 5;
        } else if (state.vel < -0.01) {
            s2 = 6;
        } else if (state.vel < 0) {
            s2 = 7;
        } else if (state.vel < 0.01) {
            s2 = 8;
        } else if (state.vel < 0.02) {
            s2 = 9;
        } else if (state.vel < 0.03) {
            s2 = 10;
        } else if (state.vel < 0.04) {
            s2 = 11;
        } else if (state.vel < 0.05) {
            s2 = 12;
        } else if (state.vel < 0.06) {
            s2 = 13;
        } else if (state.vel < 0.07) {
            s2 = 14;
        } else {
            s2 = 15;
        }

        // �g���b�L�[������������Ə�Ԕԍ��͈�ӂɋ��܂�
        return s1 * 16 + s2;
    }

    /**
     * ���̏�Ԃ֑J�ڂ���
     * 
     * @param action �͂����������
     */
    public void nextState(int action) {
        // ���x���X�V����
        state.vel += 0.001 * (action - 1) + -0.0025 * Math.cos(3 * state.pos);
        state.bound();
        // �ʒu���X�V����
        state.pos += state.vel;
        state.bound();
    }

    /**
     * ��Ԃɉ����ĕ�V��Ԃ��B
     * 
     * @return �S�[����Ԃł�0�A����ȊO�ł�-1
     */
    public double getReward() {
        // �S�[���ɂ�����0�A����ȊO�Ȃ�-1
        if (state.isGoal()) {
            return 0;
        }

        return -1;
    }

    /**
     * 1�G�s�\�[�h���I��������true��Ԃ�
     * 
     * @return �Ԃ��S�[����Ԃɂ�����true
     */
    public boolean isEnd() {
        return state.isGoal();
    }

    /**
     * �ԂƎR��`��
     * 
     * @param g �O���t�B�b�N�X�I�u�W�F�N�g
     */
    public void draw(Graphics g) {
        // 1m������̃s�N�Z���������߂�
        int wScale = (int) (MainPanel.WIDTH / 1.8);
        int hScale = (int) ((MainPanel.HEIGHT - 20) / 2.0);

        // ���C���p�l���̑傫��
        int width = MainPanel.WIDTH;
        int height = MainPanel.HEIGHT;

        // ��ʂ��N���A����
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        // ���`��
        g.setColor(Color.DARK_GRAY);
        // 0.01m�����ɐ��łȂ��ŋȐ���`��
        Point p, oldp;
        oldp = new Point(0, (int) ((height / 2) - Math.sin(3 * State.MIN_POS)
                * hScale));
        for (double x = State.MIN_POS; x < State.MAX_POS; x += 0.01) {
            // �R��-sin3t�̋Ȑ�
            // 1.2�𑫂��Ă���͍̂��[��0�Ƃ��邽��
            p = new Point((int) ((x + 1.2) * wScale),
                    (int) ((height / 2) - Math.sin(3 * x) * hScale));
            g.drawLine(oldp.x, oldp.y, p.x, p.y);
            oldp = p;
        }

        // �G�[�W�F���g��`��
        g.fillOval((int) ((state.pos + 1.2) * wScale - 10), (int) ((height / 2)
                - Math.sin(3 * state.pos) * hScale - 10), 20, 20);
    }
}