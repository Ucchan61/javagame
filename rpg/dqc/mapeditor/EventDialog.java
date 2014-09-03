package mapeditor;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import dqc.Chara;
import dqc.Door;
import dqc.Move;
import dqc.Treasure;

public class EventDialog extends JDialog {
    // �C�x���g���W
    private int x, y;
    
    // �C���[�W�ԍ�
    private int mapchipImageNo = 12;
    private int charaImageNo = 0;
    
    // �������ꂽ�C���[�W
    private Image[] mapchipImages;
    private Image[][] charaImages;
    
    // �C�x���g���X�g
    private ArrayList eventList = new ArrayList();

    // GUI���i
    private JLabel mapchipLabel;
    private JTextField destMapNameText;
    private JTextField destXText, destYText;
    private JButton okButton, cancelButton;
    private JLabel charaLabel;
    private JComboBox directionBox, moveTypeBox;
    private JTextArea messageArea;
    private JLabel treasureLabel;
    private JTextField itemText;
    private JLabel doorLabel;
    
    // �}�b�v�`�b�v�p���b�g�ւ̎Q��
    private MapchipPalette mapchipPalette;
    // �L�����N�^�[�p���b�g�ւ̎Q��
    private CharaPalette charaPalette;
    // ���C���p�l���ւ̎Q��
    private MainPanel mainPanel;
    
