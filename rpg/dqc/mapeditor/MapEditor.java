/*
 * �}�b�v�G�f�B�^���C���t���[��
 */
package mapeditor;

import java.awt.BorderLayout;
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

public class MapEditor extends JFrame implements ActionListener {
    // ���C���p�l��
    private MainPanel mainPanel;
    private JScrollPane scrollPane;
    
    // �}�b�v�`�b�v�p���b�g
    private MapchipPalette mapchipPalette;
    // �L�����N�^�[�p���b�g
    private CharaPalette charaPalette;
    
    // ���p�l��
    private InfoPanel infoPanel;
    
    // ���j���[�A�C�e��
    private JMenuItem newItem;  // �V�K�쐬
    private JMenuItem openItem;  // �J��
    private JMenuItem saveItem;  // �ۑ�
    private JMenuItem exitItem;  // �I��
    private JMenuItem fillItem;  // �h��Ԃ�
    private JMenuItem addEventItem;  // �C�x���g�ǉ�
    private JMenuItem removeEventItem;  // �C�x���g�폜
    private JMenuItem versionItem;  // �o�[�W�������
    
    // �}�b�v�̑傫��
    // MapSizeDialog�Œl���Z�b�g����
    private int row, col;
    
    // �t�@�C���I���_�C�A���O
    private JFileChooser fileChooser = new JFileChooser(".");
    
