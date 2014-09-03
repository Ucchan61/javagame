import java.awt.geom.Ellipse2D;

/*
 * Created on 2006/12/29
 */

public class Spotlight {
    // �X�|�b�g���C�g�͈̔́i�~�`�j
    protected Ellipse2D.Double spot;

    public Spotlight() {
        this(0, 0, 0);
    }

    /**
     * �R���X�g���N�^
     * 
     * @param x �X�|�b�g���C�g���S��X���W
     * @param y �X�|�b�g���C�g���S��Y���W
     * @param radius �X�|�b�g���C�g�̔��a
     */
    public Spotlight(int x, int y, int radius) {
        this.spot = new Ellipse2D.Double(x - radius, y - radius, radius * 2,
                radius * 2);
    }

    /**
     * �X�|�b�g���C�g�̈ʒu���Z�b�g
     * 
     * @param x �X�|�b�g���C�g���S��X���W
     * @param y �X�|�b�g���C�g���S��Y���W
     * @param radius �X�|�b�g���C�g�̔��a
     */
    public void setSpot(int x, int y, int radius) {
        spot.x = x - radius;
        spot.y = y - radius;
        spot.width = radius * 2;
        spot.height = radius * 2;
    }

    /**
     * �X�|�b�g���C�g�̉~��Ԃ�
     * @return
     */
    public Ellipse2D getSpot() {
        return spot;
    }
}
