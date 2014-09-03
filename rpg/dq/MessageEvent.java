/*
 * Created on 2006/02/04
 */

/**
 * @author mori
 */
public class MessageEvent extends Event {
    private String message;

    /**
     * @param x X���W
     * @param y Y���W
     * @param isHit �Ԃ��邩
     */
    public MessageEvent(int x, int y, int chipNo, String message) {
        super(x, y, chipNo, false);
        this.message = message;
    }
    
    /**
     * ���b�Z�[�W��Ԃ�
     * @return ���b�Z�[�W
     */
    public String getMessage() {
        return message;
    }
    
    public String toString() {
        return "MESSAGE:" + super.toString() + ":" + message;
    }
}
