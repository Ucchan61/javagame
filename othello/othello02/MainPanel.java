/*
 * �쐬��: 2004/12/17
 *
 */
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import javax.swing.*;
/**
 * @author mori
 *  
 */
public class MainPanel extends JPanel implements MouseListener {
    // �}�X�̃T�C�Y�iGRID SIZE�j
    private static final int GS = 32;
    // �}�X�̐��B�I�Z����8�~8�}�X
    private static final int MASU = 8;
    // �Ֆʂ̑傫�������C���p�l���̑傫���Ɠ���
    private static final int WIDTH = GS * MASU;
    private static final int HEIGHT = WIDTH;
    // ��
    private static final int BLANK = 0;
    // ����
    private static final int BLACK_STONE = 1;
    // ����
    private static final int WHITE_STONE = -1;

    // �Ֆ�
    private int[][] board = new int[MASU][MASU];
    // ���̔Ԃ�
    private boolean flagForWhite;
    // �΂�ł�
    private AudioClip kachi;

    public MainPanel() {
        // Othello��pack()����Ƃ��ɕK�v
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // �Ֆʂ�����������
        initBoard();
        // �T�E���h�����[�h����
        kachi = Applet.newAudioClip(getClass().getResource("kachi.wav"));
        // �}�E�X������󂯕t����悤�ɂ���
        addMouseListener(this);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // �Ֆʂ�`��
        drawBoard(g);
        // �΂�`��
        drawStone(g);
    }

    /**
     * �}�E�X���N���b�N�����Ƃ��΂�ł�
     */
    public void mouseClicked(MouseEvent e) {
        // �ǂ��̃}�X���𒲂ׂ�
        int x = e.getX() / GS;
        int y = e.getY() / GS;
        // ���̏ꏊ�ɐ΂�ł�
        putDownStone(x, y);
        // ����炷
        kachi.play();
        // �Ֆʂ��ω������̂ōĕ`�悷��
        repaint();
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
        board[3][3] = board[4][4] = WHITE_STONE;
        board[3][4] = board[4][3] = BLACK_STONE;

        flagForWhite = false;
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
        for (int y = 0; y < MASU; y++) {
            for (int x = 0; x < MASU; x++) {
                // �}�X�g��`�悷��
                g.setColor(Color.BLACK);
                g.drawRect(x * GS, y * GS, GS, GS);
            }
        }
    }

    /**
     * �΂�`���B
     * 
     * @param g �`��I�u�W�F�N�g
     */
    private void drawStone(Graphics g) {
        for (int y = 0; y < MASU; y++) {
            for (int x = 0; x < MASU; x++) {
                if (board[y][x] == BLANK) {
                    continue;
                } else if (board[y][x] == BLACK_STONE) {
                    g.setColor(Color.BLACK);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillOval(x * GS + 3, y * GS + 3, GS - 6, GS - 6);
            }
        }
    }

    /**
     * �Ֆʂɐ΂�ł�
     * 
     * @param x �΂�łꏊ��x���W
     * @param y �΂�łꏊ��y���W
     */
    private void putDownStone(int x, int y) {
        int stone;

        // �ǂ����̎�Ԃ����ׂĐ΂̐F�����߂�
        if (flagForWhite) {
            stone = WHITE_STONE;
        } else {
            stone = BLACK_STONE;
        }
        // �΂�ł�
        board[y][x] = stone;
        // ��Ԃ�ς���
        flagForWhite = !flagForWhite;
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }
}