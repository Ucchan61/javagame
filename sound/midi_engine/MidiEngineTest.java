import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/*
 * Created on 2006/11/05
 */

public class MidiEngineTest extends JFrame {
    private static final String[] midiNames = {"�t�̐����ꏊ��", "�Ă̗�", "�H�̖�", "�~�̐�"};
    private MidiEngine midiEngine = new MidiEngine();

    public MidiEngineTest() {
        setTitle("MIDI�G���W���e�X�g");
        setResizable(false);

        // �R���g���[���p�l����ǉ�
        ControlPanel controlPanel = new ControlPanel(midiEngine);
        Container container = getContentPane();
        container.add(controlPanel);

        // MIDI�t�@�C�������[�h
        loadMidi();

        pack();

        // �E�B���h�E������Ƃ�
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
                midiEngine.close();  // �V�[�P���T�[�����
                System.exit(0);
            }
        });
    }

    /**
     * MIDI�t�@�C�������[�h
     */
    private void loadMidi() {
        // WAVE�����[�h
        for (int i = 0; i < midiNames.length; i++) {
            midiEngine.load(midiNames[i], "midi/" + midiNames[i] + ".mid");
        }
    }

    public static void main(String[] args) {
        MidiEngineTest frame = new MidiEngineTest();
        frame.setVisible(true);
    }
}
