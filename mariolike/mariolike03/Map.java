import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/*
 * Created on 2005/06/16
 *
 */

/**
 * @author mori
 *  
 */
public class Map {
    // �^�C���T�C�Y
    private static final int TILE_SIZE = 32;
    // �s��
    private static final int ROW = 15;
    // ��
    private static final int COL = 20;
    // �d��
    public static final double GRAVITY = 1.0;

    // �}�b�v
    private int[][] map = {
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,1,1,1,1,1,1,1,1,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,0,0,0,0,0,0,0,0,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
    };

    public Map() {
    }

    /**
     * �}�b�v��`�悷��
     * 
     * @param g �`��I�u�W�F�N�g
     */
    public void draw(Graphics g) {
        g.setColor(Color.ORANGE);
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                // map�̒l�ɉ����ĉ摜��`��
                switch (map[i][j]) {
                    case 1 : // �u���b�N
                        g.fillRect(tilesToPixels(j), tilesToPixels(i), TILE_SIZE, TILE_SIZE);
                        break;
                }
            }
        }
    }
    
    /**
     * (newX, newY)�ŏՓ˂���u���b�N�̍��W��Ԃ�
     * @param player �v���C���[�ւ̎Q��
     * @param newX X���W
     * @param newY Y���W
     * @return �Փ˂���u���b�N�̍��W
     */
    public Point getTileCollision(Player player, double newX, double newY) {
        // �����_�ȉ��؂�グ
        // ���������_�̊֌W�Ő؂�グ���Ȃ��ƏՓ˂��ĂȂ��Ɣ��肳���ꍇ������
        newX = Math.ceil(newX);
        newY = Math.ceil(newY);

        double fromX = Math.min(player.getX(), newX);
        double fromY = Math.min(player.getY(), newY);
        double toX = Math.max(player.getX(), newX);
        double toY = Math.max(player.getY(), newY);
        
        int fromTileX = pixelsToTiles(fromX);
        int fromTileY = pixelsToTiles(fromY);
        int toTileX = pixelsToTiles(toX + Player.WIDTH - 1);
        int toTileY = pixelsToTiles(toY + Player.HEIGHT - 1);

        // �Փ˂��Ă��邩���ׂ�
        for (int x = fromTileX; x <= toTileX; x++) {
            for (int y = fromTileY; y <= toTileY; y++) {
                // ��ʊO�͏Փ�
                if (x < 0 || x >= COL) {
                    return new Point(x, y);
                }
                if (y < 0 || y >= ROW) {
                    return new Point(x, y);
                }
                // �u���b�N����������Փ�
                if (map[y][x] == 1) {
                    return new Point(x, y);
                }
            }
        }
        
        return null;
    }
    
    /**
     * �s�N�Z���P�ʂ��^�C���P�ʂɕύX����
     * @param pixels �s�N�Z���P��
     * @return �^�C���P��
     */
    public static int pixelsToTiles(double pixels) {
        return (int)Math.floor(pixels / TILE_SIZE);
    }
    
    /**
     * �^�C���P�ʂ��s�N�Z���P�ʂɕύX����
     * @param tiles �^�C���P��
     * @return �s�N�Z���P��
     */
    public static int tilesToPixels(int tiles) {
        return tiles * TILE_SIZE;
    }
}