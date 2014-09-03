/*
 * �쐬��: 2004/12/16
 *
 */
import java.awt.*;
import java.util.Random;
/**
 * ���N���X�B�G�[�W�F���g�ȊO�̂��ׂĂ��Ǘ�����B
 * 
 * @author mori
 *  
 */
public class Environment {
    // ��Ԑ�
    public static final int NUM_STATE = 4 * 4 * 8 + 1;
    // �s�����i���̊��őI���ł���s���̐��j
    public static final int NUM_ACTION = 3;

    // �{�[���̃T�C�Y
    private static final int BALL_SIZE = 10;
    // ���P�b�g�̃T�C�Y
    private static final int RACKET_SIZE = MainPanel.WIDTH / 6;

    // ���̏��
    private State state;

    // �{�[���̈ʒu
    private int x, y;
    // �{�[���̑��x
    private int vx, vy;
    // �o�E���h��
    private int bound;

    // ����������
    private static final Random rand = new Random();

    /**
     * �R���X�g���N�^�B
     * 
     * @param panel �p�l���ւ̎Q�ƁB
     */
    public Environment(MainPanel panel) {
        // ��Ԃ��쐬
        state = new State();
        x = rand.nextInt(MainPanel.WIDTH);
        y = 0;
        vx = 10 - rand.nextInt(5);
        vy = 5;
        bound = 0;
    }

    /**
     * ��������������B
     */
    public void init() {
        x = rand.nextInt(MainPanel.WIDTH);
        y = 0;
        vx = 10 - rand.nextInt(5);
        vy = 5;
        bound = 0;
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
        int s1, s2, s3;

        // �{�[�����󂯑��Ȃ����玸�s�B
        if (isFailed()) {
            return NUM_STATE - 1;
        }

        // ���P�b�g�̈ʒu��4�ɕ���
        if (state.racketX == 0) {
            s1 = 0;
        } else if (state.racketX == RACKET_SIZE) {
            s1 = 1;
        } else if (state.racketX == RACKET_SIZE * 2) {
            s1 = 2;
        } else {
            s1 = 3;
        }

        // ���P�b�g�ƃ{�[���̐���������4�ɕ���
        if (state.dist < 75) {
            s2 = 0;
        } else if (state.dist < 150) {
            s2 = 1;
        } else if (state.dist < 225) {
            s2 = 2;
        } else {
            s2 = 3;
        }

        // �{�[�����x�x�N�g���̊p�x��8�ɕ���
        if (state.angle < 45) {
            s3 = 0;
        } else if (state.angle < 90) {
            s3 = 1;
        } else if (state.angle < 135) {
            s3 = 2;
        } else if (state.angle < 180) {
            s3 = 3;
        } else if (state.angle < 225) {
            s3 = 4;
        } else if (state.angle < 270) {
            s3 = 5;
        } else if (state.angle < 315) {
            s3 = 6;
        } else {
            s3 = 7;
        }

        // ��������Ə�Ԕԍ�����ӂɂȂ�
        return s1 * 4 * 8 + s2 * 8 + s3;
    }

    /**
     * ���̏�Ԃ֑J�ڂ���B
     * 
     * @param action �͂�����������B
     */
    public void nextState(int action) {
        // ���P�b�g�𓮂���
        switch (action) {
            case 0 :
                break;
            case 1 :
                state.racketX -= RACKET_SIZE;
                break;
            case 2 :
                state.racketX += RACKET_SIZE;
                break;
        }
        if (state.racketX < 0) {
            state.racketX = 0;
        } else if (state.racketX > MainPanel.WIDTH - RACKET_SIZE) {
            state.racketX = MainPanel.WIDTH - RACKET_SIZE;
        }

        // �{�[���̈ʒu���X�V
        x += vx;
        y += vy;
        // �ǂɂ��������璵�˕Ԃ�
        if (x < 0) {
            x = 0;
            vx = -vx;
        } else if (x > MainPanel.WIDTH - BALL_SIZE) {
            x = MainPanel.WIDTH - BALL_SIZE;
            vx = -vx;
        }
        // �ǂ܂��̓��P�b�g�ɂ��������璵�˕Ԃ�
        if (y < 0) {
            y = 0;
            vy = -vy;
        } else if (y > MainPanel.HEIGHT - BALL_SIZE
                && x + BALL_SIZE / 2 >= state.racketX
                && x + BALL_SIZE / 2 <= state.racketX + RACKET_SIZE) {
            y = MainPanel.HEIGHT - BALL_SIZE;
            vy = -vy;
            // �o�E���h����+1
            bound++;
        }

        state.dist = MainPanel.HEIGHT - y;
        state.angle = Math.atan((double) vy / vx);
        state.angle = state.angle * 180 / Math.PI;
        if (vx > 0 && vy < 0) {
            state.angle = -state.angle;
        } else if (vx > 0 && vy > 0) {
            state.angle = 360 - state.angle;
        } else if (vx < 0 && vy < 0) {
            state.angle = 180 - state.angle;
        } else if (vx < 0 && vy > 0) {
            state.angle = 180 - state.angle;
        }
    }

    /**
     * ��Ԃɉ����ĕ�V��Ԃ��B
     * 
     * @return ���s��Ԃ�-1000�A����ȊO��0�B
     */
    public double getReward() {
        // �S�[���ɂ�����0�A����ȊO�Ȃ�-1000
        if (isFailed()) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * ���P�b�g�Ń{�[���𒵂˕Ԃ������B
     * 
     * @return ���s������true��Ԃ��B
     */
    public boolean isFailed() {
        if (y > MainPanel.HEIGHT) {
            return true;
        }
        return false;
    }

    /**
     * �{�[���ƃ��P�b�g��`��
     * 
     * @param g �O���t�B�b�N�X�I�u�W�F�N�g�B
     */
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        // �{�[����`��
        g.fillOval(x, y, BALL_SIZE, BALL_SIZE);

        g.setColor(Color.MAGENTA);
        // ���P�b�g��`��
        g.fillRect(state.racketX, MainPanel.HEIGHT - 2, RACKET_SIZE, 2);
    }

    /**
     * �o�E���h�����Z�b�g����B
     * 
     * @param bound �o�E���h���B
     */
    public void setBound(int bound) {
        this.bound = bound;
    }

    /**
     * �o�E���h����Ԃ��B
     * 
     * @return �o�E���h���B
     */
    public int getBound() {
        return bound;
    }
}