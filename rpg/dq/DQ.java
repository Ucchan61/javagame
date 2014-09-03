import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

/*
 * Created on 2006/2/5
 *
 */

/**
 * @author mori
 *  
 */
public class DQ extends JFrame implements KeyListener, Runnable {
    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;

    private static final int PERIOD = 20;  // 50FPS�Ȃ̂�1�t���[��20ms

    // �f�o�b�O���[�h�itrue���ƍ��W�Ȃǂ��\�������j
    private static final boolean DEBUG_MODE = true;

    // �Q�[�����
    private int gameState = GameState.TITLE;

    // �}�b�v
    private Map[] maps;
    // ���݂̃}�b�v�ԍ�
    private int mapNo;

    // �E��
    private Chara hero;

    // �A�N�V�����L�[
    private ActionKey leftKey;
    private ActionKey rightKey;
    private ActionKey upKey;
    private ActionKey downKey;
    private ActionKey spaceKey;
    private ActionKey cmdLeftKey;
    private ActionKey cmdRightKey;
    private ActionKey cmdUpKey;
    private ActionKey cmdDownKey;

    // �T�u�E�B���h�E
    private MessageWindow messageWindow;
    private CommandWindow commandWindow;

    // �Q�[�����[�v
    private Thread gameLoop;
    private volatile boolean running = false;

    // ����������
    private Random rand = new Random();

    // �t���X�N���[���p
    private GraphicsDevice device;
    private Graphics gScr;
    private BufferStrategy bufferStrategy;

    // BGM��
    private static final String[] bgmNames = {"castle", "overworld", "town", "cave", "village", "shrine", "battle"};
    // ���ʉ���
    private static final String[] seNames = {"barrier", "beep", "door", "stairs", "treasure", "warp", "spell", "winbattle"};


    // �퓬��ʃe�X�g�p�X���C��
    private BufferedImage enemyImage;

