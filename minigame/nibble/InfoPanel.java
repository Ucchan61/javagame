/*
 * �쐬��: 2004/12/14
 *
 */
import javax.swing.*;
/**
 * �ւ̒����A���_�A�c�莞�Ԃ�\������p�l��
 * @author mori
 *
 */
public class InfoPanel extends JPanel {
    private JLabel lengthLabel;
    private JLabel scoreLabel;
    private JLabel timeLabel;

    public InfoPanel() {
        add(new JLabel("LENGTH: "));
        lengthLabel = new JLabel("0");
        add(lengthLabel);
        
        add(new JLabel("SCORE: "));
        scoreLabel = new JLabel("0");
        add(scoreLabel);
        
        add(new JLabel("TIME: "));
        timeLabel = new JLabel("0");
        add(timeLabel);
    }

    /**
     * LENGTH���x���ɒl���Z�b�g����B
     * 
     * @param text �Z�b�g����e�L�X�g�B
     */
    public void setLengthLabel(String text) {
        lengthLabel.setText(text);
    }

    /**
     * SCORE���x���ɒl���Z�b�g����B
     * 
     * @param text �Z�b�g����e�L�X�g�B
     */
    public void setScoreLabel(String text) {
        scoreLabel.setText(text);
    }
    
    /**
     * TIME���x���ɒl���Z�b�g����
     * 
     * @param text
     */
    public void setTimeLabel(String text) {
        timeLabel.setText(text);
    }
}
