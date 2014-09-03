import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

/*
 * Created on 2005/10/10
 *
 */

/**
 * @author mori
 *
 */
public class Map implements Common {
    // �s���i�P�ʁF�}�X�j
    private static final int ROW = 15;
    // �񐔁i�P�ʁF�}�X�j
    private static final int COL = 15;

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
    
    // ���C���p�l���ւ̎Q��
    private MainPanel panel;

    public Map(MainPanel panel) {
        // �C���[�W�����[�h
        loadImage();
    }

    public void draw(Graphics g) {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                // map�̒l�ɉ����ĉ摜��`��
                switch (map[i][j]) {
                    case 0 : // ��
                        g.drawImage(floorImage, j * CS, i * CS, panel);
                        break;
                    case 1 : // ��
                        g.drawImage(wallImage, j * CS, i * CS, panel);
                        break;
                }
            }
        }
    }

    public boolean isHit(int x, int y) {
        // (x,y)�ɕǂ���������Ԃ���
        if (map[y][x] == 1) {
            return true;
        }
        
        // �Ȃ���΂Ԃ���Ȃ�
        return false;
    }

    private void loadImage() {
        ImageIcon icon = new ImageIcon(getClass().getResource("image/floor.gif"));
        floorImage = icon.getImage();
        
        icon = new ImageIcon(getClass().getResource("image/wall.gif"));
        wallImage = icon.getImage();
    }
}
