/*
 * Created on 2005/04/16
 *
 */
import java.awt.*;
import java.util.Random;
/**
 * �ǐՎ҃N���X
 * 
 * @author mori
 *  
 */
public class Predator {
    // �O���b�h�T�C�Y
    private static final int GS = 8;
    // �ǐՉ\�ȍő勗��
    private static final int MAX_PATH_LENGTH = 256;

    // �ʒu
    public int x;
    public int y;

    private Random rand = new Random();

    public Predator() {
        this(0, 0);
    }

    public Predator(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * �u���b�h�N�����ǐ�
     * 
     * @param prey �l��
     */
    public void chase(Prey prey) {
        // ���������Ղ̃C���f�b�N�X�ԍ�
        int foundCrumb = -1;

        // �l���̑��Ղ��擾
        Point[] trail = prey.getBreadCrumb();

        // ����8�}�X�ɑ��Ղ��Ȃ������ׂ�
        // �l���ɋ߂��itrail[0]�j�ꏊ����T���Ă����̂��~�\
        // �ŏ��Ɍ�����̂̓v���C���[�Ɉ�ԋ߂��ꏊ�Ȃ̂�
        // ���񂾂�߂Â��Ă���
        for (int i = 0; i < Prey.MAX_TRAIL_LENGTH; i++) {
            if (trail[i] == null)
                break;
            // ����ɂ������I
            if (trail[i].x == x - 1 && trail[i].y == y - 1) {
                foundCrumb = i;
                break;
            }
            // ��ɂ������I
            if (trail[i].x == x && trail[i].y == y - 1) {
                foundCrumb = i;
                break;
            }
            // �E��ɂ������I
            if (trail[i].x == x - 1 && trail[i].y == y - 1) {
                foundCrumb = i;
                break;
            }
            // �E�ɂ������I
            if (trail[i].x == x + 1 && trail[i].y == y) {
                foundCrumb = i;
                break;
            }
            // �E���ɂ������I
            if (trail[i].x == x + 1 && trail[i].y == y + 1) {
                foundCrumb = i;
                break;
            }
            // ���ɂ������I
            if (trail[i].x == x && trail[i].y == y + 1) {
                foundCrumb = i;
                break;
            }
            // �����ɂ������I
            if (trail[i].x == x - 1 && trail[i].y == y + 1) {
                foundCrumb = i;
                break;
            }
            // ���ɂ������I
            if (trail[i].x == x - 1 && trail[i].y == y) {
                foundCrumb = i;
                break;
            }
        }

        if (foundCrumb >= 0) {
            // �l���̑��Ղ����������炻���Ɉړ�
            x = trail[foundCrumb].x;
            y = trail[foundCrumb].y;
        } else {
            // ������Ȃ������烉���_���Ɉړ�
            x += rand.nextInt(3) - 1;
            y += rand.nextInt(3) - 1;
        }

        if (x < 0) {
            x = 0;
        } else if (x > MainPanel.COL - 1) {
            x = MainPanel.COL - 1;
        }
        if (y < 0) {
            y = 0;
        } else if (y > MainPanel.ROW - 1) {
            y = MainPanel.ROW - 1;
        }
    }

    /**
     * �ǐՎ҂�`�悷��
     * 
     * @param g �`��I�u�W�F�N�g
     */
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x * GS, y * GS, GS, GS);
    }
}