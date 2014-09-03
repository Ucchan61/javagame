/*
 * �쐬��: 2004/12/17
 *
 */
import java.awt.*;
import javax.swing.*;
/**
 * @author mori
 *  
 */
public class MainPanel extends JPanel {
    // �}�X�̃T�C�Y
    private static final int GS = 32;
    // �}�X�̐��B�I�Z����8�~8�}�X
    private static final int MASU = 8;
    // �Ֆʂ̑傫�������C���p�l���̑傫���Ɠ���
    private static final int WIDTH = GS * MASU;
    private static final int HEIGHT = WIDTH;
    // ��
    private static final int BLANK = 0;

    // �Ֆ�
    private int[][] board = new int[MASU][MASU];

    public MainPanel() {
        // Othello��pack()����Ƃ��ɕK�v
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // �Ֆʂ�����������
        initBoard();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // �Ֆʂ�`��
        drawBoard(g);
    }

    /**
     * �Ֆʂ�����������B
     *  
     */
    private void initBoard() {
        for (int y = 0; y < MASU; y++) {
            for (int x = 0; x < MASU; x++) {
                board[y][x] = BLANK;
            }
        }
    }

    /**
     * �Ֆʂ�`���B
     * 
     * @param g �`��I�u�W�F�N�g�B
     */
    private void drawBoard(Graphics g) {
        // �}�X��h��Ԃ�
        g.setColor(new Color(0, 128, 128));
        g.fillRect(0, 0, WIDTH, HEIGHT);
//        for (int y = 0; y < MASU; y++) {
//            for (int x = 0; x < MASU; x++) {
//                // �}�X�g��`�悷��
//                g.setColor(Color.BLACK);
//                g.drawRect(x * GS, y * GS, GS, GS);
//            }
//        }
        // �}�X�g��`��
        g.setColor(Color.black);
        // �c��
        for(int i = 1; i < MASU; i++) {
            g.drawLine(i*GS, 1, i*GS, HEIGHT);
        }
        // ����
        for(int i = 1; i < MASU; i++) {
            g.drawLine(0, i*GS, WIDTH, i*GS);
        }
        // �O�g
        g.drawRect(0, 0, WIDTH, HEIGHT);
    }
}