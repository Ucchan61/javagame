import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JPanel;

/*
 * Created on 2005/02/09
 *
 */

/**
 * ���C���p�l��
 * 
 * @author mori
 *  
 */
public class MainPanel extends JPanel implements Runnable, KeyListener {
    // �p�l���T�C�Y
    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;

    // �����萔
    private static final int LEFT = 0;
    private static final int RIGHT = 1;

    // �A�����˂ł���e�̐�
    private static final int NUM_SHOT = 5;
    // ���˂ł���Ԋu�i�e�̏[�U���ԁj
    private static final int FIRE_INTERVAL = 300;

    // �G�C���A���̐�
    private static final int NUM_ALIEN = 50;
    // �r�[���̐�
    private static final int NUM_BEAM = 20;

    // �v���C���[
    private Player player;
    // �e
    private Shot[] shots;
    // �Ō�ɔ��˂�������
    private long lastFire = 0;
    // �G�C���A��
    private Alien[] aliens;
    // �r�[��
    private Beam[] beams;

    // �L�[�̏�ԁi���̃L�[��Ԃ��g���ăv���C���[���ړ�����j
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean firePressed = false;

    // �Q�[�����[�v�p�X���b�h
    private Thread gameLoop;

    // ����������
    private Random rand;

    // �T�E���h
    private AudioClip fireSound;
    private AudioClip crySound;
    private AudioClip bombSound;

    public MainPanel() {
        // �p�l���̐����T�C�Y��ݒ�Apack()����Ƃ��ɕK�v
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // �p�l�����L�[���͂��󂯕t����悤�ɂ���
        setFocusable(true);

        // �Q�[���̏�����
        initGame();

        rand = new Random();

        // �T�E���h�����[�h
        fireSound = Applet.newAudioClip(getClass().getResource("se/pi02.wav"));
        crySound = Applet.newAudioClip(getClass().getResource("se/pi00.wav"));
        bombSound = Applet.newAudioClip(getClass().getResource("se/bom28_a.wav"));

        // �L�[�C�x���g���X�i�[��o�^
        addKeyListener(this);

        // �Q�[�����[�v�J�n
        gameLoop = new Thread(this);
        gameLoop.start();
    }

