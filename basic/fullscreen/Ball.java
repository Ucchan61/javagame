import java.awt.*;

/*
 * Created on 2007/04/29
 */

public class Ball {
    // �{�[���̑傫��
    private static final int SIZE = 10;
    // �{�[���̈ʒu (x, y) �~�̍���̍��W
    private int x, y;
    // �{�[���̑��x (vx, vy)
    protected int vx, vy;

    // �X�N���[���T�C�Y
    private int width, height;
    
    public Ball(int x, int y, int vx, int vy, int width, int height) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.width = width;
        this.height = height;
    }

    public void move() {
        // �{�[���𑬓x�������ړ�������
        x += vx;
        y += vy;

        // ���܂��͉E�ɓ���������x�������x�̕����𔽓]������
        if (x < 0 || x > width - SIZE) {
            vx = -vx;
        }

        // ��܂��͉��ɓ���������y�������x�̕����𔽓]������
        if (y < 0 || y > height - SIZE) {
            vy = -vy;
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(x, y, SIZE, SIZE);
    }
}
