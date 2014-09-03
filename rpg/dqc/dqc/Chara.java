package dqc;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;

/*
 * �L�����N�^�[�N���X
 */

public class Chara extends TalkEvent {
    // �����萔
    public static final int DOWN = 0;
    public static final int UP = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;

    // �L�����N�^�[�t�@�C����1�s�̐l��
    protected static final int NUM_CHARAS_IN_ROW = 16;
    
    // �ړ��X�s�[�h�i�s�N�Z���P�ʁj
    protected static final int SPEED = 4;

    // �ړ��m��
    public static final double PROB_MOVE = 0.005;
    
    // �ړ��^�C�v
    public static final int STOP = 0;
    public static final int MOVE_AROUND = 1;
    
    // �����Ă������
    protected int direction;
    // �ړ��^�C�v
    protected int moveType;
    
    // �ړ��i�X�N���[���j����
    protected boolean isMoving;
    // �ړ����̏ꍇ�̈ړ��s�N�Z����
    protected int movingLength;
    
    // �A�j���[�V�����J�E���^
    protected int counter;

    // �L�����N�^�[�C���[�W�i�N���X�ϐ��Ȃ̂ŃI�u�W�F�N�g�ŋ��L�j
    protected static Image charaImage;
    
    // �}�b�v�ւ̎Q��
    protected Map map;

    public Chara(int x, int y, int imageNo, int direction, int moveType, String message, Map map) {
        super(x, y, imageNo, message);
        this.direction = direction;
        this.moveType = moveType;
        this.map = map;

        // �C���[�W�����[�h
        if (charaImage == null) {
            loadImage();
        }
        
        // �A�j���[�V�����^�C�}�[�N��
        Timer animationTimer = new Timer();
        animationTimer.schedule(new AnimationTask(), 0, 300);
    }

    /**
     * �L�����N�^�[�̕`��iEvent.draw()�̃I�[�o�[���C�h�j
     * 
     * @param g �O���t�B�b�N�X�I�u�W�F�N�g
     * @param offsetX X�����I�t�Z�b�g
     * @param offsetY Y�����I�t�Z�b�g
     */
    public void draw(Graphics g, int offsetX, int offsetY) {
        int cx = (imageNo % NUM_CHARAS_IN_ROW) * (CS * 2);
        int cy = (imageNo / NUM_CHARAS_IN_ROW) * (CS * 4);
        
        g.drawImage(charaImage,
                px - offsetX,
                py - offsetY,
                px - offsetX + CS,
                py - offsetY + CS,
                cx + counter * CS,
                cy + direction * CS,
                cx + counter * CS + CS,
                cy + direction * CS + CS,
                null);
    }

    /**
     * �ړ�����
     * 
     */
    public void move() {
        switch (direction) {
            case DOWN :
                if (moveDown()) {
                    // �ړ�����������
                }
                break;
            case UP :
                if (moveUp()) {
                    // �ړ�����������
                }
                break;
            case LEFT :
                if (moveLeft()) {
                    // �ړ�����������
                }
                break;
            case RIGHT :
                if (moveRight()) {
                    // �ړ�����������
                }
                break;
        }
    }

