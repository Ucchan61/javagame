import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * Created on 2005/05/04
 *
 */

/**
 * 3�w�p�[�Z�v�g�����ɂ�镶���F���̃T���v��
 * @author mori
 *
 */
public class PatternRecognition extends JFrame {
    public PatternRecognition() {
        // �^�C�g����ݒ�
        setTitle("NN�ɂ��p�^�[���F��");
        setResizable(false);
        
        InfoPanel infoPanel = new InfoPanel();

        // ���C���p�l�����쐬���ăt���[���ɒǉ�
        MainPanel mainPanel = new MainPanel(infoPanel);
        Container contentPane = getContentPane();
        contentPane.add(mainPanel, BorderLayout.CENTER);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        panel.add(infoPanel, BorderLayout.CENTER);
        
        ControlPanel controlPanel = new ControlPanel(mainPanel);
        panel.add(controlPanel, BorderLayout.SOUTH);
        
        contentPane.add(panel, BorderLayout.EAST);
        
        // �p�l���T�C�Y�ɍ��킹�ăt���[���T�C�Y�������ݒ�
        pack();
    }

    public static void main(String[] args) {
        PatternRecognition frame = new PatternRecognition();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
