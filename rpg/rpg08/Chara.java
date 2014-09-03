import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

/*
 * Created on 2005/10/10
 *
 */

/**
 * @author mori
 *
 */
public class Chara implements Common { 
    // �L�����N�^�[�̃C���[�W
    private Image image;

    // �L�����N�^�[�̍��W�i�P�ʁF�}�X�j
    private int x, y;
    // �L�����N�^�[�̌����Ă�������iLEFT,RIGHT,UP,DOWN�̂ǂꂩ�j
    private int direction;
    // �L�����N�^�[�̃A�j���[�V�����J�E���^
    private int count;
    
    // �L�����N�^�[�A�j���[�V�����p�X���b�h
    private Thread threadAnime;
    
    // �}�b�v�ւ̎Q��
    private Map map;

    // �p�l���ւ̎Q��
    private MainPanel panel;

    public Chara(int x, int y, String filename, Map map, MainPanel panel) {
        this.x = x;
        this.y = y;

        direction = DOWN;
        count = 0;
        
        this.map = map;
        this.panel = panel;

        // �C���[�W�����[�h
        loadImage(filename);

        // �L�����N�^�[�A�j���[�V�����p�X���b�h�J�n
        threadAnime = new Thread(new AnimationThread());
        threadAnime.start();
    }
    
    public void draw(Graphics g, int offsetX, int offsetY) {
        // count��direction�̒l�ɉ����ĕ\������摜��؂�ւ���
        g.drawImage(image, x * CS + offsetX, y * CS + offsetY, x * CS + offsetX + CS, y * CS + offsetY + CS,
            count * CS, direction * CS, CS + count * CS, direction * CS + CS, panel);
    }

    public void move(int dir) {
        // dir�̕����łԂ���Ȃ���Έړ�����
        switch (dir) {
            case LEFT:
                if (!map.isHit(x-1, y)) x--;
                direction = LEFT;
                break;
            case RIGHT:
                if (!map.isHit(x+1, y)) x++;
                direction = RIGHT;
                break;
            case UP:
                if (!map.isHit(x, y-1)) y--;
                direction = UP;
                break;
            case DOWN:
                if (!map.isHit(x, y+1)) y++;
                direction = DOWN;
                break;
        }
    }

    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }

    private void loadImage(String filename) {
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));
        image = icon.getImage();
    }
    
    // �A�j���[�V�����N���X
    private class AnimationThread extends Thread {
        public void run() {
            while (true) {
                // count��؂�ւ���
                if (count == 0) {
                    count = 1;
                } else if (count == 1) {
                    count = 0;
                }
                
                panel.repaint();

                // 300�~���b�x�~��300�~���b�����ɃL�����N�^�[�̊G��؂�ւ���
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } 
            }
        }
    }
}
