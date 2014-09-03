import java.awt.Graphics;
import java.awt.Image;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;

/*
 * Created on 2006/01/29
 */

/**
 * @author mori
 */
public class Explosion {
    // �����C���[�W�͑S����16��
    private static final int NUM_IMAGES = 16;
    // �����C���[�W�̃T�C�Y
    private static final int SIZE = 96;

    // �����C���[�W�i�S�I�u�W�F�N�g�ŋ��L�j
    private static Image explosionImage;
    // �A�j���[�V�����p�̃J�E���^
    private int counter;

    // �����̈ʒu
    private int x;
    private int y;

    //  �����A�j���[�V������\������
    private boolean used;

    private Timer timer = new Timer();
    //  �����^�X�N
    private ExplosionTask task = null;

    public Explosion() {
        x = y = 0;
        counter = 0;
        used = false;

        // �����G�t�F�N�g�̃C���[�W��ǂݍ���
        ImageIcon icon = new ImageIcon(getClass().getResource("explosion.gif"));
        explosionImage = icon.getImage();
    }

    public void draw(Graphics g) {
        if (task != null) {
            g.drawImage(explosionImage, x, y, x + SIZE, y + SIZE, counter
                    * SIZE, 0, counter * SIZE + SIZE, SIZE, null);
        }
    }

    public void play(int x, int y) {
        // �N���b�N�����ʒu�������ɂȂ�悤��
        this.x = x - SIZE / 2;
        this.y = y - SIZE / 2;

        counter = 0;

        used = true; // �g�p��

        // �����A�j���[�V�����J�n
        task = new ExplosionTask();
        timer.schedule(task, 0L, 80L);
    }

    /**
     * �����A�j���[�V������\������
     * 
     * @return �\�����Ȃ�true��Ԃ�
     */
    public boolean isUsed() {
        return used;
    }

    /**
     * �����A�j���[�V�����p�^�X�N
     */
    class ExplosionTask extends TimerTask {
        public void run() {
            counter++;
            if (counter == NUM_IMAGES - 1) { // �Ō�̃C���[�W�܂ł�������
                task.cancel(); // �^�X�N�𒆎~
                task = null;
                used = false; // �g�p���łȂ�
                return; // �A�j���[�V�����̓��[�v���Ȃ�
            }
        }
    }
}