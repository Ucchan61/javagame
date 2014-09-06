/*
 * �쐬��: 2004/10/15
 *
 */
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.io.*;
import java.applet.*;
import java.util.*;
/**
 * ���C�t�Q�[�����C���p�l���B
 * 
 * @author mori
 *  
 */
public class MainPanel extends JPanel
        implements
            Runnable,
            MouseListener,
            KeyListener {
    // �p�l���T�C�Y
    private static final int WIDTH = 480;
    private static final int HEIGHT = 480;
    // �p�l���̃Z����
    private static final int ROW = 96;
    private static final int COL = 96;
    // �Z���̃T�C�Y
    private static final int CS = 5;
    // �Z���̐����萔
    private static final int DEAD = 0;
    private static final int ALIVE = 1;
    // �A�j���[�V�����̑��x
    private static final int SLEEP = 100;
    // �����_���ɔ�������m��
    private static final double RAND_LIFE = 0.3;

    // �t�B�[���h�B�Z������������ꏊ�B
    private int[][] field;
    // ���㐔
    private int generation;
    // �X���b�h
    private Thread thread;
    // ����������
    private Random rand;
    // ���C�t���Z�[�u�����Ƃ��̉�
    private AudioClip saveAudio;
    // ���p�l���ւ̎Q��
    private InfoPanel infoPanel;

    // �L�[�̌��݈ʒu
    private int r, c;

    public MainPanel() {
        // �p�l���̐����T�C�Y��ݒ�Apack()����Ƃ��ɕK�v
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        rand = new Random();
        // �t�B�[���h��������
        field = new int[ROW][COL];
        clear();
        // �L�[�̌��݈ʒu�̏�����
        r = c = ROW / 2 - 1;
        // ���C�t��ۑ������Ƃ��̉���ǂݍ���
        saveAudio = Applet.newAudioClip(getClass().getResource("buble03.wav"));

        // �C�x���g�n���h����o�^
        addMouseListener(this);
        addKeyListener(this);
    }

    /**
     * �����i�߂�B
     *  
     */
    public void start() {
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    /**
     * ������~�߂�B
     *  
     */
    public void stop() {
        if (thread != null) {
            thread = null;
        }
    }

    /**
     * �t�B�[���h������������B
     *  
     */
    public void clear() {
        generation = 0;
        for (int i = 0; i < ROW; i++)
            for (int j = 0; j < COL; j++)
                field[i][j] = DEAD;
        repaint();
    }

    /**
     * 1���ゾ���i�߂�B
     *  
     */
    public void step() {
        int[][] nextField = new int[ROW][COL];
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                switch (around(i, j)) {
                    case 2 :
                        // ���͂�2�Z���������Ă���΂��̂܂�
                        nextField[i][j] = field[i][j];
                        break;
                    case 3 :
                        // ���͂�3�Z���������Ă���ΔɐB
                        nextField[i][j] = ALIVE;
                        break;
                    default :
                        // ����ȊO�ł͌ǓƎ� or ������
                        nextField[i][j] = DEAD;
                        break;
                }
            }
        }
        field = nextField;
        generation++;
        repaint();
    }

    public void run() {
        while (thread != null) {
            step();
            try {
                Thread.sleep(SLEEP);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void paintComponent(Graphics g) {
        // �Z����`��
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                if (field[i][j] == ALIVE) {
                    // �����Ă����物�F
                    g.setColor(Color.YELLOW);
                } else {
                    // ����ł����獕�F
                    g.setColor(Color.BLACK);
                }
                g.fillRect(j * CS, i * CS, CS, CS);
            }
        }

        // ���S����`��
        g.setColor(Color.RED);
        g.drawLine(WIDTH / 2, 0, WIDTH / 2, HEIGHT);
        g.drawLine(0, HEIGHT / 2, WIDTH, HEIGHT / 2);

        // �L�[�̌��݈ʒu���͂�
        g.setColor(Color.WHITE);
        g.drawRect(c * CS, r * CS, CS, CS);

        // ���㐔��\��
        g.drawString("generation: " + generation, 2, 10);
    }

    /**
     * ���㐔��Ԃ��B
     * 
     * @return ���㐔�B
     */
    public int getGeneration() {
        return generation;
    }

    public void mousePressed(MouseEvent e) {
        // �t�H�[�J�X���ڂ�
        requestFocus();

        int x = e.getX();
        int y = e.getY();

        // �L�[�{�[�h�̈ʒu��ݒ�
        c = x / CS;
        r = y / CS;

        // �}�E�X�ŃN���b�N�����ʒu�̃Z���̐����𔽓]
        if (field[r][c] == DEAD) {
            field[r][c] = ALIVE;
        } else {
            field[r][c] = DEAD;
        }

        repaint();
    }

    public void mouseClicked(MouseEvent e) {
    }
    public void mouseEntered(MouseEvent e) {
    }
    public void mouseExited(MouseEvent e) {
    }
    public void mouseReleased(MouseEvent e) {
    }

    /**
     * ���W(i,j)�̎���8�����̐����Ă���Z���̐��𐔂���
     * 
     * @param i �s���W
     * @param j ����W
     * @return ����8�����̐����Ă���Z���̐�
     */
    private int around(int i, int j) {
        if (i == 0 || i == ROW - 1 || j == 0 || j == COL - 1)
            return 0;
        int sum = 0;
        sum += field[i - 1][j - 1]; // ����
        sum += field[i][j - 1]; // ��
        sum += field[i + 1][j - 1]; // ����
        sum += field[i - 1][j]; // ��
        sum += field[i + 1][j]; // ��
        sum += field[i - 1][j + 1]; // �E��
        sum += field[i][j + 1]; // �E
        sum += field[i + 1][j + 1]; // �E��

        return sum;
    }

    /**
     * ���p�l���ւ̎Q�Ƃ��Z�b�g����B
     * 
     * @param infoPanel ���p�l���B
     */
    public void setInfoPanel(InfoPanel infoPanel) {
        this.infoPanel = infoPanel;
    }

    /**
     * �t�B�[���h�ɂ��郉�C�t��ۑ�����B
     *  
     */
    public void saveLife() {
        // ���p�l�����烉�C�t�̖��O���擾
        String lifeName = infoPanel.getLifeName();
        // ���p�l�����烉�C�t�̐������擾
        String lifeInfo = infoPanel.getLifeInfo();

        // �������O���󔒂Ȃ�ۑ��ł��Ȃ�
        if (lifeName.equals("")) {
            JOptionPane.showMessageDialog(this, "���O�������Ɣ߂����ł��B", "������",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // �t�@�C���ɕۑ�����
        try {
            // ���C�t��ۑ�����t�@�C��
            File lifeFile = new File("life" + File.separator + lifeName);
            // ���łɃt�@�C��������Ȃ�㏑�����Ă������_�C�A���O��\��
            if (lifeFile.exists()) {
                int answer = JOptionPane.showConfirmDialog(this, lifeFile
                        .getName()
                        + "�Ɠ������O�����Ǐ㏑�����Ă����ł���?", "�㏑���m�F",
                        JOptionPane.YES_NO_OPTION);
                if (answer == JOptionPane.NO_OPTION)
                    return;
            } else {
                // �͂��߂Ẵ��C�t�Ȃ�R���{�{�b�N�X�Ƀ��[�h����
                // ���łɂ���t�@�C�����㏑������ꍇ�͒ǉ�����K�v�͂Ȃ�
                infoPanel.addLife(lifeName);
            }
            // �t�@�C�����J��
            PrintWriter pr = new PrintWriter(new BufferedWriter(new FileWriter(
                    lifeFile)));
            // ���C�t�̐�����ۑ�
            pr.println(lifeInfo);
            // �����Ă���Z���̏ꏊ������ۑ�
            for (int i = 0; i < ROW; i++) {
                for (int j = 0; j < COL; j++) {
                    if (field[i][j] == ALIVE) {
                        pr.println(i + " " + j);
                    }
                }
            }
            // �ۑ������Ƃ�����炷
            saveAudio.play();
            // �t�@�C�������
            pr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ���C�t���t�@�C������ǂݍ���Ŕz�u����B
     * 
     * @param filename ���C�t�������Ă���t�@�C�����B
     */
    public void loadLife(String filename) {
        // �t�B�[���h���N���A����
        clear();

        try {
            // ���C�t��ǂݍ���
            BufferedReader br = new BufferedReader(new FileReader("life"
                    + File.separator + filename));
            // ������ǂݍ���ŏ��p�l���֕\��
            String lifeInfo = br.readLine();
            infoPanel.setLifeInfo(lifeInfo);
            // �����Ă���Z���̈ʒu��ǂݍ���
            String line;
            while ((line = br.readLine()) != null) {
                StringTokenizer parser = new StringTokenizer(line);
                while (parser.hasMoreTokens()) {
                    int i = Integer.parseInt(parser.nextToken());
                    int j = Integer.parseInt(parser.nextToken());
                    // �t�B�[���h��ALIVE�ɐݒ�
                    field[i][j] = ALIVE;
                }
            }
            // �t�B�[���h���ĕ`��
            repaint();
        } catch (FileNotFoundException e) {
            // �t�@�C����������Ȃ��ꍇ�͉������Ȃ�
            // �R���{�{�b�N�X�ɐV�������͂����Ƃ��ɂ��̗�O���N����
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * �����_���Ƀ��C�t��z�u����B
     *  
     */
    public void randLife() {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                if (rand.nextDouble() < RAND_LIFE) {
                    // RAND_LIFE�̊m���ŃZ����ALIVE�ɂ���
                    field[i][j] = ALIVE;
                }
            }
        }
        repaint();
    }

    /**
     * �p�l�����L�[�{�[�h���󂯕t����悤�ɂ���B
     */
    public boolean isFocusable() {
        return true;
    }

    /**
     * �L�[���������Ƃ��J�[�\���������B
     */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT :
                c--;
                if (c < 0)
                    c = 0;
                break;
            case KeyEvent.VK_RIGHT :
                c++;
                if (c > COL - 1)
                    c = COL - 1;
                break;
            case KeyEvent.VK_UP :
                r--;
                if (r < 0)
                    r = 0;
                break;
            case KeyEvent.VK_DOWN :
                r++;
                if (r > ROW - 1)
                    r = ROW - 1;
                break;
            case KeyEvent.VK_SPACE :
                // �X�y�[�X�������Ɛ������؂�ւ��
                if (field[r][c] == ALIVE) {
                    field[r][c] = DEAD;
                } else {
                    field[r][c] = ALIVE;
                }
                break;
        }
        repaint();
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }
}