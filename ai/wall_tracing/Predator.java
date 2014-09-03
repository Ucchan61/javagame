import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

/*
 * Created on 2005/04/16
 *
 */

/**
 * @author mori
 *  
 */
public class Predator {
    // �O���b�h�T�C�Y
    private static final int CS = 16;

    // �����萔
    private static final int UP = 0;
    private static final int DOWN = 1;
    private static final int LEFT = 2;
    private static final int RIGHT = 3;

    // �ʒu
    public int x;
    public int y;

    // ����
    private int direction;

    // �}�b�v�ւ̎Q��
    private Map map;

    // �ǐՎ҂̃C���[�W
    private Image image;

    public Predator(Map map) {
        this(0, 0, map);
    }

    public Predator(int x, int y, Map map) {
        this.x = x;
        this.y = y;
        this.map = map;
        direction = DOWN;

        // �C���[�W�����[�h
        ImageIcon icon = new ImageIcon(getClass().getResource("enemy.gif"));
        image = icon.getImage();
    }

    /**
     * ����@�ŏ��񂷂� �����Ă�������ɑ΂��č��A�O�A�E�A��̗D�揇�ʂňړ�����
     *  
     */
    public void patrol() {
        if (direction == RIGHT) { // �E�������Ă���Ƃ�
            if (!map.isHit(x, y - 1)) { // ����i�܂��j�Ɉړ��ł����
                // �ړ�����
                y--;
                // �������
                direction = UP;
            } else if (!map.isHit(x + 1, y)) { // �O�i�E�j
                x++;
                direction = RIGHT;
            } else if (!map.isHit(x, y + 1)) { // �E��i���j
                y++;
                direction = DOWN;
            } else if (!map.isHit(x - 1, y)) { // ��i���j
                x--;
                direction = LEFT;
            }
        } else if (direction == DOWN) { // ���������Ă���Ƃ�
            if (!map.isHit(x + 1, y)) { // ����i�E�j
                x++;
                direction = RIGHT;
            } else if (!map.isHit(x, y + 1)) { // �O�i���j
                y++;
                direction = DOWN;
            } else if (!map.isHit(x - 1, y)) { // �E��i���j
                x--;
                direction = LEFT;
            } else if (!map.isHit(x, y - 1)) { // ��i��j
                y--;
                direction = UP;
            }
        } else if (direction == LEFT) { // ���������Ă���Ƃ�
            if (!map.isHit(x, y + 1)) { // ����i���j
                y++;
                direction = DOWN;
            } else if (!map.isHit(x - 1, y)) { // �O�i���j
                x--;
                direction = LEFT;
            } else if (!map.isHit(x, y - 1)) { // �E��i��j
                y--;
                direction = UP;
            } else if (!map.isHit(x + 1, y)) { // ��i�E�j
                x++;
                direction = RIGHT;
            }
        } else if (direction == UP) { // ��������Ă���Ƃ�
            if (!map.isHit(x - 1, y)) { // ����i���j
                x--;
                direction = LEFT;
            } else if (!map.isHit(x, y - 1)) { // �O�i��j
                y--;
                direction = UP;
            } else if (!map.isHit(x + 1, y)) { // �E��i�E�j
                x++;
                direction = RIGHT;
            } else if (!map.isHit(x, y + 1)) { // ��i���j
                y++;
                direction = DOWN;
            }
        }
    }

    /**
     * �ǐՎ҂�`�悷��
     * 
     * @param g �`��I�u�W�F�N�g
     */
    public void draw(Graphics g) {
        g.drawImage(image, x * CS, y * CS, x * CS + CS, y * CS + CS, direction
                * CS, 0, direction * CS + CS, CS, null);
    }
}