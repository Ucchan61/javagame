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
class MainPanel extends JPanel implements KeyListener, Common {
    // �p�l���T�C�Y
    private static final int WIDTH = 480;
    private static final int HEIGHT = 480;

    // �}�b�v
    private Map map;
    // �E��
    private Chara hero;

    public MainPanel() {
        // �p�l���̐����T�C�Y��ݒ�
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        // �p�l�����L�[������󂯕t����悤�ɓo�^����
        setFocusable(true);
        addKeyListener(this);
        
        // �}�b�v���쐬
        map = new Map(this);
        // �E�҂��쐬
        hero = new Chara(1, 1, "image/hero.gif", map, this);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // �}�b�v��`��
        map.draw(g);

        // �E�҂�`��
        hero.draw(g);
    }

    public void keyPressed(KeyEvent e) {
        // �����ꂽ�L�[�𒲂ׂ�
        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_LEFT :
                // ���L�[��������E�҂�1������
                hero.move(LEFT);
                break;
            case KeyEvent.VK_RIGHT :
                // �E�L�[��������E�҂�1���E��
                hero.move(RIGHT);
                break;
            case KeyEvent.VK_UP :
                // ��L�[��������E�҂�1�����
                hero.move(UP);
                break;
            case KeyEvent.VK_DOWN :
                // ���L�[��������E�҂�1������
                hero.move(DOWN);
                break;
        }

        // �E�҂̈ʒu�𓮂������̂ōĕ`��
        repaint();
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }
}
