import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/*
 * Created on 2005/04/10
 *
 */

/**
 * �ǐՎ҃N���X
 * 
 * @author mori
 *
 */
public class Predator {
    // �O���b�h�T�C�Y
    private static final int GS = 8;
    // �ǐՉ\�ȍő勗��
    private static final int MAX_PATH_LENGTH = 4096;

    // �ʒu
    public int x;
    public int y;

    // �ǐՌo�H
    private Point[] path;
    // �ǐՌo�H���̉��X�e�b�v�߂�
    private int currentStep;

    public Predator() {
        this(0, 0);
    }

    public Predator(int x, int y) {
        this.x = x;
        this.y = y;
        
        path = new Point[MAX_PATH_LENGTH];
    }

    /**
     * �ړ�����
     */
    public void move() {
        // �����Ō�܂ňړ�������ŏ��̒n�_�֖߂�
        // �o�H�����[�v���Ă���i���ɉ�������
        if (path[currentStep] == null) {
            currentStep = 0;
        }

        x = path[currentStep].x;
        y = path[currentStep].y;
        currentStep++;
    }

    /**
     * �ǐՎ҂�`�悷��
     * 
     * @param g �`��I�u�W�F�N�g
     */
    public void draw(Graphics g) {
//        g.setColor(Color.YELLOW);
//        for (int i=0; i<MAX_PATH_LENGTH; i++) {
//            if (path[i] == null) break;
//            g.fillRect(path[i].x * GS, path[i].y * GS, GS, GS);
//        }

        g.setColor(Color.RED);
        g.fillRect(x * GS, y * GS, GS, GS);
    }
    
    
    /**
     * �o�H���쐬����
     * 
     * @param start �n�_
     * @param end �I�_
     */
    public void buildPathSegment(Point start, Point end) {       
        int nextX = start.x;
        int nextY = start.y;
        int deltaX = end.x - start.x;
        int deltaY = end.y - start.y;
        int stepX, stepY;
        int step = 0;
        int fraction;
        
        // �p�X�̍Ō�̈ʒu��T���A��������ǉ�����
        for (int i = 0; i<MAX_PATH_LENGTH; i++) {
            if (path[i] == null) {
                step = i;
                break;
            }
        }

        if (deltaX < 0) stepX = -1; else stepX = 1;
        if (deltaY < 0) stepY = -1; else stepY = 1;
        
        deltaX = Math.abs(deltaX * 2);
        deltaY = Math.abs(deltaY * 2);
        
        path[step] = new Point(nextX, nextY);
        step++;
        
        if (deltaX > deltaY) {
            fraction = deltaY * 2 - deltaX;
            while (nextX != end.x) {
                if (fraction >= 0) {
                    nextY += stepY;
                    fraction -= deltaX;
                }
                nextX += stepX;
                fraction += deltaY;
                path[step] = new Point(nextX, nextY);
                step++;
            }
        } else {
            fraction = deltaX * 2 - deltaY;
            while (nextY != end.y) {
                if (fraction >= 0) {
                    nextX += stepX;
                    fraction -= deltaY;
                }
                nextY += stepY;
                fraction += deltaX;
                path[step] = new Point(nextX, nextY);
                step++;
            }
        }
    }
    
    /**
     * �p�X��\������
     */
    public void showPath() {
        for (int i=0; i<MAX_PATH_LENGTH; i++) {
            if (path[i] != null) {
                System.out.println(path[i]);
            }
        }
    }
}
