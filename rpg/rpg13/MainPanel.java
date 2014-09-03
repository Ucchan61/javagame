import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JPanel;

/*
 * Created on 2005/10/09
 *
 */

/**
 * @author mori
 *  
 */
class MainPanel extends JPanel implements KeyListener, Runnable, Common {
    // �p�l���T�C�Y
    public static final int WIDTH = 480;
    public static final int HEIGHT = 480;

    // �}�b�v
    private Map map;
    // �E��
    private Chara hero;
    // ���l
    private Chara king;
    // ���m
    private Chara soldier;

    // �A�N�V�����L�[
    private ActionKey leftKey;
    private ActionKey rightKey;
    private ActionKey upKey;
    private ActionKey downKey;

    // �Q�[�����[�v
    private Thread gameLoop;

    // ����������
    private Random rand = new Random();
    
    public MainPanel() {
        // �p�l���̐����T�C�Y��ݒ�
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        // �p�l�����L�[������󂯕t����悤�ɓo�^����
        setFocusable(true);
        addKeyListener(this);

        // �A�N�V�����L�[���쐬
        leftKey = new ActionKey();
        rightKey = new ActionKey();
        upKey = new ActionKey();
        downKey = new ActionKey();

        // �}�b�v���쐬
        map = new Map("map/map.dat", this);

        // �E�҂��쐬
        hero = new Chara(4, 4, 0, map);
        // ���l���쐬
        king = new Chara(6, 6, 1, map);
        // ���m���쐬
        soldier = new Chara(8, 9, 2, map);

        // �}�b�v�ɃL�����N�^�[��o�^
        // �L�����N�^�[�̓}�b�v�ɑ���
        map.addChara(hero);
        map.addChara(king);
        map.addChara(soldier);

        // �Q�[�����[�v�J�n
        gameLoop = new Thread(this);
        gameLoop.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // X�����̃I�t�Z�b�g���v�Z
        int offsetX = MainPanel.WIDTH / 2 - hero.getPx();
        // �}�b�v�̒[�ł̓X�N���[�����Ȃ��悤�ɂ���
        offsetX = Math.min(offsetX, 0);
        offsetX = Math.max(offsetX, MainPanel.WIDTH - map.getWidth());
        
        // Y�����̃I�t�Z�b�g���v�Z
        int offsetY = MainPanel.HEIGHT / 2 - hero.getPy();
        // �}�b�v�̒[�ł̓X�N���[�����Ȃ��悤�ɂ���
        offsetY = Math.min(offsetY, 0);
        offsetY = Math.max(offsetY, MainPanel.HEIGHT - map.getHeight());

        // �}�b�v��`��
        // �L�����N�^�[�̓}�b�v���`���Ă����
        map.draw(g, offsetX, offsetY);
    }

    public void run() {
        while (true) {
            // �L�[���͂��`�F�b�N����
            checkInput();

            // �E�҂̈ړ�����
            heroMove();
            // �L�����N�^�[�̈ړ�����
            charaMove();

            repaint();

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * �L�[���͂��`�F�b�N����
     */
    private void checkInput() {
        if (leftKey.isPressed()) { // ��
            if (!hero.isMoving()) {       // �ړ����łȂ����
                hero.setDirection(LEFT);  // �������Z�b�g����
                hero.setMoving(true);     // �ړ��i�X�N���[���j�J�n
            }
        }
        if (rightKey.isPressed()) { // �E
            if (!hero.isMoving()) {
                hero.setDirection(RIGHT);
                hero.setMoving(true);
            }
        }
        if (upKey.isPressed()) { // ��
            if (!hero.isMoving()) {
                hero.setDirection(UP);
                hero.setMoving(true);
            }
        }
        if (downKey.isPressed()) { // ��
            if (!hero.isMoving()) {
                hero.setDirection(DOWN);
                hero.setMoving(true);
            }
        }
    }

    /**
     * �E�҂̈ړ�����
     */
    private void heroMove() {
        // �ړ��i�X�N���[���j���Ȃ�ړ�����
        if (hero.isMoving()) {
            if (hero.move()) {  // �ړ��i�X�N���[���j
                // �ړ�������������̏����͂����ɏ���
            }
        }
    }

    /**
     * �E�҈ȊO�̃L�����N�^�[�̈ړ�����
     */
    private void charaMove() {
        if (soldier.isMoving()) {  // �ړ����Ȃ�
            soldier.move();  // �ړ��𑱂���
        } else if (rand.nextDouble() < 0.02) {
            // �ړ����ĂȂ��ꍇ��0.02�̊m���ōĈړ�����
            // �����̓����_���Ɍ��߂�
            soldier.setDirection(rand.nextInt(4));
            soldier.setMoving(true);
        }
    }

    /**
     * �L�[�������ꂽ��L�[�̏�Ԃ��u�����ꂽ�v�ɕς���
     * 
     * @param e �L�[�C�x���g
     */
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_LEFT) {
            leftKey.press();
        }
        if (keyCode == KeyEvent.VK_RIGHT) {
            rightKey.press();
        }
        if (keyCode == KeyEvent.VK_UP) {
            upKey.press();
        }
        if (keyCode == KeyEvent.VK_DOWN) {
            downKey.press();
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
        }
        if (keyCode == KeyEvent.VK_RIGHT) {
            rightKey.release();
        }
        if (keyCode == KeyEvent.VK_UP) {
            upKey.release();
        }
        if (keyCode == KeyEvent.VK_DOWN) {
            downKey.release();
        }
    }

    public void keyTyped(KeyEvent e) {
    }
}
