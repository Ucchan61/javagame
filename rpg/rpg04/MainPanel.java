import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
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

    // �s���i�P�ʁF�}�X�j
    private static final int ROW = 15;
    // �񐔁i�P�ʁF�}�X�j
    private static final int COL = 15;
    // �`�b�v�Z�b�g�̃T�C�Y�i�P�ʁF�s�N�Z���j
    private static final int CS = 32;

    // �}�b�v 0:�� 1:��
    private int[][] map = {
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,1,1,1,1,1,0,0,0,0,1},
        {1,0,0,0,0,1,0,0,0,1,0,0,0,0,1},
        {1,0,0,0,0,1,0,0,0,1,0,0,0,0,1},
        {1,0,0,0,0,1,0,0,0,1,0,0,0,0,1},
        {1,0,0,0,0,1,1,0,1,1,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};

    // �`�b�v�Z�b�g
    private Image floorImage;
    private Image wallImage;
    private Image heroImage;

    // �E�҂̍��W
    private int x, y;

    public MainPanel() {
        // �p�l���̐����T�C�Y��ݒ�
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        // �C���[�W�����[�h
        loadImage();
        
        // �E�҂̈ʒu��������
        x = 1;
        y = 1;

        // �p�l�����L�[������󂯕t����悤�ɓo�^����
        setFocusable(true);
        addKeyListener(this);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // �}�b�v��`��
        drawMap(g);

        // �E�҂�`��
        drawChara(g);
    }

    public void keyPressed(KeyEvent e) {
        // �����ꂽ�L�[�𒲂ׂ�
        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_LEFT :
                // ���L�[��������E�҂�1������
                move(LEFT);
                break;
            case KeyEvent.VK_RIGHT :
                // �E�L�[��������E�҂�1���E��
                move(RIGHT);
                break;
            case KeyEvent.VK_UP :
                // ��L�[��������E�҂�1�����
                move(UP);
                break;
            case KeyEvent.VK_DOWN :
                // ���L�[��������E�҂�1������
                move(DOWN);
                break;
        }

        // �E�҂̈ʒu�𓮂������̂ōĕ`��
        repaint();
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    private boolean isHit(int x, int y) {
        // (x,y)�ɕǂ���������Ԃ���
        if (map[y][x] == 1) {
            return true;
        }
        
        // �Ȃ���΂Ԃ���Ȃ�
        return false;
    }
    
    private void move(int dir) {
        // dir�̕����łԂ���Ȃ���Έړ�����
        switch (dir) {
            case LEFT:
                if (!isHit(x-1, y)) x--;
                break;
            case RIGHT:
                if (!isHit(x+1, y)) x++;
                break;
            case UP:
                if (!isHit(x, y-1)) y--;
                break;
            case DOWN:
                if (!isHit(x, y+1)) y++;
                break;
        }
    }

    private void loadImage() {
        ImageIcon icon = new ImageIcon(getClass().getResource("image/hero.gif"));
        heroImage = icon.getImage();

        icon = new ImageIcon(getClass().getResource("image/floor.gif"));
        floorImage = icon.getImage();
        
        icon = new ImageIcon(getClass().getResource("image/wall.gif"));
        wallImage = icon.getImage();
    }

    private void drawChara(Graphics g) {
        g.drawImage(heroImage, x * CS, y * CS, this);
    }

    private void drawMap(Graphics g) {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                // map�̒l�ɉ����ĉ摜��`��
                switch (map[i][j]) {
                    case 0 : // ��
                        g.drawImage(floorImage, j * CS, i * CS, this);
                        break;
                    case 1 : // ��
                        g.drawImage(wallImage, j * CS, i * CS, this);
                        break;
                }
            }
        }
    }
}
