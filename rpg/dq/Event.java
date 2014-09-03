/*
 * Created on 2005/12/25
 *
 */

/**
 * @author mori
 *
 */
public abstract class Event {
    // X���W
    public int x;
    // Y���W
    public int y;
    // �`�b�v�ԍ�
    public int chipNo;
    // �Ԃ��邩
    public boolean isHit;
    
    /**
     * �R���X�g���N�^
     * @param x X���W
     * @param y Y���W
     * @param chipNo �`�b�v�ԍ�
     * @param isHit �Ԃ��邩
     */
    public Event(int x, int y, int chipNo, boolean isHit) {
        this.x = x;
        this.y = y;
        this.chipNo = chipNo;
        this.isHit = isHit;
    }
    
    /**
     * �C�x���g�𕶎���ɕϊ��i�f�o�b�O�p�j
     */
    public String toString() {
        return x + ":" + y + ":" + chipNo + ":" + isHit;
    }
}
