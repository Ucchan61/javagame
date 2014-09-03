import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/*
 * Created on 2006/01/29
 */

/**
 * @author mori
 */
public class Wizard {
    private static final int SIZE = 8;
    // �ő�t�@�C�A�{�[����
    private static final int MAX_FIRES = 128;
    
    private int x, y;
    
    private FireBall[] fireball;
    private Teleport teleport;
    
    public Wizard(int x, int y, MainPanel panel) {
        this.x = x;
        this.y = y;
        
        // �I�u�W�F�N�g���u���炩���߁v�p��
        // �N���b�N�����Ƃ��ɍ��ƒx������
        fireball = new FireBall[MAX_FIRES];
        for (int i = 0; i < MAX_FIRES; i++) {
            fireball[i] = new FireBall();
        }
        teleport = new Teleport();
    }
    
    public void update() {
        // �t�@�C�A�{�[���̈ړ�
        for (int i=0; i<MAX_FIRES; i++) {
            fireball[i].move();
        }
    }
    
    public void fire(int targetX, int targetY) {
        Point start = new Point(x, y);
        Point target = new Point(targetX, targetY);
        for (int i = 0; i < MAX_FIRES; i++) {
            if (!fireball[i].isUsed()) { // �g���Ă��Ȃ��I�u�W�F�N�g������
                fireball[i].shot(start, target); // �t�@�C�A�I
                break; // 1��������OK�Ȃ̂Ń��[�v������
            }
        }
    }

    public void teleport(int targetX, int targetY) {
        teleport.play(this, targetX, targetY);
    }

    public boolean isInTeleport() {
        return teleport.isInTeleport();
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(x, y, SIZE, SIZE);
        
        // �t�@�C�A�{�[����`��
        for (int i = 0; i < MAX_FIRES; i++) {
            fireball[i].draw(g);
        }
        
        // �e���|�[�g��`��
        teleport.draw(g);
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x + SIZE / 2;
    }
    
    public int getY() {
        return y + SIZE / 2;
    }
}
