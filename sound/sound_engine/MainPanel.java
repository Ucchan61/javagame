import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JPanel;

/*
 * Created on 2005/12/10
 *
 */

/**
 * @author mori
 *  
 */
public class MainPanel extends JPanel implements Runnable, ActionListener {

    // WAVE�t�@�C����
    private static final String[] waveNames = {"attack.wav", "spell.wav", "escape.wav"};

    private JButton s1Button, s2Button, s3Button;

    public MainPanel() {
        // �p�l���̐����T�C�Y��ݒ�Apack()����Ƃ��ɕK�v
        setPreferredSize(new Dimension(400, 40));
        // �p�l�����L�[���͂��󂯕t����悤�ɂ���
        setFocusable(true);

        // GUI���쐬
        initGUI();

        // �T�E���h�����[�h
        loadSound();

        MidiEngine.play(0);

        // �Q�[�����[�v�J�n
        Thread gameLoop = new Thread(this);
        gameLoop.start();
    }

    public void run() {
        while (true) {
            // �T�E���h�̃����_�����O�iWAVE�̂ݕK�v�j
            WaveEngine.render();

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton)e.getSource();
        
        if (button == s1Button) {
            WaveEngine.play(0);
        } else if (button == s2Button) {
            WaveEngine.play(1);
        } else if (button == s3Button) {
            WaveEngine.play(2);
        }
    }

    /**
     * GUI��������
     */
    private void initGUI() {
        s1Button = new JButton("��������");
        s2Button = new JButton("�������");
        s3Button = new JButton("�ɂ���");
        
        add(s1Button);
        add(s2Button);
        add(s3Button);
        
        s1Button.addActionListener(this);
        s2Button.addActionListener(this);
        s3Button.addActionListener(this);
    }

    /**
     * �T�E���h�����[�h
     */
    private void loadSound() {
        // WAVE�����[�h
        for (int i=0; i<waveNames.length; i++) {
            try {
                WaveEngine.load("wave/" + waveNames[i]);
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        }
        
        // MIDI�����[�h
        try {
            MidiEngine.load("midi/lovesong.mid");
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}