/*
 * Created on 2005/03/15
 *
 */
import java.awt.*;
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
    private static final int MAX_PATH_LENGTH = 256;

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
     * LOS�ǐ�
     * 
     * @param prey �l��
     */
    public void chaseByLOS(Prey prey) {
        if (path[currentStep] == null) return;

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
        g.setColor(Color.YELLOW);
        for (int i=0; i<MAX_PATH_LENGTH; i++) {
            if (path[i] == null) break;
            g.fillRect(path[i].x * GS, path[i].y * GS, GS, GS);
        }

        g.setColor(Color.RED);
        g.fillRect(x * GS, y * GS, GS, GS);
    }
    
    /**
     * �l���܂ł̒ǐՌo�H���u���[���n���A���S���Y���ŋ��߂�
     * 
     * @param prey �l��
     */
    public void buildPathTo(Prey prey) {
        // �o�H���Čv�Z�����̂ŃJ�����g�X�e�b�v�͏�����
        currentStep = 1;
        
        if (x == prey.x && y == prey.y) return;
        
        int nextX = x;
        int nextY = y;
        int deltaX = prey.x - x;
        int deltaY = prey.y - y;
        int stepX, stepY;
        int step;
        int fraction;
        
        for (step = 0; step<MAX_PATH_LENGTH; step++) {
            path[step] = null;
        }
        
        step = 0;
        
        if (deltaX < 0) stepX = -1; else stepX = 1;
        if (deltaY < 0) stepY = -1; else stepY = 1;
        
        deltaX = Math.abs(deltaX * 2);
        deltaY = Math.abs(deltaY * 2);
        
        path[step] = new Point(nextX, nextY);
        step++;
        
        if (deltaX > deltaY) {
            fraction = deltaY - deltaX / 2;
            while (nextX != prey.x) {
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
            fraction = deltaX - deltaY / 2;
            while (nextY != prey.y) {
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
}