/*
 * �C�x���g�N���X
 * 
 */
package dqc;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public abstract class Event {
    public static final int CS = 32;
    // �}�b�v�`�b�v��1�s�̐�
    public static final int NUM_CHIPS_IN_ROW = 30;
    
    // ���W�i�}�X�P�ʁj
    protected int x, y;
    // ���W�i�s�N�Z���P�ʁj
    protected int px, py;
    // �C���[�W�ԍ�
    protected int imageNo;
    // �ړ��\��
    protected boolean isMovable;

    // �}�b�v�`�b�v�C���[�W�i�N���X�ϐ��Ȃ̂ŃI�u�W�F�N�g�ŋ��L�j
    protected static Image mapchipImage;
    
    public Event(int x, int y, int imageNo, boolean isMovable) {
        this.x = x;
        this.y = y;
        px = x * CS;
        py = y * CS;
        this.imageNo = imageNo;
        this.isMovable = isMovable;
        
        // �C���[�W�����[�h
        if (mapchipImage == null) {
            loadImage();
        }
    }
    
    /**
     * �`��
     * 
     * @param g �O���t�B�b�N�X�I�u�W�F�N�g
     * @param offsetX X�����I�t�Z�b�g
     * @param offsetY Y�����I�t�Z�b�g
     * @param mapchipImage �}�b�v�`�b�v�C���[�W
     */
    public void draw(Graphics g, int offsetX, int offsetY) {
        int cx = (imageNo % NUM_CHIPS_IN_ROW) * CS;
        int cy = (imageNo / NUM_CHIPS_IN_ROW) * CS;
        g.drawImage(mapchipImage,
                px - offsetX,
                py - offsetY,
                px - offsetX + CS,
                py - offsetY + CS,
                cx,
                cy,
                cx + CS,
                cy + CS,
                null);
    }
    
    public abstract void start(Hero hero, Map map, MessageWindow msgWnd);
    
    /**
     * X���W��Ԃ�
     * 
     * @return �ʒu��X���W�i�}�X�P�ʁj
     */
    public int getX() {
        return x;
    }
    
    /**
     * Y���W��Ԃ�
     * 
     * @return �ʒu��Y���W�i�}�X�P�ʁj
     */
    public int getY() {
        return y;
    }
    
    /**
     * X���W���Z�b�g
     * 
     * @param x X���W�i�}�X�P�ʁj
     */
    public void setX(int x) {
        this.x = x;
        px = x * CS;
    }
    
    /**
     * Y���W���Z�b�g
     * 
     * @param x Y���W�i�}�X�P�ʁj
     */
    public void setY(int y) {
        this.y = y;
        py = y * CS;
    }
    
    /**
     * X���W�i�s�N�Z���P�ʁj��Ԃ�
     * 
     * @return �ʒu��X���W�i�s�N�Z���P�ʁj
     */
    public int getPx() {
        return px;
    }

    /**
     * Y���W�i�s�N�Z���P�ʁj��Ԃ�
     * 
     * @return �ʒu��Y���W�i�s�N�Z���P�ʁj
     */
    public int getPy() {
        return py;
    }
    
    /**
     * �C���[�W�ԍ���Ԃ�
     * 
     * @return �C���[�W�ԍ�
     */
    public int getImageNo() {
        return imageNo;
    }
    
    /**
     * �ړ��\�����ׂ�
     * 
     * @return �ړ��\�Ȃ�true
     */
    public boolean isMovable() {
        return isMovable;
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
     * �C�x���g�̕������Ԃ��i�f�o�b�O�p�j
     * 
     * @return �C�x���g������
     */
    public String toString() {
        return x + "," + y + "," + px + "," + py + "," + imageNo + "," + isMovable;
    }
}