    public MapEditor() {
        setTitle("Map Editor");

        initGUI();

        pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * GUI��������
     * 
     */
    private void initGUI() {
        // �}�b�v�`�b�v�p���b�g
        mapchipPalette = new MapchipPalette(this);
        mapchipPalette.setVisible(true);

        // �L�����N�^�[�p���b�g
        charaPalette = new CharaPalette(this);
        charaPalette.setVisible(true);
        
        // �C�x���g�_�C�A���O
        EventDialog eventDialog = new EventDialog(this, mapchipPalette, charaPalette);
        eventDialog.setVisible(false);
        
        // ���p�l��
        infoPanel = new InfoPanel();
        
        // ���C���p�l��
        mainPanel = new MainPanel(mapchipPalette, charaPalette, eventDialog, infoPanel);
        scrollPane = new JScrollPane(mainPanel);

        // �C�x���g�_�C�A���O�Ƀ��C���p�l���ւ̎Q�Ƃ��Z�b�g
        eventDialog.setMainPanel(mainPanel);
        
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(scrollPane, BorderLayout.CENTER);
        contentPane.add(infoPanel, BorderLayout.NORTH);
        // ���j���[�o�[���쐬
        createMenu();
    }

    /**
     * ���j���[�o�[���쐬
     *
     */
    private void createMenu() {
        JMenu fileMenu = new JMenu("�t�@�C��");
        JMenu editMenu = new JMenu("�ҏW");
        JMenu eventMenu = new JMenu("�C�x���g");
        JMenu helpMenu = new JMenu("�w���v");
        
        newItem = new JMenuItem("�V�K�쐬");
        openItem = new JMenuItem("�J��");
        saveItem = new JMenuItem("�ۑ�");
        exitItem = new JMenuItem("�I��");
        fillItem = new JMenuItem("�h��Ԃ�");
        addEventItem = new JMenuItem("�ǉ�");
        removeEventItem = new JMenuItem("�폜");
        versionItem = new JMenuItem("�o�[�W�������");
        
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator();  // ��؂�
        fileMenu.add(exitItem);
        editMenu.add(fillItem);
        eventMenu.add(addEventItem);
        eventMenu.add(removeEventItem);
        helpMenu.add(versionItem);
        
        newItem.addActionListener(this);
        openItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);
        fillItem.addActionListener(this);
        addEventItem.addActionListener(this);
        removeEventItem.addActionListener(this);
        versionItem.addActionListener(this);
        
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(eventMenu);
        menuBar.add(helpMenu);
        
        setJMenuBar(menuBar);
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        
        if (source == newItem) {
            newMap();
        } else if (source == openItem) {
            openMap();
        } else if (source == saveItem) {
            saveMap();
        } else if (source == exitItem) {
            exit();
        } else if (source == fillItem) {
            mainPanel.fillMap();
        } else if (source == addEventItem) {
            mainPanel.addEvent();
        } else if (source == removeEventItem) {
            mainPanel.removeEvent();
        } else if (source == versionItem) {
            JOptionPane.showMessageDialog(MapEditor.this,
                    "�}�b�v�G�f�B�^�[",
                    "�o�[�W�������",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * ���j���[/�V�K�쐬 �V�����}�b�v���쐬����
     * 
     */
    private void newMap() {
        // �}�b�v�T�C�Y�_�C�A���O���J��
        MapSizeDialog dialog = new MapSizeDialog(this);
        dialog.setVisible(true);
        
        // ��ݾك{�^���������ꂽ�牽�����Ȃ�
        if (!dialog.isOKPressed()) {
            return;
        }
        
        // ���C���p�l���ɐV�����}�b�v���쐬
        mainPanel.initMap(row, col);
        
        // �p�l���̑傫�����}�b�v�̑傫���Ɠ����ɂ���
        mainPanel.setPreferredSize(new Dimension(col * MainPanel.CS, row * MainPanel.CS));
        // �X�N���[���o�[��\��
        scrollPane.getViewport().revalidate();
        scrollPane.getViewport().repaint();
    }

    /**
     * ���j���[/�J�� �}�b�v���J��
     *
     */
    private void openMap() {
        fileChooser.addChoosableFileFilter(new MapFileFilter());
        fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        fileChooser.setDialogTitle("�}�b�v���J��");
        
        int ret = fileChooser.showOpenDialog(null);
        
        File mapFile;
        if (ret == JFileChooser.APPROVE_OPTION) {
            // �����J���{�^���������ꂽ��}�b�v�t�@�C�������[�h����
            mapFile = fileChooser.getSelectedFile();
            mainPanel.loadMap(mapFile.getPath());
            // �X�N���[���o�[��\��
            scrollPane.getViewport().revalidate();
            scrollPane.getViewport().repaint();
        }
    }
    
    /**
     * ���j���[/�ۑ� �}�b�v��ۑ�����
     *
     * @return �_�C�A���O�ŉ����ꂽ�{�^��
     */
    private int saveMap() {
        fileChooser.addChoosableFileFilter(new MapFileFilter());
        fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
        fileChooser.setDialogTitle("�}�b�v��ۑ�����");
        
        int ret = fileChooser.showSaveDialog(null);

        File mapFile;
        if (ret == JFileChooser.APPROVE_OPTION) {
            // �����ۑ��{�^���������ꂽ��}�b�v�t�@�C�����Z�[�u����
            mapFile = fileChooser.getSelectedFile();
            mainPanel.saveMap(mapFile.getPath());
        }
        
        return ret;
    }
    
    /**
     * TODO:�A�v���P�[�V�������I������
     *
     */
    private void exit() {
        
    }
    
    /**
     * �}�b�v�T�C�Y�_�C�A���O
     */
    private class MapSizeDialog extends JDialog implements ActionListener {
        private JTextField rowTextField;
        private JTextField colTextField;
        private JButton okButton;
        private JButton cancelButton;
        
        // OK�{�^���������ꂽtrue�ɂȂ�
        private boolean isOKPressed;
        
        public MapSizeDialog(JFrame owner) {
            super(owner, "�V�K�}�b�v�쐬", true);
            
            isOKPressed = false;
            
            initGUI();
        }
        
        /**
         * GUI��������
         *
         */
        private void initGUI() {
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
                    // MapEditor�̃C���X�^���X�ϐ��ɃZ�b�g
                    row = Integer.parseInt(rowTextField.getText());
                    col = Integer.parseInt(colTextField.getText());
                } catch (NumberFormatException ex) {
                    // �e�L�X�g�{�b�N�X�ɐ��l�ȊO�����͂��ꂽ�Ƃ�
                    JOptionPane.showMessageDialog(MapSizeDialog.this,
                            "���l����͂��Ă��������B");
                    rowTextField.setText("");
                    colTextField.setText("");
                    return;
                }
                
                // �}�b�v�͍Œ�15x20
                if (row < 15 || col < 20) {
                    JOptionPane.showMessageDialog(MapSizeDialog.this,
                            "�}�b�v�̑傫����15x20�ȏ�ɂ��Ă��������B");
                    row = 15;
                    col = 20;
                    return;
                }
                
                // �}�b�v�͍ő�256x256
                if (row > 256 || col > 256) {
                    JOptionPane.showMessageDialog(MapSizeDialog.this,
                            "�}�b�v�̑傫����256x256�ȓ��ɂ��Ă��������B");
                    row = col = 256;
                    return;
                }
                
                isOKPressed = true;
                setVisible(false);
            } else if (e.getSource() == cancelButton) {
                isOKPressed = false;
                setVisible(false);
            }
        }
        
        /**
         * OK�{�^���������ꂽ���`�F�b�N
         * 
         * @return �_�C�A���O��OK�{�^���������ꂽ��true
         */
        public boolean isOKPressed() {
            return isOKPressed;
        }
    }
    
    private class MapFileFilter extends FileFilter {
        public boolean accept(File file) {
            String extension = "";  // �g���q
            if (file.getPath().lastIndexOf('.') > 0) {
                extension = file.getPath().substring(
                        file.getPath().lastIndexOf('.') + 1).toLowerCase();
            }
            
            // map�t�@�C�����f�B���N�g����������true��Ԃ�
            if (extension != "") {
                return extension.equals("map");
            } else {
                return file.isDirectory();
            }
        }
        
        public String getDescription() {
            return "Map Files (*.map)";
        }
    }
    
    public static void main(String[] args) {
        new MapEditor();
    }
}
