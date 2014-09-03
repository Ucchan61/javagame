import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

/*
 * Created on 2005/06/06
 *
 */

/**
 * @author mori
 *  
 */
public class MainPanel extends JPanel implements Runnable, KeyListener {
    // �p�l���T�C�Y
    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;

    // �}�b�v
    private Map map;

    // �v���C���[
    private Player player;

    // �L�[�̏�ԁi������Ă��邩�A������ĂȂ����j
    private boolean leftPressed;
    private boolean rightPressed;
    private boolean upPressed;

    // �Q�[�����[�v�p�X���b�h
    private Thread gameLoop;

    public MainPanel() {
        // �p�l���̐����T�C�Y��ݒ�Apack()����Ƃ��ɕK�v
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // �p�l�����L�[���͂��󂯕t����悤�ɂ���
        setFocusable(true);

        // �}�b�v���쐬
        map = new Map();

        // �v���C���[���쐬
        player = new Player(192, 32, map);

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
            if (leftPressed) {
                // ���L�[��������Ă���΍������ɉ���
                player.accelerateLeft();
            } else if (rightPressed) {
                // �E�L�[��������Ă���ΉE�����ɉ���
                player.accelerateRight();
            } else {
                // ����������ĂȂ��Ƃ��͒�~
                player.stop();
            }

            if (upPressed) {
                // �W�����v����
                player.jump();
            }

            // �v���C���[�̏�Ԃ��X�V
            player.update();

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
     * �`�揈��
     * 
     * @param �`��I�u�W�F�N�g
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // �w�i�����œh��Ԃ�
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        // X�����̃I�t�Z�b�g���v�Z
        int offsetX = MainPanel.WIDTH / 2 - (int)player.getX();
        // �}�b�v�̒[�ł̓X�N���[�����Ȃ��悤�ɂ���
        offsetX = Math.min(offsetX, 0);
        offsetX = Math.max(offsetX, MainPanel.WIDTH - Map.WIDTH);

        // Y�����̃I�t�Z�b�g���v�Z
        int offsetY = MainPanel.HEIGHT / 2 - (int)player.getY();
        // �}�b�v�̒[�ł̓X�N���[�����Ȃ��悤�ɂ���
        offsetY = Math.min(offsetY, 0);
        offsetY = Math.max(offsetY, MainPanel.HEIGHT - Map.HEIGHT);

        // �}�b�v��`��
        map.draw(g, offsetX, offsetY);

        // �v���C���[��`��
        player.draw(g, offsetX, offsetY);
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
        if (key == KeyEvent.VK_UP) {
            upPressed = true;
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
        if (key == KeyEvent.VK_UP) {
            upPressed = false;
        }
    }

    public void keyTyped(KeyEvent e) {
    }
}