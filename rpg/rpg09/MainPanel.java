import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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

    // �A�N�V�����L�[
    private ActionKey leftKey;
    private ActionKey rightKey;
    private ActionKey upKey;
    private ActionKey downKey;

    // �Q�[�����[�v
    private Thread gameLoop;

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
        map = new Map(this);
        // �E�҂��쐬
        hero = new Chara(1, 1, "image/hero.gif", map);
        
        // �Q�[�����[�v�J�n
        gameLoop = new Thread(this);
        gameLoop.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // X�����̃I�t�Z�b�g���v�Z
        int offsetX = MainPanel.WIDTH / 2 - hero.getX() * CS;
        // �}�b�v�̒[�ł̓X�N���[�����Ȃ��悤�ɂ���
        offsetX = Math.min(offsetX, 0);
        offsetX = Math.max(offsetX, MainPanel.WIDTH - Map.WIDTH);
        
        // Y�����̃I�t�Z�b�g���v�Z
        int offsetY = MainPanel.HEIGHT / 2 - hero.getY() * CS;
        // �}�b�v�̒[�ł̓X�N���[�����Ȃ��悤�ɂ���
        offsetY = Math.min(offsetY, 0);
        offsetY = Math.max(offsetY, MainPanel.HEIGHT - Map.HEIGHT);

        // �}�b�v��`��
        map.draw(g, offsetX, offsetY);

        // �E�҂�`��
        hero.draw(g, offsetX, offsetY);
    }
    
    public void run() {
        while (true) {
            // Key�I�u�W�F�N�g�̏�Ԃ����ĕ��������肷��
            if (leftKey.isPressed()) {
                hero.move(LEFT);
            } else if (rightKey.isPressed()) {
                hero.move(RIGHT);
            } else if (upKey.isPressed()) {
                hero.move(UP);
            } else if (downKey.isPressed()) {
                hero.move(DOWN);
            }
            
            // �ĕ`��
            repaint();
            
            // �x�~
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
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
