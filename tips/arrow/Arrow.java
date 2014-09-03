import java.awt.Color;
import java.awt.Graphics;

/*
 * ��
 * 
 * Created on 2006/06/02
 */

public class Arrow {
    private static final double GRAVITY = 200; // �d�́i�s�N�Z��/s^2�j
    private static final int LENGTH = 20; // ��̒���

    // �ʒu
    private double x;
    private double y;
    // ���x
    private double vx;
    private double vy;
    // �g�p����
    private boolean isUsed;

    public Arrow() {
        x = y = -10;
        vx = vy = 0;
        isUsed = false;
    }

    /**
     * ����
     * 
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
     * 
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
        vy = 1 / dt * (dy - 1.0 / 2.0 * GRAVITY * dt * dt);
        isUsed = true;
    }

    /**
     * ����ړ�������
     * 
     * @param dt �o�ߎ���
     */
    public void move(double dt) {
        if (!isUsed)
            return;
        vy += GRAVITY * dt; // �d�͂ɂ�鑬�x�ω�
        x += vx * dt; // �ʒu�ω�
        y += vy * dt;
        // ��ʊO�ɏo����g�p����false��
        if (x >= MainPanel.WIDTH || y >= MainPanel.HEIGHT) {
            isUsed = false;
        }
    }

    /**
     * �����ʂɕ`��
     * 
     * @param g �O���t�B�b�N�I�u�W�F�N�g
     */
    public void draw(Graphics g) {
        if (!isUsed)
            return; // �g���ĂȂ���Ε`�悵�Ȃ�
        g.setColor(Color.BLACK);

        double slope = vy / vx; // ��̌X��
        double theta = Math.atan(slope); // �X���̊p�x
        double lengthX = LENGTH * Math.cos(theta); // ���X���֎ˉe��������

        // ��̎n�_���W
        // (x,y)�ł̐ڐ��̕����������߂č��W���v�Z����
        double headX = x + lengthX / 2;
        double headY = y + slope * (lengthX / 2);

        // ��̏I�_���W
        double tailX = x - lengthX / 2;
        double tailY = y + slope * (-lengthX / 2);

        g.drawLine((int) headX, (int) headY, (int) tailX, (int) tailY);
    }

    /**
     * ���̖�͎g�p���i���˒��j���H
     * 
     * @return �g�p���Ȃ�true��Ԃ�
     */
    public boolean isUsed() {
        return isUsed;
    }
}
