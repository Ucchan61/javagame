import java.awt.Color;
import java.awt.Graphics;

/*
 * Created on 2005/05/13
 *
 */

/**
 * �ǐՎ҃N���X
 * �v���C���[�����삷��
 * @author mori
 *
 */
public class Predator extends Chara {
    private static final int GS = MainPanel.GS;

    public Predator(int x, int y) {
        super(x, y);
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
