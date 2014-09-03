/*
 * �}�b�v�`�b�v�p�l��
 */
package mapeditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MapchipPalette extends JDialog {
    public static final int WIDTH = 480;
    public static final int HEIGHT = 256;
    
    // �}�b�v�`�b�v�̃T�C�Y�i�s�N�Z���P�ʁj
    private static final int CS = 16;
    
    // �}�b�v�`�b�v��
    private static final int NUM_CHIPS = 480;
    // 1�s�̃}�b�v�`�b�v��
    private static final int NUM_CHIPS_IN_ROW = 30;
    
    // �}�b�v�`�b�v�C���[�W
    private Image mapchipImage = null;
    // �}�b�v�`�b�v���Ƃɕ��������C���[�W
    private Image[] divImages = null;
    
    // �I������Ă���}�b�v�`�b�v�ԍ�
    private int selectedMapchipNo = 12;  // �È�
    
    public MapchipPalette(JFrame owner) {
        super(owner, "�}�b�v�`�b�v�p���b�g", false);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBounds(680, 210, WIDTH, HEIGHT);
        setResizable(false);
        
        // �_�C�A���O�̏�Ƀp�l�����̂���
        MapchipPanel mapchipPanel = new MapchipPanel();
        getContentPane().add(mapchipPanel);
        
        pack();
        
        loadImage();
    }
    
    /**
     * �I������Ă���}�b�v�`�b�v�ԍ���Ԃ�
     * 
     * @return �I������Ă���}�b�v�`�b�v�ԍ�
     */
    public int getSelectedMapchipNo() {
        return selectedMapchipNo;
    }
    
    /**
     * �}�b�v�`�b�v�ԍ����Z�b�g
     * 
     * @param mapchipNo �}�b�v�`�b�v�ԍ�
     */
    public void setSelectedMapchipNo(int mapchipNo) {
        selectedMapchipNo = mapchipNo;
    }
    
    /**
     * ���������}�b�v�`�b�v�̃C���[�W��Ԃ�
     * 
     * @return ���������}�b�v�`�b�v�C���[�W
     */
    public Image[] getMapchipImages() {
        return divImages;
    }

    /**
     * �C���[�W�����[�h
     *
     */
    private void loadImage() {
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("image/mapchip_16.png"));
        mapchipImage = icon.getImage();
        
        // �}�b�v�`�b�v���ƂɃC���[�W�𕪊�
        divImages = new Image[NUM_CHIPS];
        for (int i=0; i<NUM_CHIPS; i++) {
            // �`�����m��
            divImages[i] = createImage(CS, CS);
            // �`��
            int x = i % NUM_CHIPS_IN_ROW;
            int y = i / NUM_CHIPS_IN_ROW;
            Graphics g = divImages[i].getGraphics();
            g.drawImage(mapchipImage, 0, 0, CS, CS,
                    x * CS, y * CS,
                    x * CS + CS, y * CS + CS, this);
            g.dispose();
        }
    }
    
    private class MapchipPanel extends JPanel {
        public MapchipPanel() {
            setPreferredSize(new Dimension(WIDTH, HEIGHT));
            
            // �}�E�X�ŃN���b�N�����Ƃ����̃}�b�v�`�b�v��I������
            addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    int x = e.getX() / CS;
                    int y = e.getY() / CS;
                    
                    int mapchipNo = y * NUM_CHIPS_IN_ROW + x;
                    if (mapchipNo > NUM_CHIPS) {
                        mapchipNo = 0;
                    }
                    
                    selectedMapchipNo = mapchipNo;
                    // �I������Ă���}�b�v�`�b�v��g�ň͂�
                    repaint();
                }
            });
        }
        
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            // �}�b�v�`�b�v��`��
            g.drawImage(mapchipImage, 0, 0, this);
            g.drawImage(divImages[10], 100, 100, this);
            // �I������Ă���}�b�v�`�b�v��g�ň͂�
            int x = selectedMapchipNo % NUM_CHIPS_IN_ROW;
            int y = selectedMapchipNo / NUM_CHIPS_IN_ROW;
            g.setColor(Color.YELLOW);
            g.drawRect(x * CS, y * CS, CS, CS);
        }
    }
}
