import java.awt.Graphics;
import java.awt.Point;

/*
 * Created on 2005/06/06
 *
 */

/**
 * @author mori
 *  
 */
public class Player extends Sprite {
    // �X�s�[�h
    private static final int SPEED = 6;
    // �W�����v��
    private static final int JUMP_SPEED = 12;

    // ����
    private static final int RIGHT = 0;
    private static final int LEFT = 1;

    // ���x
    protected double vx;
    protected double vy;

    // ���n���Ă��邩
    private boolean onGround;

    // �ăW�����v�ł��邩
    private boolean forceJump;

    // �����Ă������
    private int dir;

    public Player(double x, double y, String filename, Map map) {
        super(x, y, filename, map);
        
        vx = 0;
        vy = 0;
        onGround = false;
        forceJump = false;
        dir = RIGHT;
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
        dir = LEFT;
    }

    /**
     * �E�ɉ�������
     */
    public void accelerateRight() {
        vx = SPEED;
        dir = RIGHT;
    }

    /**
     * �W�����v����
     */
    public void jump() {
        // �n��ɂ��邩�ăW�����v�\�Ȃ�
        if (onGround || forceJump) {
            // ������ɑ��x��������
            vy = -JUMP_SPEED;
            onGround = false;
            forceJump = false;
        }
    }

    /**
     * �v���C���[�̏�Ԃ��X�V����
     */
    public void update() {
        // �d�͂ŉ������ɉ����x��������
        vy += Map.GRAVITY;

        // x�����̓����蔻��
        // �ړ�����W�����߂�
        double newX = x + vx;
        // �ړ�����W�ŏՓ˂���^�C���̈ʒu���擾
        // x���������l����̂�y���W�͕ω����Ȃ��Ɖ���
        Point tile = map.getTileCollision(this, newX, y);
        if (tile == null) {
            // �Փ˂���^�C�����Ȃ���Έړ�
            x = newX;
        } else {
            // �Փ˂���^�C��������ꍇ
            if (vx > 0) { // �E�ֈړ����Ȃ̂ŉE�̃u���b�N�ƏՓ�
                // �u���b�N�ɂ߂肱�� or ���Ԃ��Ȃ��悤�Ɉʒu����
                x = Map.tilesToPixels(tile.x) - width;
            } else if (vx < 0) { // ���ֈړ����Ȃ̂ō��̃u���b�N�ƏՓ�
                // �ʒu����
                x = Map.tilesToPixels(tile.x + 1);
            }
            vx = 0;
        }

        // y�����̓����蔻��
        // �ړ�����W�����߂�
        double newY = y + vy;
        // �ړ�����W�ŏՓ˂���^�C���̈ʒu���擾
        // y���������l����̂�x���W�͕ω����Ȃ��Ɖ���
        tile = map.getTileCollision(this, x, newY);
        if (tile == null) {
            // �Փ˂���^�C�����Ȃ���Έړ�
            y = newY;
            // �Փ˂��ĂȂ��Ƃ������Ƃ͋�
            onGround = false;
        } else {
            // �Փ˂���^�C��������ꍇ
            if (vy > 0) { // ���ֈړ����Ȃ̂ŉ��̃u���b�N�ƏՓˁi���n�j
                // �ʒu����
                y = Map.tilesToPixels(tile.y) - height;
                // ���n�����̂�y�������x��0��
                vy = 0;
                // ���n
                onGround = true;
            } else if (vy < 0) { // ��ֈړ����Ȃ̂ŏ�̃u���b�N�ƏՓˁi�V�䂲��I�j
                // �ʒu����
                y = Map.tilesToPixels(tile.y + 1);
                // �V��ɂԂ������̂�y�������x��0��
                vy = 0;
            }
        }
    }

    /**
     * �v���C���[��`��i�I�[�o�[���C�h�j
     * 
     * @param g �`��I�u�W�F�N�g
     * @param offsetX X�����I�t�Z�b�g
     * @param offsetY Y�����I�t�Z�b�g
     */
    public void draw(Graphics g, int offsetX, int offsetY) {
        g.drawImage(image,
                (int) x + offsetX, (int) y + offsetY, 
                (int) x + offsetX + width, (int) y + offsetY + height,
                count * width, dir * height,
                count * width + width, dir * height + height,
                null);
    }

    /**
     * @param forceJump The forceJump to set.
     */
    public void setForceJump(boolean forceJump) {
        this.forceJump = forceJump;
    }
}