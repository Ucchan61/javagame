/*
 * Created on 2004/12/26
 *
 */
import java.awt.*;
/**
 * @author mori
 *
 */
public class GreenToad extends Toad {
    /**
     * pos�̈ʒu�Ɋ^���쐬����B
     * �Ί^�̃G�l���M�[��1�B
     * 
     * @param pos �^�̍��W�B
     */
    public GreenToad(Point pos, MainPanel panel) {
        super(1, pos, panel);
    }
    
    /**
     * �Ί^�͈ړ����Ȃ��B
     *  
     */
    public void move() {
    }

    /**
     * �^�̉摜�����[�h����B
     * 
     * @param panel MainPanel�ւ̎Q�ƁB
     */
    protected void loadImage(MainPanel panel) {
        MediaTracker tracker = new MediaTracker(panel);
        toadImage = Toolkit.getDefaultToolkit().getImage(
                getClass().getResource("green_toad.gif"));
        tracker.addImage(toadImage, 0);
        try {
            tracker.waitForAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
