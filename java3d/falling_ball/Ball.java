import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.Texture;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;

/*
 * Created on 2006/02/04
 */

/**
 * �{�[��
 * 
 * @author mori
 */
public class Ball extends TransformGroup {
    private Vector3d position; // �ʒu
    private Vector3d velocity; // ���x
    private Vector3d acceleration;  // �����x
    
    private Transform3D positionT3D = new Transform3D(); // �ړ��p
    private float radius = 0.5f; // �{�[���̔��a
    private Appearance app;

    public Ball(double x, double y, double z) {
        position = new Vector3d(x, y, z);
        velocity = new Vector3d(0, 0, 0);
        acceleration = new Vector3d(0, -0.01, 0);  // �����x�͈��
        
        // �ړ��\
        this.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        this.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        app = new Appearance();
        Material mat = new Material();
        mat.setAmbientColor(new Color3f(0.0f, 0.0f, 1.0f));
        mat.setSpecularColor(new Color3f(1.0f, 1.0f, 1.0f));
        app.setMaterial(mat);

        TransparencyAttributes transAttr = new TransparencyAttributes(
                TransparencyAttributes.BLENDED, 0.2f);
        app.setTransparencyAttributes(transAttr);

        // �{�[����ǉ�
        this.addChild(new Sphere(radius, Sphere.GENERATE_NORMALS, 100, app));

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

    public Vector3d getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector3d v) {
        velocity.x = v.x;
        velocity.y = v.y;
        velocity.z = v.z;
    }
    
    public Vector3d getAcceleration() {
        return acceleration;
    }
    
    public void setAcceleration(Vector3d a) {
        acceleration.x = a.x;
        acceleration.y = a.y;
        acceleration.z = a.z;
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