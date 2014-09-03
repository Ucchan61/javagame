import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;

/*
 * Created on 2005/04/24
 *
 */

/**
 * @author mori
 *  
 */
public class Ant {
    // �`�b�v�Z�b�g�̃T�C�Y�i�P�ʁF�s�N�Z���j
    private static final int CS = 16;

    // �a�̃^�C�v
    public static final int BLACK_ANT = 0; // ���a
    public static final int RED_ANT = 1; // �ԋa

    // �a�̓������
    public static final int FORAGE = 0; // �H�ו���T��
    public static final int GO_HOME = 1; // ���ɖ߂�
    public static final int THIRSTY = 2; // ����T��
    public static final int DEAD = 3; // ��

    private int type; // �a�̃^�C�v
    private int state; // �a�̓������
    private int x; // �a�̈ʒu
    private int y;

    private Map map; // �}�b�v�ւ̎Q��
    private MainPanel panel; // �p�l���ւ̎Q��
    private Image image; // �a�̃C���[�W

    private Random rand = new Random();

    public Ant(int type, int state, int x, int y, Map map, MainPanel panel) {
        this.type = type;
        this.state = state;
        this.x = x;
        this.y = y;

        this.map = map;
        this.panel = panel;

        // �C���[�W�����[�h
        if (type == BLACK_ANT) {
            ImageIcon icon = new ImageIcon(getClass().getResource(
                    "black_ant.gif"));
            image = icon.getImage();
        } else {
            ImageIcon icon = new ImageIcon(getClass()
                    .getResource("red_ant.gif"));
            image = icon.getImage();
        }

    }

    /**
     * ������Ԃɉ����ċa�𓮂���
     */
    public void act() {
        System.out.println(state);
        switch (state) {
            case FORAGE :
                forage();
                break;
            case GO_HOME :
                goHome();
                break;
            case THIRSTY :
                thirsty();
                break;
            case DEAD :
                dead();
                break;
        }
    }

    /**
     * �H�ו���T���ĕ������
     */
    private void forage() {
        int dir = rand.nextInt(4);
        switch (dir) {
            case 0 : // ��ֈړ�
                if (!map.isHit(x, y - 1))
                    y--;
                break;
            case 1 : // �E�ֈړ�
                if (!map.isHit(x + 1, y))
                    x++;
                break;
            case 2 : // ���ֈړ�
                if (!map.isHit(x, y + 1))
                    y++;
                break;
            case 3 : // ���ֈړ�
                if (!map.isHit(x - 1, y))
                    x--;
                break;
        }

        // �H�ו����������I
        if (map.isFood(x, y)) {
            // �H�ו�������
            map.setGround(x, y);
            // ��ԑJ��
            state = GO_HOME;
            // �V�����H�ו����Z�b�g
            map.scatterFood();
        }

        // �ł��������E�E�E
        if (map.isPoison(x, y)) {
            // �ł�����
            map.setGround(x, y);
            // ��ԑJ��
            state = DEAD;
            // �V�����ł��Z�b�g
            map.scatterPoison();
        }
    }

    /**
     * ���ɖ߂�
     */
    private void goHome() {
        int homeX, homeY;

        if (type == BLACK_ANT) {
            homeX = Map.BLACK_HOME_POS.x;
            homeY = Map.BLACK_HOME_POS.y;
        } else {
            homeX = Map.RED_HOME_POS.x;
            homeY = Map.RED_HOME_POS.y;
        }

        if (x < homeX) {
            if (!map.isHit(x + 1, y))
                x++;
        } else if (x > homeX) {
            if (!map.isHit(x - 1, y))
                x--;
        }

        if (y < homeY) {
            if (!map.isHit(x, y + 1))
                y++;
        } else if (y > homeY) {
            if (!map.isHit(x, y - 1))
                y--;
        }

        // �ł��������E�E�E
        if (map.isPoison(x, y)) {
            // �ł�����
            map.setGround(x, y);
            // ��ԑJ��
            state = DEAD;
            // �V�����ł��Z�b�g
            map.scatterPoison();
        }

        // ���ɂ���
        if (x == homeX && y == homeY) {
            // ��ԑJ��
            state = THIRSTY;
            // �V�����a���a��
            panel.birthAnt(type, homeX, homeY);
        }
    }

    /**
     * ����T��
     */
    private void thirsty() {
        int dir = rand.nextInt(4);
        switch (dir) {
            case 0 : // ��ֈړ�
                if (!map.isHit(x, y - 1))
                    y--;
                break;
            case 1 : // �E�ֈړ�
                if (!map.isHit(x + 1, y))
                    x++;
                break;
            case 2 : // ���ֈړ�
                if (!map.isHit(x, y + 1))
                    y++;
                break;
            case 3 : // ���ֈړ�
                if (!map.isHit(x - 1, y))
                    x--;
                break;
        }

        // �����������I
        if (map.isWater(x, y)) {
            // ��������
            map.setGround(x, y);
            // ��ԑJ��
            state = FORAGE;
            // �V���������Z�b�g
            map.scatterWater();
        }

        // �ł��������E�E�E
        if (map.isPoison(x, y)) {
            // �ł�����
            map.setGround(x, y);
            // ��ԑJ��
            state = DEAD;
            // �V�����ł��Z�b�g
            map.scatterPoison();
        }
    }

    /**
     * �a������
     */
    private void dead() {
        x = y = -1;
    }

    /*
     * �a��`��
     */
    public void draw(Graphics g) {
        g.drawImage(image, x * CS, y * CS, null);
    }
}