import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.Random;

/*
 * Created on 2005/04/24
 *
 */

/**
 * @author mori
 *  
 */
public class Map {
    // �`�b�v�Z�b�g�̃T�C�Y�i�P�ʁF�s�N�Z���j
    private static final int CS = 16;

    // �s�A�񐔁i�}�X�j
    private static final int ROW = 32;
    private static final int COL = 42;

    // �n�`�l
    private static final int GROUND = 0; // �n��
    private static final int WATER = 1; // ��
    private static final int BLACK_HOME = 2; // ���a�̑�
    private static final int RED_HOME = 3; // �ԋa�̑�
    private static final int POISON = 4; // ��
    private static final int FOOD = 5; // �H�ו�

    // ���a�̑��̈ʒu
    public static final Point BLACK_HOME_POS = new Point(36, 5);
    // �ԋa�̑��̈ʒu
    public static final Point RED_HOME_POS = new Point(5, 5);

    // ���̐�
    private static final int MAX_WATER = 100;
    // �ł̐�
    private static final int MAX_POISON = 5;
    // �H�ו��̐�
    private static final int MAX_FOOD = 100;

    // �}�b�v
    private int[][] map = new int[ROW][COL];

    // �}�b�v�Z�b�g
    private Image blackHomeImage;
    private Image redHomeImage;

    private Random rand = new Random();

    /**
     * �R���X�g���N�^�B
     */
    public Map() {
        init();
    }

    /**
     * �}�b�v������������
     */
    public void init() {
        // �n�ʂŏ�����
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                map[i][j] = GROUND;
            }
        }

        // �����΂�܂�
        for (int i = 0; i < MAX_WATER; i++) {
            map[rand.nextInt(ROW)][rand.nextInt(COL)] = WATER;
        }
        // �ł��΂�܂��i���߂�I�a���񂽂��j
        for (int i = 0; i < MAX_POISON; i++) {
            map[rand.nextInt(ROW)][rand.nextInt(COL)] = POISON;
        }
        // �H�ו����΂�܂�
        for (int i = 0; i < MAX_FOOD; i++) {
            map[rand.nextInt(ROW)][rand.nextInt(COL)] = FOOD;
        }

        // ���a�̑�
        map[BLACK_HOME_POS.y][BLACK_HOME_POS.x] = BLACK_HOME;
        // �ԋa�̑�
        map[RED_HOME_POS.y][RED_HOME_POS.x] = RED_HOME;
    }

    /**
     * �}�b�v��`���B
     * 
     * @param g �w�肳�ꂽGraphics�E�B���h�E
     */
    public void draw(Graphics g) {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                // map�̒l�ɉ����ĉ摜��`��
                switch (map[i][j]) {
                    case 0 : // �n��
                        g.setColor(new Color(153, 255, 153));
                        g.fillRect(j * CS, i * CS, CS, CS);
                        break;
                    case 1 : // ��
                        g.setColor(Color.BLUE);
                        g.fillRect(j * CS, i * CS, CS, CS);
                        break;
                    case 2 : // ���a�̑�
                        g.setColor(Color.LIGHT_GRAY);
                        g.fillOval(j * CS, i * CS, CS, CS);
                        break;
                    case 3 : // �ԋa�̑�
                        g.setColor(Color.PINK);
                        g.fillOval(j * CS, i * CS, CS, CS);
                        break;
                    case 4 : // ��
                        g.setColor(new Color(204, 153, 204));
                        g.fillRect(j * CS, i * CS, CS, CS);
                        break;
                    case 5 : // �H�ו��i�����̂���j
                        g.setColor(Color.WHITE);
                        g.fillRect(j * CS, i * CS, CS, CS);
                        break;
                }
            }
        }
    }

    /**
     * (x,y)�ɂԂ�����̂����邩���ׂ�
     * 
     * @param x �}�b�v��x���W
     * @param y �}�b�v��y���W
     * @return (x,y)�ɂԂ�����̂���������true��Ԃ�
     */
    public boolean isHit(int x, int y) {
        // ��ʂ͈̔͊O��������Ԃ���
        if (x < 0 || x > COL - 1 || y < 0 || y > ROW - 1) {
            return true;
        }

        return false;
    }

    /**
     * �H�ו������邩�H
     * 
     * @param x x���W
     * @param y y���W
     * @return �H�ו�����������true
     */
    public boolean isFood(int x, int y) {
        if (map[y][x] == FOOD) {
            return true;
        }
        return false;
    }

    /**
     * �ł����邩�H
     * 
     * @param x x���W
     * @param y y���W
     * @return �ł���������true
     */
    public boolean isPoison(int x, int y) {
        if (map[y][x] == POISON) {
            return true;
        }
        return false;
    }

    /**
     * �������邩�H
     * 
     * @param x x���W
     * @param y y���W
     * @return ������������true
     */
    public boolean isWater(int x, int y) {
        if (map[y][x] == WATER) {
            return true;
        }
        return false;
    }

    /**
     * �����̒n�ʂɂ���
     * 
     * @param x x���W
     * @param y y���W
     */
    public void setGround(int x, int y) {
        map[y][x] = GROUND;
    }

    /**
     * �H�ו��������_���ȏꏊ�ɒu��
     */
    public void scatterFood() {
        int x, y;
        // �n�ʂ̏ꏊ��T��
        do {
            x = rand.nextInt(COL - 1);
            y = rand.nextInt(ROW - 1);
        } while (map[y][x] != GROUND);
        map[y][x] = FOOD;
    }

    /**
     * �ł������_���ȏꏊ�ɒu��
     */
    public void scatterPoison() {
        int x, y;
        // �n�ʂ̏ꏊ��T��
        do {
            x = rand.nextInt(COL - 1);
            y = rand.nextInt(ROW - 1);
        } while (map[y][x] != GROUND);
        map[y][x] = POISON;
    }

    /**
     * ���������_���ȏꏊ�ɒu��
     */
    public void scatterWater() {
        int x, y;
        // �n�ʂ̏ꏊ��T��
        do {
            x = rand.nextInt(COL - 1);
            y = rand.nextInt(ROW - 1);
        } while (map[y][x] != GROUND);
        map[y][x] = WATER;
    }

    /**
     * �}�b�v���R���\�[���ɕ\���B�f�o�b�O�p�B
     */
    public void show() {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }
}