/*
 * Created on 2007/04/01
 */
package dqc;

public class Treasure extends SearchEvent {
    // �󔠂̃A�C�e����
    private String itemName;
    
    /**
     * �R���X�g���N�^
     * 
     * @param x X���W
     * @param y Y���W
     * @param itemName �A�C�e����
     */
    public Treasure(int x, int y, int imageNo, String itemName) {
        super(x, y, imageNo, true);  // �󔠂͈ړ��\
        
        this.itemName = itemName;
    }
    
    /**
     * �A�C�e������Ԃ�
     * 
     * @return �A�C�e����
     */
    public String getItemName() {
        return itemName;
    }
    
    // TODO: itemName����{���̃A�C�e���I�u�W�F�N�g���쐬���ĕԂ�
    // hero�I�u�W�F�N�g�������Ŏ󂯂�haveItem�ł��悢

    /**
     * �C�x���g�̕������Ԃ��i�f�o�b�O�p�j
     * 
     * @return �C�x���g������
     */
    public String toString() {
        return "TREASURE," + x + "," + y + "," + imageNo + "," + itemName;
    }

    public void start(Hero hero, Map map, MessageWindow msgWnd) {
        DQC.soundManager.playSE("treasure");
        msgWnd.setMessage(getItemName() + "����ɓ��ꂽ�B");
        // TODO: �A�C�e�����菈��
        // hero.take(treasure.getItem());
        msgWnd.show();
        map.removeEvent(this);
    }
}
