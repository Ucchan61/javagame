/*
 * �h���N�G���̔��g�E�B���h�E�̋��ʃN���X
 * 
 */
package dqc;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

public class Window {
    // �O�g�ƃe�L�X�g�̈�̊Ԋu
    protected static final int FRAME_TEXT = 16;
    // �O�g�̕�
    protected static final int EDGE_WIDTH = 2;
    
    // �e�L�X�g�̈�i���ۂɃe�L�X�g��`�悷�镔���j
    protected Rectangle textRect;
    // �O�g�i��ԊO���j
    protected Rectangle frameRect;
    // �g����`�����߂̓��g�iEDGE_WIDTH���������j
    protected Rectangle innerRect;
    
    // �E�B���h�E��\������
    protected boolean isVisible = false;
    
    public Window(Rectangle textRect) {
        this.textRect = textRect;
        
        // textRect���FRAME_TEXT�����O��
        frameRect = new Rectangle(textRect.x-FRAME_TEXT, textRect.y-FRAME_TEXT,
                textRect.width+FRAME_TEXT*2, textRect.height+FRAME_TEXT*2);

        // frameRect���EDGE_WIDTH��������
        innerRect = new Rectangle(frameRect.x+EDGE_WIDTH, frameRect.y+EDGE_WIDTH,
                frameRect.width-EDGE_WIDTH*2, frameRect.height-EDGE_WIDTH*2);
    }
    
    public void draw(Graphics2D g) {
        if (isVisible == false) {
            return;
        }

        // �e�L�X�g�̃A���`�G�C���A�V���O
        g.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g.setRenderingHint(
                RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        
        g.setColor(Color.WHITE);

        // �O�g�𔒐F�ŕ`��
        g.setColor(Color.WHITE);
        g.fillRect(frameRect.x, frameRect.y, frameRect.width, frameRect.height);
        
        // ���g�����F�ŕ`��=>�O�g�ƍ��킹��Ɣ����g�Ɍ�����
        g.setColor(Color.BLACK);
        g.fillRect(innerRect.x, innerRect.y, innerRect.width, innerRect.height);
    }
    
    /**
     * �E�B���h�E��\��
     *
     */
    public void show() {
        isVisible = true;
    }
    
    /**
     * �E�B���h�E���B��
     *
     */
    public void hide() {
        isVisible = false;
    }
    
    /**
     * �\�������H
     * 
     * @return �\�����Ȃ�true
     */
    public boolean isVisible() {
        return isVisible;
    }
}
