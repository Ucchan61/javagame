/*
 * Created on 2005/02/15
 *
 */

/**
 * ���̏�Ԃ�\���N���X
 * 
 * @author mori
 *  
 */
public class State {
    
    // ��̖_�̊p�x
    public double theta1;
    // ��̖_�̊p���x
    public double thetaDot1;
    // ���̖_�̊p�x
    public double theta2;
    // ���̖_�̊p���x
    public double thetaDot2;
    
    public State() {
        this(0.0, 0.0, 0.0, 0.0);
    }

    public State(double theta1, double thetaDot1, double theta2, double thetaDot2) {
        this.theta1 = theta1;
        this.thetaDot1 = thetaDot1;
        this.theta2 = theta2;
        this.thetaDot2 = thetaDot2;
    }

    /**
     * ��Ԃ�����������
     *
     */
    public void init() {
        theta1 = thetaDot1 = theta2 = thetaDot2 = 0.0;
    }
    
    /**
     * �_2�̐�[���o�[�̏�ɂ��邩�ǂ���
     * 
     * @return �o�[�̏�ɂ����true��Ԃ�
     */
    public boolean isTouchBar() {
        int y2;

        // ����̍��W
        y2 = (int) ((Environment.L1 * 100) * Math.cos(theta1) + (Environment.L2 * 100)
                * Math.cos(theta1 + theta2) + 200);
        if (y2 < Environment.BAR_HEIGHT) {
            return true;
        } else {
            return false;
        }
    }
}