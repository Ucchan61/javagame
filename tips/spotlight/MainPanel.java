import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/*
 * Created on 2006/12/03
 */

public class MainPanel extends JPanel implements MouseMotionListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    // �X�|�b�g���C�g�̔��a
    private static final int RADIUS = 128;

    // �w�i�C���[�W
    private Image bgImage;
    // �������C���[�W
    private Image obakeImage;

    // �X�|�b�g���C�g
    private Spotlight spotlight;

    public MainPanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        addMouseMotionListener(this);

        ImageIcon icon = new ImageIcon(getClass().getResource("castle.jpg"));
        bgImage = icon.getImage();

        icon = new ImageIcon(getClass().getResource("obake.gif"));
        obakeImage = icon.getImage();

        // ��ʂ̒����ɃX�|�b�g���C�g���Z�b�g
        spotlight = new Spotlight(WIDTH / 2, HEIGHT / 2, RADIUS);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // �w�i��`��
        g.drawImage(bgImage, 0, 0, this);

        // ��������`��
        g.drawImage(obakeImage, 20, 400, this);

        // �X�|�b�g���C�g
        Rectangle2D screen = new Rectangle2D.Double(0, 0, WIDTH, HEIGHT);
        // ��ʑS�̂𕢂��}�X�N
        Area mask = new Area(screen);
        // �}�X�N����X�|�b�g���C�g��������苎��
        mask.subtract(new Area(spotlight.getSpot()));

        // �}�X�N��w�i�̏�ɕ`��
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.fill(mask);
    }

    public void mouseDragged(MouseEvent e) {
        spotlight.setSpot(e.getX(), e.getY(), RADIUS);
        repaint();
    }

    public void mouseMoved(MouseEvent e) {
    }
}
