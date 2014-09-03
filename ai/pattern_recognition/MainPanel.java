import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

/*
 * Created on 2005/05/04
 *
 */

/**
 * @author mori
 *  
 */
public class MainPanel extends JPanel
        implements
            MouseListener,
            MouseMotionListener {
    // �O���b�h�T�C�Y
    private static final int GS = 64;
    // �s���A��
    public static final int ROW = 5;
    public static final int COL = 5;
    // �p�l���T�C�Y
    private static final int WIDTH = GS * COL;
    private static final int HEIGHT = GS * ROW;

    // �ő�p�^�[����
    private static final int MAX_PATTERN = 50;

    // �p�^�[��
    private int[] pattern;

    // �P���f�[�^�Z�b�g
    private double[][] trainingSet;
    // ���t�M��
    private double[][] teacherSet;

    // �j���[�����l�b�g�i3�w�p�[�Z�v�g�����j
    private NeuralNetwork nn;
    // ���͂����p�^�[����
    private int numPattern;

    // ���p�l���ւ̎Q��
    private InfoPanel infoPanel;

    public MainPanel(InfoPanel infoPanel) {
        // �p�l���̐����T�C�Y��ݒ�Apack()����Ƃ��ɕK�v
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        this.infoPanel = infoPanel;

        addMouseListener(this);
        addMouseMotionListener(this);

        // �p�^�[����2����������1�����z��ŕ\��
        pattern = new int[ROW * COL];

        trainingSet = new double[MAX_PATTERN][ROW * COL];
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(204, 255, 255));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // �p�^�[����`��
        g.setColor(Color.RED);
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                if (pattern[i * COL + j] == 1) {
                    g.fillRect(j * GS, i * GS, GS, GS);
                }
            }
        }

        // �i�q��`��
        g.setColor(Color.BLACK);
        for (int i = 0; i <= ROW; i++) {
            g.drawLine(0, i * GS, WIDTH, i * GS);
        }
        for (int i = 0; i <= COL + 1; i++) {
            g.drawLine(i * GS, 0, i * GS, HEIGHT);
        }
    }

    /**
     * �p�^�[����ǉ�����
     */
    public void addPattern() {
        // �P���f�[�^�z��ɕ`���ꂽ�p�^�[����ǉ�
        for (int i = 0; i < ROW * COL; i++) {
            trainingSet[numPattern][i] = pattern[i];
        }
        infoPanel.print("�p�^�[��" + numPattern + "��ǉ����܂���");

        numPattern++;
        clearPattern();
    }

    /**
     * �p�^�[������������
     */
    public void clearPattern() {
        // �p�^�[��������
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                pattern[i * COL + j] = 0;
            }
        }

        repaint();
    }

    /**
     * �P���f�[�^�����ƂɃp�^�[�����w�K����
     */
    public void learnPattern() {
        // ���͂��ꂽ�p�^�[�����ɉ����ċ��t�M���𐶐�
        teacherSet = new double[numPattern][numPattern];
        // �P�ʍs��ɂ���
        for (int i = 0; i < numPattern; i++) {
            for (int j = 0; j < numPattern; j++) {
                if (i == j) {
                    teacherSet[i][j] = 1.0;
                } else {
                    teacherSet[i][j] = 0.0;
                }
            }
        }

        nn = new NeuralNetwork();
        // ���͑w�i�p�^�[����5�~5�j
        // �B��w�i�K���Ɍ��߂��j
        // �o�͑w�i�ǉ��p�^�[�����j
        nn.init(25, 50, numPattern);

        // �P���f�[�^���w�K
        double error = 1.0;
        int count = 0;
        while ((error > 0.0001) && (count < 50000)) {
            error = 0;
            count++;
            // �e�P���f�[�^���덷���������Ȃ�܂ŌJ��Ԃ��w�K
            for (int i = 0; i < numPattern; i++) {
                // ���͂��Z�b�g
                for (int j = 0; j < trainingSet[i].length; j++) {
                    nn.setInput(j, trainingSet[i][j]);
                }
                // ���t�M�����Z�b�g
                for (int j = 0; j < numPattern; j++) {
                    nn.setTeacherValue(j, teacherSet[i][j]);
                }
                // �w�K�J�n
                nn.feedForward();
                error += nn.calculateError();
                nn.backPropagate();
            }
            error /= trainingSet.length;

            // ���p�l���֌덷���o��
            // infoPanel.print(error + "");
        }

        infoPanel.print("�w�K���������܂���");
        infoPanel.print("�p�^�[������͂��F���ł��邩�����Ă�������");
    }

    /**
     * �p�^�[���F��
     */
    public void recognizePattern() {
        // ���̓p�^�[�����Z�b�g
        for (int j = 0; j < ROW * COL; j++) {
            nn.setInput(j, pattern[j]);
        }
        // �o�͂��v�Z
        nn.feedForward();
        // �ő�o�͂��o���Ă���m�[�h�ԍ��𓾂�
        int id = nn.getMaxOutputID();

        infoPanel.print("�p�^�[��" + id + "�ł�");
        clearPattern();
    }

    public void mouseClicked(MouseEvent e) {
        int x = e.getX() / GS;
        int y = e.getY() / GS;

        if (x < 0 || x > COL - 1)
            return;
        if (y < 0 || y > ROW - 1)
            return;

        pattern[y * COL + x] = 1;

        repaint();
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
        int x = e.getX() / GS;
        int y = e.getY() / GS;

        if (x < 0 || x > COL - 1)
            return;
        if (y < 0 || y > ROW - 1)
            return;

        pattern[y * COL + x] = 1;

        repaint();
    }

    public void mouseMoved(MouseEvent e) {
    }
}