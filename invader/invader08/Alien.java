import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

/*
 * Created on 2005/03/09
 *
 */

/**
 * �G�C���A���N���X
 * 
 * @author mori
 *  
 */
public class Alien {
    // �G�C���A���̈ړ��͈�
    private static final int MOVE_WIDTH = 210;
    // �G�C���A���̕�i��ʂɕ\������Ȃ��ꏊ�j
    private static final Point TOMB = new Point(-50, -50);

    // �ړ��X�s�[�h
    private int speed;

    // �G�C���A���̈ʒu�ix���W�j
    private int x;
    // �G�C���A���̈ʒu�iy���W�j
    private int y;
    // �G�C���A���̕�
    private int width;
    // �G�C���A���̍���
    private int height;
    // �G�C���A���̉摜
    private Image image;

    // �G�C���A���̈ړ��͈�
    private int left;
    private int right;

    // �G�C���A���������Ă邩�ǂ���
    private boolean isAlive;

    // ���C���p�l���ւ̎Q��
    private MainPanel panel;

    public Alien(int x, int y, int speed, MainPanel panel) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.panel = panel;

        // �G�C���A���̏����ʒu����ړ��͈͂����߂�
        left = x;
        right = x + MOVE_WIDTH;

        isAlive = true;

        // �C���[�W�����[�h
        loadImage();
    }

    /**
     * �G�C���A�����ړ�����
     *  
     */
    public void move() {
        x += speed;

        // �ړ��͈͂𒴂��Ă����甽�]�ړ�
        if (x < left) {
            speed = -speed;
        }
        if (x > right) {
            speed = -speed;
        }
    }

    /**
     * �G�C���A���ƒe�̏Փ˂𔻒肷��
     * 
     * @param shot �Փ˂��Ă��邩���ׂ�e�I�u�W�F�N�g
     * @return �Փ˂��Ă�����true��Ԃ�
     */
    public boolean collideWith(Shot shot) {
        // �G�C���A���̋�`�����߂�
        Rectangle rectAlien = new Rectangle(x, y, width, height);
        // �e�̋�`�����߂�
        Point pos = shot.getPos();
        Rectangle rectShot = new Rectangle(pos.x, pos.y, 
                shot.getWidth(), shot.getHeight());

        // ��`���m���d�Ȃ��Ă��邩���ׂ�
        // �d�Ȃ��Ă�����Փ˂��Ă���
        return rectAlien.intersects(rectShot);
    }

    /**
     * �G�C���A�������ʁA��ֈړ�
     *  
     */
    public void die() {
        setPos(TOMB.x, TOMB.y);
        isAlive = false;
    }

    /**
     * �G�C���A���̕���Ԃ�
     * 
     * @param width �G�C���A���̕�
     */
    public int getWidth() {
        return width;
    }

    /**
     * �G�C���A���̍�����Ԃ�
     * 
     * @return height �G�C���A���̍���
     */
    public int getHeight() {
        return height;
    }

    /**
     * �G�C���A���̈ʒu��Ԃ�
     * 
     * @return �G�C���A���̈ʒu���W
     */
    public Point getPos() {
        return new Point(x, y);
    }

    /**
     * �G�C���A���̈ʒu��(x,y)�ɃZ�b�g����
     * 
     * @param x �ړ����x���W
     * @param y �ړ����y���W
     */
    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * �G�C���A���������Ă��邩
     * 
     * @return �����Ă�����true��Ԃ�
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * �G�C���A����`�悷��
     * 
     * @param g �`��I�u�W�F�N�g
     */
    public void draw(Graphics g) {
        g.drawImage(image, x, y, null);
    }

    /**
     * �C���[�W�����[�h����
     *  
     */
    private void loadImage() {
        // �G�C���A���̃C���[�W��ǂݍ���
        // ImageIcon���g����MediaTracker���g��Ȃ��Ă���
        ImageIcon icon = new ImageIcon(getClass()
                .getResource("image/alien.gif"));
        image = icon.getImage();

        // ���ƍ������Z�b�g
        width = image.getWidth(panel);
        height = image.getHeight(panel);
    }
}