/*
 * �쐬��: 2004/12/14
 *
 */
import java.awt.*;
import java.applet.*;
/**
 * �^��\���N���X
 * 
 * @author mori
 *  
 */
public abstract class Toad {
    // �^�̍��W
    protected Point pos;
    // �^�̃C���[�W
    protected Image toadImage;
    // �^�̖����̃T�E���h
    protected AudioClip geko;
    // �G�l���M�[�i�ւ��H�ׂ��Ƃ��ɐg�̂��L�т�傫���j
    protected int energy;

    /**
     * pos�̈ʒu�Ɋ^���쐬����
     * 
     * @param energy �^�̃G�l���M�[
     * @param pos �^�̍��W
     * @param panel ���C���p�l���ւ̎Q��
     */
    public Toad(int energy, Point pos, MainPanel panel) {
        this.energy = energy;
        this.pos = pos;
        loadImage(panel);
        geko = Applet.newAudioClip(getClass().getResource("geko.wav"));
    }

    /**
     * �^�̍��W��Ԃ�
     * 
     * @return �^�̍��W
     */
    public Point getPos() {
        return pos;
    }

    /**
     * �^�������
     *  
     */
    public void croak() {
        geko.play();
    }

    /**
     * �^��`�悷��
     * 
     * @param g �`��I�u�W�F�N�g
     *  
     */
    public void draw(Graphics g) {
        g.drawImage(toadImage,
                pos.x * MainPanel.GS,
                pos.y * MainPanel.GS, null);
    }

    /**
     * �G�l���M�[�̑傫����Ԃ��B
     * @return �G�l���M�[�̑傫���B
     */
    public int getEnergy() {
        return energy;
    }
    
    /**
     * �^���ړ�����
     *  
     */
    public abstract void move();
    
    /**
     * �^�̉摜�����[�h����
     * 
     * @param panel MainPanel�ւ̎Q��
     */
    protected abstract void loadImage(MainPanel panel);
}