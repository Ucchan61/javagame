/*
 * Created on 2007/04/07
 */
package dqc;

public class SoundManager {
    private static final String[] bgmNames = {
        "lovesong", "castle", "overworld", "town", "cave", "village", "shrine", "battle"};
    
    private static final String[] seNames = {
        "barrier", "beep", "door", "stairs", "treasure", "warp", "spell", "winbattle"};

    // MIDI
    MidiEngine midiEngine = new MidiEngine();
    // WAVE
    WaveEngine waveEngine = new WaveEngine();

    public SoundManager() {
        loadSound();
    }
    
    /**
     * BGM���Đ�
     * 
     * @param bgmName BGM��
     */
    public void playBGM(String bgmName) {
        midiEngine.play(bgmName);
    }
    
    /**
     * ���ʉ����Đ�
     * 
     * @param seName ���ʉ���
     */
    public void playSE(String seName) {
        waveEngine.play(seName);
    }
    
    /**
     * �T�E���h�����[�h
     *
     */
    private void loadSound() {
        // BGM�����[�h
        for (int i=0; i<bgmNames.length; i++) {
            midiEngine.load(bgmNames[i], "bgm/" + bgmNames[i] + ".mid");
        }
        
        // ���ʉ������[�h
        for (int i=0; i<seNames.length; i++) {
            waveEngine.load(seNames[i], "se/" + seNames[i] + ".wav");
        }
    }
}
