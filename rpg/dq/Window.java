import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/*
 * Created on 2006/02/05
 */

/**
 * @author mori
 */
public abstract class Window {
    // ���g�̕�
    protected static final int EDGE_WIDTH = 2;
    // �s�Ԃ̑傫��
    protected static final int LINE_HEIGHT = 8;

    // ��ԊO���̘g
    protected Rectangle rect;
    // ������̘g�i�����g�����ł���悤�Ɂj
    protected Rectangle innerRect;
    // ���ۂɃe�L�X�g��`�悷��g
    protected Rectangle textRect;
    
    // ���b�Z�[�W�E�B���h�E��\������
    protected boolean isVisible = false;
    
    // ���b�Z�[�W�G���W��
    protected MessageEngine messageEngine;
    
    public Window(Rectangle rect) {
        this.rect = rect;
        
        innerRect = new Rectangle(
                rect.x + EDGE_WIDTH,
                rect.y + EDGE_WIDTH,
                rect.width - EDGE_WIDTH * 2,
                rect.height - EDGE_WIDTH * 2);

        textRect = new Rectangle(
                innerRect.x + 16,
                innerRect.y + 16,
                320,  // TODO:
                120);
        
        // ���b�Z�[�W�G���W�����쐬
        messageEngine = new MessageEngine();
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
    
    public void show() {
        isVisible = true;
    }

    public void hide() {
        isVisible = false;
    }
    
    public boolean isVisible() {
        return isVisible;
    }
}
