/*
 * �쐬��: 2004/12/06
 *
 */
import javax.swing.*;
/**
 * �G�s�\�[�h����o�E���h����\������p�l���B
 * @author mori
 *  
 */
public class InfoPanel extends JPanel {
    private JLabel episodeLabel;
    private JLabel boundLabel;

    public InfoPanel() {
        add(new JLabel("Episode: "));
        episodeLabel = new JLabel("0");
        add(episodeLabel);
        boundLabel = new JLabel("0");
        add(new JLabel("Bound: "));
        add(boundLabel);
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
     * �o�E���h���x���ɒl���Z�b�g����B
     * 
     * @param text �Z�b�g����e�L�X�g�B
     */
    public void setBoundLabel(String text) {
        boundLabel.setText(text);
    }
}