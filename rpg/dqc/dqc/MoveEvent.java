/*
 * Created on 2007/04/01
 */
package dqc;

public abstract class MoveEvent extends Event {
    // �ړ���}�b�v��
    protected String destMapName;
    // �ړ���X���W
    protected int destX;
    // �ړ���Y���W
    protected int destY;
    
    public MoveEvent(int x, int y, int imageNo, String destMapName, int destX, int destY) {
        super(x, y, imageNo, true);
        this.destMapName = destMapName;
        this.destX = destX;
        this.destY = destY;
    }

    public abstract void start(Hero hero, Map map, MessageWindow msgWnd);
    
    /**
     * �ړ���}�b�v����Ԃ�
     * 
     * @return �ړ���}�b�v��
     */
    public String getDestMapName() {
        return destMapName;
    }
    
    /**
     * �ړ���X���W��Ԃ�
     * 
     * @return �ړ���X���W
     */
    public int getDestX() {
        return destX;
    }
    
    /**
     * �ړ���Y���W��Ԃ�
     * 
     * @return �ړ���Y���W
     */
    public int getDestY() {
        return destY;
    }
}
