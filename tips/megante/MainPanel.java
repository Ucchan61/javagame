import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.PixelGrabber;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/*
 * Created on 2005/12/03
 *
 */

/**
 * @author mori
 *  
 */
public class MainPanel extends JPanel implements ActionListener {
    // �p�l���T�C�Y
    public static final int WIDTH = 400;
    public static final int HEIGHT = 400;

    // �j�ӂ���摜
    private Image image;

    // �摜�̕��ƍ���
    private int width;
    private int height;

    // �摜�̊e�j�Ёi�s�N�Z���j
    private Fraction[] fractions;

    // �A�j���[�V�����p�^�C�}�[
    private Timer timer;

    // �{�^��
    private JButton meganteButton;
    private JButton revivalButton;

    // �����̉�
    private AudioClip spellClip;

    private Random rand = new Random();

    public MainPanel() {
        // �p�l���̐����T�C�Y��ݒ�
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // ���R���C�A�E�g
        setLayout(null);

        meganteButton = new JButton("���K���e");
        meganteButton.addActionListener(this);
        add(meganteButton);
        meganteButton.setBounds(10, 10, 100, 30);

        revivalButton = new JButton("�U�I���N");
        revivalButton.addActionListener(this);
        add(revivalButton);
        revivalButton.setBounds(120, 10, 100, 30);
        revivalButton.setEnabled(false);

        // �C���[�W�����[�h����
        loadImage("suraimu.gif");

        // �T�E���h�����[�h
        spellClip = Applet.newAudioClip(getClass().getResource("spell.wav"));

        // �C���[�W����j�Ђ��쐬
        init(image);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == meganteButton) {
            spellClip.play();
            megante();
            meganteButton.setEnabled(false);
            revivalButton.setEnabled(true);
        } else if (e.getSource() == revivalButton) {
            spellClip.play();
            init(image);
            repaint();
            meganteButton.setEnabled(true);
            revivalButton.setEnabled(false);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // �w�i��h��Ԃ�
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // �j�Ђ�1��1��`��
        for (int n = 0; n < width * height; n++) {
            int x = (int) fractions[n].x;
            int y = (int) fractions[n].y;
            // �s�N�Z���l����F�����o��
            // �e�s�N�Z����ARGB�̏��ɕ���ł���i�e8�r�b�g�j
            int red = (fractions[n].color >> 16) & 0xff;
            int green = (fractions[n].color >> 8) & 0xff;
            int blue = (fractions[n].color) & 0xff;
            // �F��ݒ肷��
            g.setColor(new Color(red, green, blue));
            // �_��ł�
            g.drawLine(x, y, x, y);
        }
    }

    private void loadImage(String filename) {
        // �X���C���̉摜�����[�h����
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));
        image = icon.getImage();
        width = image.getWidth(this);
        height = image.getHeight(this);
    }

    /**
     * �j�Ђ�������
     * 
     * @param image
     */
    private void init(Image image) {
        if (timer != null) {
            timer.cancel();
        }

        fractions = new Fraction[width * height];

        // �C���[�W����PixelGrabber�𐶐�
        PixelGrabber pg = new PixelGrabber(image, 0, 0, width, height, true);
        try {
            pg.grabPixels();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // �s�N�Z������擾���Ċe�s�N�Z������Fraction�����
        int pixel[] = (int[]) pg.getPixels();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int n = y * width + x;
                fractions[n] = new Fraction();
                // ��юU������������_���Ɍ��߂�
                double angle = rand.nextInt(360);
                // ��юU��X�s�[�h�������_���Ɍ��߂�
                double speed = 10.0 / rand.nextInt(30);
                // fraction�ɒl���Z�b�g
                fractions[n].x = 184 + x;
                fractions[n].y = 184 + y;
                fractions[n].vx = Math.cos(angle * Math.PI / 180) * speed;
                fractions[n].vy = Math.sin(angle * Math.PI / 180) * speed;
                fractions[n].color = pixel[n];
                fractions[n].countToCrush = x / 6 + rand.nextInt(10);
            }
        }
    }

    /**
     * ���K���e�I��юU��A�j���[�V�������J�n
     */
    private void megante() {
        TimerTask task = new CrashTask();
        timer = new Timer();
        timer.schedule(task, 0, 20L);
    }

    class CrashTask extends TimerTask {
        public void run() {
            for (int n = 0; n < width * height; n++) {
                if (fractions[n].countToCrush <= 0) { // �J�E���g0�̔j��
                    // �j�Ђ��ړ�����
                    fractions[n].x += fractions[n].vx;
                    fractions[n].y += fractions[n].vy;
                    fractions[n].vy += 0.1; // �������ɉ����x��������
                } else {
                    // �j�Ђ���юU��܂ł̃J�E���g�_�E���I
                    fractions[n].countToCrush--;
                }
            }

            repaint();
        }
    }
}