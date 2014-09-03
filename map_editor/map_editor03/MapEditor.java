import java.awt.Container;

import javax.swing.JFrame;

/*
 * Created on 2005/12/17
 *
 */

/**
 * @author mori
 *
 */
public class MapEditor extends JFrame {
    // ���C���p�l��
    private MainPanel mainPanel;

    // �}�b�v�`�b�v�p���b�g
    private PaletteDialog paletteDialog;

    public MapEditor() {
        setTitle("�}�b�v�`�b�v�̑I���ƕ`��");
        setResizable(false);
        
        initGUI();

        // �p�l���T�C�Y�ɍ��킹�ăt���[���T�C�Y�������ݒ�
        pack();
    }

    /**
     * GUI��������
     */
    private void initGUI() {
        // �}�b�v�`�b�v�p���b�g���쐬
        paletteDialog = new PaletteDialog(this);
        paletteDialog.setVisible(true);
        
        // ���C���p�l�����쐬
        mainPanel = new MainPanel(paletteDialog);
        Container contentPane = getContentPane();
        contentPane.add(mainPanel);
    }
    
    public static void main(String[] args) {
        MapEditor frame = new MapEditor();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
