import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
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

    // �s�Ԃ̑傫��
    protected static final int LINE_HEIGHT = 8;
    // 1�s�̍ő啶����
    private static final int MAX_CHAR_IN_LINE = 20;
    // 1�y�[�W�ɕ\���ł���ő�s��
    private static final int MAX_LINES = 3;
    // 1�y�[�W�ɕ\���ł���ő啶����
    private static final int MAX_CHAR_IN_PAGE = MAX_CHAR_IN_LINE * MAX_LINES;

    // ��ԊO���̘g
    private Rectangle rect;
    // ������̘g�i�����g�����ł���悤�Ɂj
    private Rectangle innerRect;
    // ���ۂɃe�L�X�g��`�悷��g
    private Rectangle textRect;

    // ���b�Z�[�W�E�B���h�E��\������
    private boolean isVisible = false;

    // �J�[�\���̃A�j���[�V����GIF
    private Image cursorImage;
    
    // ���b�Z�[�W���i�[�����z��
    private char[] text = new char[128 * MAX_CHAR_IN_LINE];
    // �ő�y�[�W
    private int maxPage;
    // ���ݕ\�����Ă���y�[�W
    private int curPage = 0;

    // ���b�Z�[�W�G���W��
    private MessageEngine messageEngine;

    public MessageWindow(Rectangle rect) {
        this.rect = rect;

        innerRect = new Rectangle(
                rect.x + EDGE_WIDTH,
                rect.y + EDGE_WIDTH,
                rect.width - EDGE_WIDTH * 2,
                rect.height - EDGE_WIDTH * 2);

        textRect = new Rectangle(
                innerRect.x + 16,
                innerRect.y + 16,
                320,
                120);
        
        // ���b�Z�[�W�G���W�����쐬
        messageEngine = new MessageEngine();

        // �J�[�\���C���[�W�����[�h
        ImageIcon icon = new ImageIcon(getClass().getResource("image/cursor.gif"));
        cursorImage = icon.getImage();
    }

    public void draw(Graphics g) {
        if (isVisible == false) return;
        
        // �g��`��
        g.setColor(Color.WHITE);
        g.fillRect(rect.x, rect.y, rect.width, rect.height);

        // �����̘g��`��
        g.setColor(Color.BLACK);
        g.fillRect(innerRect.x, innerRect.y, innerRect.width, innerRect.height);
        
        // ���݂̃y�[�W�icurPage�j��1�y�[�W���̓��e��\��
        for (int i=0; i<MAX_CHAR_IN_PAGE; i++) {
            char c = text[curPage * MAX_CHAR_IN_PAGE + i];
            int dx = textRect.x + MessageEngine.FONT_WIDTH * (i % MAX_CHAR_IN_LINE);
            int dy = textRect.y + (LINE_HEIGHT + MessageEngine.FONT_HEIGHT) * (i / MAX_CHAR_IN_LINE);
            messageEngine.drawCharacter(dx, dy, c, g);
        }

        // �Ō�̃y�[�W�łȂ��ꍇ�͖���\������
        if (curPage < maxPage) {
            int dx = textRect.x + (MAX_CHAR_IN_LINE / 2) * MessageEngine.FONT_WIDTH - 8;
            int dy = textRect.y + (LINE_HEIGHT + MessageEngine.FONT_HEIGHT) * 3;
            g.drawImage(cursorImage, dx, dy, null);
        }
    }

    /**
     * ���b�Z�[�W���Z�b�g����
     * @param msg ���b�Z�[�W
     */
    public void setMessage(String msg) {
        curPage = 0;

        // �S�p�X�y�[�X�ŏ�����
        for (int i=0; i<text.length; i++) {
            text[i] = '�@';
        }

        int p = 0;  // �������̕����ʒu
        for (int i=0; i<msg.length(); i++) {
            char c = msg.charAt(i);
            if (c == '\\') {
                i++;
                if (msg.charAt(i) == 'n') {  // ���s
                    p += MAX_CHAR_IN_LINE;
                    p = (p / MAX_CHAR_IN_LINE) * MAX_CHAR_IN_LINE;
                } else if (msg.charAt(i) == 'f') {  // ���y�[�W
                    p += MAX_CHAR_IN_PAGE;
                    p = (p / MAX_CHAR_IN_PAGE) * MAX_CHAR_IN_PAGE;
                }
            } else {
                text[p++] = c;
            }
        }
        
        maxPage = p / MAX_CHAR_IN_PAGE;
    }
    
    /**
     * ���b�Z�[�W���ɐi�߂�
     * @return ���b�Z�[�W���I��������true��Ԃ�
     */
    public boolean nextMessage() {
        // ���݃y�[�W���Ō�̃y�[�W�������烁�b�Z�[�W���I������
        if (curPage == maxPage) {
            return true;
        }
        curPage++;
        
        return false;
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
