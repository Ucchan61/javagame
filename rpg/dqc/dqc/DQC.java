package dqc;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

/*
 * DQC���C���t���[��
 */

public class DQC extends JFrame implements KeyListener, Runnable {
    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;

    // �����萔
    public static final int DOWN = 0;
    public static final int UP = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;

    // �t���X�N���[�����[�h�ɂ���Ȃ�true�A�E�B���h�E���[�h�Ȃ�false
    private boolean isFullScreenMode = true;

    // 1�t���[���̎������ԁims�j
    private static final int PERIOD = 10;

    // �L�����N�^�[
    private Hero hero;  // ��l��

    // �}�b�v
    public static MapManager mapManager = new MapManager();
    
    // �A�N�V�����L�[
    private ActionKey downKey = new ActionKey();
    private ActionKey upKey = new ActionKey();
    private ActionKey leftKey = new ActionKey();
    private ActionKey rightKey = new ActionKey();
    private ActionKey anyKey = new ActionKey(ActionKey.DETECT_INITIAL_PRESS_ONLY);  // �ǂ̃L�[�ł��悢
    private ActionKey spaceKey = new ActionKey(ActionKey.DETECT_INITIAL_PRESS_ONLY);  // �֗��L�[
    private ActionKey talkKey = new ActionKey(ActionKey.DETECT_INITIAL_PRESS_ONLY);  // �b��
    private ActionKey searchKey = new ActionKey(ActionKey.DETECT_INITIAL_PRESS_ONLY);  // ���ׂ�
    private ActionKey openKey = new ActionKey(ActionKey.DETECT_INITIAL_PRESS_ONLY);  // �J����
    
    // �Q�[�����[�v
    private Thread gameLoop;
    private volatile boolean running = false;

    // �t���X�N���[���p
    private GraphicsDevice device;
    // �_�u���o�b�t�@�����O�p
    private BufferStrategy bufferStrategy;

    // ����������
    private Random rand;

    // ���b�Z�[�W�E�B���h�E
    private MessageWindow msgWnd;

    // �T�E���h�}�l�W���[
    public static SoundManager soundManager = new SoundManager();
    
    private Font font = new Font("HG�n�p�p�޼��UB", Font.PLAIN, 16);

