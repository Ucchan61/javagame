/*
 * �쐬��: 2004/12/14
 *
 */
import java.awt.*;
import javax.swing.*;
/**
 * Nibble�A�v���P�[�V����
 * 
 * @author mori
 *  
 */
public class Nibble extends JFrame {
    public Nibble() {
        // �^�C�g����ݒ�
        setTitle("Nibble");

        Container contentPane = getContentPane();

        // �C���t�H���[�V�����p�l�����쐬
        InfoPanel infoPanel = new InfoPanel();
        contentPane.add(infoPanel, BorderLayout.NORTH);

        // ���C���p�l�����쐬���ăt���[���ɒǉ�
        MainPanel mainPanel = new MainPanel(infoPanel);
        contentPane.add(mainPanel, BorderLayout.CENTER);

        // �p�l���T�C�Y�ɍ��킹�ăt���[���T�C�Y�������ݒ�
        pack();
    }

    public static void main(String[] args) {
        Nibble frame = new Nibble();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}