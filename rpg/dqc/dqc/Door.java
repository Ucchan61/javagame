/*
 * Created on 2007/04/01
 */
package dqc;

public class Door extends OpenEvent {
    // TODO: ���ɂ���ĊJ������悤�Ƀh�A�̎�ނ�ǉ�
    
    public Door(int x, int y, int imageNo) {
        super(x, y, imageNo);  // �h�A�͈ړ��s��
    }
    
    /**
     * �C�x���g�̕������Ԃ��i�f�o�b�O�p�j
     * 
     * @return �C�x���g������
     */
    public String toString() {
        return "DOOR," + x + "," + y + "," + imageNo;
    }

    public void start(Hero hero, Map map, MessageWindow msgWnd) {
        DQC.soundManager.playSE("door");
        map.removeEvent(this);
    }
}
