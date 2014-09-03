import java.awt.Color;
import java.awt.Graphics;

/*
 * Created on 2007/02/04
 */

public class Soul {
    private double x, y; // �ʒu
    private double cx, cy; // ��]�̒��S���W
    private double radius; // ��]���a
    private double velocity; // �p���x�i�x/�b�j
    private Color color; // �~�̐F

    public Soul(double cx, double cy, double radius, double velocity, Color color) {
        this.cx = cx;
        this.cy = cy;
        this.radius = radius;
        this.velocity = velocity;
        this.color = color;

        x = cx + radius;
        y = cy;
    }

    public void draw(Graphics g) {
        g.setColor(color);

        g.fillOval((int) x, (int) y, 16, 16);
    }

    public void update(double t) {
        x = cx + radius * Math.cos(Math.toRadians(velocity * t));
        y = cy + radius * Math.sin(Math.toRadians(velocity * t)); // -�ɂ���Ƌt��]
    }

    public void setCenter(double cx, double cy) {
        this.cx = cx;
        this.cy = cy;
    }
}
