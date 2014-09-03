import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Light;
import javax.media.j3d.Material;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;

/*
 * Created on 2006/01/28
 */

/**
 * Sphere in the Box�̃��C���N���X
 * 
 * @author mori
 */
public class Main extends Applet {
    private SimpleUniverse universe;

    public Main() {
        setLayout(new BorderLayout());
        GraphicsConfiguration config = SimpleUniverse
                .getPreferredConfiguration();
        Canvas3D canvas = new Canvas3D(config);
        add(canvas, BorderLayout.CENTER);

        universe = new SimpleUniverse(canvas);

        // �V�[�����\�z
        BranchGroup scene = createSceneGraph();
        scene.compile();

        // ���_���Z�b�g
        universe.getViewingPlatform().setNominalViewingTransform();

        // �}�E�X����
        orbitControls(canvas);

        universe.addBranchGraph(scene);
    }

    /**
     * �V�[�����\�z����
     * 
     * @return BG
     */
    public BranchGroup createSceneGraph() {
        BranchGroup bg = new BranchGroup();

        // �����ŃV�[�����쐬����

        // ���ˉ��{�[����ǉ�
        Ball ball = new Ball(0, 0, 0, 0.05, 0.02, -0.03);
        BallBehavior ballBehavior = new BallBehavior(ball, 50);
        bg.addChild(ball);
        bg.addChild(ballBehavior);

        // ����ǉ�
        bg.addChild(createBox());

        // ������ǉ�
        bg.addChild(createAmbientLight());
        bg.addChild(createDirectionalLight());

        return bg;
    }

    private Box createBox() {
        // ��
        Appearance app = new Appearance();
        Material mat = new Material();
        mat.setAmbientColor(new Color3f(0.0f, 0.0f, 1.0f));
        mat.setSpecularColor(new Color3f(1.0f, 1.0f, 1.0f));
        app.setMaterial(mat);

        // �����ɂ���
        TransparencyAttributes transAttr = new TransparencyAttributes(
                TransparencyAttributes.BLENDED, 0.5f);
        app.setTransparencyAttributes(transAttr);

        Box box = new Box(0.5f, 0.5f, 0.5f, app);

        return box;
    }

    public Light createAmbientLight() {
        BoundingSphere bounds = new BoundingSphere();
        Light light = new AmbientLight();
        light.setInfluencingBounds(bounds);
        return light;
    }

    public Light createDirectionalLight() {
        BoundingSphere bounds = new BoundingSphere();
        Light light = new DirectionalLight(new Color3f(1.0f, 1.0f, 1.0f),
                new Vector3f(1.0f, -1.0f, -1.0f));
        light.setInfluencingBounds(bounds);
        return light;
    }

    /**
     * �}�E�X������\�ɂ���
     * 
     * @param canvas �L�����o�X
     */
    private void orbitControls(Canvas3D canvas) {
        BoundingSphere bounds = new BoundingSphere(new Point3d(0, 0, 0), 100);

        OrbitBehavior orbit = new OrbitBehavior(canvas,
                OrbitBehavior.REVERSE_ALL);
        orbit.setSchedulingBounds(bounds);

        ViewingPlatform vp = universe.getViewingPlatform();
        vp.setViewPlatformBehavior(orbit);
    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame(new Main(), 256, 256);
        frame.setTitle("Sphere in the Box");
    }
}