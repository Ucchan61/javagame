/*
 * Created on 2005/01/21
 *
 */

/**
 * ���̏�Ԃ�\���N���X�B
 * @author mori
 *
 */
public class State {
    // ��Ԃ̈ʒu
    public double x;
    // ��Ԃ̑��x
    public double xDot;
    // �_�̊p�x
    public double theta;
    // �_�̊p���x
    public double thetaDot;

    public State() {
        this(0, 0, 0, 0);
    }

    public State(double x, double xDot, double theta, double thetaDot) {
        this.x = x;
        this.xDot = xDot;
        this.theta = theta;
        this.thetaDot = thetaDot;
    }

    public void init() {
        this.x = 0;
        this.xDot = 0;
        this.theta = 0;
        this.thetaDot = 0;
    }
}
