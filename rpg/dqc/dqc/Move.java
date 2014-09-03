/*
 * Created on 2007/04/15
 */
package dqc;

public class Move extends MoveEvent {
    public Move(int x, int y, int imageNo, String destMapName, int destX, int destY) {
        super(x, y, imageNo, destMapName, destX, destY);
    }

    public void start(Hero hero, Map map, MessageWindow msgWnd) {
        DQC.soundManager.playSE("stairs");

        // ���݂̃}�b�v�����l�����폜
        map.removeEvent(hero);
        
        // �ړ���̃}�b�v�Ɏ�l����ǉ�
        Map destMap = DQC.mapManager.getMap(getDestMapName());
        destMap.addEvent(hero);

        // ��l���̏�Ԃ��ăZ�b�g
        hero.setX(getDestX());
        hero.setY(getDestY());
        hero.setDirection(DQC.DOWN);
        hero.setMap(destMap);
        
        DQC.mapManager.setCurMap(destMap);
        DQC.soundManager.playBGM(destMap.getBGM());
    }
    
    /**
     * �C�x���g�̕������Ԃ��i�f�o�b�O�p�j
     * 
     * @return �C�x���g������
     */
    public String toString() {
        return "MOVE," + x + "," + y + "," + imageNo + "," + destMapName + "," + destX + "," + destY;
    }
}
