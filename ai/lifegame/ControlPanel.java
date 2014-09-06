/*
 * �쐬��: 2004/10/15
 *
 */
import javax.swing.*;
import java.awt.event.*;
/**
 * ���C�t�Q�[���R���g���[���p�l���B
 * 
 * @author mori
 *  
 */
public class ControlPanel extends JPanel implements ActionListener {
    // ���C���p�l���ւ̎Q��
    private MainPanel panel;

    // START�{�^��
    private JButton startButton;
    // STOP�{�^��
    private JButton stopButton;
    // STEP�{�^��
    private JButton stepButton;
    // CLEAR�{�^��
    private JButton clearButton;
    // SAVE�{�^��
    private JButton saveButton;
    // RAND�{�^��
    private JButton randButton;

    /**
     * �R���X�g���N�^�B
     * 
     * @param panel ���C���p�l���ւ̎Q�ƁB
     */
    public ControlPanel(MainPanel panel) {
        this.panel = panel;

        // �{�^�����쐬
        startButton = new JButton("Start");
        stopButton = new JButton("Stop");
        stepButton = new JButton("Step");
        clearButton = new JButton("Clear");
        saveButton = new JButton("Save");
        randButton = new JButton("Rand");

        // �C�x���g�n���h����ǉ�����
        startButton.addActionListener(this);
        stopButton.addActionListener(this);
        stepButton.addActionListener(this);
        clearButton.addActionListener(this);
        saveButton.addActionListener(this);
        randButton.addActionListener(this);

        // �{�^����z�u����
        add(startButton);
        add(stopButton);
        add(stepButton);
        add(clearButton);
        add(saveButton);
        add(randButton);

        // �X�g�b�v�{�^���͍ŏ��͉����Ȃ�
        stopButton.setEnabled(false);
    }

    /**
     * �{�^���̃C�x���g�n���h���B
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            // START�{�^���������ꂽ�ꍇ
            // �{�^���̉����鉟���Ȃ����Z�b�g
            startButton.setEnabled(false);
            stopButton.setEnabled(true);
            stepButton.setEnabled(false);
            clearButton.setEnabled(false);
            panel.start();
        } else if (e.getSource() == stopButton) {
            // STOP�{�^���������ꂽ�ꍇ
            // �{�^���̉����鉟���Ȃ����Z�b�g
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
            stepButton.setEnabled(true);
            clearButton.setEnabled(true);
            panel.stop();
        } else if (e.getSource() == stepButton) {
            // STEP�{�^���������ꂽ�ꍇ
            // 1����i�߂�
            panel.step();
        } else if (e.getSource() == clearButton) {
            // CLEAR�{�^���������ꂽ�ꍇ
            // �t�B�[���h���N���A
            panel.clear();
        } else if (e.getSource() == saveButton) {
            // SAVE�{�^���������ꂽ�ꍇ
            // ���C�t��ۑ�
            panel.saveLife();
        } else if (e.getSource() == randButton) {
            panel.randLife();
        }
    }
}