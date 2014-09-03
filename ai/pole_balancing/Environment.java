/*
 * �쐬��: 2004/12/16
 *
 */
import java.awt.*;
/**
 * ���N���X�B�G�[�W�F���g�ȊO�̂��ׂĂ��Ǘ�����B
 * 
 * @author mori
 *  
 */
public class Environment {

    // ��Ԃ̎��ʁi�P��:kg�j
    public static final double MASS_CART = 1.0;
    // �_�̎��ʁi�P��:kg�j
    public static final double MASS_POLE = 0.1;
    public static final double TOTAL_MASS = MASS_POLE + MASS_CART;
    // �_�̔����̒����i�P��:m�j
    public static final double LENGTH = 1;
    public static final double POLE_MASS_LENGTH = MASS_POLE * LENGTH;
    // ��Ԃɉ�����́i�P��:N�j
    public static final double FORCE_MAG = 10.0;
    // �d�͉����x�i�P��:m/s^2�j
    public static final double GRAVITY = 9.8;
    // �X�e�b�v���ԁi�P��:s�j
    public static final double TAU = 0.02;

    // 4/3�i���������v�Z����͖̂ʓ|�Ȃ̂Œ萔�ɂ���j
    public static final double FOUR_THIRDS = 1.3333333333333;
    // �p�x�̃��W�A���\���i������\�ߌv�Z���Ă����j
    private static final double ONE_DEGREE = 0.0174532;
    private static final double SIX_DEGREES = 0.1047192;
    private static final double TWELVE_DEGREES = 0.2094384;
    private static final double FIFTY_DEGREES = 0.87266;
    private static final double ONE_HUNDRED_EIGHTY_DEGREES = 3.141592;

    // ��Ԑ��i3 * 3 * 6 * 3 + 1�ł��j
    public static final int NUM_STATE = 163;
    // �s�����i���̊��őI���ł���s���̐��j
    public static final int NUM_ACTION = 2;

    // ���̏��
    private State state;

    // ��Ԃ̃C���[�W
    private Image cartImage;
    // ��Ԃ̕��i�P��:�s�N�Z���j
    private int cartWidth;
    // ��Ԃ̍����i�P��:�s�N�Z���j
    private int cartHeight;
    // �n�ʂ�y���W�B
    private int groundHeight;

    /**
     * �R���X�g���N�^�B
     * 
     * @param panel �p�l���ւ̎Q�ƁB
     */
    public Environment(MainPanel panel) {
        // ��Ԃ��쐬
        state = new State();
        // �n�ʂ̏ꏊ�̓p�l���̍�����5/6�̈ʒu�Ƃ���
        groundHeight = MainPanel.HEIGHT * 5 / 6;
        // ��Ԃ̃C���[�W�����[�h����
        loadImage(panel);
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
        int s1, s2, s3, s4;

        // �_���|�ꂽ��A�͈͂𒴂����ꍇ�͎��s(162�Ԃ̏�ԂƂ���)
        if (isFailed()) {
            return 162;
        }

        // ��ԁA�_�̏�Ԃ����Ԕԍ������߂�
        // �ʒux��3�ɕ���
        if (state.x < -0.8) {
            s1 = 0;
        } else if (state.x < 0.8) {
            s1 = 1;
        } else {
            s1 = 2;
        }

        // ���xxDot��3�ɕ���
        if (state.xDot < -0.5) {
            s2 = 0;
        } else if (state.xDot < 0.5) {
            s2 = 1;
        } else {
            s2 = 2;
        }

        // �p�xtheta��6�ɕ���
        if (state.theta < -SIX_DEGREES) {
            s3 = 0;
        } else if (state.theta < -ONE_DEGREE) {
            s3 = 1;
        } else if (state.theta < 0) {
            s3 = 2;
        } else if (state.theta < ONE_DEGREE) {
            s3 = 3;
        } else if (state.theta < SIX_DEGREES) {
            s3 = 4;
        } else {
            s3 = 5;
        }

        // �p���xthetaDot��3�ɕ���
        if (state.thetaDot < -FIFTY_DEGREES) {
            s4 = 0;
        } else if (state.thetaDot < FIFTY_DEGREES) {
            s4 = 1;
        } else {
            s4 = 2;
        }

        // �g���b�L�[������������Ə�Ԕԍ��͈�ӂɋ��܂�
        // 3, 6, 3�͏�Ԃ𕪊�������
        return s1 * 3 * 6 * 3 + s2 * 6 * 3 + s3 * 3 + s4;
    }

