import java.awt.Color;
import java.awt.Graphics;

/*
 * ��C�̒e
 * 
 * Created on 2006/06/02
 */

public class Shell {
    private static final double GRAVITY = 200;  // �d�́i�s�N�Z��/s^2�j
    private static final int RADIUS = 10;  // ���a

    // �ʒu
    private double x;
    private double y;
    // ���x
    private double vx;
    private double vy;
    // �g�p����
    private boolean isUsed;
    
    public Shell() {
        x = y = -10;
        vx = vy = 0;
        isUsed = false;
    }

    /**
     * ����
     * @param x ���˒n�_X���W
     * @param y ���˒n�_Y���W
     * @param speed �����i���x�̑傫���j
     * @param direction ����
     */
    public void fire(double x, double y, double speed, double direction) {
        this.x = x;
        this.y = y;
        // �����ƕ������瑬�x���v�Z
        vx = speed * Math.cos(direction);
        vy = speed * Math.sin(direction);
        isUsed = true;
    }

    /**
     * ����
     * @param x ���˒n�_X���W
     * @param y ���˒n�_Y���W
     * @param tx �^�[�Q�b�g��X���W
     * @param ty �^�[�Q�b�g��Y���W
     * @param dt ���b��Ƀ^�[�Q�b�g�ɓ�����悤�ɂ��邩
     */
    public void fire(double x, double y, double tx, double ty, double dt) {
        this.x = x;
        this.y = y;
        // �ڕW�n�_�ƌo�ߎ��Ԃ��珉�������߂�
        double dx = tx - x;
        double dy = ty - y;
        vx = dx / dt;
        vy = 1/dt * (dy - 1.0/2.0 * GRAVITY * dt * dt);
        isUsed = true;
    }

    /**
     * �e���ړ�������
     * @param dt �o�ߎ���
     */
    public void move(double dt) {
        if (!isUsed) return;
        vy += GRAVITY * dt;  // �d�͂ɂ�鑬�x�ω�
        x += vx * dt;  // �ʒu�ω�
        y += vy * dt;
        // ��ʊO�ɏo����g�p����false��
        if (x >= MainPanel.WIDTH || y >= MainPanel.HEIGHT) {
            isUsed = false;
        }
    }

    /**
     * �e����ʂɕ`��
     * @param g �O���t�B�b�N�I�u�W�F�N�g
     */
    public void draw(Graphics g) {
        if (!isUsed) return;  // �g���ĂȂ���Ε`�悵�Ȃ�
        g.setColor(Color.BLACK);
        // double�^��int�^�ɕϊ����ĕ`��
        g.fillOval((int) (x - RADIUS / 2), (int) (y - RADIUS / 2), RADIUS, RADIUS);
    }
    
    /**
     * ���̒e�͎g�p���i���˒��j���H
     * @return �g�p���Ȃ�true��Ԃ�
     */
    public boolean isUsed() {
        return isUsed;
    }
}
