import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;

import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.PointArray;
import javax.media.j3d.PointAttributes;
import javax.media.j3d.Shape3D;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;

/*
 * Created on 2006/01/01
 */

/**
 * �_��ł�
 * 
 * @author mori
 */
public class Main3D extends Applet {
    private SimpleUniverse universe;

    public Main3D() {
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

        // ������3D�I�u�W�F�N�g��ǉ�

        // ���W����ǉ�
        Axis axis = new Axis();
        bg.addChild(axis.getBG());

        // �_��ǉ�
        Point3f[] vertices = { // �_�̍��W
                new Point3f(-0.5f, 0.5f, -0.5f),
                new Point3f(0.5f, 0.5f, -0.5f),
                new Point3f(0.5f, 0.5f, 0.5f),
                new Point3f(-0.5f, 0.5f, 0.5f),
                new Point3f(-0.5f, -0.5f, -0.5f),
                new Point3f(0.5f, -0.5f, -0.5f),
                new Point3f(0.5f, -0.5f, 0.5f),
                new Point3f(-0.5f, -0.5f, 0.5f)
        };

        Color3f[] colors = { // �_�̐F
                new Color3f(1.0f, 1.0f, 0.0f),
                new Color3f(1.0f, 1.0f, 0.0f),
                new Color3f(1.0f, 1.0f, 0.0f),
                new Color3f(1.0f, 1.0f, 0.0f),
                new Color3f(1.0f, 1.0f, 0.0f),
                new Color3f(1.0f, 1.0f, 0.0f),
                new Color3f(1.0f, 1.0f, 0.0f),
                new Color3f(1.0f, 1.0f, 0.0f)
        };

        // Geometry
        PointArray geo = new PointArray(8, PointArray.COORDINATES
                | PointArray.COLOR_3);
        geo.setCoordinates(0, vertices); // ���_���W���Z�b�g
        geo.setColors(0, colors); // �F���Z�b�g

        // Appearance
        Appearance app = new Appearance();
        PointAttributes attr = new PointAttributes(5.0f, false); // �_�̑傫��
        app.setPointAttributes(attr);

        bg.addChild(new Shape3D(geo, app));

        // �����܂�

        return bg;
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
        MainFrame frame = new MainFrame(new Main3D(), 256, 256);
        frame.setTitle("�_��ł�");
    }
}