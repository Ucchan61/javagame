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
    private static final int ROW = 20;
    // �񐔁i�P�ʁF�}�X�j
    private static final int COL = 30;

    // �}�b�v�S�̂̑傫���i�P�ʁF�s�N�Z���j
    public static final int WIDTH = COL * CS;
    public static final int HEIGHT = ROW * CS;

    // �}�b�v 0:�� 1:��
    private int[][] map = {
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,1,1,1,1,1,0,0,0,0,1,0,0,0,0,0,1,1,1,1,1,0,0,0,0,1},
        {1,0,0,0,0,1,0,0,0,1,0,0,0,0,1,0,0,0,0,0,1,0,0,0,1,0,0,0,0,1},
        {1,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,1},
        {1,0,0,0,0,1,0,0,0,1,0,0,0,0,1,0,0,0,0,0,1,0,0,0,1,0,0,0,0,1},
        {1,0,0,0,0,1,1,0,1,1,0,0,0,0,1,0,0,0,0,0,1,1,0,1,1,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};

    // �`�b�v�Z�b�g
    private Image floorImage;
    private Image wallImage;
    
    // ���C���p�l���ւ̎Q��
    private MainPanel panel;

    public Map(MainPanel panel) {
        // �C���[�W�����[�h
        loadImage();
    }

    public void draw(Graphics g, int offsetX, int offsetY) {
        // �I�t�Z�b�g�����ɕ`��͈͂����߂�
        int firstTileX = pixelsToTiles(-offsetX);
        int lastTileX = firstTileX + pixelsToTiles(MainPanel.WIDTH) + 1;
        // �`��͈͂��}�b�v�̑傫�����傫���Ȃ�Ȃ��悤�ɒ���
        lastTileX = Math.min(lastTileX, COL);
        
        int firstTileY = pixelsToTiles(-offsetY);
        int lastTileY = firstTileY + pixelsToTiles(MainPanel.HEIGHT) + 1;
        // �`��͈͂��}�b�v�̑傫�����傫���Ȃ�Ȃ��悤�ɒ���
        lastTileY = Math.min(lastTileY, ROW);
        
        for (int i = firstTileY; i < lastTileY; i++) {
            for (int j = firstTileX; j < lastTileX; j++) {
                // map�̒l�ɉ����ĉ摜��`��
                switch (map[i][j]) {
                    case 0 : // ��
                        g.drawImage(floorImage, tilesToPixels(j) + offsetX, tilesToPixels(i) + offsetY, panel);
                        break;
                    case 1 : // ��
                        g.drawImage(wallImage, tilesToPixels(j) + offsetX, tilesToPixels(i) + offsetY, panel);
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

    /**
     * �s�N�Z���P�ʂ��}�X�P�ʂɕύX����
     * @param pixels �s�N�Z���P��
     * @return �}�X�P��
     */
    public static int pixelsToTiles(double pixels) {
        return (int)Math.floor(pixels / CS);
    }
    
    /**
     * �}�X�P�ʂ��s�N�Z���P�ʂɕύX����
     * @param tiles �}�X�P��
     * @return �s�N�Z���P��
     */
    public static int tilesToPixels(int tiles) {
        return tiles * CS;
    }

    private void loadImage() {
        ImageIcon icon = new ImageIcon(getClass().getResource("image/floor.gif"));
        floorImage = icon.getImage();
        
        icon = new ImageIcon(getClass().getResource("image/wall.gif"));
        wallImage = icon.getImage();
    }
}
