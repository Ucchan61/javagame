/*
 * Created on 2006/02/06
 */

/**
 * @author mori
 */
public class MessageBoardEvent extends Event {
    private String message;
    
    public MessageBoardEvent(int x, int y, String message) {
        super(x, y, Chipset.MSGBOARD, true);
        this.message = message;
    }
    
    /**
     * ���b�Z�[�W��ǂ�
     * @return ���b�Z�[�W
     */
    public String read() {
        // TODO: �T�[�o�[����ǂݎ��
        return message;
    }

    /**
     * ���b�Z�[�W����������
     * @param message
     */
    public void write(String message) {
        // TODO:�T�[�o�[�֏�������
    }

    /**
     * ���b�Z�[�W��Ԃ�
     * @return ���b�Z�[�W
     */
    public String getMessage() {
        return message;
    }

    public String toString() {
        return "BOARD:" + super.toString() + ":" + message;
    }
}
