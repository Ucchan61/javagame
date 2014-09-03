import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/*
 * Created on 2006/02/05
 */

/**
 * �R�}���h�E�B���h�E
 * @author mori
 */
public class CommandWindow extends Window {
    // �R�}���h�ԍ�
    public static final int TALK = 0;  // �͂Ȃ�
    public static final int STATUS = 1;  // �悳
    public static final int EQUIPMENT = 2;  // ������
    public static final int DOOR = 3;  // �Ƃт�
    public static final int SPELL = 4;  // �������
    public static final int ITEM = 5;  // �ǂ���
    public static final int TACTICS = 6;  // ��������
    public static final int SEARCH = 7;  // ����ׂ�
    
    // �E�B���h�E�ɕ\������e�L�X�g
    private String[] commands = {"�͂Ȃ�", "�悳", "������", "�Ƃт�",
            "�������", "�ǂ���", "��������", "����ׂ�"};

    // �I������Ă���R�}���h�ԍ�
    private int selectedCmdNo;
    // �J�[�\���̃C���[�W
    private BufferedImage cursorImage;

    public CommandWindow(Rectangle rect) {
        super(rect);
        
        selectedCmdNo = TALK;

        try {
            cursorImage = ImageIO.read(getClass().getResource("image/cmd_cursor.gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * �E�B���h�E��`��
     */
    public void draw(Graphics g) {
        super.draw(g);
        
        if (isVisible == false) return;

        // �͂Ȃ��A�悳�A�����сA�Ƃт�
        for (int i=0; i<4; i++) {
            int dx = textRect.x + MessageEngine.FONT_WIDTH;
            int dy = textRect.y + (LINE_HEIGHT + MessageEngine.FONT_HEIGHT) * i;
            messageEngine.drawMessage(dx, dy, commands[i], g);
        }
        
        // �������A�ǂ����A��������A����ׂ�
        for (int i=4; i<8; i++) {
            int dx = textRect.x + 116;
            int dy = textRect.y + (LINE_HEIGHT + MessageEngine.FONT_HEIGHT) * (i%4);
            messageEngine.drawMessage(dx, dy, commands[i], g);
        }
        
        int dx = textRect.x +100 * (selectedCmdNo / 4);
        int dy = textRect.y + (LINE_HEIGHT + MessageEngine.FONT_HEIGHT) * (selectedCmdNo % 4);
        g.drawImage(cursorImage, dx, dy, null);
    }

    /**
     * �J�[�\�������Ɉړ�
     */
    public void leftCursor() {
        // �͂Ȃ��A�悳�A�����сA�Ƃт�̂Ƃ��̓J�[�\�����ړ��ł��Ȃ�
        if (selectedCmdNo <= 3) return;
        selectedCmdNo -= 4;
    }

    /**
     * �J�[�\�����E�Ɉړ�
     */
    public void rightCursor() {
        // �������A�ǂ����A��������A����ׂ�̂Ƃ��̓J�[�\�����ړ��ł��Ȃ�
        if (selectedCmdNo >= 4) return;
        selectedCmdNo += 4;
    }

    /**
     * �J�[�\������Ɉړ�
     */
    public void upCursor() {
        // �͂Ȃ��A�������̂Ƃ��̓J�[�\�����ړ��ł��Ȃ�
        if (selectedCmdNo == 0 || selectedCmdNo == 4) return;
        selectedCmdNo--;
    }
    
    /**
     * �J�[�\�������Ɉړ�
     */
    public void downCursor() {
        // �Ƃт�A����ׂ�̂Ƃ��̓J�[�\�����ړ��ł��Ȃ�
        if (selectedCmdNo == 3 || selectedCmdNo == 7) return;
        selectedCmdNo++;
    }

    /**
     * �I������Ă���R�}���h�ԍ���Ԃ�
     * @return �R�}���h�ԍ�
     */
    public int getSelectedCmdNo() {
        return selectedCmdNo;
    }
    
    // �I�[�o�[���C�h
    public void show() {
        WaveEngine.play(Sound.BEEP);
        isVisible = true;
        selectedCmdNo = TALK;  // �J�[�\���͂͂Ȃ��ɏ�����
    }
}
