import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;

import javax.swing.JPanel;

/*
 * Created on 2006/02/25
 */

public class MainPanel extends JPanel {
    // �p�l���T�C�Y
    private static final int WIDTH = 369;
    private static final int HEIGHT = 254;
    // �C���[�W
    private Image image;

    public MainPanel() {
        // �p�l���̐����T�C�Y��ݒ�Apack()����Ƃ��ɕK�v
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        // �C���[�W��ǂݍ���
        image = Toolkit.getDefaultToolkit().getImage(
                getClass().getResource("image.gif"));

        // MediaTracker�ɓo�^
        MediaTracker tracker = new MediaTracker(this);
        tracker.addImage(image, 0);
        // �C���[�W�ǂݍ��݊����܂őҋ@
        try {
            tracker.waitForID(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(image, 0, 0, this);
    }
}