    public EventDialog(JFrame owner, MapchipPalette mapchipPalette, CharaPalette charaPalette) {
        super(owner, "�C�x���g�_�C�A���O", false);
        setResizable(false);
        
        this.mapchipPalette = mapchipPalette;
        this.charaPalette = charaPalette;
        
        // ���������}�b�v�`�b�v�C���[�W���}�b�v�`�b�v�p���b�g����擾
        mapchipImages = mapchipPalette.getMapchipImages();
        charaImages = charaPalette.getCharaImages();
        
        // GUI��������
        initGUI();
        pack();
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
     * �C�x���g���X�g���Z�b�g����
     * 
     * @param eventList �C�x���g���X�g
     */
    public void setEventList(ArrayList eventList) {
        this.eventList = eventList;
    }
    
    private void initGUI() {
        // �^�u�y�C��
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // �ړ��C�x���g�^�u
        JPanel moveEventPanel = createMoveEventTab();
        // �L�����N�^�[�C�x���g�^�u
        JPanel charaEventPanel = createCharaEventTab();
        // �󔠃C�x���g�^�u
        JPanel treasureEventPanel = createTreasureEventTab();
        // ���C�x���g�^�u
        JPanel doorEventPanel = createDoorEventTab();
        
        tabbedPane.addTab("�ړ�", moveEventPanel);
        tabbedPane.addTab("�L����", charaEventPanel);
        tabbedPane.addTab("��", treasureEventPanel);
        tabbedPane.addTab("��", doorEventPanel);
        
        getContentPane().add(tabbedPane);
    }

    /**
     * �ړ��C�x���g�^�u���쐬
     * 
     * @return �ړ��C�x���g�^�u�̃p�l��
     */
    private JPanel createMoveEventTab() {
        JPanel moveEventPanel = new JPanel();
        moveEventPanel.setLayout(new BorderLayout());
        
        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(7, 2));
        
        mapchipLabel = new JLabel();
        mapchipLabel.setIcon(new ImageIcon(mapchipImages[mapchipImageNo]));
        mapchipLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                // �I������Ă���}�b�v�`�b�v�����x���ɃZ�b�g
                mapchipImageNo = mapchipPalette.getSelectedMapchipNo();
                mapchipLabel.setIcon(new ImageIcon(mapchipImages[mapchipImageNo]));
            }
        });
        p1.add(new JLabel("�}�b�v�`�b�v"));
        p1.add(mapchipLabel);
        
        // �ړ���}�b�v��
        destMapNameText = new JTextField(8);
        p1.add(new JLabel("�ړ���}�b�v��"));
        p1.add(destMapNameText);
        
        // �ړ�����W
        destXText = new JTextField(8);
        p1.add(new JLabel("�ړ���X"));
        p1.add(destXText);
        
        destYText = new JTextField(8);
        p1.add(new JLabel("�ړ���Y"));
        p1.add(destYText);
        
        // OK�A�L�����Z���{�^��
        JPanel p2 = new JPanel();
        okButton = new JButton("OK");
        cancelButton = new JButton("��ݾ�");
        p2.add(okButton);
        p2.add(cancelButton);
        
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Move�̐���
                Move evt;
                try {
                    evt = new Move(x, y, mapchipImageNo,
                            destMapNameText.getText(),
                            Integer.parseInt(destXText.getText()),
                            Integer.parseInt(destYText.getText()));
                } catch (NumberFormatException ex) {
                    // ���l�ȊO�����͂���AInteger�ɕϊ��ł��Ȃ������Ƃ�
                    JOptionPane.showMessageDialog(EventDialog.this, "X���W��Y���W�͐��l����͂��Ă��������B");
                    destXText.setText("");
                    destYText.setText("");
                    return;
                }
                // �C�x���g���X�g�ɒǉ�
                eventList.add(evt);
                setVisible(false);
                mainPanel.repaint();
                System.out.println("�ړ��C�x���g�ǉ�: " + evt);
            }
        });
        
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        
        moveEventPanel.add(p1, BorderLayout.CENTER);
        moveEventPanel.add(p2, BorderLayout.SOUTH);
        
        return moveEventPanel;
    }
    
    /**
     * �L�����N�^�[�C�x���g�^�u���쐬
     * 
     * @return �L�����N�^�[�C�x���g�^�u�̃p�l��
     */
    private JPanel createCharaEventTab() {
        JPanel charaEventPanel = new JPanel();
        charaEventPanel.setLayout(new BorderLayout());
        
        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(3, 2));
        
        charaLabel = new JLabel();
        charaLabel.setIcon(new ImageIcon(charaImages[charaImageNo][0]));
        charaLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                // �I������Ă���L�����N�^�[�����x���ɃZ�b�g
                charaImageNo = charaPalette.getSelectedCharaNo();
                int index = directionBox.getSelectedIndex();
                charaLabel.setIcon(new ImageIcon(charaImages[charaImageNo][index]));
            }
        });
        p1.add(new JLabel("�L�����N�^�["));
        p1.add(charaLabel);
        
        // ����
        directionBox = new JComboBox();
        directionBox.setEditable(false);
        directionBox.addItem("������");
        directionBox.addItem("�����");
        directionBox.addItem("������");
        directionBox.addItem("�E����");
        directionBox.setSelectedIndex(0);  // �f�t�H���g�͉�����
        directionBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = directionBox.getSelectedIndex();
                // ���x���̊G�̌�����ς���
                charaLabel.setIcon(new ImageIcon(charaImages[charaImageNo][index]));
            }
        });
        p1.add(new JLabel("����"));
        p1.add(directionBox);
        
        // �ړ��^�C�v
        moveTypeBox = new JComboBox();
        moveTypeBox.setEditable(false);
        moveTypeBox.addItem("�ړ����Ȃ�");
        moveTypeBox.addItem("�����_���ړ�");
        p1.add(new JLabel("�ړ��^�C�v"));
        p1.add(moveTypeBox);
        
        // ���b�Z�[�W
        JPanel p2 = new JPanel();
        messageArea = new JTextArea();
        messageArea.setRows(5);
        messageArea.setColumns(20);
        messageArea.setLineWrap(true);
        messageArea.setText("���b�Z�[�W����͂��Ă��������B");
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(messageArea);
        p2.add(scrollPane);
        
        // OK�A�L�����Z���{�^��
        JPanel p3 = new JPanel();
        okButton = new JButton("OK");
        cancelButton = new JButton("��ݾ�");
        p3.add(okButton);
        p3.add(cancelButton);
        
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Chara�̐���
                Chara chara = new Chara(x, y, charaImageNo,
                            directionBox.getSelectedIndex(),
                            moveTypeBox.getSelectedIndex(),
                            messageArea.getText(),
                            null);
                // �C�x���g���X�g�ɒǉ�
                eventList.add(chara);
                setVisible(false);
                mainPanel.repaint();
                System.out.println("�L�����N�^�[�C�x���g�ǉ�: " + chara);
            }
        });
        
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        
        charaEventPanel.add(p1, BorderLayout.NORTH);
        charaEventPanel.add(p2, BorderLayout.CENTER);
        charaEventPanel.add(p3, BorderLayout.SOUTH);
        
        return charaEventPanel;
    }
    
    /**
     * �󔠃C�x���g�^�u���쐬
     * TODO: �󔠂̃C���[�W��ς�����悤�ɂ���
     * TODO: TreasureEvent�͂ڂȂǃA�C�e�������ʂɎg��
     * 
     * @return �󔠃C�x���g�^�u�̃p�l��
     */
    private JPanel createTreasureEventTab() {
        JPanel treasureEventPanel = new JPanel();
        treasureEventPanel.setLayout(new BorderLayout());
        
        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(2, 2));
        
        mapchipImageNo = 115;  // 115�͕󔠂̊G
        treasureLabel = new JLabel();
        treasureLabel.setIcon(new ImageIcon(mapchipImages[mapchipImageNo]));
        treasureLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                // �I������Ă���}�b�v�`�b�v�����x���ɃZ�b�g
                mapchipImageNo = mapchipPalette.getSelectedMapchipNo();
                treasureLabel.setIcon(new ImageIcon(mapchipImages[mapchipImageNo]));
            }
        });
        
        p1.add(new JLabel("��"));
        p1.add(treasureLabel);
        
        itemText = new JTextField(8);
        p1.add(new JLabel("�A�C�e����"));
        p1.add(itemText);
        
        // OK�A�L�����Z���{�^��
        JPanel p2 = new JPanel();
        okButton = new JButton("OK");
        cancelButton = new JButton("��ݾ�");
        p2.add(okButton);
        p2.add(cancelButton);
        
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // TreasureEvent�̍쐬
                Treasure treasure = new Treasure(x, y, mapchipImageNo, itemText.getText());
                
                // �C�x���g���X�g�ɒǉ�
                eventList.add(treasure);
                setVisible(false);
                mainPanel.repaint();
                System.out.println("�󔠃C�x���g�ǉ�: " + treasure);
            }
        });
        
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        
        treasureEventPanel.add(p1, BorderLayout.NORTH);
        treasureEventPanel.add(p2, BorderLayout.SOUTH);
        
        return treasureEventPanel;
    }
    
    /**
     * ���C�x���g�^�u���쐬
     * TODO: �S���Ƃ��ɑΉ��ł���悤�ɃC���[�W���ςɂ���
     * 
     * @return ���C�x���g�^�u�̃p�l��
     */
    private JPanel createDoorEventTab() {
        JPanel treasureEventPanel = new JPanel();
        treasureEventPanel.setLayout(new BorderLayout());
        
        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(1, 2));
        
        mapchipImageNo = 85;  // 85�͕󔠂̊G
        
        doorLabel = new JLabel();
        doorLabel.setIcon(new ImageIcon(mapchipImages[mapchipImageNo]));
        doorLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                // �I������Ă���}�b�v�`�b�v�����x���ɃZ�b�g
                mapchipImageNo = mapchipPalette.getSelectedMapchipNo();
                doorLabel.setIcon(new ImageIcon(mapchipImages[mapchipImageNo]));
            }
        });
        
        p1.add(new JLabel("��"));
        p1.add(doorLabel);
        
        // OK�A�L�����Z���{�^��
        JPanel p2 = new JPanel();
        okButton = new JButton("OK");
        cancelButton = new JButton("��ݾ�");
        p2.add(okButton);
        p2.add(cancelButton);
        
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // DoorEvent�̍쐬
                Door door = new Door(x, y, mapchipImageNo);
                
                // �C�x���g���X�g�ɒǉ�
                eventList.add(door);
                setVisible(false);
                mainPanel.repaint();
                System.out.println("���C�x���g�ǉ�: " + door);
            }
        });
        
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        
        treasureEventPanel.add(p1, BorderLayout.NORTH);
        treasureEventPanel.add(p2, BorderLayout.SOUTH);
        
        return treasureEventPanel;
    }
    
    /**
     * �C�x���g���W���Z�b�g
     * 
     * @param x X���W
     * @param y Y���W
     */
    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * ���C���p�l���ւ̎Q�Ƃ��Z�b�g
     * 
     * @param mainPanel ���C���p�l��
     */
    public void setMainPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }
}