    /**
     * ���̏�Ԃ֑J�ڂ���B ��ԁA�_�̈ʒu�̓V�~�����[�V�����v�Z�ŋ��߂�B
     * 
     * @param action �͂�����������B
     */
    public void nextState(int action) {
        // ��Ԃ̉����x
        double xAcc;
        // �_�̊p�����x
        double thetaAcc;
        // ��Ԃɉ������
        double force;
        // cos��
        double cosTheta;
        // sin��
        double sinTheta;
        double temp;

        // action�ɉ����ė͂������������ς���
        force = 0;
        switch (action) {
            case 0 :
                force = -FORCE_MAG;
                break;
            case 1 :
                force = FORCE_MAG;
                break;
        }

        // ��ԂƖ_�̉^���������ɂ��������Ď��̏�Ԃ����߂�
        // ��Ԃ̏ڂ����^����������Web���Q�Ƃ��Ă�������
        cosTheta = Math.cos(state.theta);
        sinTheta = Math.sin(state.theta);
        temp = (force + POLE_MASS_LENGTH * state.thetaDot * state.thetaDot
                * sinTheta) / TOTAL_MASS;
        thetaAcc = (GRAVITY * sinTheta - cosTheta * temp)
                / (LENGTH * (FOUR_THIRDS - MASS_POLE * cosTheta * cosTheta / TOTAL_MASS));
        xAcc = temp - POLE_MASS_LENGTH * thetaAcc * cosTheta / TOTAL_MASS;

        // ��Ԃ��X�V����
        state.x += TAU * state.xDot;
        state.xDot += TAU * xAcc;
        state.theta += TAU * state.thetaDot;
        state.thetaDot += TAU * thetaAcc;
    }

    /**
     * ��Ԃɉ����ĕ�V��Ԃ��B
     * 
     * @return ���s��Ԃ�-1000�A�|��Ă��Ȃ�������0�B
     */
    public double getReward() {
        // �S�[���ɂ�����0�A����ȊO�Ȃ�-1000
        if (isFailed()) {
            return -1000;
        } else {
            return 0;
        }
    }

    /**
     * �_���|��Ă��Ȃ���
     * 
     * @return �_���|�ꂽ��true��Ԃ�
     */
    public boolean isFailed() {
        // �_���|�ꂽ��A�͈͂𒴂����ꍇ�͎��s
        if (state.x < -2.4 || state.x > 2.4 || state.theta < -TWELVE_DEGREES
                || state.theta > TWELVE_DEGREES) {
            return true;
        }
        return false;
    }

    /**
     * �_�������Ƃ��ꂳ�����Ă��邩
     * 
     * @return ���ꂳ��������true��Ԃ�
     */
    public boolean isDroopy() {
        // �_�������Ƃ��ꂳ��������true��Ԃ�
        if ((state.theta > 0 && state.theta < ONE_HUNDRED_EIGHTY_DEGREES)
                || (state.theta < 0 && state.theta > -ONE_HUNDRED_EIGHTY_DEGREES)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * ���̕���Ԃ��B�p�l���̕��Ɠ����B
     * 
     * @return �n�ʂ̕��B
     */
    public int getWidth() {
        return MainPanel.WIDTH;
    }

    /**
     * �n�ʂ̍�����Ԃ��B
     * 
     * @return �n�ʂ̍����B
     */
    public int getGroundHeight() {
        return groundHeight;
    }

    /**
     * ��Ԃƒn�ʂ�`���B
     * 
     * @param g �O���t�B�b�N�X�I�u�W�F�N�g�B
     */
    public void draw(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, groundHeight, MainPanel.WIDTH, MainPanel.HEIGHT
                - groundHeight);

        // ��Ԃ̏d�S�̈ʒu
        Point cartPos = new Point();
        // �_�̐�[�̍��W
        Point polePos = new Point();

        // ��Ԃ̏d�S�̎����W���v�Z
        // x��100�{�ɃX�P�[���A�b�v���Ă���
        cartPos.x = (int) (getWidth() / 2 + state.x * 100);
        cartPos.y = getGroundHeight() - cartHeight / 2;

        // �_�̐�[�̎����W���v�Z
        // �_�̒�����100�{�ɃX�P�[���A�b�v���Ă���
        polePos.x = (int) (cartPos.x + 2 * LENGTH * 100 * Math.sin(state.theta));
        polePos.y = (int) (cartPos.y - 2 * LENGTH * 100 * Math.cos(state.theta));

        // ��Ԃ�`��
        g.drawImage(cartImage, cartPos.x - cartWidth / 2, cartPos.y
                - cartHeight / 2, null);
        // �_�̕`��
        g.drawLine(cartPos.x, cartPos.y, polePos.x, polePos.y);
    }

    /**
     * �C���[�W�����[�h����B
     * 
     * @param panel �p�l���ւ̎Q�ƁB
     */
    private void loadImage(MainPanel panel) {
        MediaTracker tracker = new MediaTracker(panel);

        // ��Ԃ̃C���[�W��ǂݍ���
        cartImage = Toolkit.getDefaultToolkit().getImage(
                getClass().getResource("cart.gif"));
        tracker.addImage(cartImage, 0);

        try {
            tracker.waitForAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // ��Ԃ̑傫��
        cartWidth = cartImage.getWidth(panel);
        cartHeight = cartImage.getHeight(panel);
    }
}