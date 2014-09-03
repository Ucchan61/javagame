import java.awt.Color;
import java.awt.Cursor;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.Random;

import javax.swing.JFrame;

/*
 * Created on 2007/05/02
 */

public class FullScreenTest extends JFrame implements Runnable {
    // �{�[���̐�
    private static final int NUM_BALLS = 256;
    private Ball[] ball;
    private Random rand;

    // �Q�[�����[�v
    private Thread gameLoop;
    private volatile boolean running;

    // �t���X�N���[���p
    private static final int NUM_BUFFERS = 2; // BufferStrategy�̃o�b�t�@�̐�
    private boolean isFullScreenMode = true; // �t���X�N���[�����[�h�ɂ��邩�H
    private GraphicsDevice device;
    private BufferStrategy bufferStrategy;

    // �f�t�H���g�t���[���T�C�Y
    private int width = 640;
    private int height = 480;

    // FPS�v�Z�p
    private static final int FPS = 60; // ���҂���FPS
    private static final long PERIOD = (long) (1.0 / FPS * 1000000000); // 1�t���[���̎���
    // �P��:
    // ns
    private static long MAX_STATS_INTERVAL = 1000000000L; // FPS�v�Z�Ԋu �P��: ns
    private long calcInterval = 0L;
    private long prevCalcTime;
    private long frameCount = 0; // �t���[���J�E���^�[
    private double actualFPS = 0.0; // ���ۂ�FPS
    private DecimalFormat df = new DecimalFormat("0.0");

    public FullScreenTest() {
        setTitle("�t���X�N���[��");
        setBounds(0, 0, width, height);
        setResizable(false);
        setIgnoreRepaint(true); // paint�C�x���g�𖳌���

        // �t���X�N���[�����[�h�̏�����
        if (isFullScreenMode) {
            initFullScreen();
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // BufferStrategy�̐ݒ�
        createBufferStrategy(NUM_BUFFERS);
        bufferStrategy = getBufferStrategy();

        rand = new Random(System.currentTimeMillis());
        ball = new Ball[NUM_BALLS];
        for (int i = 0; i < NUM_BALLS; i++) {
            // �ʒu�E���x�������_���ɐݒ�
            int x = rand.nextInt(width);
            int y = rand.nextInt(height);
            int vx = rand.nextInt(10);
            int vy = rand.nextInt(10);
            // �{�[�����쐬
            ball[i] = new Ball(x, y, vx, vy, width, height);
        }

        // �I���L�[�̒�`�iESC�L�[�ŏI���j
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_ESCAPE) {
                    running = false;
                }
            }
        });

        // �Q�[�����[�v�J�n
        gameLoop = new Thread(this);
        gameLoop.start();
    }

    /**
     * �Q�[�����[�v
     * 
     */
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
            screenUpdate();

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

        // �Q�[�����[�v���ʂ�����I��
        restoreScreen();
        System.exit(0);
    }

    /**
     * �t���X�N���[�����[�h�̌㏈��
     * 
     */
    private void restoreScreen() {
        Window w = device.getFullScreenWindow();
        if (w != null) {
            w.dispose();
        }
        device.setFullScreenWindow(null);
    }

    /**
     * �Q�[����Ԃ̍X�V
     * 
     */
    private void gameUpdate() {
        for (int i = 0; i < NUM_BALLS; i++) {
            ball[i].move();
        }
    }

    /**
     * �����_�����O
     * 
     */
    private void gameRender() {
        Graphics dbg = bufferStrategy.getDrawGraphics();

        // �w�i
        dbg.setColor(Color.BLACK);
        dbg.fillRect(0, 0, width, height);

        // �{�[���̕`��
        for (int i = 0; i < NUM_BALLS; i++) {
            ball[i].draw(dbg);
        }

        // FPS�̕`��
        dbg.setColor(Color.YELLOW);
        dbg.drawString("FPS: " + df.format(actualFPS), 4, 16);

        dbg.dispose();
    }

    /**
     * �X�N���[���̍X�V�iBufferStrategy���g�p�j
     * 
     */
    private void screenUpdate() {
        if (!bufferStrategy.contentsLost()) {
            bufferStrategy.show();
        } else {
            System.out.println("Contents Lost");
        }
        Toolkit.getDefaultToolkit().sync();
    }

    /**
     * �t���X�N���[�����[�h�̏�����
     * 
     */
    private void initFullScreen() {
        GraphicsEnvironment ge = GraphicsEnvironment
                .getLocalGraphicsEnvironment();
        device = ge.getDefaultScreenDevice();

        setUndecorated(true); // �^�C�g���o�[�E�{�[�_�[��\��

        // �K�v�Ȃ�}�E�X�J�[�\��������
        Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(
                new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
                new Point(), "");
        setCursor(cursor);

        if (!device.isFullScreenSupported()) {
            System.out.println("�t���X�N���[�����[�h�̓T�|�[�g����Ă��܂���B");
            System.exit(0);
        }

        // �t���X�N���[�����I
        device.setFullScreenWindow(this);
        // �ύX�\�ȃf�B�X�v���C���[�h��\��
        showDisplayModes();
        // �f�B�X�v���C���[�h�̕ύX�̓t���X�N���[������
        // �ύX�\�ȃf�B�X�v���C���[�h�����g���Ȃ�
        // 640x480,800x600,1024x768��32bit�����肪�Ó�
        setDisplayMode(1024, 768, 32);
        showCurrentMode();

        width = getBounds().width;
        height = getBounds().height;
    }

    private void showDisplayModes() {
        System.out.println("�ύX�\�ȃf�B�X�v���C���[�h");
        DisplayMode[] modes = device.getDisplayModes();
        for (int i = 0; i < modes.length; i++) {
            System.out.print("(" + modes[i].getWidth() + ","
                    + modes[i].getHeight() + "," + modes[i].getBitDepth() + ","
                    + modes[i].getRefreshRate() + ") ");
            if ((i + 1) % 4 == 0) {
                System.out.println();
            }
        }
        System.out.println();
    }

    /**
     * �f�B�X�v���C���[�h��ݒ�
     * 
     * @param width
     * @param height
     * @param bitDepth
     */
    private void setDisplayMode(int width, int height, int bitDepth) {
        if (!device.isDisplayChangeSupported()) {
            System.out.println("�f�B�X�v���C���[�h�̕ύX�̓T�|�[�g����Ă��܂���B");
            return;
        }

        DisplayMode dm = new DisplayMode(width, height, bitDepth,
                DisplayMode.REFRESH_RATE_UNKNOWN);
        device.setDisplayMode(dm);
    }

    /**
     * ���݂̃f�B�X�v���C���[�h��\��
     * 
     */
    private void showCurrentMode() {
        DisplayMode dm = device.getDisplayMode();
        System.out.println("���݂̃f�B�X�v���C���[�h");
        System.out.println("Width: " + dm.getWidth());
        System.out.println("Height: " + dm.getHeight());
        System.out.println("Bit Depth: " + dm.getBitDepth());
        System.out.println("Refresh Rate: " + dm.getRefreshRate());
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
            // System.out.println(df.format(actualFPS));

            frameCount = 0L;
            calcInterval = 0L;
            prevCalcTime = timeNow;
        }
    }

    public static void main(String[] args) {
        new FullScreenTest();
    }
}
