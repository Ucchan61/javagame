import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.text.DecimalFormat;
import java.util.Random;

import javax.swing.JPanel;

/*
 * Created on 2007/04/26
 */

public class MainPanel extends JPanel implements Runnable {
    public static final int WIDTH = 360;
    public static final int HEIGHT = 360;

    // �{�[���̐�
    private static final int NUM_BALLS = 50;

    // ���҂���FPS�i1�b�Ԃɕ`�悷��t���[�����j
    private static final int FPS = 50;  // ���l��ς��Ă݂悤

    // 1�t���[���Ŏg���鎝������
    private static final long PERIOD = (long) (1.0 / FPS * 1000000000); // �P��: ns

    // FPS���v�Z����Ԋu�i1s = 10^9ns�j
    private static long MAX_STATS_INTERVAL = 1000000000L; // �P��: ns

    // �{�[��
    private Ball[] ball;

    // �Q�[�����[�v�p�X���b�h
    private volatile boolean running = false;
    private Thread gameLoop;

    // �_�u���o�b�t�@�����O�p
    private Graphics dbg;
    private Image dbImage = null;

    private Random rand;

    // FPS�v�Z�p
    private long calcInterval = 0L; // in ns
    private long prevCalcTime;

    // �t���[����
    private long frameCount = 0;
    // ���ۂ�FPS
    private double actualFPS = 0.0;

    private DecimalFormat df = new DecimalFormat("0.0");

    public MainPanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        rand = new Random(System.currentTimeMillis());

        ball = new Ball[NUM_BALLS];
        for (int i = 0; i < NUM_BALLS; i++) {
            // �ʒu�E���x�������_���ɐݒ�
            int x = rand.nextInt(WIDTH);
            int y = rand.nextInt(HEIGHT);
            int vx = rand.nextInt(10);
            int vy = rand.nextInt(10);
            // �{�[�����쐬
            ball[i] = new Ball(x, y, vx, vy);
        }
    }

    public void addNotify() {
        super.addNotify();
        // �Q�[�����[�v�̋N��
        if (gameLoop == null || !running) {
            gameLoop = new Thread(this);
            gameLoop.start();
        }
    }

    public void run() {
        long beforeTime, afterTime, timeDiff, sleepTime;
        long overSleepTime = 0L;
        int noDelays = 0;

        beforeTime = System.nanoTime();
        prevCalcTime = beforeTime;

        running = true;
        while (running) {
            gameUpdate();
            gameRender();
            paintScreen();

            afterTime = System.nanoTime();
            timeDiff = afterTime - beforeTime;
            // �O��̃t���[���̋x�~���Ԍ덷�������Ă���
            sleepTime = (PERIOD - timeDiff) - overSleepTime;

            if (sleepTime > 0) {
                // �x�~���Ԃ��Ƃ��ꍇ
                try {
                    Thread.sleep(sleepTime / 1000000L); // nano->ms
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // sleep()�̌덷
                overSleepTime = (System.nanoTime() - afterTime) - sleepTime;
            } else {
                // ��ԍX�V�E�����_�����O�Ŏ��Ԃ��g���؂��Ă��܂�
                // �x�~���Ԃ��Ƃ�Ȃ��ꍇ
                overSleepTime = 0L;
                // �x�~�Ȃ���16��ȏ㑱������
                if (++noDelays >= 16) {
                    Thread.yield(); // ���̃X���b�h���������s
                    noDelays = 0;
                }
            }

            beforeTime = System.nanoTime();

            // FPS���v�Z
            calcFPS();
        }

        System.exit(0);
    }

    private void gameUpdate() {
        for (int i = 0; i < NUM_BALLS; i++) {
            ball[i].move();
        }
    }

    private void gameRender() {
        if (dbImage == null) {
            dbImage = createImage(WIDTH, HEIGHT);
            if (dbImage == null) {
                System.out.println("dbImage���쐬�ł��܂���B");
                return;
            } else {
                dbg = dbImage.getGraphics();
            }
        }

        // �w�i�̓h��Ԃ�
        dbg.setColor(Color.WHITE);
        dbg.fillRect(0, 0, WIDTH, HEIGHT);

        // FPS�̕`��
        dbg.setColor(Color.BLUE);
        dbg.drawString("FPS: " + df.format(actualFPS), 4, 16);

        // �{�[���̕`��
        for (int i = 0; i < NUM_BALLS; i++) {
            ball[i].draw(dbg);
        }
    }

    private void paintScreen() {
        Graphics g;
        try {
            g = this.getGraphics();
            if ((g != null) && (dbImage != null)) {
                g.drawImage(dbImage, 0, 0, null);
            }
            Toolkit.getDefaultToolkit().sync();
            g.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * FPS�̌v�Z
     * 
     */
    private void calcFPS() {
        frameCount++;
        calcInterval += PERIOD;

        // 1�b������FPS���Čv�Z����
        if (calcInterval >= MAX_STATS_INTERVAL) {
            long timeNow = System.nanoTime();
            // ���ۂ̌o�ߎ��Ԃ𑪒�
            long realElapsedTime = timeNow - prevCalcTime; // �P��: ns

            // FPS���v�Z
            // realElapsedTime�̒P�ʂ�ns�Ȃ̂�s�ɕϊ�����
            actualFPS = ((double) frameCount / realElapsedTime) * 1000000000L;
            System.out.println(df.format(actualFPS));

            frameCount = 0L;
            calcInterval = 0L;
            prevCalcTime = timeNow;
        }
    }
}
