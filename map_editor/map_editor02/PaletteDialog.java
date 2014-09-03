import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * Created on 2005/12/17
 *
 */

/**
 * @author mori
 *  
 */
public class PaletteDialog extends JDialog {
    // �p�l���̃T�C�Y�i�P�ʁF�s�N�Z���j
    private static final int WIDTH = 256;
    private static final int HEIGHT = 256;

    // �}�b�v�`�b�v�̃T�C�Y�i�P�ʁF�s�N�Z���j
    private static final int CHIP_SIZE = 32;

    // �}�b�v�`�b�v�̃C���[�W
    private Image mapChipImage;

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
     * �}�b�v�`�b�v�C���[�W�����[�h
     */
    private void loadImage() {
        ImageIcon icon = new ImageIcon(getClass().getResource(
                "image/mapchip.gif"));
        mapChipImage = icon.getImage();
    }

    // �p���b�g�p�l��
    private class PalettePanel extends JPanel {
        private static final int NUM_CHIPS = 64;

        public PalettePanel() {
            setPreferredSize(new Dimension(PaletteDialog.WIDTH,
                    PaletteDialog.HEIGHT));
        }

        public void paintComponent(Graphics g) {
            g.setColor(new Color(32, 0, 0));
            g.fillRect(0, 0, PaletteDialog.WIDTH, PaletteDialog.HEIGHT);

            // �}�b�v�`�b�v�C���[�W��`��
            g.drawImage(mapChipImage, 0, 0, this);
        }
    }
}