    /**
     * ���ֈړ�����
     * 
     * @return 1�}�X�ړ�������������true��Ԃ��B�ړ�����false��Ԃ��B
     */
    protected boolean moveDown() {
        // 1�}�X��̍��W
        int nextX = x;
        int nextY = y + 1;
        if (nextY > map.getRow() - 1) nextY = map.getRow() - 1;
        // �ړ��\�ȏꏊ�Ȃ�Έړ��J�n
        if (map.isMovable(nextX, nextY)) {
            py += SPEED;
            if (py > map.getHeight() - CS) py = map.getHeight() - CS;
            movingLength += SPEED;
            // �ړ���1�}�X���𒴂��Ă�����
            if (movingLength >= CS) {
                // ���W���X�V
                y++;
                if (y > map.getRow() - 1) y = map.getRow() - 1;
                py = y * CS;
                // �ړ�����
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
     * ��ֈړ�����
     * 
     * @return 1�}�X�ړ�������������true��Ԃ��B�ړ�����false��Ԃ��B
     */
    protected boolean moveUp() {
        // 1�}�X��̍��W
        int nextX = x;
        int nextY = y - 1;
        if (nextY < 0) nextY = 0;
        // �ړ��\�ȏꏊ�Ȃ�Έړ��J�n
        if (map.isMovable(nextX, nextY)) {
            py -= SPEED;
            if (py < 0) py = 0;
            movingLength += SPEED;
            // �ړ���1�}�X���𒴂��Ă�����
            if (movingLength >= CS) {
                // ���W���X�V
                y--;
                if (y < 0) y = 0;
                py = y * CS;
                // �ړ�����
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
     * ���ֈړ�����
     * 
     * @return 1�}�X�ړ�������������true��Ԃ��B�ړ�����false��Ԃ��B
     */
    protected boolean moveLeft() {
        // 1�}�X��̍��W
        int nextX = x - 1;
        int nextY = y;
        if (nextX < 0) nextX = 0;
        // �ړ��\�ȏꏊ�Ȃ�Έړ��J�n
        if (map.isMovable(nextX, nextY)) {
            px -= SPEED;
            if (px < 0) px = 0;
            movingLength += SPEED;
            // �ړ���1�}�X���𒴂��Ă�����
            if (movingLength >= CS) {
                // ���W���X�V
                x--;
                if (x < 0) x = 0;
                px = x * CS;
                // �ړ�����
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
     * �E�ֈړ�����
     * 
     * @return 1�}�X�ړ�������������true��Ԃ��B�ړ�����false��Ԃ��B
     */
    protected boolean moveRight() {
        // 1�}�X��̍��W
        int nextX = x + 1;
        int nextY = y;
        if (nextX > map.getCol() - 1) nextX = map.getCol() - 1;
        // �ړ��\�ȏꏊ�Ȃ�Έړ��J�n
        if (map.isMovable(nextX, nextY)) {
            px += SPEED;
            if (px > map.getWidth() - CS) px = map.getWidth() - CS;
            movingLength += SPEED;
            // �ړ���1�}�X���𒴂��Ă�����
            if (movingLength >= CS) {
                // ���W���X�V
                x++;
                if (x > map.getCol() - 1) x = map.getCol() - 1;
                px = x * CS;
                // �ړ�����
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
     * �������Z�b�g
     * 
     * @param direction �����萔
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }
    
    /**
     * ������Ԃ�
     * 
     * @return �����萔
     */
    public int getDirection() {
        return direction;
    }
    
    /**
     * �ړ��i�X�N���[���j�������ׂ�
     * @return
     */
    public boolean isMoving() {
        return isMoving;
    }
    
    /**
     * �ړ��i�X�N���[���j�������Z�b�g
     * 
     * @param flag �ړ��i�X�N���[���j���J�n����Ȃ�true
     */
    public void setMoving(boolean flag) {
        isMoving = flag;
        // �ړ�������������
        movingLength = 0;
    }
    
    /**
     * �ړ��^�C�v��Ԃ�
     * 
     * @return �ړ��^�C�v
     */
    public int getMoveType() {
        return moveType;
    }
    
    /**
     * ���b�Z�[�W��Ԃ�
     * 
     * @return ���b�Z�[�W
     */
    public String getMessage() {
        return message;
    }
    
    /**
     * �}�b�v�ւ̎Q�Ƃ��Z�b�g
     * 
     * @param map �}�b�v�ւ̎Q��
     */
    public void setMap(Map map) {
        this.map = map;
    }
    
    /**
     * �C���[�W�����[�h
     * 
     */
    private void loadImage() {
        charaImage = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("image/character.png"));
    }

    /**
     * �C�x���g�̕������Ԃ��i�f�o�b�O�p�j
     * 
     * @return �C�x���g������
     */
    public String toString() {
        return "CHARA," + x + "," + y + "," + imageNo + "," + direction + "," + moveType + "," + message;
    }

    /**
     * �A�j���[�V�����p�^�C�}�[�^�X�N�N���X
     * 
     */
    private class AnimationTask extends TimerTask {
        public void run() {
            // �J�E���^�[�̐ؑ�
            if (counter == 0) {
                counter = 1;
            } else if (counter == 1) {
                counter = 0;
            }
        }
    }

    /**
     * �C�x���g�J�n
     * 
     */
    public void start(Hero hero, Map map, MessageWindow msgWnd) {
        // ��l���̕��֌�����
        switch (hero.getDirection()) {
            case DOWN:
                setDirection(UP);
                break;
            case UP:
                setDirection(DOWN);
                break;
            case LEFT:
                setDirection(RIGHT);
                break;
            case RIGHT:
                setDirection(LEFT);
                break;
        }
        // ���b�Z�[�W���Z�b�g����
        msgWnd.setMessage(getMessage());
        // ���b�Z�[�W�E�B���h�E��\��
        msgWnd.show();
    }
}
