package dqc;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/*
 * �}�b�v�N���X
 */

public class Map {
    // �`�b�v�T�C�Y
    private static final int CS = 32;
    // �}�b�v�`�b�v��
    private static final int NUM_CHIPS = 480;
    // 1�s�̃}�b�v�`�b�v��
    private static final int NUM_CHIPS_IN_ROW = 30;
    
    // �}�b�v
    private int[][] map;
    
    // �ړ��\���F�}�b�v�`�b�v�ԍ�=>0 or 1
    // 0�Ȃ�ړ��\
    // TODO: �}�b�v�P�ʂŎ������Ă��悢
    private int[] moveType;
    
    // �}�b�v�̍s���E�񐔁i�}�X�P�ʁj
    private int row;
    private int col;
    
    // �}�b�v�S�̂̑傫���i�s�N�Z���P�ʁj
    private int width;
    private int height;

    // �}�b�v�`�b�v�C���[�W
    private Image mapchipImage;

    // �C�x���g
    private ArrayList eventList = new ArrayList();
    
    // �C�x���g���[�_�[
    private EventLoader eventLoader;
    
    // �}�b�v��
    private String mapName;
    
    // BGM��
    private String bgmName;
    
    public Map(String mapName) {
        this.mapName = mapName;
        
        // �}�b�v�����[�h
        load("map/" + mapName + ".map");
        
        // �}�b�v�`�b�v�ړ��\����ǂݍ���
        loadMoveType("map/move_type.dat");

        // �C���[�W�����[�h
        if (mapchipImage == null) {
            loadImage();
        }
        
        // �C�x���g���[�_�[�𐶐�
        if (eventLoader == null) {
            eventLoader = new EventLoader(this);
        }
        
        // �C�x���g�����[�h
        // �}�b�v���Ɠ����̃C�x���g�t�@�C����ǂݍ���
        loadEvent("map/" + mapName + ".evt");
    }

    /**
     * �}�b�v��`��
     * 
     * @param g �`��f�o�C�X
     * @param offsetX X�����I�t�Z�b�g
     * @param offsetY Y�����I�t�Z�b�g
     */
    public void draw(Graphics g, int offsetX, int offsetY) {
        // �I�t�Z�b�g����`��͈͂����߂�
        int firstTileX = pixelsToTiles(offsetX);
        int lastTileX = firstTileX + pixelsToTiles(DQC.WIDTH) + 1;  // 1�}�X�]����
        // �`��͈͂��}�b�v���傫���Ȃ�Ȃ��悤�ɒ���
        lastTileX = Math.min(lastTileX, col);
        
        int firstTileY = pixelsToTiles(offsetY);
        int lastTileY = firstTileY + pixelsToTiles(DQC.HEIGHT) + 1;  // 1�}�X�]����
        // �`��͈͂��}�b�v���傫���Ȃ�Ȃ��悤�ɒ���
        lastTileY = Math.min(lastTileY, row);
        
        for (int i = firstTileY; i < lastTileY; i++) {
            for (int j = firstTileX; j < lastTileX; j++) {
                int mapchipNo = map[i][j];
                int cx = (mapchipNo % NUM_CHIPS_IN_ROW) * CS;
                int cy = (mapchipNo / NUM_CHIPS_IN_ROW) * CS;
                g.drawImage(mapchipImage,
                        tilesToPixels(j) - offsetX,
                        tilesToPixels(i) - offsetY,
                        tilesToPixels(j) - offsetX + CS,
                        tilesToPixels(i) - offsetY + CS,
                        cx, cy,
                        cx + CS, cy + CS,
                        null);
            }
        }
        
        drawEvent(g, offsetX, offsetY);
    }

    /**
     * ���̃}�b�v�̃C�x���g��`��
     * 
     * @param g �`��f�o�C�X
     * @param offsetX X�����I�t�Z�b�g
     * @param offsetY Y�����I�t�Z�b�g
     */
    private void drawEvent(Graphics g, int offsetX, int offsetY) {
        for (int i=0; i<eventList.size(); i++) {
            Event evt = (Event)eventList.get(i);
            evt.draw(g, offsetX, offsetY);
        }
    }
    
    /**
     * �ړ��\���ǂ������ׂ�
     * 
     * @param x X���W
     * @param y Y���W
     * @return �ړ��\�Ȃ�true
     */
    public boolean isMovable(int x, int y) {
        // �}�b�v�`�b�v���`�F�b�N
        int mapchipNo = map[y][x];
        if (moveType[mapchipNo] == 1) {
            return false;
        }

        // �ړ��s�̃C�x���g���Ȃ����`�F�b�N
        Event evt = (Event) getEvent(x, y);
        if (evt != null && !evt.isMovable()) {
            return false;
        }
        
        // �C�x���g���Ȃ����ړ��\�C�x���g�Ȃ�ړ��\
        return true;
    }
    
