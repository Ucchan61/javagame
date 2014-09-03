import java.awt.*;
import javax.swing.*;

public class MazeLearning extends JFrame {
    public MazeLearning() {
        // �^�C�g����ݒ�
        setTitle("���H�w�K");

        // ���C���p�l�����쐬
        MainPanel mainPanel = new MainPanel();
        Container contentPane = getContentPane();
        contentPane.add(mainPanel, BorderLayout.CENTER);

        // �R���g���[���p�l�����쐬
        ControlPanel controlPanel = new ControlPanel(mainPanel);
        contentPane.add(controlPanel, BorderLayout.SOUTH);

        // �C���t�H���[�V�����p�l�����쐬
        InfoPanel infoPanel = new InfoPanel();
        contentPane.add(infoPanel, BorderLayout.NORTH);
        // ���C���p�l���ɃC���t�H���[�V�����p�l����n��
        mainPanel.setInfoPanel(infoPanel);

        // �p�l���T�C�Y�ɍ��킹�ăt���[���T�C�Y�������ݒ�
        pack();
    }

    public static void main(String[] args) {
        MazeLearning frame = new MazeLearning();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}