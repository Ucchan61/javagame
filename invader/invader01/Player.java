import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

/*
 * Created on 2005/02/09
 *
 */

/**
 * �v���C���[�N���X
 * 
 * @author mori
 *  
 */
public class Player {
    // �����萔
    private static final int LEFT = 0;
    private static final int RIGHT = 1;

    // �ړ��X�s�[�h
    private static final int SPEED = 5;

    // �v���C���[�̈ʒu�ix���W�j
    private int x;
    // �v���C���[�̈ʒu�iy���W�j
    private int y;
    // �v���C���[�̕�
    private int width;
    // �v���C���[�̍���
    private int height;
    // �v���C���[�̉摜
    private Image image;

    // ���C���p�l���ւ̎Q��
    private MainPanel panel;

    public Player(int x, int y, MainPanel panel) {
        this.x = x;
        this.y = y;
        this.panel = panel;

        // �C���[�W�����[�h
        loadImage();
    }

    /**
     * �v���C���[���ړ�����
     * 
     * @param dir �ړ�����
     */
    public void move(int dir) {
        if (dir == LEFT) {
            x -= SPEED;
        } else if (dir == RIGHT) {
            x += SPEED;
        }

        // ��ʂ̊O�ɏo�Ă����璆�ɖ߂�
        if (x < 0) {
            x = 0;
        }
        if (x > MainPanel.WIDTH - width) {
            x = MainPanel.WIDTH - width;
        }
    }

    /**
     * �v���C���[��`�悷��
     * 
     * @param g �`��I�u�W�F�N�g
     */
    public void draw(Graphics g) {
        g.drawImage(image, x, y, null);
    }

    /**
     * �v���C���[�̈ʒu��Ԃ�
     * 
     * @return �v���C���[�̈ʒu���W
     */
    public Point getPos() {
        return new Point(x, y);
    }

    /**
     * �v���C���[�̕���Ԃ�
     * 
     * @param width �v���C���[�̕�
     */
    public int getWidth() {
        return width;
    }

    /**
     * �v���C���[�̍�����Ԃ�
     * 
     * @return height �v���C���[�̍���
     */
    public int getHeight() {
        return height;
    }

    /**
     * �C���[�W�����[�h����
     *  
     */
    private void loadImage() {
        // �v���C���[�̃C���[�W��ǂݍ���
        // ImageIcon���g����MediaTracker���g��Ȃ��Ă���
        ImageIcon icon = new ImageIcon(getClass().getResource(
                "image/player.gif"));
        image = icon.getImage();

        // ���ƍ������Z�b�g
        width = image.getWidth(panel);
        height = image.getHeight(panel);
    }
}