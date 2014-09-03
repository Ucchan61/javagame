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
    // �L�����N�^�[�̈ړ��X�s�[�h
    private static final int SPEED = 4;

    // �L�����N�^�[�̃C���[�W
    private static Image charaImage;

    // �L�����N�^�[�ԍ�
    private int charaNo;

    // �L�����N�^�[�̍��W
    private int x, y;   // �P�ʁF�}�X
    private int px, py; // �P�ʁF�s�N�Z��

    // �L�����N�^�[�̌����Ă�������iLEFT,RIGHT,UP,DOWN�̂ǂꂩ�j
    private int direction;
    // �L�����N�^�[�̃A�j���[�V�����J�E���^
    private int count;
    
    //  �ړ����i�X�N���[�����j��
    private boolean isMoving;
    //  �ړ����̏ꍇ�̈ړ��s�N�Z����
    private int movingLength;

    // �L�����N�^�[�A�j���[�V�����p�X���b�h
    private Thread threadAnime;
    
    // �}�b�v�ւ̎Q��
    private Map map;

    public Chara(int x, int y, int charaNo, Map map) {
        this.x = x;
        this.y = y;

        px = x * CS;
        py = y * CS;

        direction = DOWN;
        count = 0;
        
        this.map = map;

        // ����̌Ăяo���̂݃C���[�W�����[�h
        if (charaImage == null) {
            loadImage();
        }

        this.charaNo = charaNo;

        // �L�����N�^�[�A�j���[�V�����p�X���b�h�J�n
        threadAnime = new Thread(new AnimationThread());
        threadAnime.start();
    }

    public void draw(Graphics g, int offsetX, int offsetY) {
        int cx = (charaNo % 8) * (CS * 2);
        int cy = (charaNo / 8) * (CS * 4);
        // count��direction�̒l�ɉ����ĕ\������摜��؂�ւ���
        g.drawImage(charaImage, px + offsetX, py + offsetY, px + offsetX + CS, py + offsetY + CS,
            cx + count * CS, cy + direction * CS, cx + CS + count * CS, cy + direction * CS + CS, null);
    }

    /**
     * �ړ������B 
     * @return 1�}�X�ړ�������������true��Ԃ��B�ړ�����false��Ԃ��B
     */
    public boolean move() {
        switch (direction) {
            case LEFT:
                if (moveLeft()) {
                    // �ړ�����������
                    return true;
                }
                break;
            case RIGHT:
                if (moveRight()) {
                    // �ړ�����������
                    return true;
                }
                break;
            case UP:
                if (moveUp()) {
                    // �ړ�����������
                    return true;
                }
                break;
            case DOWN:
                if (moveDown()) {
                    // �ړ�����������
                    return true;
                }
                break;
        }
        
        // �ړ����������Ă��Ȃ�
        return false;
    }

    /**
     * ���ֈړ�����B
     * @return 1�}�X�ړ�������������true��Ԃ��B�ړ�����false��Ԃ��B
     */
    private boolean moveLeft() {
        // 1�}�X��̍��W
        int nextX = x - 1;
        int nextY = y;
        if (nextX < 0) {
            nextX = 0;
        }
        // ���̏ꏊ�ɏ�Q�����Ȃ���Έړ����J�n
        if (!map.isHit(nextX, nextY)) {
            // SPEED�s�N�Z�����ړ�
            px -= Chara.SPEED;
            if (px < 0) {
                px = 0;
            }
            // �ړ����������Z
            movingLength += Chara.SPEED;
            // �ړ���1�}�X���𒴂��Ă�����
            if (movingLength >= CS) {
                // �ړ�����
                x--;
                px = x * CS;
                // �ړ�������
                isMoving = false;
                return true;
            }
        } else {
            isMoving = false;
            // ���̈ʒu�ɖ߂�
            px = x * CS;
            py = y * CS;
        }
        
        return false;
    }

    /**
     * �E�ֈړ�����B
     * @return 1�}�X�ړ�������������true��Ԃ��B�ړ�����false��Ԃ��B
     */
    private boolean moveRight() {
        // 1�}�X��̍��W
        int nextX = x + 1;
        int nextY = y;
        if (nextX > map.getCol() - 1) {
            nextX = map.getCol() - 1;
        }
        // ���̏ꏊ�ɏ�Q�����Ȃ���Έړ����J�n
        if (!map.isHit(nextX, nextY)) {
            // SPEED�s�N�Z�����ړ�
            px += Chara.SPEED;
            if (px > map.getWidth() - CS) {
                px = map.getWidth() - CS;
            }
            // �ړ����������Z
            movingLength += Chara.SPEED;
            // �ړ���1�}�X���𒴂��Ă�����
            if (movingLength >= CS) {
                // �ړ�����
                x++;
                px = x * CS;
                // �ړ�������
                isMoving = false;
                return true;
            }
        } else {
            isMoving = false;
            px = x * CS;
            py = y * CS;
        }
        
        return false;
    }

    /**
     * ��ֈړ�����B
     * @return 1�}�X�ړ�������������true��Ԃ��B�ړ�����false��Ԃ��B
     */
    private boolean moveUp() {
        // 1�}�X��̍��W
        int nextX = x;
        int nextY = y - 1;
        if (nextY < 0) {
            nextY = 0;
        }
        // ���̏ꏊ�ɏ�Q�����Ȃ���Έړ����J�n
        if (!map.isHit(nextX, nextY)) {
            // SPEED�s�N�Z�����ړ�
            py -= Chara.SPEED;
            if (py < 0) py = 0;
            // �ړ����������Z
            movingLength += Chara.SPEED;
            // �ړ���1�}�X���𒴂��Ă�����
            if (movingLength >= CS) {
                // �ړ�����
                y--;
                py = y * CS;
                // �ړ�������
                isMoving = false;
                return true;
            }
        } else {
            isMoving = false;
            px = x * CS;
            py = y * CS;
        }
        
        return false;
    }

    /**
     * ���ֈړ�����B
     * @return 1�}�X�ړ�������������true��Ԃ��B�ړ�����false��Ԃ��B
     */
    private boolean moveDown() {
        // 1�}�X��̍��W
        int nextX = x;
        int nextY = y + 1;
        if (nextY > map.getRow() - 1) {
            nextY = map.getRow() - 1;
        }
        // ���̏ꏊ�ɏ�Q�����Ȃ���Έړ����J�n
        if (!map.isHit(nextX, nextY)) {
            // SPEED�s�N�Z�����ړ�
            py += Chara.SPEED;
            if (py > map.getHeight() - CS) {
                py = map.getHeight() - CS;
            }
            // �ړ����������Z
            movingLength += Chara.SPEED;
            // �ړ���1�}�X���𒴂��Ă�����
            if (movingLength >= CS) {
                // �ړ�����
                y++;
                py = y * CS;
                // �ړ�������
                isMoving = false;
                return true;
            }
        } else {
            isMoving = false;
            px = x * CS;
            py = y * CS;
        }
        
        return false;
    }

    private void loadImage() {
        // �L�����N�^�[�̃C���[�W�����[�h
        ImageIcon icon = new ImageIcon(getClass().getResource("image/chara.gif"));
        charaImage = icon.getImage();
    }

    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public int getPx() {
        return px;
    }
    
    public int getPy() {
        return py;
    }

    public void setDirection(int dir) {
        direction = dir;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean flag) {
        isMoving = flag;
        // �ړ�������������
        movingLength = 0;
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
