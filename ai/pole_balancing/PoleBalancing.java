import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;

/*
 * �쐬��: 2004/12/07
 *
 */

/**
 * �A�v���P�[�V�����N���X�B
 * @author mori
 *
 */
public class PoleBalancing extends JFrame {
    public PoleBalancing() {
        // �^�C�g����ݒ�
        setTitle("�|���U�q������");

        Container contentPane = getContentPane();
        
        // �C���t�H���[�V�����p�l�����쐬
        InfoPanel infoPanel = new InfoPanel();
        contentPane.add(infoPanel, BorderLayout.NORTH);
        
        // ���C���p�l�����쐬
        MainPanel mainPanel = new MainPanel();
        contentPane.add(mainPanel, BorderLayout.CENTER);
        // ���C���p�l���ɃC���t�H���[�V�����p�l����n��
        mainPanel.setInfoPanel(infoPanel);
        
        // �R���g���[���p�l�����쐬
        ControlPanel controlPanel = new ControlPanel(mainPanel);
        contentPane.add(controlPanel, BorderLayout.SOUTH);

        // �p�l���T�C�Y�ɍ��킹�ăt���[���T�C�Y�������ݒ�
        pack();
    }

    public static void main(String[] args) {
        PoleBalancing frame = new PoleBalancing();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