    public DQC() {
        setTitle("DQC");
        setBounds(0, 0, WIDTH, HEIGHT);
        setResizable(false);
        setIgnoreRepaint(true); // paint�C�x���g�𖳌���

        // �t���X�N���[�����[�h�̏�����
        if (isFullScreenMode) {
            initFullScreen(); // setVisible()�̑O�łȂ��ƃ_���I
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // BufferStrategy���g�p
        // setVisible()�̌�łȂ��ƃ_���I
        createBufferStrategy(2);
        bufferStrategy = getBufferStrategy();

        addKeyListener(this);

        rand = new Random(System.currentTimeMillis());

        Map curMap = mapManager.getMap("king_room");
        mapManager.setCurMap(curMap);
        soundManager.playBGM(curMap.getBGM());
        
        hero = new Hero(8, 6, 0, Chara.DOWN, Chara.STOP, "", curMap);
        curMap.addEvent(hero);
        
        msgWnd = new MessageWindow(new Rectangle(80, 352, 480, 108));
        
        // �Q�[�����[�v�J�n
        gameLoop = new Thread(this);
        gameLoop.start();
    }

    /**
     * �Q�[�����[�v
     * 
     */
    public void run() {
        long beforeTime, timeDiff, sleepTime;
        beforeTime = System.currentTimeMillis();
        running = true;
        while (running) {
            gameUpdate(); // �Q�[����Ԃ̍X�V
            gameRender(); // �o�b�t�@�Ƀ����_�����O
            paintScreen(); // �o�b�t�@����ʂɕ`��

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleepTime = PERIOD - timeDiff; // ���̃t���[���̎c�莞��

            if (sleepTime <= 0) {
                sleepTime = 5; // �Œ�5ms�͋x��
            }

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            beforeTime = System.currentTimeMillis();
        }

        // �Q�[�����[�v���ʂ�����I��
        System.exit(0);
    }

    /**
     * �Q�[����Ԃ��X�V����
     * 
     */
    private void gameUpdate() {
        // �L�[���͂��`�F�b�N
        checkInput();

        // ���b�Z�[�W�E�B���h�E�\�����͈ړ����Ȃ�
        if (msgWnd.isVisible()) {
            return;
        }
        
        // ��l���̈ړ�����
        heroMove();
        
        // ���̑��̃L�����N�^�[�̈ړ�����
        charaMove();
    }

    /**
     * �o�b�t�@�Ƀ����_�����O
     * 
     */
    private void gameRender() {
        // �o�b�t�@�̕`��f�o�C�X���擾
        Graphics2D dbg = (Graphics2D)bufferStrategy.getDrawGraphics();
        
        dbg.setColor(Color.BLACK);
        dbg.fillRect(0, 0, WIDTH, HEIGHT);

        dbg.setFont(font);
        
        Map curMap = mapManager.getCurMap();
        
        // �I�t�Z�b�g���v�Z
        int offsetX = hero.getPx() - WIDTH / 2;
        int offsetY = hero.getPy() - HEIGHT / 2;
        // �}�b�v�̒[�ł̓X�N���[�����Ȃ��悤�ɂ���
        offsetX = Math.max(offsetX, 0);
        offsetX = Math.min(offsetX, curMap.getWidth() - WIDTH);

        offsetY = Math.max(offsetY, 0);
        offsetY = Math.min(offsetY, curMap.getHeight() - HEIGHT);

        curMap.draw(dbg, offsetX, offsetY);
        
        msgWnd.draw(dbg);

        dbg.dispose();
    }

    /**
     * �o�b�t�@����ʂɕ`��
     * 
     */
    private void paintScreen() {
        if (!bufferStrategy.contentsLost()) {
            bufferStrategy.show();
        }
        Toolkit.getDefaultToolkit().sync();
    }

    /**
     * �L�[���͂��`�F�b�N
     * 
     */
    private void checkInput() {
        // ���b�Z�[�W�E�B���h�E�\����
        if (msgWnd.isVisible()) {
            msgWndCheckInput();
        } else {
            mainCheckInput();
        }
    }
    
    /**
     * ���C���E�B���h�E�̃L�[����
     *
     */
    private void mainCheckInput() {
        // TODO: �֗��L�[�i�͂Ȃ��E����ׂ�ȂǏ󋵂ɉ����āj�̏����ǉ�
        if (downKey.isPressed()) {
            // �ړ����łȂ���Έړ��J�n
            if (!hero.isMoving()) {
                hero.setDirection(DOWN);
                hero.setMoving(true);
            }
        } else if (upKey.isPressed()) {
            if (!hero.isMoving()) {
                hero.setDirection(UP);
                hero.setMoving(true);
            }
        } else if (leftKey.isPressed()) {
            if (!hero.isMoving()) {
                hero.setDirection(LEFT);
                hero.setMoving(true);
            }
        } else if (rightKey.isPressed()) {
            if (!hero.isMoving()) {
                hero.setDirection(RIGHT);
                hero.setMoving(true);
            }
        } else if (talkKey.isPressed()) {
            if (!hero.isMoving()) {  // �͂Ȃ�
                hero.talk(msgWnd);
            }
        } else if (searchKey.isPressed()) {  // ����������ׂ�
            if (!hero.isMoving()) {
                hero.search(msgWnd);

            }
        } else if (openKey.isPressed()) {  // �J����
            if (!hero.isMoving()) {
                hero.open(msgWnd);
            }
        }
    }
    
    /**
     * ���b�Z�[�W�E�B���h�E�\�����̃L�[����
     *
     */
    private void msgWndCheckInput() {
        if (anyKey.isPressed()) {
            // ���̃��b�Z�[�W��
            if (msgWnd.nextMessage()) {
                // ���b�Z�[�W���I��������E�B���h�E���B��
                msgWnd.hide();
            }
        }
        
        downKey.reset();
        upKey.reset();
        leftKey.reset();
        rightKey.reset();
        talkKey.reset();
        searchKey.reset();
        openKey.reset();
        spaceKey.reset();
    }
    
    /**
     * ��l���̈ړ�����
     *
     */
    private void heroMove() {
        // �X�N���[�����Ȃ�ړ��𑱂���
        if (hero.isMoving()) {
            hero.move();
        }
    }
    
    /**
     * �L�����N�^�[�̈ړ�����
     * 
     */
    private void charaMove() {
        // �}�b�v�ɂ���C�x���g���擾
        ArrayList eventList = mapManager.getCurMap().getEventList();

        for (int i = 0; i < eventList.size(); i++) {
            Event evt = (Event) eventList.get(i);
            if (evt instanceof Chara) {
                Chara chara = (Chara) evt;
                // �L�����N�^�[�̈ړ��^�C�v�𒲂ׂ�
                if (chara.getMoveType() == Chara.MOVE_AROUND) {
                    if (chara.isMoving()) { // �ړ���
                        chara.move(); // �ړ��𑱂���
                    } else if (rand.nextDouble() < Chara.PROB_MOVE) { // ��~��
                        // �ړ����ĂȂ��ꍇ��PROB_MOVE�̊m���ōĈړ�����
                        // �����̓����_��
                        chara.setDirection(rand.nextInt(4));
                        chara.setMoving(true);
                    }
                }
            }
        }
    }

    /**
     * �t���X�N���[�����[�h�̏�����
     * 
     */
    private void initFullScreen() {
        GraphicsEnvironment env = GraphicsEnvironment
                .getLocalGraphicsEnvironment();
        device = env.getDefaultScreenDevice();

        // �^�C�g���o�[������
        setUndecorated(true);
        // �}�E�X�J�[�\��������
        Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(
                new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
                new Point(), "");
        setCursor(cursor);

        if (!device.isFullScreenSupported()) {
            System.out.println("�t���X�N���[�����[�h�̓T�|�[�g����Ă��܂���B");
            System.exit(0);
        }

        device.setFullScreenWindow(this); // �t���X�N���[�����I

        DisplayMode mode = new DisplayMode(640, 480, 16,
                DisplayMode.REFRESH_RATE_UNKNOWN);
        device.setDisplayMode(mode);
    }
    
    /**
     * �L�[�������ꂽ�Ƃ��̏���
     * 
     */
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        anyKey.press();
        
        switch (keyCode) {
            case KeyEvent.VK_DOWN :
                downKey.press();
                break;
            case KeyEvent.VK_UP :
                upKey.press();
                break;
            case KeyEvent.VK_LEFT :
                leftKey.press();
                break;
            case KeyEvent.VK_RIGHT :
                rightKey.press();
                break;
            case KeyEvent.VK_SPACE:
                spaceKey.press();
                break;
            case KeyEvent.VK_T:
                talkKey.press();
                break;
            case KeyEvent.VK_S:
                searchKey.press();
                break;
            case KeyEvent.VK_O:
                openKey.press();
                break;
            case KeyEvent.VK_ESCAPE :
                System.exit(0);
        }
    }

    /**
     * �L�[�������ꂽ�Ƃ��̏���
     * 
     */
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        anyKey.release();
        
        switch (keyCode) {
            case KeyEvent.VK_DOWN :
                downKey.release();
                break;
            case KeyEvent.VK_UP :
                upKey.release();
                break;
            case KeyEvent.VK_LEFT :
                leftKey.release();
                break;
            case KeyEvent.VK_RIGHT :
                rightKey.release();
                break;
            case KeyEvent.VK_SPACE:
                spaceKey.release();
                break;
            case KeyEvent.VK_T:
                talkKey.release();
                break;
            case KeyEvent.VK_S:
                searchKey.release();
                break;
            case KeyEvent.VK_O:
                openKey.release();
                break;
            case KeyEvent.VK_ESCAPE :
                System.exit(0);
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) {
        new DQC();
    }
}
