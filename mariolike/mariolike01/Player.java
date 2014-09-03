import java.awt.Color;
import java.awt.Graphics;

/*
 * Created on 2005/06/06
 *
 */

/**
 * @author mori
 *  
 */
public class Player {
    // ��
    public static final int WIDTH = 32;
    // ����
    public static final int HEIGHT = 32;
    // �X�s�[�h
    private static final int SPEED = 6;

    // �ʒu
    private double x;
    private double y;

    // ���x
    private double vx;
    private double vy;

    public Player(double x, double y) {
        this.x = x;
        this.y = y;
        vx = 0;
        vy = 0;
    }

    /**
     * ��~����
     */
    public void stop() {
        vx = 0;
    }

    /**
     * ���ɉ�������
     */
    public void accelerateLeft() {
        vx = -SPEED;
    }

    /**
     * �E�ɉ�������
     */
    public void accelerateRight() {
        vx = SPEED;
    }

    /**
     * �v���C���[�̏�Ԃ��X�V����
     */
    public void update() {
        x += vx;
        y += vy;
    }

    /**
     * �v���C���[��`��
     * 
     * @param g �`��I�u�W�F�N�g
     */
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect((int) x, (int) y, WIDTH, HEIGHT);
    }
}