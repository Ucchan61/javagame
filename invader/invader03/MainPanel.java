import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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

    // �v���C���[
    private Player player;
    // �e
    private Shot[] shots;
    // �Ō�ɔ��˂�������
    private long lastFire = 0;

    // �L�[�̏�ԁi���̃L�[��Ԃ��g���ăv���C���[���ړ�����j
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean firePressed = false;

    // �Q�[�����[�v�p�X���b�h
    private Thread gameLoop;

    public MainPanel() {
        // �p�l���̐����T�C�Y��ݒ�Apack()����Ƃ��ɕK�v
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // �p�l�����L�[���͂��󂯕t����悤�ɂ���
        setFocusable(true);

        // �v���C���[���쐬
        player = new Player(0, HEIGHT - 20, this);

        // �e���쐬
        shots = new Shot[NUM_SHOT];
        for (int i = 0; i < NUM_SHOT; i++) {
            shots[i] = new Shot(this);
        }

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
            // �v���C���[���ړ�����
            // ����������Ă��Ȃ��Ƃ��͈ړ����Ȃ�
            if (leftPressed) {
                player.move(LEFT);
            } else if (rightPressed) {
                player.move(RIGHT);
            }

            // ���˃{�^���������ꂽ��e�𔭎�
            if (firePressed) {
                tryToFire();
            }

            // �e���ړ�����
            for (int i = 0; i < NUM_SHOT; i++) {
                shots[i].move();
            }

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
                // 1�������甭�˂���break�Ń��[�v���ʂ���
                break;
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

        // �e��`��
        for (int i = 0; i < NUM_SHOT; i++) {
            shots[i].draw(g);
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