    /**
     * (x,y)�̃C�x���g��Ԃ�
     * 
     * @param x X���W
     * @param y Y���W
     * @return (x,y)�̃C�x���g�B�Ȃ����null�B
     */
    public Event getEvent(int x, int y) {
        for (int i=0; i<eventList.size(); i++) {
            Event evt = (Event)eventList.get(i);
            if (evt.x == x && evt.y == y) {
                return evt;
            }
        }
        return null;
    }
    
    /**
     * �}�b�v�`�b�v�ԍ���Ԃ�
     * 
     * @param x X���W
     * @param y Y���W
     * @return �}�b�v�`�b�v�ԍ�
     */
    public int getMapchipNo(int x, int y) {
        return map[y][x];
    }

    /**
     * �C�x���g�����̃}�b�v�ɒǉ�����
     * 
     * @param evt �C�x���g
     */
    public void addEvent(Event evt) {
        eventList.add(evt);
    }
    
    /**
     * �C�x���g���폜����
     * 
     * @param evt �C�x���g
     */
    public void removeEvent(Event evt) {
        eventList.remove(evt);
    }
    
    /**
     * �C�x���g���X�g��Ԃ�
     * 
     * @return �C�x���g���X�g
     */
    public ArrayList getEventList() {
        return eventList;
    }
    
    /**
     * �}�b�v�̍s����Ԃ�
     * @return �s��
     */
    public int getRow() {
        return row;
    }
    
    /**
     * �}�b�v�̗񐔂�Ԃ�
     * 
     * @return ��
     */
    public int getCol() {
        return col;
    }
    
    /**
     * �}�b�v�̕���Ԃ�
     * 
     * @return �}�b�v�̕�
     */
    public int getWidth() {
        return width;
    }
    
    /**
     * �}�b�v�̍�����Ԃ�
     * 
     * @return �}�b�v�̍���
     */
    public int getHeight() {
        return height;
    }

    /**
     * �}�b�v����Ԃ�
     * 
     * @return �}�b�v��
     */
    public String getMapName() {
        return mapName;
    }
    
    /**
     * BGM����Ԃ�
     * 
     * @return BGM��
     */
    public String getBGM() {
        return bgmName;
    }
    
    /**
     * BGM���Z�b�g
     * 
     * @param bgmName BGM��
     */
    public void setBGM(String bgmName) {
        this.bgmName = bgmName;
    }
    
    /**
     * �}�b�v�����[�h
     * 
     * @param filename �t�@�C����
     */
    private void load(String filename) {
        System.out.println(filename);
        try {
            InputStream in = getClass().getClassLoader().getResourceAsStream(filename);
            
            // �s���E�񐔂�ǂݍ���
            row = in.read();
            col = in.read();
            width = col * CS;
            height = row * CS;

            // �}�b�v��ǂݍ���
            map = new int[row][col];
            for (int i=0; i<row; i++) {
                for (int j=0; j<col; j++) {
                    map[i][j] = in.read();  // ����8�r�b�g��ǂݍ���
                    map[i][j] = (in.read() << 8) | map[i][j];  // ���8�r�b�g��ǂݍ���Ō���
                }
            }
            
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * �}�b�v�`�b�v�ړ��\����ǂݍ���
     * 
     * @param filename �t�@�C����
     */
    private void loadMoveType(String filename) {
        moveType = new int[NUM_CHIPS];
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(getClass().getClassLoader().getResourceAsStream(filename)));
            String line = br.readLine();
            for (int i=0; i<NUM_CHIPS; i++) {
                moveType[i] = Integer.parseInt(line.charAt(i) + "");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * �C���[�W�����[�h
     * 
     */
    private void loadImage() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        mapchipImage = toolkit.getImage(getClass().getClassLoader().getResource("image/mapchip.png"));
    }
    
    /**
     * �C�x���g�����[�h����
     * 
     * @param eventFile �C�x���g�t�@�C��
     */
    private void loadEvent(String eventFile) {
        eventList = eventLoader.load(eventFile);
//        for (int i=0; i<eventList.size(); i++) {
//            Event evt = (Event)eventList.get(i);
//            System.out.println(evt);
//        }
    }
    
    /**
     * �s�N�Z���P�ʂ��}�X�P�ʂɕύX����
     * 
     * @param pixels �s�N�Z���P��
     * @return �}�X�P��
     */
    private int pixelsToTiles(int pixels) {
        return (int)Math.floor(pixels / CS);
    }
    
    /**
     * �}�X�P�ʂ��s�N�Z���P�ʂɕύX����
     * 
     * @param tiles �}�X�P��
     * @return �s�N�Z���P��
     */
    private int tilesToPixels(int tiles) {
        return tiles * CS;
    }
}
