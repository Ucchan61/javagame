import javax.media.j3d.Appearance;
import javax.media.j3d.Texture;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;

/*
 * Created on 2006/01/28
 */

/**
 * �{�[��
 * 
 * @author mori
 */
public class Ball extends TransformGroup {
    private Vector3d position; // �ʒu
    private Vector3d speed; // ���x
    private Transform3D positionT3D = new Transform3D(); // �ړ��p
    private float radius = 0.1f; // �{�[���̔��a
    private Appearance app;

    public Ball(double x, double y, double z, double vx, double vy, double vz) {
        position = new Vector3d(x, y, z);
        speed = new Vector3d(vx, vy, vz);

        // �ړ��\
        this.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        this.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        app = new Appearance();
        app.setTexture(loadTexture("carpet.jpg"));

        // �{�[����ǉ�
        this.addChild(new Sphere(radius, Sphere.GENERATE_TEXTURE_COORDS, 100, app));

        move();
    }

    /*
     * �{�[�����ړ�
     */
    public void move() {
        positionT3D.setTranslation(position);
        this.setTransform(positionT3D);
    }

    public Vector3d getPosition() {
        return position;
    }

    public void setPosition(Vector3d p) {
        position.x = p.x;
        position.y = p.y;
        position.z = p.z;
    }

    public Vector3d getSpeed() {
        return speed;
    }

    public void setSpeed(Vector3d s) {
        speed.x = s.x;
        speed.y = s.y;
        speed.z = s.z;
    }

    public float getRadius() {
        return radius;
    }

    /**
     * �e�N�X�`�������[�h
     * 
     * @param filename �t�@�C����
     * @return �e�N�X�`��
     */
    private Texture loadTexture(String filename) {
        Texture texture;

        texture = new TextureLoader(getClass().getResource(filename), null)
                .getTexture();

        return texture;
    }
}