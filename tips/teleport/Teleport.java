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
public class Teleport {
    // �Z䊐��̃C���[�W�͑S����7��
    private static final int NUM_IMAGES = 8;
    // �C���[�W�̃T�C�Y
    private static final int SIZE = 32;

    // �C���[�W�i�S�I�u�W�F�N�g�ŋ��L�j
    private static Image hexagramImage;
    // �A�j���[�V�����p�J�E���^
    private int counter;

    // �ʒu
    private int x, y;

    // �e���|�[�g����
    private boolean isInTeleport;

    private Timer timer = new Timer();
    // �A�j���[�V�����p�^�X�N
    private TeleportTask task = null;

    public Teleport() {
        x = y = 0;
        counter = 0;
        isInTeleport = false;

        // �Z䊐��̃C���[�W��ǂݍ���
        ImageIcon icon = new ImageIcon(getClass().getResource("hexagram.gif"));
        hexagramImage = icon.getImage();
    }

    public void draw(Graphics g) {
        if (task != null) {
            
            g.drawImage(hexagramImage, x, y, x + SIZE, y + SIZE, counter
                    * SIZE, 0, counter * SIZE + SIZE, SIZE, null);
        }
    }
    
    public void  play(Wizard wizard, int targetX, int targetY) {
        this.x = wizard.getX() - SIZE / 2;
        this.y = wizard.getY() - SIZE / 2;
        
        counter = 0;
        
        isInTeleport = true;  // �g�p��
        
        // �A�j���[�V�����J�n
        task = new TeleportTask(wizard, targetX, targetY);
        timer.schedule(task, 0L, 80L);
    }
    
    public boolean isInTeleport() {
        return isInTeleport;
    }
    
    /**
     * �A�j���[�V�����p�^�X�N
     */
    class TeleportTask extends TimerTask {
        private Wizard wizard;
        private int toX, toY;
        
        public TeleportTask(Wizard wizard, int toX, int toY) {
            this.wizard = wizard;
            this.toX = toX;
            this.toY = toY;
        }

        public void run() {
            while (true) {
                counter++;
                if (counter == NUM_IMAGES - 1) { // �Ō�̃C���[�W�܂ł�������
                    wizard.setX(toX);
                    wizard.setY(toY);
                    
                    task.cancel(); // �^�X�N�𒆎~
                    task = null;
                    isInTeleport = false; // �e���|�[�g�I��
                    return; // �A�j���[�V�����̓��[�v���Ȃ�
                }

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}