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
    // �W�����v��
    private static final int JUMP_SPEED = 24;
    // �d��
    private static final double GRAVITY = 1.0;

    // �ʒu
    private double x;
    private double y;

    // ���x
    private double vx;
    private double vy;

    // ���n���Ă��邩
    private boolean onGround;

    public Player(double x, double y) {
        this.x = x;
        this.y = y;
        vx = 0;
        vy = 0;
        onGround = false;
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
     * �W�����v����
     */
    public void jump() {
        if (onGround) {
            // ������ɑ��x��������
            vy = -JUMP_SPEED;
            onGround = false;
        }
    }

    /**
     * �v���C���[�̏�Ԃ��X�V����
     */
    public void update() {
        // �d�͂ŉ������ɉ����x��������
        vy += GRAVITY;

        // ���x�����Ɉʒu���X�V
        x += vx;
        y += vy;
        // ���n���������ׂ�
        if (y > MainPanel.HEIGHT - HEIGHT) {
            vy = 0;
            y = MainPanel.HEIGHT - HEIGHT;
            onGround = true;
        }
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