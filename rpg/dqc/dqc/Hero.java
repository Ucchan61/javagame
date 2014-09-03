/*
 * Created on 2007/04/15
 */
package dqc;

public class Hero extends Chara {

    public Hero(int x, int y, int imageNo, int direction, int moveType, String message, Map map) {
        super(x, y, imageNo, direction, moveType, message, map);
    }
    
    /**
     * �ړ������i�I�[�o�[���C�h�j
     * 
     */
    public void move() {
        switch (direction) {
            case DOWN :
                if (moveDown()) {
                    // �ړ�����������
                    // �ړ��C�x���g
                    Event evt = map.getEvent(x, y);
                    if (evt instanceof MoveEvent) {
                        evt.start(this, map, null);
                    }
                }
                break;
            case UP :
                if (moveUp()) {
                    // �ړ�����������
                    // �ړ��C�x���g
                    Event evt = map.getEvent(x, y);
                    if (evt instanceof MoveEvent) {
                        evt.start(this, map, null);
                    }
                }
                break;
            case LEFT :
                if (moveLeft()) {
                    // �ړ�����������
                    // �ړ��C�x���g
                    Event evt = map.getEvent(x, y);
                    if (evt instanceof MoveEvent) {
                        evt.start(this, map, null);
                    }
                }
                break;
            case RIGHT :
                if (moveRight()) {
                    // �ړ�����������
                    // �ړ��C�x���g
                    Event evt = map.getEvent(x, y);
                    if (evt instanceof MoveEvent) {
                        evt.start(this, map, null);
                    }
                }
                break;
        }
    }
    
    /**
     * �����Ă�������ɃL�����N�^�[��������b��
     * 
     */
    public void talk(MessageWindow msgWnd) {
        // �L�����N�^�[�̌����Ă��������1�}�X�ƂȂ�̍��W�����߂�
        int nextX = 0;
        int nextY = 0;
        switch (direction) {
            case DOWN:
                nextX = x;
                nextY = y + 1;
                // �J�E���^�[������ꍇ�͂���ɐ���w��
                if (map.getMapchipNo(nextX, nextY) == 388) {  // TODO: 388�̓J�E���^�[
                    nextY++;
                }
                break;
            case UP:
                nextX = x;
                nextY = y - 1;
                // �J�E���^�[������ꍇ�͂���ɐ���w��
                if (map.getMapchipNo(nextX, nextY) == 388) {  // TODO: 388�̓J�E���^�[
                    nextY--;
                }
                break;
            case LEFT:
                nextX = x - 1;
                nextY = y;
                // �J�E���^�[������ꍇ�͂���ɐ���w��
                if (map.getMapchipNo(nextX, nextY) == 388) {  // TODO: 388�̓J�E���^�[
                    nextX--;
                }
                break;
            case RIGHT:
                nextX = x + 1;
                nextY = y;
                // �J�E���^�[������ꍇ�͂���ɐ���w��
                if (map.getMapchipNo(nextX, nextY) == 388) {  // TODO: 388�̓J�E���^�[
                    nextX++;
                }
                break;
        }
        
        // ���̕����ɃL�����N�^�[�����邩���ׂ�
        Event evt = map.getEvent(nextX, nextY);
        if (evt != null && evt instanceof TalkEvent) {
            evt.start(this, map, msgWnd);
        } else {
            msgWnd.setMessage("���̕����ɂ͒N�����Ȃ��B");
            msgWnd.show();
        }
    }
    
    /**
     * �����𒲂ׂ�
     * 
     */
    public void search(MessageWindow msgWnd) {
        Event evt = map.getEvent(x, y);
        if (evt != null && evt instanceof SearchEvent) {
            evt.start(this, map, msgWnd);
        } else {
            msgWnd.setMessage("����������������Ȃ������B");
            msgWnd.show();
        }
    }
    
    /**
     * �J����
     *
     */
    public void open(MessageWindow msgWnd) {
        // �L�����N�^�[�̌����Ă��������1�}�X�ƂȂ�̍��W�����߂�
        int nextX = 0;
        int nextY = 0;
        switch (direction) {
            case DOWN:
                nextX = x;
                nextY = y + 1;
                break;
            case UP:
                nextX = x;
                nextY = y - 1;
                break;
            case LEFT:
                nextX = x - 1;
                nextY = y;
                break;
            case RIGHT:
                nextX = x + 1;
                nextY = y;
                break;
        }
        
        // �������邩���ׂ�
        Event evt = map.getEvent(nextX, nextY);
        if (evt instanceof OpenEvent) {
            evt.start(this, map, msgWnd);
        }
    }
}
