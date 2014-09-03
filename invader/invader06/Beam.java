import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

/*
 * Created on 2005/03/19
 *
 */

/**
 * �r�[���N���X
 * 
 * @author mori
 *  
 */
public class Beam {
    // �r�[���̃X�s�[�h
    private static final int SPEED = 5;
    // �r�[���̕ۊǍ��W�i��ʂɕ\������Ȃ��ꏊ�j
    private static final Point STORAGE = new Point(-20, -20);

    // �r�[���̈ʒu�ix���W�j
    private int x;
    // �r�[���̈ʒu�iy���W�j
    private int y;
    // �r�[���̕�
    private int width;
    // �r�[���̍���
    private int height;
    // �r�[���̉摜
    private Image image;

    // ���C���p�l���ւ̎Q��
    private MainPanel panel;

    public Beam(MainPanel panel) {
        x = STORAGE.x;
        y = STORAGE.y;
        this.panel = panel;

        // �C���[�W�����[�h
        loadImage();
    }

    /**
     * �r�[�����ړ�����
     */
    public void move() {
        // �ۊǌɂɓ����Ă���Ȃ牽�����Ȃ�
        if (isInStorage())
            return;

        // �r�[����y�����ɂ����ړ����Ȃ�
        y += SPEED;
        // ��ʊO�̃r�[���͕ۊǌɍs��
        if (y > MainPanel.HEIGHT) {
            store();
        }
    }

    /**
     * �r�[���̈ʒu��Ԃ�
     * 
     * @return �r�[���̈ʒu���W
     */
    public Point getPos() {
        return new Point(x, y);
    }

    /**
     * �r�[���̈ʒu���Z�b�g����
     * 
     * @param x �r�[����x���W
     * @param y �r�[����y���W
     */
    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * �r�[���̕���Ԃ��B
     * 
     * @param width �r�[���̕��B
     */
    public int getWidth() {
        return width;
    }

    /**
     * �r�[���̍�����Ԃ��B
     * 
     * @return height �r�[���̍����B
     */
    public int getHeight() {
        return height;
    }

    /**
     * �r�[����ۊǌɂɓ����
     */
    public void store() {
        x = STORAGE.x;
        y = STORAGE.y;
    }

    /**
     * �r�[�����ۊǌɂɓ����Ă��邩
     * 
     * @return �����Ă���Ȃ�true��Ԃ�
     */
    public boolean isInStorage() {
        if (x == STORAGE.x && y == STORAGE.x)
            return true;
        return false;
    }

    /**
     * �r�[����`�悷��
     * 
     * @param g �`��I�u�W�F�N�g
     */
    public void draw(Graphics g) {
        // �r�[����`�悷��
        g.drawImage(image, x, y, null);
    }

    /**
     * �C���[�W�����[�h����
     *  
     */
    private void loadImage() {
        ImageIcon icon = new ImageIcon(getClass().getResource("image/beam.gif"));
        image = icon.getImage();

        // ���ƍ������Z�b�g
        width = image.getWidth(panel);
        height = image.getHeight(panel);
    }
}