import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import javax.swing.JPanel;

/*
 * Created on 2006/04/30
 */

public class MainPanel extends JPanel implements Runnable {
    // �p�l���T�C�Y
    public static final int WIDTH = 480;
    public static final int HEIGHT = 480;

    private static final int NUM_BALL = 3;

    // �p�l���ɕ`�悷��C���[�W
    // BufferedImage�̓s�N�Z�����삪�\
    private BufferedImage bufImage;
    private Graphics bufG;

    // �_�u���o�b�t�@�����O�p
    private Image dbImage = null;
    private Graphics dbg;

    // �摜�̃s�N�Z���ւ̃A�N�Z�X�A�ύX���ł���WritableRaster
    private WritableRaster raster;

    // ���^�{�[��
    private Metaball[] metaball;

    public MainPanel() {
        // �p�l���̐����T�C�Y��ݒ�
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        // ���^�{�[��
        metaball = new Metaball[NUM_BALL];
        metaball[0] = new Metaball(WIDTH / 2, HEIGHT / 2, 5, 4);
        metaball[1] = new Metaball(WIDTH / 2, HEIGHT / 2, -3, -6);
        metaball[2] = new Metaball(WIDTH / 2, HEIGHT / 2, -4, 7);

        Thread thread = new Thread(this);
        thread.start();
    }

    public void run() {
        while (true) {
            gameUpdate(); // �Q�[����ԍX�V
            gameRender(); // �����_�����O
            paintScreen(); // ��ʕ`��

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * �Q�[����Ԃ��X�V����
     */
    private void gameUpdate() {
        // ���^�{�[���̈ړ�
        for (int i = 0; i < NUM_BALL; i++) {
            metaball[i].move();
        }
    }

    /**
     * �����_�����O
     */
    private void gameRender() {
        // �_�u���o�b�t�@�����O�p�I�u�W�F�N�g�̐���
        if (dbImage == null) {
            dbImage = createImage(WIDTH, HEIGHT);
            if (dbImage == null) {
                System.out.println("dbImage is null");
                return;
            } else {
                dbg = dbImage.getGraphics();
            }
        }

        // �s�N�Z��������s��BufferedImage�̐���
        if (bufImage == null) {
            bufImage = new BufferedImage(WIDTH, HEIGHT,
                    BufferedImage.TYPE_INT_RGB);
            bufG = bufImage.getGraphics();
            raster = bufImage.getRaster(); // �s�N�Z������Ɏg��
        }

        // BufferedImage�������h��Ԃ�
        bufG.setColor(Color.BLACK);
        bufG.fillRect(0, 0, WIDTH, HEIGHT);

        // ���^�{�[�����d�ˍ��킹��
        for (int i = 0; i < NUM_BALL; i++) {
            metaball[i].draw(raster); // raster��n����draw()���Ńs�N�Z��������s��
        }

        dbg.drawImage(bufImage, 0, 0, this);
    }

    /**
     * �o�b�t�@�̓��e����ʂɕ`��
     */
    private void paintScreen() {
        Graphics g;
        try {
            g = getGraphics();
            if ((g != null) & (dbImage != null)) {
                g.drawImage(dbImage, 0, 0, null);
            }
            Toolkit.getDefaultToolkit().sync();
            g.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
