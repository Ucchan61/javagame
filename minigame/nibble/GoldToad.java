import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.Random;

/*
 * Created on 2004/12/26
 *
 */

/**
 * @author mori
 *
 */
public class GoldToad extends Toad {
    private static final double PROB_MOVE = 0.5;
    // ����������i���̃N���X��������S�^�ŋ��L�j
    private static Random rand = new Random();
    
    /**
     * pos�̈ʒu�Ɋ^���쐬����B
     * �Ί^�̃G�l���M�[��1�B
     * 
     * @param pos �^�̍��W�B
     */
    public GoldToad(Point pos, MainPanel panel) {
        super(10, pos, panel);
    }
    
    /**
     * �^��0.1�̊m���ňړ��B
     *  
     */
    public void move() {
        // PROB_MOVE�̊m���Ń����_���Ɉړ�����
        if (rand.nextDouble() < PROB_MOVE) {
            int dir = rand.nextInt(4);
            switch (dir) {
                case 0 : // ��ֈړ�
                    pos.y--;
                    break;
                case 1 : // �E�ֈړ�
                    pos.x++;
                    break;
                case 2 : // ���ֈړ�
                    pos.y++;
                    break;
                case 3 : // ���ֈړ�
                    pos.x--;
                    break;
            }
        }

        // ��ʓ����`�F�b�N����
        if (pos.x < 0)
            pos.x = 0;
        if (pos.x > MainPanel.COL - 1)
            pos.x = MainPanel.COL - 1;
        if (pos.y < 0)
            pos.y = 0;
        if (pos.y > MainPanel.ROW - 1)
            pos.y = MainPanel.ROW - 1;
    }

    /**
     * �^�̉摜�����[�h����B
     * 
     * @param panel MainPanel�ւ̎Q�ƁB
     */
    protected void loadImage(MainPanel panel) {
        MediaTracker tracker = new MediaTracker(panel);
        toadImage = Toolkit.getDefaultToolkit().getImage(
                getClass().getResource("gold_toad.gif"));
        tracker.addImage(toadImage, 0);
        try {
            tracker.waitForAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
