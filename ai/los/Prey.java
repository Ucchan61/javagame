/*
 * Created on 2005/01/16
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
    // �ʒu
    public int x;
    public int y;

    public Prey() {
        x = MainPanel.COL / 2;
        y = MainPanel.ROW / 2;
    }

    public Prey(int x, int y) {
        this.x = x;
        this.y = y;
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
                break;
            case DOWN :
                y++;
                break;
            case LEFT :
                x--;
                break;
            case RIGHT :
                x++;
                break;
        }
    }

    /**
     * �l����`�悷��
     * 
     * @param g �`��I�u�W�F�N�g
     */
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x * GS, y * GS, GS, GS);
    }
}