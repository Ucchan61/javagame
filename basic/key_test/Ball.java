import java.awt.*;
import java.util.*;

public class Ball {
    // ������\���萔
    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;

    // �{�[���̑傫��
    public static final int SIZE = 16;

    // �{�[���̈ʒu (x, y) �~�̍���̍��W
    private int x, y;
    // �{�[���̑��x (vx, vy)
    private int vx, vy;
    // �{�[���̐F
    private Color color;

    // ����������
    private static final Random rand = new Random();

    /**
     * �R���X�g���N�^�i�V�����{�[���I�u�W�F�N�g�����H��j
     */
    public Ball(int x, int y, int vx, int vy) {
        // �{�[���̑�����ݒ�
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
    }

    /**
     * �{�[�����ړ�����B
     * 
     * @param dir �{�[�����ړ���������B
     */
    public void move(int dir) {
        switch (dir) {
            case UP :
                y -= vy;
                break;
            case DOWN :
                y += vy;
                break;
            case LEFT :
                x -= vx;
                break;
            case RIGHT :
                x += vx;
                break;
        }

        // �[�ɂ����炻��ȏ�ړ����Ȃ�
        if (x < 0) {
            x = 0;
        } else if (x > MainPanel.WIDTH - SIZE) {
            x = MainPanel.WIDTH - SIZE;
        }

        if (y < 0) {
            y = 0;
        } else if (y > MainPanel.HEIGHT - SIZE) {
            y = MainPanel.HEIGHT - SIZE;
        }
    }

    /**
     * �{�[���̐F�������_���ɕς���B
     */
    public void changeColor() {
        int red = rand.nextInt(256); // �Ԑ�����0-255
        int green = rand.nextInt(256); // �ΐ�����0-255
        int blue = rand.nextInt(256); // ������0-255

        color = new Color(red, green, blue);
    }

    /**
     * �{�[����`�悷��B
     * 
     * @param g �`��I�u�W�F�N�g�B
     */
    public void draw(Graphics g) {
        // �F���Z�b�g����
        g.setColor(color);
        // �ۂ�`���i�{�[���̂���j
        g.fillOval(x, y, SIZE, SIZE);
    }
}