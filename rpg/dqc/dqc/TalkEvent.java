/*
 * �b���iTalk�j���Ƃ��ł���C�x���g
 * 
 */
package dqc;

import java.awt.Graphics;

public abstract class TalkEvent extends Event {
    // TALK�ŕ\������郁�b�Z�[�W
    protected String message;
    
    public TalkEvent(int x, int y, int imageNo, String message) {
        super(x, y, imageNo, false);  // �b����C�x���g�͈ړ��s�\
        this.message = message;
    }

    public abstract void draw(Graphics g, int offsetX, int offsetY);
    public abstract void start(Hero hero, Map map, MessageWindow msgWnd);
    
    public String toString() {
        return super.toString() + "," + message;
    }
}
