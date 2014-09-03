import java.awt.*;

public class Ball {
    // �{�[���̑傫��
    private static final int SIZE = 10;
    // �{�[���̈ʒu (x, y) �~�̍���̍��W
    private int x, y;
    // �{�[���̑��x (vx, vy)
    protected int vx, vy;

    // �R���X�g���N�^�i�V�����{�[���I�u�W�F�N�g�����H��j
    public Ball(int x, int y, int vx, int vy) {
        // �{�[���̑�����ݒ�
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
    }

    public void move() {
        // �{�[���𑬓x�������ړ�������
        x += vx;
        y += vy;

        // ���܂��͉E�ɓ���������x�������x�̕����𔽓]������
        if (x < 0 || x > MainPanel.WIDTH - SIZE) {
            vx = -vx;
        }

        // ��܂��͉��ɓ���������y�������x�̕����𔽓]������
        if (y < 0 || y > MainPanel.HEIGHT - SIZE) {
            vy = -vy;
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(x, y, SIZE, SIZE);
    }
}
