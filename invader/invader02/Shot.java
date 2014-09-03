import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

/*
 * Created on 2005/02/17
 *
 */

/**
 * �e�N���X
 * 
 * @author mori
 *  
 */
public class Shot {
    // �e�̃X�s�[�h
    private static final int SPEED = 10;
    // �e�̕ۊǍ��W�i��ʂɕ\������Ȃ��ꏊ�j
    private static final Point STORAGE = new Point(-20, -20);

    // �e�̈ʒu�ix���W�j
    private int x;
    // �e�̈ʒu�iy���W�j
    private int y;
    // �e�̕�
    private int width;
    // �e�̍���
    private int height;
    // �e�̉摜
    private Image image;

    // ���C���p�l���ւ̎Q��
    private MainPanel panel;

    public Shot(MainPanel panel) {
        x = STORAGE.x;
        y = STORAGE.y;
        this.panel = panel;

        // �C���[�W�����[�h
        loadImage();
    }

    /**
     * �e���ړ�����
     *  
     */
    public void move() {
        // �ۊǌɂɓ����Ă���Ȃ牽�����Ȃ�
        if (isInStorage())
            return;

        // �e��y�����ɂ����ړ����Ȃ�
        y -= SPEED;
        // ��ʊO�̒e�͕ۊǌɍs��
        if (y < 0) {
            store();
        }
    }

    /**
     * �e�̈ʒu��Ԃ�
     * 
     * @return �e�̈ʒu���W
     */
    public Point getPos() {
        return new Point(x, y);
    }

    /**
     * �e�̈ʒu���Z�b�g����
     * 
     * @param x �e��x���W
     * @param y �e��y���W
     *  
     */
    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * �e��ۊǌɂɓ����
     *  
     */
    public void store() {
        x = STORAGE.x;
        y = STORAGE.y;
    }

    /**
     * �e���ۊǌɂɓ����Ă��邩
     * 
     * @return �����Ă���Ȃ�true��Ԃ�
     */
    public boolean isInStorage() {
        if (x == STORAGE.x && y == STORAGE.x)
            return true;
        return false;
    }

    /**
     * �e��`�悷��
     * 
     * @param g �`��I�u�W�F�N�g
     */
    public void draw(Graphics g) {
        // �e��`�悷��
        g.drawImage(image, x, y, null);
    }

    /**
     * �C���[�W�����[�h����
     *  
     */
    private void loadImage() {
        ImageIcon icon = new ImageIcon(getClass().getResource("image/shot.gif"));
        image = icon.getImage();

        // ���ƍ������Z�b�g
        width = image.getWidth(panel);
        height = image.getHeight(panel);
    }

}