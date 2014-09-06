/*
 * Created on 2004/12/24
 *
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
/**
 * ���p�l���B���C�t�̑I���Ɛ�����ǉ�����B
 * 
 * @author mori
 *  
 */
public class InfoPanel extends JPanel implements ActionListener {
    // ���C���p�l���ւ̎Q��
    private MainPanel panel;
    // ���C�t�̖��O��\������R���{�{�b�N�X
    private JComboBox lifeBox;
    // ������\������G���A
    private JTextArea infoArea;

    /**
     * �R���X�g���N�^�B
     * 
     * @param panel ���C���p�l���ւ̎Q�ƁB
     */
    public InfoPanel(MainPanel panel) {
        // �p�l���̐����T�C�Y��ݒ�Apack()����Ƃ��ɕK�v
        setPreferredSize(new Dimension(480, 100));

        this.panel = panel;

        // lifeBox��infoLabel��\��t����p�l��
        JPanel tempPanel = new JPanel();
        tempPanel.setLayout(new BorderLayout());

        // ���O�{�b�N�X���쐬
        lifeBox = new JComboBox();
        // ������������悤�ɂ���
        lifeBox.setEditable(true);
        // ���C�t�����[�h����
        loadLife();
        // �C�x���g���X�i�[��ǉ�
        lifeBox.addActionListener(this);
        // ���O�{�b�N�X��ǉ�
        tempPanel.add(lifeBox, BorderLayout.NORTH);

        // �����G���A���쐬
        infoArea = new JTextArea();
        infoArea.setPreferredSize(new Dimension(480, 100));
        // �܂�Ԃ����悤�ɂ���
        infoArea.setLineWrap(true);
        // ��񃉃x����ǉ�
        tempPanel.add(infoArea, BorderLayout.CENTER);
        // �悤�������C�t�Q�[����
        infoArea.setText("�悤�������C�t�Q�[���ցB");

        add(tempPanel);
    }

    /**
     * ���O�{�b�N�X�ɓ��͂���Ă��镶�����Ԃ��B
     * 
     * @return ���O�{�b�N�X�ɓ��͂���Ă��镶����B
     */
    public String getLifeName() {
        return (String) lifeBox.getEditor().getItem();
    }

    /**
     * �����G���A�ɓ��͂���Ă��镶�����Ԃ��B
     * 
     * @return �����G���A�ɓ��͂���Ă��镶����B
     */
    public String getLifeInfo() {
        return infoArea.getText();
    }

    /**
     * �����G���A�ɕ�������Z�b�g����B
     */
    public void setLifeInfo(String str) {
        infoArea.setText(str);
    }

    /**
     * ���C�t�t�@�C�������[�h���Ė��O�{�b�N�X�ɃZ�b�g����B
     *  
     */
    public void loadLife() {
        // ��ԏ�͋󔒂ɂ���
        lifeBox.addItem("");
        // ���C�t�������Ă���f�B���N�g��
        File lifeDir = new File("life");
        // ���̒��ɓ����Ă���t�@�C���̖��O���擾
        String[] lifeNameList = lifeDir.list();
        if (lifeNameList == null) return;
        for (int i = 0; i < lifeNameList.length; i++) {
            lifeBox.addItem(lifeNameList[i]);
        }
    }

    /**
     * ���O�{�b�N�X�ɒǉ�����B
     * 
     * @param lifeName �ǉ����郉�C�t�̖��O�B
     */
    public void addLife(String lifeName) {
        lifeBox.addItem(lifeName);
    }

    /**
     * ���O�{�b�N�X�Ń��C�t��I�������Ƃ��Ă΂��B
     */
    public void actionPerformed(ActionEvent e) {
        // �I������Ă��郉�C�t�̖��O
        String filename = (String) lifeBox.getSelectedItem();
        // �󔒂̂Ƃ�
        if (filename.equals("")) {
            setLifeInfo("");
            panel.clear();
        } else {
            // �󔒈ȊO�̂Ƃ��͂��̃t�@�C����ǂݍ���
            panel.loadLife(filename);
        }
    }
}