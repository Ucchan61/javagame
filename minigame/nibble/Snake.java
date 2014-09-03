/*
 * �쐬��: 2004/12/14
 *
 */
import java.awt.*;
/**
 * �ւ�\���N���X
 * 
 * @author mori
 *  
 */
public class Snake {
    /**
     * �������\���萔
     */
    public static final int UP = 1;
    /**
     * �E������\���萔
     */
    public static final int RIGHT = 2;
    /**
     * ��������\���萔
     */
    public static final int DOWN = 3;
    /**
     * ��������\���萔
     */
    public static final int LEFT = 4;

    // �ւ̍ő咷
    private static final int MAXSIZE = 256;
    // �ւ̐g�̂̍��W
    private Point[] body;
    // �ւ̓��̈ʒu
    private int head;
    // �ւ̃T�C�Y
    private int size;
    // �ւ̕���
    private int dir;
    // �ւ̃C���[�W
    private Image snakeImage;

    /**
     * (0, 0)�̍��W�Ɏւ��쐬����
     *  
     */
    public Snake(MainPanel panel) {
        this(0, 0, panel);
    }

    /**
     * (x, y)�̍��W�Ɏւ��쐬����
     * 
     * @param x �ւ�x���W
     * @param y �ւ�y���W
     */
    public Snake(int x, int y, MainPanel panel) {
        body = new Point[MAXSIZE];
        size = 1;
        head = size - 1;
        dir = RIGHT;
        for (int i = 0; i < MAXSIZE; i++) {
            body[i] = new Point(x, y);
        }
        // �ւ̃C���[�W�����[�h����
        loadImage(panel);
    }

    /**
     * �ւ��^��H�ׂ����ǂ���
     * 
     * @param toad �^
     * @return �^��H�ׂ���true�A�H�ׂȂ�������false
     */
    public boolean eat(Toad toad) {
        // �^�̈ʒu���擾
        Point toadPos = toad.getPos();
        // �ւ̓��̍��W�Ɗ^�̍��W����v���Ă���ΐH�ׂ���
        if (body[head].x == toadPos.x && body[head].y == toadPos.y) {
            expand(toad.getEnergy());
            return true;
        }
        return false;
    }

    /**
     * �ւ̐g�̂�i�����L�΂�
     * 
     * @param n �L�΂���
     */
    public void expand(int n) {
        for (int i = 0; i < n; i++) {
            // �T�C�Y��1�L�΂�
            size++;
            // ���͔z��̍Ō�̗v�f�ibody[]��size-1�̈ʒu�j
            head = size - 1;
            // ���̈ʒu��body[head-1]�̈ʒu�Ɠ����ɂ��Ă���
            body[head] = new Point(body[head - 1].x, body[head - 1].y);
        }
    }

    /**
     * �ւ̃T�C�Y��Ԃ�
     * 
     * @return �ւ̃T�C�Y
     */
    public int getSize() {
        return size;
    }

    /**
     * �ւ̌����Ă��������Ԃ�
     * 
     * @return �ւ̌����Ă������
     */
    public int getDir() {
        return dir;
    }

    /**
     * �ւ̌������Z�b�g����
     * 
     * @param dir �ւ̌����Ă������
     */
    public void setDir(int dir) {
        this.dir = dir;
    }

    /**
     * �ւ̓�����ʊO�ɂ��邩
     * 
     * @return ��ʊO��������true�A��ʓ��Ȃ�false
     */
    public boolean isOutOfField() {
        if (body[head].x < 0 || body[head].x > MainPanel.COL - 1
                || body[head].y < 0 || body[head].y > MainPanel.ROW - 1) {
            return true;
        }

        return false;
    }

    /**
     * �ւ��ړ�����
     *  
     */
    public void move() {
        // �̂����炷
        // 0123
        // ��������
        //  0123
        //  ��������
        // i�̈ʒu�ɂ͌���i+1�̒l������
        // ���͈ړ���������ɂ���Č��܂�
        for (int i = 0; i < head; i++) {
            body[i] = body[i + 1];
        }
        // ����dir�̕����Ɉړ�������
        switch (dir) {
            case UP :
                body[head] = new Point(body[head].x, body[head].y - 1);
                break;
            case RIGHT :
                body[head] = new Point(body[head].x + 1, body[head].y);
                break;
            case DOWN :
                body[head] = new Point(body[head].x, body[head].y + 1);
                break;
            case LEFT :
                body[head] = new Point(body[head].x - 1, body[head].y);
                break;
        }
    }

    /**
     * ���������̑̂ɐG��Ă��Ȃ���
     * 
     * @return �G��Ă�����true�A�G��Ă��Ȃ�������false��Ԃ�
     */
    public boolean touchOwnBody() {
        for (int i = 0; i < head; i++) {
            if (body[head].x == body[i].x && body[head].y == body[i].y) {
                return true;
            }
        }

        return false;
    }

    /**
     * �ւ�`�悷��
     * 
     * @param g �`��I�u�W�F�N�g
     */
    public void draw(Graphics g) {
        for (int i = 0; i < size; i++) {
            g.drawImage(snakeImage, body[i].x * MainPanel.GS, body[i].y
                    * MainPanel.GS, null);
        }
    }

    /**
     * �ւ̉摜�����[�h����
     * 
     * @param panel MainPanel�ւ̎Q��
     */
    private void loadImage(MainPanel panel) {
        MediaTracker tracker = new MediaTracker(panel);
        snakeImage = Toolkit.getDefaultToolkit().getImage(
                getClass().getResource("snake.gif"));
        tracker.addImage(snakeImage, 0);
        try {
            tracker.waitForAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}