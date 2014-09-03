/*
 * ���b�Z�[�W�ɓ��������E�B���h�E�N���X
 * 
 */
package dqc;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Timer;
import java.util.TimerTask;

public class MessageWindow extends Window {
    // 1�s�̍ő啶����
    private static final int MAX_CHARS_PER_LINE = 30;
    // 1�y�[�W�ɕ\���ł���ő�s��
    private static final int MAX_LINES_PER_PAGE = 5;
    // 1�y�[�W�ɕ\���ł���ő啶����
    private static final int MAX_CHARS_PER_PAGE = MAX_CHARS_PER_LINE * MAX_LINES_PER_PAGE;
    // �i�[�ł���ő�s��
    private static final int MAX_LINES = 128;
    
    // Font�ɍ��킹�ĕς��邱��
    // M2�t�H���g��16.0f���Ƃ��̃T�C�Y
    private static final int FONT_WIDTH = 16;
    private static final int FONT_HEIGHT = 18;
    
    // �e�L�X�g��\������̈�
    private Rectangle textRect;
    
    // ���b�Z�[�W���i�[����z��
    private char[] text = new char[MAX_LINES * MAX_CHARS_PER_LINE];
    // ���ݕ\�����Ă���y�[�W
    private int curPage;
    // ���݂̃y�[�W�ŕ\�������������i�ő�l: MAX_CHARS_PER_LINE * MAX_LINES_PER_PAGE�j
    private int curPos;
    // ���̃y�[�W�����邩�H
    private boolean nextFlag;
    // �E�B���h�E���B���邩�H�i�Ō�܂ŕ\��������true�ɂȂ�j
    private boolean hideFlag;
    
    // �e�L�X�g�𗬂�TimerTask
    private Timer timer;
    private TimerTask task;
    
    public MessageWindow(Rectangle textRect) {
        super(textRect);
        
        this.textRect = textRect;
        timer = new Timer();
    }
    
    public void draw(Graphics2D g) {
        super.draw(g);

        if (isVisible == false) {
            return;
        }
        
        g.setColor(Color.WHITE);
        
        // ���ݕ\�����Ă���y�[�W��curPos�܂ŕ\��
        // curPos��DrawingTimerTask�ő����Ă����̂ŗ���ĕ\�������悤�Ɍ�����
        for (int i=0; i<curPos; i++) {
            char c = text[curPage * MAX_CHARS_PER_PAGE + i];
            if (c == '/' || c == '%' || c == '!') continue;  // �R���g���[�������͕\�����Ȃ�
            int dx = textRect.x + FONT_WIDTH * (i % MAX_CHARS_PER_LINE);
            int dy = textRect.y + FONT_HEIGHT + FONT_HEIGHT * (i / MAX_CHARS_PER_LINE);
            g.drawString(c + "", dx, dy);
        }
        
        // �Ō�̃y�[�W�łȂ��ꍇ�́���\������
        if (nextFlag) {
            int dx = textRect.x + (MAX_CHARS_PER_LINE / 2) * FONT_WIDTH - 8;
            int dy = textRect.y + FONT_HEIGHT + (FONT_HEIGHT * 5);
            g.drawString("��", dx, dy);
        }
    }
    
    /**
     * ���b�Z�[�W���Z�b�g����
     * 
     * @param message ���b�Z�[�W������
     */
    public void setMessage(String message) {
        curPos = 0;
        curPage = 0;
        nextFlag = false;
        hideFlag = false;
        
        // �S�p�X�y�[�X�ŏ�����
        for (int i=0; i<text.length; i++) {
            text[i] = '�@';
        }
        
        int p = 0;  // �������̕����ʒu
        for (int i=0; i<message.length(); i++) {
            char c = message.charAt(i);
            if (c == '/') {  // ���s
                text[p] = '/';
                p += MAX_CHARS_PER_LINE;
                p = (p / MAX_CHARS_PER_LINE) * MAX_CHARS_PER_LINE;
            } else if (c == '%') {  // ���y�[�W
                text[p] = '%';
                p += MAX_CHARS_PER_PAGE;
                p = (p / MAX_CHARS_PER_PAGE) * MAX_CHARS_PER_PAGE;
            } else {
                text[p++] = c;
            }
        }
        text[p] = '!';  // �I�[�L��
        
        task = new DrawingMessageTask();
        timer.schedule(task, 0L, 20L);
    }
    
    /**
     * ���b�Z�[�W���ɐi�߂�
     * 
     * @return ���b�Z�[�W���I��������true��Ԃ�
     */
    public boolean nextMessage() {
        // ���݂̃y�[�W���Ō�̃y�[�W�������烁�b�Z�[�W���I������
        if (hideFlag) {
            task.cancel();
            task = null;
            return true;
        }
        
        // �����\������Ă��Ȃ���Ύ��̃y�[�W�ւ����Ȃ�
        if (nextFlag) {
            curPage++;
            curPos = 0;
            nextFlag = false;
            // TODO: �r�[�v��
        }
        
        return false;
    }
    
    /**
     * ���b�Z�[�W��1���������ɕ`�悷��^�X�N
     * 
     */
    private class DrawingMessageTask extends TimerTask {
        public void run() {
            if (!nextFlag) {
                curPos++;  // 1�������₷
                // �e�L�X�g�S�̂��猩�����݈ʒu
                int p = curPage * MAX_CHARS_PER_PAGE + curPos;
                if (text[p] == '/') {
                    curPos += MAX_CHARS_PER_LINE;
                    curPos = (curPos / MAX_CHARS_PER_LINE) * MAX_CHARS_PER_LINE;
                } else if (text[p] == '%') {
                    curPos += MAX_CHARS_PER_PAGE;
                    curPos = (curPos / MAX_CHARS_PER_PAGE) * MAX_CHARS_PER_PAGE;
                } else if (text[p] == '!') {
                    hideFlag = true;
                }
                
                // 1�y�[�W�̕������ɒB�����灥��\��
                if (curPos % MAX_CHARS_PER_PAGE == 0) {
                    nextFlag = true;
                }
            }
        }
    }
}
