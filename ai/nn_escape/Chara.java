import java.awt.Graphics;

/*
 * Created on 2005/05/13
 *
 */

/**
 * �ǐՎ҂Ɗl���̐e�N���X
 * @author mori
 *
 */
public abstract class Chara {
    // �ʒu
    public int x;
    public int y;

    public Chara(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * dir�Ŏw�肳�ꂽ�����Ɉړ�����
     * 012
     * 345
     * 678
     * @param dir �ړ�����
     */
    public void move(int dir) {
        switch (dir) {
            case 0:
                x--; y--;
                break;
            case 1:
                y--;
                break;
            case 2:
                x++; y--;
                break;
            case 3:
                x--;
                break;
            case 4:
                break;
            case 5:
                x++;
                break;
            case 6:
                x--; y++;
                break;
            case 7:
                y++;
                break;
            case 8:
                x++; y++;
                break;
        }
        
        // ��ʊO�ɏo�ĂȂ����`�F�b�N
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
     * �l����`�悷��
     * 
     * @param g �`��I�u�W�F�N�g
     */
    public abstract void draw(Graphics g);
}
