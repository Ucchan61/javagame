/*
 * �쐬��: 2004/12/06
 *
 */
import javax.swing.*;
/**
 * �G�s�\�[�h����X�e�b�v����\������p�l���B
 * @author mori
 *  
 */
public class InfoPanel extends JPanel {
    private JLabel episodeLabel;
    private JLabel stepLabel;

    public InfoPanel() {
        add(new JLabel("Episode: "));
        episodeLabel = new JLabel("0");
        add(episodeLabel);
        stepLabel = new JLabel("0");
        add(new JLabel("Step: "));
        add(stepLabel);
    }

    /**
     * �G�s�\�[�h���x���ɒl���Z�b�g����B
     * 
     * @param text �Z�b�g����e�L�X�g�B
     */
    public void setEpisodeLabel(String text) {
        episodeLabel.setText(text);
    }

    /**
     * �X�e�b�v���x���ɒl���Z�b�g����B
     * 
     * @param text �Z�b�g����e�L�X�g�B
     */
    public void setStepLabel(String text) {
        stepLabel.setText(text);
    }
}