import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

/*
 * Created on 2006/01/29
 */

/**
 * @author mori
 */
public class FireBall {
    // �ʒu
    private double x, y;
    // ���x�i�����Ƒ傫���j
    private double vx, vy;
    private double speed;

    // �C���[�W
    private static Image fireballImage;

    // �g�p����
    private boolean used;

    // MainPanel�ւ̎Q��
    private MainPanel panel;

    public FireBall() {
        x = y = -10;
        vx = vy = 0;
        speed = 10;

        used = false;
        
        // �t�@�C�A�{�[���̃C���[�W��ǂݍ���
        ImageIcon icon = new ImageIcon(getClass().getResource("fireball.gif"));
        fireballImage = icon.getImage();
    }

    /**
     * �t�@�C�A�{�[�������
     * 
     * @param start �n�_
     * @param target �I�_
     */
    public void shot(Point start, Point target) {
        x = start.x;
        y = start.y;
        // �n�_�ƏI�_����p�x���v�Z����
        double direction = Math.atan2(target.y - start.y, target.x - start.x);
        vx = Math.cos(direction) * speed;
        vy = Math.sin(direction) * speed;
        // System.out.println("vx: " + vx + " vy: " + vy);
        used = true;
    }

    /**
     * �ړ�
     */
    public void move() {
        x += vx;
        y += vy;

        // ��ʊO�ɏo����
        if (x < 0 || x > MainPanel.WIDTH || y < 0 || y > MainPanel.HEIGHT) {
            used = false;
        }
    }

    /**
     * �`��
     * 
     * @param g
     */
    public void draw(Graphics g) {
        g.drawImage(fireballImage, (int)x, (int)y, null);
    }

    /**
     * �t�@�C�A�{�[�����g�p����
     * 
     * @return �\�����Ȃ�true��Ԃ�
     */
    public boolean isUsed() {
        return used;
    }
}