    /**
     * �Q�[�����[�v
     */
    public void run() {

        while (true) {
            move();

            // ���˃{�^���������ꂽ��e�𔭎�
            if (firePressed) {
                tryToFire();
            }

            // �G�C���A���̍U��
            alienAttack();

            // �Փ˔���
            collisionDetection();

            // �ĕ`��
            repaint();

            // �x�~
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * �Q�[���̏�����
     */
    private void initGame() {
        // �v���C���[���쐬
        player = new Player(0, HEIGHT - 20, this);

        // �e���쐬
        shots = new Shot[NUM_SHOT];
        for (int i = 0; i < NUM_SHOT; i++) {
            shots[i] = new Shot(this);
        }

        // �G�C���A�����쐬
        aliens = new Alien[NUM_ALIEN];
        for (int i = 0; i < NUM_ALIEN; i++) {
            aliens[i] = new Alien(20 + (i % 10) * 40, 20 + (i / 10) * 40, 3, this);
        }

        // �r�[�����쐬
        beams = new Beam[NUM_BEAM];
        for (int i = 0; i < NUM_BEAM; i++) {
            beams[i] = new Beam(this);
        }
    }

    /**
     * �ړ�����
     */
    private void move() {
        // �v���C���[���ړ�����
        // ����������Ă��Ȃ��Ƃ��͈ړ����Ȃ�
        if (leftPressed) {
            player.move(LEFT);
        } else if (rightPressed) {
            player.move(RIGHT);
        }

        // �G�C���A�����ړ�����
        for (int i = 0; i < NUM_ALIEN; i++) {
            aliens[i].move();
        }

        // �e���ړ�����
        for (int i = 0; i < NUM_SHOT; i++) {
            shots[i].move();
        }

        // �r�[�����ړ�����
        for (int i = 0; i < NUM_BEAM; i++) {
            beams[i].move();
        }
    }

    /**
     * �e�𔭎˂���
     */
    private void tryToFire() {
        // �O�Ƃ̔��ˊԊu��FIRE_INTERVAL�ȉ��������甭�˂ł��Ȃ�
        if (System.currentTimeMillis() - lastFire < FIRE_INTERVAL) {
            return;
        }

        lastFire = System.currentTimeMillis();
        // ���˂���Ă��Ȃ��e��������
        for (int i = 0; i < NUM_SHOT; i++) {
            if (shots[i].isInStorage()) {
                // �e���ۊǌɂɂ���Δ��˂ł���
                // �e�̍��W���v���C���[�̍��W�ɂ���Δ��˂����
                Point pos = player.getPos();
                shots[i].setPos(pos.x + player.getWidth() / 2, pos.y);
                // ���ˉ�
                fireSound.play();
                // 1�������甭�˂���break�Ń��[�v���ʂ���
                break;
            }
        }
    }

    /**
     * �G�C���A���̍U��
     */
    private void alienAttack() {
        // 1�^�[����NUM_BEAM�������˂���
        // �܂�G�C���A��1�l�ɂȂ��Ă�������NUM_BEAM���˂���
        for (int i = 0; i < NUM_BEAM; i++) {
            // �G�C���A���̍U��
            // �����_���ɃG�C���A����I��
            int n = rand.nextInt(NUM_ALIEN);
            // ���̃G�C���A���������Ă���΃r�[������
            if (aliens[n].isAlive()) {
                // ���˂���Ă��Ȃ��r�[����������
                // 1�������甭�˂���break�Ń��[�v���ʂ���
                for (int j = 0; j < NUM_BEAM; j++) {
                    if (beams[j].isInStorage()) {
                        // �r�[�����ۊǌɂɂ���Δ��˂ł���
                        // �r�[���̍��W���G�C���A���̍��W�ɃZ�b�g����Δ��˂����
                        Point pos = aliens[n].getPos();
                        beams[j].setPos(pos.x + aliens[n].getWidth() / 2, pos.y);
                        break;
                    }
                }
            }
        }
    }

    /**
     * �Փˌ��o
     *  
     */
    private void collisionDetection() {
        // �G�C���A���ƒe�̏Փˌ��o
        for (int i = 0; i < NUM_ALIEN; i++) {
            for (int j = 0; j < NUM_SHOT; j++) {
                if (aliens[i].collideWith(shots[j])) {
                    // i�Ԗڂ̃G�C���A����j�Ԗڂ̒e���Փ�
                    // �G�C���A���͎���
                    aliens[i].die();
                    // �f����
                    crySound.play();
                    // �e�͕ۊǌɂցi�ۊǌɂ֑���Ȃ���Ίђʒe�ɂȂ�j
                    shots[j].store();
                    // �G�C���A�������񂾂�������[�v�܂킷�K�v�Ȃ�
                    break;
                }
            }
        }
        
        // �v���[���[�ƃr�[���̏Փˌ��o
        for (int i = 0; i < NUM_BEAM; i++) {
            if (player.collideWith(beams[i])) {
                // ������
                bombSound.play();
                // �v���[���[��i�Ԗڂ̃r�[�����Փ�
                // �r�[���͕ۊǌɂ�
                beams[i].store();
                // �Q�[���I�[�o�[
                initGame();
            }
        }
    }

    /**
     * �`�揈��
     * 
     * @param �`��I�u�W�F�N�g
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // �w�i�����œh��Ԃ�
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        // �v���C���[��`��
        player.draw(g);

        // �G�C���A����`��
        for (int i = 0; i < NUM_ALIEN; i++) {
            aliens[i].draw(g);
        }

        // �e��`��
        for (int i = 0; i < NUM_SHOT; i++) {
            shots[i].draw(g);
        }

        // �r�[����`��
        for (int i = 0; i < NUM_BEAM; i++) {
            beams[i].draw(g);
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    /**
     * �L�[�������ꂽ��L�[�̏�Ԃ��u�����ꂽ�v�ɕς���
     * 
     * @param e �L�[�C�x���g
     */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }
        if (key == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }
        if (key == KeyEvent.VK_SPACE) {
            firePressed = true;
        }
    }

    /**
     * �L�[�������ꂽ��L�[�̏�Ԃ��u�����ꂽ�v�ɕς���
     * 
     * @param e �L�[�C�x���g
     */
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }
        if (key == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
        if (key == KeyEvent.VK_SPACE) {
            firePressed = false;
        }
    }
}