/*
 * �쐬��: 2004/10/15
 *
 */
import java.awt.*;
import javax.swing.*;
/**
 * ���C�t�Q�[���A�v���P�[�V�����B
 * 
 * @author mori
 *  
 */
public class LifeGame extends JFrame {
    public LifeGame() {
        // �^�C�g����ݒ�
        setTitle("���C�t�Q�[��");
        // �T�C�Y�ύX��s�\�ɂ���
        setResizable(false);

        // ���C���p�l�����쐬
        MainPanel mainPanel = new MainPanel();
        Container contentPane = getContentPane();
        contentPane.add(mainPanel, BorderLayout.CENTER);

        // �R���g���[���p�l�����쐬
        ControlPanel ctrlPanel = new ControlPanel(mainPanel);
        contentPane.add(ctrlPanel, BorderLayout.NORTH);

        // ���C�t�Q�[���̏��p�l��
        InfoPanel infoPanel = new InfoPanel(mainPanel);
        contentPane.add(infoPanel, BorderLayout.SOUTH);

        // ���C���p�l���ɏ��p�l����n��
        mainPanel.setInfoPanel(infoPanel);

        // �p�l���T�C�Y�ɍ��킹�ăt���[���T�C�Y�������ݒ�
        pack();
    }

    public static void main(String[] args) {
        LifeGame frame = new LifeGame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}