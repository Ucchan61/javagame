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

/*
 * Created on 2005/12/25
 *
 */

/**
 * @author mori
 *  
 */
public class PaletteDialog extends JDialog {
    // �p�l���̃T�C�Y�i�P�ʁF�s�N�Z���j
    public static final int WIDTH = 256;
    public static final int HEIGHT = 256;

    // �}�b�v�`�b�v�̃T�C�Y�i�P�ʁF�s�N�Z���j
    private static final int CHIP_SIZE = 32;

    // �}�b�v�`�b�v�̐�
    private static final int NUM_CHIPS = 64;
    private static final int NUM_CHIPS_IN_ROW = 8;

    // �}�b�v�`�b�v�̃C���[�W
    private Image mapChipImage;
    // �}�b�v�`�b�v���`�b�v���Ƃɕ��������C���[�W
    private Image[] mapChipImages;

    // �I������Ă���}�b�v�`�b�v�̔ԍ�
    private int selectedMapChipNo;

    public PaletteDialog(JFrame owner) {
        // ���[�h���X�_�C�A���O
        super(owner, "�}�b�v�`�b�v�p���b�g", false);
        // �ʒu�ƃT�C�Y
        setBounds(600, 0, WIDTH, HEIGHT);
        setResizable(false);

        // �_�C�A���O�Ƀp�l����ǉ�
        PalettePanel palettePanel = new PalettePanel();
        getContentPane().add(palettePanel);
        pack();

        // �}�b�v�`�b�v�C���[�W�����[�h
        loadImage();
    }

    /**
     * �I������Ă���}�b�v�`�b�v�ԍ���Ԃ�
     * @return �I������Ă���}�b�v�`�b�v�ԍ�
     */
    public int getSelectedMapChipNo() {
        return selectedMapChipNo;
    }

    /**
     * �������ꂽ�}�b�v�`�b�v�C���[�W��Ԃ�
     * @return �������ꂽ�}�b�v�`�b�v�C���[�W
     */
    public Image[] getMapChipImages() {
        return mapChipImages;
    }

    /**
     * �}�b�v�`�b�v�C���[�W�����[�h
     */
    private void loadImage() {
        ImageIcon icon = new ImageIcon(getClass().getResource(
                "image/mapchip.gif"));
        mapChipImage = icon.getImage();

        // �}�b�v�`�b�v���ƂɃC���[�W�𕪊�
        mapChipImages = new Image[NUM_CHIPS];
        for (int i = 0; i < NUM_CHIPS; i++) {
            // �`�����m��
            mapChipImages[i] = createImage(CHIP_SIZE, CHIP_SIZE);

            // �`���ɏ�������
            int x = i % NUM_CHIPS_IN_ROW;
            int y = i / NUM_CHIPS_IN_ROW;
            Graphics g = mapChipImages[i].getGraphics();
            g.drawImage(mapChipImage, 0, 0, CHIP_SIZE, CHIP_SIZE,
                    x * CHIP_SIZE, y * CHIP_SIZE,
                    x * CHIP_SIZE + CHIP_SIZE, y * CHIP_SIZE + CHIP_SIZE, null);
        }
    }

    // �p���b�g�p�l��
    private class PalettePanel extends JPanel {
        public PalettePanel() {
            setPreferredSize(new Dimension(PaletteDialog.WIDTH, PaletteDialog.HEIGHT));

            // �}�E�X�ŃN���b�N�����Ƃ����̃}�b�v�`�b�v��I������
            addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    int x = e.getX() / CHIP_SIZE;
                    int y = e.getY() / CHIP_SIZE;
                    
                    // �}�b�v�`�b�v�ԍ��͍��ォ��0,1,2�Ɛ�����
                    int mapChipNo = y * NUM_CHIPS_IN_ROW + x;
                    if (mapChipNo > NUM_CHIPS) {
                        mapChipNo = NUM_CHIPS;
                    }

                    selectedMapChipNo = mapChipNo;
                    
                    // �I������Ă���}�b�v�`�b�v��g�ň͂�
                    repaint();
                }
            });
        }

        public void paintComponent(Graphics g) {
            g.setColor(new Color(32, 0, 0));
            g.fillRect(0, 0, PaletteDialog.WIDTH, PaletteDialog.HEIGHT);

            // �}�b�v�`�b�v�C���[�W��`��
            g.drawImage(mapChipImage, 0, 0, this);
            
            // �I������Ă���}�b�v�`�b�v��g�ň͂�
            int x = selectedMapChipNo % NUM_CHIPS_IN_ROW;
            int y = selectedMapChipNo / NUM_CHIPS_IN_ROW;
            g.setColor(Color.RED);
            g.drawRect(x * CHIP_SIZE, y * CHIP_SIZE, CHIP_SIZE, CHIP_SIZE);
        }
    }
}