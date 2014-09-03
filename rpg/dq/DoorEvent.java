/*
 * Created on 2005/12/25
 *
 */

/**
 * @author mori
 *
 */
public class DoorEvent extends Event {

    /**
     * @param x X���W
     * @param y Y���W
     */
    public DoorEvent(int x, int y) {
        super(x, y, Chipset.DOOR, true);
    }
    
    /**
     * �C�x���g�𕶎���ɕϊ��i�f�o�b�O�p�j
     */
    public String toString() {
        return "DOOR:" + super.toString();
    }
}