    public DQ() {
        // �^�C�g����ݒ�
        setTitle("�h���N�G�N���[��");

        initFullScreen();

        addKeyListener(this);

        // �A�N�V�����L�[���쐬
        leftKey = new ActionKey();
        rightKey = new ActionKey();
        upKey = new ActionKey();
        downKey = new ActionKey();
        spaceKey = new ActionKey(ActionKey.DETECT_INITIAL_PRESS_ONLY);
        cmdLeftKey = new ActionKey(ActionKey.DETECT_INITIAL_PRESS_ONLY);
        cmdRightKey = new ActionKey(ActionKey.DETECT_INITIAL_PRESS_ONLY);
        cmdUpKey = new ActionKey(ActionKey.DETECT_INITIAL_PRESS_ONLY);
        cmdDownKey = new ActionKey(ActionKey.DETECT_INITIAL_PRESS_ONLY);

        // �}�b�v���쐬
        maps = new Map[7];
        // ���̊�
        maps[0] = new Map("king_room", Sound.CASTLE);
        // ���_�g�[����
        maps[1] = new Map("castle", Sound.CASTLE);
        // �t�B�[���h
        maps[2] = new Map("overworld", Sound.OVERWORLD);
        // ���_�g�[���̊X
        maps[3] = new Map("town", Sound.TOWN);
        // ���g�̓��A
        maps[4] = new Map("cave", Sound.CAVE);
        // �K���C�̊X
        maps[5] = new Map("town2", Sound.VILLAGE);
        // �ق���
        maps[6] = new Map("shrine", Sound.SHRINE);

        // �ŏ��͉��̊�
        mapNo = 0;

        // �E�҂��쐬
        hero = new Chara(6, 4, 0, Direction.DOWN, 0, maps[mapNo]);

        // �}�b�v�ɃL�����N�^�[��o�^
        // �L�����N�^�[�̓}�b�v�ɑ���
        maps[mapNo].addChara(hero);

        // �T�u�E�B���h�E
        messageWindow = new MessageWindow(new Rectangle(142, 324, 356, 140));  // TODO: WND_RECT�萔��
        commandWindow = new CommandWindow(new Rectangle(30, 10, 232, 156));

        // �T�E���h�����[�h
        loadSound();

        // BGM���Đ�
        MidiEngine.play(maps[mapNo].getBgmNo());

        // �e�X�g�C���[�W�����[�h
        try {
            enemyImage = ImageIO.read(getClass().getResource("image/suraimu.gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        startGame();
    }

    private void initFullScreen() {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        device = env.getDefaultScreenDevice();
        
        setUndecorated(true);  // �^�C�g���o�[��\�����Ȃ�
        setIgnoreRepaint(true);  // �A�N�e�B�u�����_�����O����̂�paint�C�x���g�𖳌�
        setResizable(false);
        
        if (!device.isFullScreenSupported()) {
            System.out.println("full-screen mode not supported");
            System.exit(0);
        }
        
        device.setFullScreenWindow(this);  // �t���X�N���[���I
        
        DisplayMode mode = new DisplayMode(640, 480, 16, DisplayMode.REFRESH_RATE_UNKNOWN);
        device.setDisplayMode(mode);
        
        // BufferStrategy���g�p
        createBufferStrategy(2);
        bufferStrategy = getBufferStrategy();
    }

    /**
     * �Q�[�����[�v
     */
    public void run() {
        long beforeTime, timeDiff, sleepTime;
        
        beforeTime = System.currentTimeMillis();

        running = true;
        while (running) {
            gameUpdate();  // �Q�[����Ԃ̍X�V
            screenUpdate();  // �X�N���[���ɕ`��
            WaveEngine.render();  // �T�E���h�̃����_�����O

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleepTime = PERIOD - timeDiff;  // ���̃t���[���̎c�莞��
            
            if (sleepTime <= 0) {
                sleepTime = 5;
            }

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            beforeTime = System.currentTimeMillis();
        }
        MidiEngine.stop();  // ���y���~�߂�
        System.exit(0);  // �Q�[�����[�v���ʂ�����I��
    }
    
    /*
     * �Q�[�����[�v���N��
     */
    private void startGame() {
        if (gameLoop == null || !running) {
            gameLoop = new Thread(this);
            gameLoop.start();
        }
    }

    /*
     * �Q�[�����~
     */
    public void stopGame() {
        running = false;
    }

    /**
     * �Q�[����Ԃ̍X�V
     */
    private void gameUpdate() {
        switch (gameState) {
            case GameState.TITLE:
                // �^�C�g����ʂ̃L�[���͂���������
                titleProcessInput();
                break;
            case GameState.MAIN:
                // ���C����ʂ̃L�[���͂���������
                mainProcessInput();
                // �T�u�E�B���h�E���\������Ă��Ȃ��Ƃ��ړ��\
                if (!messageWindow.isVisible() && !commandWindow.isVisible()) {
                    // �E�҂̈ړ�����
                    heroMove();
                    // �L�����N�^�[�̈ړ�����
                    charaMove();
                }
                break;
            case GameState.BATTLE:
                MidiEngine.play(Sound.BATTLE);
                // �퓬��ʂ̃L�[���͂���������
                battleProcessInput();
                break;
            case GameState.GAMEOVER:
                break;
        }
    }

    /**
     * �X�N���[���ɕ`��
     */
    private void screenUpdate() {
        try {
            gScr = bufferStrategy.getDrawGraphics();
            gameRender(gScr);  // �����_�����O
            gScr.dispose();
            if (!bufferStrategy.contentsLost()) {
                bufferStrategy.show();
            } else {
                System.out.println("Contents Lost");
            }
            Toolkit.getDefaultToolkit().sync();
        } catch (Exception e) {
            e.printStackTrace();
            running = false;
        }
    }

    /**
     * �摜�̃����_�����O
     */
    private void gameRender(Graphics gScr) {
        switch (gameState) {
            case GameState.TITLE:
                titleRender();
                break;
            case GameState.MAIN:
                mainRender();
                break;
            case GameState.BATTLE:
                battleRender();
                break;
            case GameState.GAMEOVER:
                gameOverRender();
                break;
        }
    }

    private void titleRender() {
        gScr.setColor(Color.BLACK);
        gScr.fillRect(0, 0, WIDTH, HEIGHT);
        
        gScr.setColor(Color.WHITE);
        gScr.drawString("�^�C�g����ʁi�X�y�[�X�L�[�������Ăˁj", 30, 30);
    }

    private void mainRender() {
        // �X�N���[���̃O���t�B�b�N�I�u�W�F�N�ggScr���g���ĕ`�悷��
        gScr.setColor(Color.WHITE);
        gScr.fillRect(0, 0, WIDTH, HEIGHT);

        // X�����̃I�t�Z�b�g���v�Z
        int offsetX = WIDTH / 2 - hero.getPx();
        // �}�b�v�̒[�ł̓X�N���[�����Ȃ��悤�ɂ���
        offsetX = Math.min(offsetX, 0);
        offsetX = Math.max(offsetX, WIDTH - maps[mapNo].getWidth());
        
        // Y�����̃I�t�Z�b�g���v�Z
        int offsetY = HEIGHT / 2 - hero.getPy();
        // �}�b�v�̒[�ł̓X�N���[�����Ȃ��悤�ɂ���
        offsetY = Math.min(offsetY, 0);
        offsetY = Math.max(offsetY, HEIGHT - maps[mapNo].getHeight());

        // �}�b�v��`��
        // �L�����N�^�[�̓}�b�v���`���Ă����
        maps[mapNo].draw(gScr, offsetX, offsetY);
        
        // �T�u�E�B���h�E��`��
        // isVisible��true�̂Ƃ������`�悳���
        messageWindow.draw(gScr);
        commandWindow.draw(gScr);

        if (DEBUG_MODE) {
            gScr.setColor(Color.WHITE);
            gScr.drawString("�X�N���[���T�C�Y: " + WIDTH + " " + HEIGHT, 450, 32);
            gScr.drawString("�}�b�v�T�C�Y: " + maps[mapNo].getCol() + " " + maps[mapNo].getRow(), 450, 48);
            gScr.drawString("�L�����N�^�[���W: " + hero.getX() + " " + hero.getY(), 450, 64);
        }
    }

    private void battleRender() {
        // TODO: �o�g���N���X��draw()���Ăяo���悤�ɏ���������
        gScr.setColor(Color.BLACK);
        gScr.fillRect(0, 0, WIDTH, HEIGHT);
        
        gScr.setColor(Color.WHITE);
        gScr.drawString("�퓬��ʂɂȂ�\��i�X�y�[�X�������Ăˁj", 30, 30);
        
        gScr.drawImage(enemyImage, 314, 150, null);
    }
    
    private void gameOverRender() {
        
    }

    /**
     * �E�҂̈ړ�����
     */
    private void heroMove() {
        // �ړ��i�X�N���[���j���Ȃ�ړ�����
        if (hero.isMoving()) {
            if (hero.move()) {  // �ړ��i�X�N���[���j
                // �ړ�������������̏����͂����ɏ���
                // �����Ƀo���A�[������Ή���炷
                if (maps[mapNo].getMapChip(hero.getX(), hero.getY()) == Chipset.BARRIER) {
                    WaveEngine.play(Sound.BARRIER);
                    // TODO:��ʃt���b�V��
                }

                // �ړ��C�x���g
                // �C�x���g�����邩�`�F�b�N
                Event event = maps[mapNo].eventCheck(hero.getX(), hero.getY());
                if (event instanceof MoveEvent) {  // �ړ��C�x���g�Ȃ�
                    if (event.chipNo == Chipset.DOWNSTAIRS || event.chipNo == Chipset.UPSTAIRS) {  // �K�i
                        WaveEngine.play(Sound.STAIRS);
                    } else if (event.chipNo == Chipset.WARP) {  // ���̂Ƃт�
                        WaveEngine.play(Sound.WARP);
                        
                        // TODO:���̂Ƃт�̃G�t�F�N�g

                    }
                    MoveEvent m = (MoveEvent)event;
                    // �ړ����}�b�v�̗E�҂�����
                    maps[mapNo].removeChara(hero);
                    // ���݂̃}�b�v�ԍ��Ɉړ���̃}�b�v�ԍ���ݒ�
                    mapNo = m.destMapNo;
                    // �ړ���}�b�v�ł̍��W���擾���ėE�҂���蒼��
                    hero = new Chara(m.destX, m.destY, 0, Direction.DOWN, 0, maps[mapNo]);
                    // �ړ���}�b�v�ɗE�҂�o�^
                    maps[mapNo].addChara(hero);
                    // BGM
                    MidiEngine.play(maps[mapNo].getBgmNo());
                }
            }
        }
    }
    
    /**
     * �^�C�g����ʂ̃L�[���͂���������
     */
    private void titleProcessInput() {
        if (spaceKey.isPressed()) {
            gameState = GameState.MAIN;  // ���C����ʂ�
        }
    }

    /**
     * ���C����ʂ̃L�[���͂���������
     */
    private void mainProcessInput() {
        // ��ԏ�ɂ���\������Ă���E�B���h�E�̃L�[�������D�悳���
        if (messageWindow.isVisible()) {  // ���b�Z�[�W�E�B���h�E�\����
            messageWindowProcessInput();
        } else if (commandWindow.isVisible()) {  // �R�}���h�E�B���h�E��\����
            commandWindowProcessInput();
        } else {  // ���C�����
            mainWindowProcessInput();
        }
    }

    /**
     * ���b�Z�[�W�E�B���h�E���\������Ă���Ƃ��̃L�[����
     */
    private void messageWindowProcessInput() {
        if (spaceKey.isPressed()) {  // �X�y�[�X�L�[
            // ���̃��b�Z�[�W��
            if (messageWindow.nextMessage()) {
                // ���b�Z�[�W���I�������烁�b�Z�[�W�E�B���h�E���B��
                messageWindow.hide();
            }
        }
        // �L�[���Z�b�g
        // ���ꂪ�Ȃ��ƃE�B���h�E������Ƃ��Ɉړ����Ă��܂�
        leftKey.reset();
        rightKey.reset();
        upKey.reset();
        downKey.reset();
    }

    /**
     * �R�}���h�E�B���h�E���\������Ă���Ƃ��̃L�[����
     */
    private void commandWindowProcessInput() {
        if (cmdLeftKey.isPressed()) {
            commandWindow.leftCursor();
        }
        if (cmdRightKey.isPressed()) {
            commandWindow.rightCursor();
        }
        if (cmdUpKey.isPressed()) {
            commandWindow.upCursor();
        }
        if (cmdDownKey.isPressed()) {
            commandWindow.downCursor();
        }
        if (spaceKey.isPressed()) {  // �X�y�[�X�L�[
            commandWindow.hide();
            WaveEngine.play(Sound.BEEP);
            switch (commandWindow.getSelectedCmdNo()) {
                case CommandWindow.TALK:  // �͂Ȃ�
                    Chara chara = hero.talkWith();
                    if (chara != null) {
                        // ���b�Z�[�W���Z�b�g����
                        messageWindow.setMessage(chara.getMessage());
                        // ���b�Z�[�W�E�B���h�E��\��
                        messageWindow.show();
                    } else {
                        messageWindow.setMessage("���̂ق������ɂ́@��������Ȃ��B");
                        messageWindow.show();
                    }
                    break;
                case CommandWindow.STATUS:  // �悳
                    break;
                case CommandWindow.EQUIPMENT:  // ������
                    break;
                case CommandWindow.DOOR:  // �Ƃт�
                    DoorEvent door = hero.open();
                    if (door != null) {
                        WaveEngine.play(Sound.DOOR);    
                        // �h�A���폜
                        maps[mapNo].removeEvent(door);
                    }
                    break;
                case CommandWindow.SPELL:  // �������
                    WaveEngine.play(Sound.SPELL);
                    break;
                case CommandWindow.ITEM:  // �ǂ���
                    break;
                case CommandWindow.TACTICS:  // ��������
                    // �퓬��ʃe�X�g
                    gameState = GameState.BATTLE;
                    break;
                case CommandWindow.SEARCH:  // ����ׂ�
                    Event event = hero.search();
                    if (event instanceof TreasureEvent) {  // ��
                        TreasureEvent treasure = (TreasureEvent)event; 
                        WaveEngine.play(Sound.TREASURE);
                        // ���b�Z�[�W���Z�b�g����
                        messageWindow.setMessage(treasure.getItemName() + "���@�Ăɂ��ꂽ�B");

                        // TODO:�����ɃA�C�e�����菈��������

                        // ���b�Z�[�W�E�B���h�E��\��
                        messageWindow.show();
                        // �󔠂��폜
                        maps[mapNo].removeEvent(treasure);
                        break;
                    } else if (event instanceof MessageEvent) {  // ���b�Z�[�W
                        MessageEvent me = (MessageEvent)event;
                        // ���b�Z�[�W���Z�b�g����
                        messageWindow.setMessage(me.getMessage());
                        // ���b�Z�[�W�E�B���h�E��\��
                        messageWindow.show();
                        // ���b�Z�[�W�C�x���g�͏������Ȃ�
                    } else if (event instanceof MessageBoardEvent) {  // ���ĎD
                        MessageBoardEvent board = (MessageBoardEvent)event;

                        // TODO: �����ŏ������݂Ȃǂł����炢���Ȃ�

                        // ���b�Z�[�W���Z�b�g����
                        messageWindow.setMessage(board.getMessage());
                        // ���b�Z�[�W�E�B���h�E��\��
                        messageWindow.show();
                    } else {
                        messageWindow.setMessage("�������@�Ȃɂ��@�݂���Ȃ������B");
                        messageWindow.show();
                    }
            }
        }
    }

    /**
     * ���C����ʂł̃L�[����
     */
    private void mainWindowProcessInput() {
        if (leftKey.isPressed()) { // ��
            if (!hero.isMoving()) {       // �ړ����łȂ����
                hero.setDirection(Direction.LEFT);  // �������Z�b�g����
                hero.setMoving(true);     // �ړ��i�X�N���[���j�J�n
            }
        }
        if (rightKey.isPressed()) { // �E
            if (!hero.isMoving()) {
                hero.setDirection(Direction.RIGHT);
                hero.setMoving(true);
            }
        }
        if (upKey.isPressed()) { // ��
            if (!hero.isMoving()) {
                hero.setDirection(Direction.UP);
                hero.setMoving(true);
            }
        }
        if (downKey.isPressed()) { // ��
            if (!hero.isMoving()) {
                hero.setDirection(Direction.DOWN);
                hero.setMoving(true);
            }
        }
        if (spaceKey.isPressed()) {  // �X�y�[�X
            commandWindow.show();
        }
    }

    /**
     * �퓬��ʂ̓��͂���������
     */
    private void battleProcessInput() {
        if (spaceKey.isPressed()) {
            WaveEngine.play(Sound.WINBATTLE);
            MidiEngine.play(maps[mapNo].getBgmNo());
            gameState = GameState.MAIN;
        }
    }

    /**
     * �E�҈ȊO�̃L�����N�^�[�̈ړ�����
     */
    private void charaMove() {
        // �}�b�v�ɂ���L�����N�^�[���擾
        Vector charas = maps[mapNo].getCharas();
        for (int i=0; i<charas.size(); i++) {
            Chara chara = (Chara)charas.get(i);
            // �L�����N�^�[�̈ړ��^�C�v�𒲂ׂ�
            if (chara.getMoveType() == 1) {  // �ړ�����^�C�v�Ȃ�
                if (chara.isMoving()) {  // �ړ����Ȃ�
                    chara.move();  // �ړ�����
                } else if (rand.nextDouble() < Chara.PROB_MOVE) {
                    // �ړ����ĂȂ��ꍇ��Chara.PROB_MOVE�̊m���ōĈړ�����
                    // �����̓����_���Ɍ��߂�
                    chara.setDirection(rand.nextInt(4));
                    chara.setMoving(true);
                }
            }
        }
    }

    /**
     * �T�E���h�����[�h
     */
    private void loadSound() {
        // BGM�����[�h
        for (int i = 0; i < bgmNames.length; i++) {
            try {
                MidiEngine.load("bgm/" + bgmNames[i] + ".mid");
            } catch (MidiUnavailableException e) {
                e.printStackTrace();
            } catch (InvalidMidiDataException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // ���ʉ������[�h
        for (int i = 0; i < seNames.length; i++) {
            try {
                WaveEngine.load("se/" + seNames[i] + ".wav");
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * �L�[�������ꂽ��L�[�̏�Ԃ��u�����ꂽ�v�ɕς���
     * 
     * @param e �L�[�C�x���g
     */
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        // �I������
        if (keyCode == KeyEvent.VK_ESCAPE || keyCode == KeyEvent.VK_Q ||
                keyCode == KeyEvent.VK_END ||
                (keyCode == KeyEvent.VK_C && e.isControlDown())) {
            running = false;
        }

        if (keyCode == KeyEvent.VK_LEFT) {
            if (commandWindow.isVisible()) {
                cmdLeftKey.press();
            } else {
                leftKey.press();
            }
        }
        if (keyCode == KeyEvent.VK_RIGHT) {
            if (commandWindow.isVisible()) {
                cmdRightKey.press();
            } else {
                rightKey.press();
            }
        }
        if (keyCode == KeyEvent.VK_UP) {
            if (commandWindow.isVisible()) {
                cmdUpKey.press();
            } else {
                upKey.press();
            }
        }
        if (keyCode == KeyEvent.VK_DOWN) {
            if (commandWindow.isVisible()) {
                cmdDownKey.press();
            } else {
                downKey.press();
            }
        }
        if (keyCode == KeyEvent.VK_SPACE) {
            spaceKey.press();
        }
    }

    /**
     * �L�[�������ꂽ��L�[�̏�Ԃ��u�����ꂽ�v�ɕς���
     * 
     * @param e �L�[�C�x���g
     */
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_LEFT) {
            leftKey.release();
            cmdLeftKey.release();
        }
        if (keyCode == KeyEvent.VK_RIGHT) {
            rightKey.release();
            cmdRightKey.release();
        }
        if (keyCode == KeyEvent.VK_UP) {
            upKey.release();
            cmdUpKey.release();
        }
        if (keyCode == KeyEvent.VK_DOWN) {
            downKey.release();
            cmdDownKey.release();
        }
        if (keyCode == KeyEvent.VK_SPACE) {
            spaceKey.release();
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) {
        new DQ();
    }
}