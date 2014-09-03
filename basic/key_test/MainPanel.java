/*
 * Created on 2005/01/03
 *
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * @author mori
 * 
 */
public class MainPanel extends JPanel implements KeyListener {
    // �p�l���T�C�Y
    public static final int WIDTH = 240;
    public static final int HEIGHT = 240;

    // ������\���萔
    private static final int UP = 0;
    private static final int DOWN = 1;
    private static final int LEFT = 2;
    private static final int RIGHT = 3;

    // �{�[���I�u�W�F�N�g
    private Ball ball;

    public MainPanel() {
        // �p�l���̐����T�C�Y��ݒ�Apack()����Ƃ��ɕK�v
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setSize(WIDTH, HEIGHT);
        // �{�[�����쐬
        ball = new Ball(WIDTH / 2, HEIGHT / 2, 10, 10);
        // �p�l�����L�[�{�[�h���󂯕t����悤�ɂ���i�K�{�j
        this.setFocusable(true);
        // �L�[���X�i�[��o�^�i�Y��₷���j
        addKeyListener(this);
    }

    /**
     * �`�揈���B
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // �{�[����`��
        ball.draw(g);
    }

    /**
     * �L�[���^�C�v���ꂽ�Ƃ��Ă΂��B �������͂����m�������ꍇ�͂��������g���B
     */
    public void keyTyped(KeyEvent e) {
    }

    /**
     * �L�[�������ꂽ�Ƃ��Ă΂��B �Q�[���ł͂����Ă��͂��������g���B
     */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP :
                // ��L�[�������ꂽ��{�[������Ɉړ�
                ball.move(UP);
                break;
            case KeyEvent.VK_DOWN :
                // ���L�[�������ꂽ��{�[�������Ɉړ�
                ball.move(DOWN);
                break;
            case KeyEvent.VK_LEFT :
                // ���L�[�������ꂽ��{�[�������Ɉړ�
                ball.move(LEFT);
                break;
            case KeyEvent.VK_RIGHT :
                // �E�L�[�������ꂽ��{�[�����E�Ɉړ�
                ball.move(RIGHT);
                break;
            case KeyEvent.VK_X :
                // X�L�[�������ꂽ��{�[���̐F��ς���
                ball.changeColor();
                break;
        }
        // �{�[�����ړ������̂ōĕ`��
        // �Y��₷���̂Œ���
        repaint();
    }

    /**
     * �L�[�������ꂽ�Ƃ��Ă΂��B
     */
    public void keyReleased(KeyEvent e) {
    }
}