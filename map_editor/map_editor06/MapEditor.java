import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

/*
 * Created on 2005/12/25
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

    // ���j���[�A�C�e��
    private JMenuItem newItem; // �V�K�쐬
    private JMenuItem openItem;  // �J��
    private JMenuItem saveItem;  // �ۑ�

    // MapSizeDialog����擾�����s���E��
    private int row, col;

    // �X�N���[���y�C��
    private JScrollPane scrollPane;

    // �t�@�C���I���_�C�A���O�i�J�����g�f�B���N�g�����n�_�j
    private JFileChooser fileChooser = new JFileChooser(".");

    public MapEditor() {
        setTitle("�}�b�v���J��");

        row = col = 16;  // �f�t�H���g�l
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
        // ���C���p�l�����X�N���[���y�C���̏�ɏ悹��
        scrollPane = new JScrollPane(mainPanel);
        Container contentPane = getContentPane();
        contentPane.add(scrollPane);

        // �t�@�C�����j���[
        JMenu fileMenu = new JMenu("�t�@�C��");
        newItem = new JMenuItem("�V�K�쐬");
        openItem = new JMenuItem("�J��");
        saveItem = new JMenuItem("�ۑ�");

        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);

        newItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newMap();
            }
        });

        openItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openMap();
            }
        });

        saveItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveMap();
            }
        });

        // ���j���[�o�[
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }

    /**
     * ���j���[->�V�K�쐬
     * �V�����}�b�v���쐬����
     */
    public void newMap() {
        // �}�b�v�T�C�Y�_�C�A���O���J��
        // row��col��ݒ�
        MapSizeDialog dialog = new MapSizeDialog(this);
        dialog.setVisible(true);

        // ��ݾك{�^���������ꂽ�Ƃ��͉������Ȃ�
        if (!dialog.isOKPressed()) {
            return;
        }

        // ���C���p�l���ɐV�����}�b�v���쐬
        mainPanel.initMap(row, col);

        // �p�l���̑傫�����}�b�v�̑傫���Ɠ����ɂ���
        // �p�l�����傫���Ƃ��͎����I�ɃX�N���[���o�[���\�������
        mainPanel.setPreferredSize(new Dimension(col * MainPanel.CHIP_SIZE, row * MainPanel.CHIP_SIZE));
        // �p�l�����傫���Ȃ�����X�N���[���o�[��\������
        scrollPane.getViewport().revalidate();
        scrollPane.getViewport().repaint();
    }

    /**
     * ���j���[->�J��
     * �}�b�v���J��
     */
    public void openMap() {
        fileChooser.addChoosableFileFilter(new MapFileFilter());  // �t�@�C���t�B���^
        fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        fileChooser.setDialogTitle("�}�b�v���J��");
        int ret = fileChooser.showOpenDialog(null);
        File mapFile;
        if (ret == JFileChooser.APPROVE_OPTION) {
            // �����J���{�^���������ꂽ�炻�̃}�b�v�t�@�C�������[�h����
            mapFile = fileChooser.getSelectedFile();
            mainPanel.loadMap(mapFile);
            // �p�l�����傫���Ȃ�����X�N���[���o�[��\������
            scrollPane.getViewport().revalidate();
            scrollPane.getViewport().repaint();
        }
    }

    /**
     * ���j���[->�ۑ�
     * �}�b�v��ۑ�����
     */
    public void saveMap() {
        fileChooser.addChoosableFileFilter(new MapFileFilter());  // �t�@�C���t�B���^
        fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
        fileChooser.setDialogTitle("�}�b�v��ۑ�����");
        int ret = fileChooser.showSaveDialog(null);
        File mapFile;
        if (ret == JFileChooser.APPROVE_OPTION) {
            // �����ۑ��{�^���������ꂽ�炻�̃}�b�v�t�@�C�����Z�[�u����
            mapFile = fileChooser.getSelectedFile();
            mainPanel.saveMap(mapFile);
        }
    }

    public static void main(String[] args) {
        MapEditor frame = new MapEditor();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * ���j���[->�V�K�쐬�ŕ\�������_�C�A���O
     * �s���Ɨ񐔂���͂���row��col��ݒ肷��
     */
    private class MapSizeDialog extends JDialog implements ActionListener {
        private JTextField rowTextField;
        private JTextField colTextField;
        private JButton okButton;
        private JButton cancelButton;

        // OK�{�^���������ꂽ��true�ɂȂ�
        // ��ݾك{�^���Ȃ�false�ɂȂ�
        private boolean isOKPressed;;

        public MapSizeDialog(JFrame owner) {
            super(owner, "�}�b�v�쐬", true);
            
            isOKPressed = false;

            rowTextField = new JTextField(4);
            colTextField = new JTextField(4);
            okButton = new JButton("OK");
            cancelButton = new JButton("��ݾ�");
            okButton.addActionListener(this);
            cancelButton.addActionListener(this);

            JPanel p1 = new JPanel();
            p1.add(new JLabel("�s��"));
            p1.add(rowTextField);

            JPanel p2 = new JPanel();
            p2.add(new JLabel("��"));
            p2.add(colTextField);

            JPanel p3 = new JPanel();
            p3.add(okButton);
            p3.add(cancelButton);

            Container contentPane = getContentPane();
            contentPane.setLayout(new GridLayout(3, 1));
            contentPane.add(p1);
            contentPane.add(p2);
            contentPane.add(p3);

            pack();
        }

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == okButton) {
                try {
                    // MapEditor�N���X�̃C���X�^���X�ϐ�row��col��ݒ�
                    row = Integer.parseInt(rowTextField.getText());
                    col = Integer.parseInt(colTextField.getText());
                    // �}�b�v�͍Œ�16x16�̃T�C�Y
                    if (row < 16 || col < 16) {
                        JOptionPane.showMessageDialog(MapSizeDialog.this,
                                "�}�b�v�̑傫����16x16�ȏ�ɂ��Ă�������");
                        row = col = 16;
                        return;
                    }
                    // �}�b�v�̍ő�T�C�Y��256x256
                    if (row > 256 || col > 256) {
                        JOptionPane.showMessageDialog(MapSizeDialog.this,
                                "�}�b�v�̑傫����256x256�ȓ��ɂ��Ă�������");
                        row = col = 256;
                        return;
                    }
                } catch (NumberFormatException ex) {
                    // �e�L�X�g�{�b�N�X�ɐ��l�ȊO�����͂��ꂽ�Ƃ�
                    JOptionPane.showMessageDialog(MapSizeDialog.this,
                            "���l����͂��Ă�������");
                    rowTextField.setText("");
                    colTextField.setText("");
                    return;
                }
                isOKPressed = true;
                setVisible(false);
            } else if (e.getSource() == cancelButton) {
                isOKPressed = false;
                // ��ݾقȂ牽�����Ȃ�
                setVisible(false);
            }
        }
        
        public boolean isOKPressed() {
            return isOKPressed;
        }
    }

    /**
     * �}�b�v�t�@�C���t�B���^�i.map�t�@�C���ƃt�H���_�����\������j
     */
    private class MapFileFilter extends FileFilter {
        public boolean accept(File file) {
            String extension = "";  // �g���q
            if (file.getPath().lastIndexOf('.') > 0) {
                extension = file.getPath().substring(file.getPath().lastIndexOf('.') + 1).toLowerCase();
            }
            if (extension != "") {
                return extension.equals("map");  // map��������true��Ԃ�
            } else {
                return file.isDirectory();  // �f�B���N�g����������true��Ԃ�
            }
        }
        
        public String getDescription() {
            return "Map Files (*.map)";
        }
    }
}