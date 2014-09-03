import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

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
     * �G�C���A���̕���Ԃ��B
     * 
     * @param width �G�C���A���̕��B
     */
    public int getWidth() {
        return width;
    }

    /**
     * �G�C���A���̍�����Ԃ��B
     * 
     * @return height �G�C���A���̍����B
     */
    public int getHeight() {
        return height;
    }

    /**
     * �G�C���A���̈ʒu��Ԃ��B
     * 
     * @return �G�C���A���̈ʒu���W�B
     */
    public Point getPos() {
        return new Point(x, y);
    }

    /**
     * �G�C���A���̈ʒu��(x,y)�ɃZ�b�g����B
     * 
     * @param x �ړ����x���W�B
     * @param y �ړ����y���W�B
     */
    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * �G�C���A����`�悷��B
     * 
     * @param g �`��I�u�W�F�N�g�B
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