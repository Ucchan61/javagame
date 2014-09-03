import java.applet.*;

public class SoundBall extends Ball {
    // �T�E���h
    private AudioClip pong;

    public SoundBall(int x, int y, int vx, int vy) {
        super(x, y, vx, vy);
        // �T�E���h�����[�h
        pong = Applet.newAudioClip(getClass().getResource("pong.wav"));
    }

    // move()���I�[�o�[���C�h
    public void move() {
        // �{�[���𑬓x�������ړ�������
        x += vx;
        y += vy;

        // ���܂��͉E�ɓ���������x�������x�̕����𔽓]������
        if (x < 0 || x > MainPanel.WIDTH - SIZE) {
            // �ǂɓ��������特��炷
            pong.play();
            vx = -vx;
        }

        // ��܂��͉��ɓ���������y�������x�̕����𔽓]������
        if (y < 0 || y > MainPanel.HEIGHT - SIZE) {
            // �ǂɓ��������特��炷
            pong.play();
            vy = -vy;
        }
    }
}
