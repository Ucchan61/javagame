/*
 * Created on 2005/01/16
 *
 */
import java.awt.*;
/**
 * �ǐՎ҃N���X
 * 
 * @author mori
 *  
 */
public class Predator {
    // �O���b�h�T�C�Y
    private static final int GS = 8;
    // �ʒu
    public int x;
    public int y;

    public Predator() {
        this(0, 0);
    }

    public Predator(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * �ł���{�I�ȕ��@�Ŋl����ǐՂ���
     * 
     * @param prey �l��
     */
    public void chase(Prey prey) {
        if (x > prey.x) {
            x--;
        } else if (x < prey.x) {
            x++;
        }

        if (y > prey.y) {
            y--;
        } else if (y < prey.y) {
            y++;
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