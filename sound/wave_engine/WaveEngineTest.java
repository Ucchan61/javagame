import java.awt.Container;
import javax.swing.JFrame;

/*
 * Created on 2006/11/05
 */

public class WaveEngineTest extends JFrame {
    private static final String[] waveNames = {"attack", "spell", "escape"};
    private WaveEngine waveEngine = new WaveEngine();

    public WaveEngineTest() {
        setTitle("WAVE�G���W���e�X�g");
        setResizable(false);

        // �R���g���[���p�l����ǉ�
        ControlPanel controlPanel = new ControlPanel(waveEngine);
        Container container = getContentPane();
        container.add(controlPanel);

        // WAVE�t�@�C�������[�h
        loadWave();

        pack();
    }

    /**
     * WAVE�t�@�C�������[�h
     */
    private void loadWave() {
        // WAVE�����[�h
        for (int i = 0; i < waveNames.length; i++) {
            waveEngine.load(waveNames[i], "wave/" + waveNames[i] + ".wav");
        }
    }

    public static void main(String[] args) {
        WaveEngineTest frame = new WaveEngineTest();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
