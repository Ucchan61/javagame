import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

/*
 * Created on 2007/05/04
 */

public class Wizard {
    private BufferedImage wizardImg;
    private AudioClip spellClip;

    // �A���t�@�l�ialpha=0.0�œ����Aalpha=1.0�ŕs�����j
    private float alpha = 1.0f;
    
    private MainPanel panel;

    public Wizard(MainPanel panel) {
        this.panel = panel;

        // �C���[�W�����[�h
        try {
            wizardImg = ImageIO.read(getClass().getResource("wizard.gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // �T�E���h�����[�h
        spellClip = Applet.newAudioClip(getClass().getResource("spell.wav"));
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // �A���t�@�l��0.0-1.0
        if (alpha < 0.0f)
            alpha = 0.0f;
        else if (alpha > 1.0f)
            alpha = 1.0f;

        // ���݂�Composite��ۑ�
        Composite comp = g2d.getComposite();
        // �A���t�@�l���Z�b�g
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g.drawImage(wizardImg, 70, 70, null);
        // ����Composite�ɖ߂�
        g2d.setComposite(comp);
    }

    public void remuoru() {
        System.out.println("���@�g���̓����I�����������I");

        alpha = 1.0f;
        spellClip.play();

        // alpha�l�����炷�^�C�}�[�^�X�N���N��
        TimerTask task = new FadeOutTask();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(task, 0, 100);
    }

    private class FadeOutTask extends TimerTask {
        public void run() {
            alpha -= 0.05f;
            panel.repaint();

            // ���S�ɏ�������^�X�N�I��
            if (alpha < 0) {
                cancel();
            }
        }
    }
}
