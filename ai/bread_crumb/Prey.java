/*
 * Created on 2005/04/16
 *
 */
import java.awt.*;
/**
 * �l���N���X
 * 
 * @author mori
 *  
 */
public class Prey {
    // �O���b�h�T�C�Y
    private static final int GS = 8;
    // �����萔
    private static final int UP = 0;
    private static final int DOWN = 1;
    private static final int LEFT = 2;
    private static final int RIGHT = 3;
    // �L�^���鑫�Ղ̍ő吔
    public static final int MAX_TRAIL_LENGTH = 128;

    // �ʒu
    public int x;
    public int y;

    // ���Քz��
    private Point[] trail;

    public Prey() {
        x = MainPanel.COL / 2;
        y = MainPanel.ROW / 2;
    }

    public Prey(int x, int y) {
        this.x = x;
        this.y = y;

        trail = new Point[MAX_TRAIL_LENGTH];
    }

    /**
     * dir�Ŏw�肳�ꂽ�����Ɉړ�����
     * 
     * @param dir �ړ�����
     */
    public void move(int dir) {
        switch (dir) {
            case UP :
                y--;
                // �ړ��ƂƂ��ɑ��Ղ��L�^����
                dropBreadCrumb();
                break;
            case DOWN :
                y++;
                dropBreadCrumb();
                break;
            case LEFT :
                x--;
                dropBreadCrumb();
                break;
            case RIGHT :
                x++;
                dropBreadCrumb();
                break;
        }
    }

    /**
     * �l����`�悷��
     * 
     * @param g �`��I�u�W�F�N�g
     */
    public void draw(Graphics g) {
        drawTrail(g);

        g.setColor(Color.BLUE);
        g.fillRect(x * GS, y * GS, GS, GS);
    }

    public Point[] getBreadCrumb() {
        return trail;
    }

    /**
     * ���Ղ��L�^����i�p�������𗎂Ƃ��j
     *  
     */
    private void dropBreadCrumb() {
        // ��ԐV�������Ղ͔z��̍ŏ��ɋL�^���邽��
        // �v�f��1�����ɂ��炷
        for (int i = MAX_TRAIL_LENGTH - 1; i > 0; i--) {
            trail[i] = trail[i - 1];
        }

        // �V�������Ղ��L�^����
        trail[0] = new Point(x, y);
    }

    /**
     * ���Ղ�\������
     * 
     * @param g �`��I�u�W�F�N�g
     */
    private void drawTrail(Graphics g) {
        g.setColor(new Color(255, 204, 153));
        for (int i = 0; i < MAX_TRAIL_LENGTH; i++) {
            if (trail[i] == null)
                break;
            g.fillRect(trail[i].x * GS, trail[i].y * GS, GS, GS);
        }
    }
}