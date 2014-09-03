import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
/*
 * Created on 2006/02/12
 *
 */

/**
 * @author mori
 *
 */
public class MessageWindow {
    // ���g�̕�
    private static final int EDGE_WIDTH = 2;

    // ��ԊO���̘g
    private Rectangle rect;
    // ������̘g�i�����g�����ł���悤�Ɂj
    private Rectangle innerRect;

    // ���b�Z�[�W�E�B���h�E��\������
    private boolean isVisible = false;

    public MessageWindow(Rectangle rect) {
        this.rect = rect;

        innerRect = new Rectangle(
                rect.x + EDGE_WIDTH,
                rect.y + EDGE_WIDTH,
                rect.width - EDGE_WIDTH * 2,
                rect.height - EDGE_WIDTH * 2);
    }

    public void draw(Graphics g) {
        if (isVisible == false) return;
        
        // �g��`��
        g.setColor(Color.WHITE);
        g.fillRect(rect.x, rect.y, rect.width, rect.height);

        // �����̘g��`��
        g.setColor(Color.BLACK);
        g.fillRect(innerRect.x, innerRect.y, innerRect.width, innerRect.height);
    }

    /**
     * �E�B���h�E��\��
     */
    public void show() {
        isVisible = true;
    }

    /**
     * �E�B���h�E���B��
     */
    public void hide() {
        isVisible = false;
    }
    
    /**
     * �E�B���h�E��\������
     */
    public boolean isVisible() {
        return isVisible;
    }
}
