import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JPanel;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;

/*
 * Created on 2006/07/28
 */

public class MainPanel extends JPanel {
    // �p�l���T�C�Y
    private static final int WIDTH = 640;
    private static final int HEIGHT = 640;

    // ���E�͈̔́i�����Ȃǂ��͂��͈́j
    private static final int BOUND_SIZE = 100;

    // ���_�̏����ʒu
    private static final Point3d USER_POS = new Point3d(0, 5, 20);

    // ���E
    private SimpleUniverse universe;
    // BG�i�����ɂ����Ȃ��̂�ڑ�����j
    private BranchGroup sceneBG;
    // �e���͈�
    private BoundingSphere bounds;

    public MainPanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new BorderLayout());

        // 3D���f����`�悷��L�����p�X���쐬
        GraphicsConfiguration config = SimpleUniverse
                .getPreferredConfiguration();
        Canvas3D canvas3D = new Canvas3D(config);
        add(canvas3D, BorderLayout.CENTER);
        canvas3D.setFocusable(true);
        canvas3D.requestFocus();

        // ���E���쐬
        universe = new SimpleUniverse(canvas3D);

        createSceneGraph(); // ���E�i�V�[���O���t�j���\�z
        initUserPosition(); // ���[�U�̎��_��������
        orbitControls(canvas3D); // �}�E�X����

        universe.addBranchGraph(sceneBG);
    }

    /**
     * ���E���\�z
     */
    private void createSceneGraph() {
        // sceneBG�ɂ��낢��ڑ����邱�ƂŐ��E���\�������
        sceneBG = new BranchGroup();
        // ���E�͈̔́i�����Ȃǂ̋y�Ԕ͈́j
        bounds = new BoundingSphere(new Point3d(0, 0, 0), BOUND_SIZE);

        lightScene(); // ������sceneBG�ɒǉ�
        addBackground(); // ���sceneBG�ɒǉ�

        // ���W����ǉ�
        Axis axis = new Axis();
        sceneBG.addChild(axis.getBG());

        sceneBG.compile();
    }

    /**
     * ������sceneBG�ɒǉ�
     */
    private void lightScene() {
        Color3f white = new Color3f(1.0f, 1.0f, 1.0f);

        // �����iAmbientLight�j
        // �ڂ���Ƃ������A���ꂪ�Ȃ��Ɛ^����
        AmbientLight ambientLight = new AmbientLight(white);
        ambientLight.setInfluencingBounds(bounds); // �����̋y�Ԕ͈͂�ݒ�
        sceneBG.addChild(ambientLight); // sceneBG�Ɍ�����ǉ�

        // ���s�����i�\�ʂ������ƌ���j
        Vector3f lightDirection = new Vector3f(-1.0f, -1.0f, -1.0f); // �����̌���
        DirectionalLight dirLight = new DirectionalLight(white, lightDirection);
        dirLight.setInfluencingBounds(bounds);
        sceneBG.addChild(dirLight);
    }

    /**
     * ���sceneBG�ɒǉ�
     */
    private void addBackground() {
        Background back = new Background();
        back.setApplicationBounds(bounds);
        back.setColor(0.0f, 0.0f, 0.5f); // ����
        sceneBG.addChild(back);
    }

    /**
     * ���[�U�̎��_��������
     */
    private void initUserPosition() {
        ViewingPlatform vp = universe.getViewingPlatform(); // SimpleUniverse�̃f�t�H���g���擾
        TransformGroup steerTG = vp.getViewPlatformTransform(); // vp��TG���擾

        Transform3D t3d = new Transform3D(); // ���_�ivp�j�ړ��p��T3D
        steerTG.getTransform(t3d); // ���݂̎��_���擾

        // �V�������_��ݒ�
        // ���[�U�̈ʒu�A������̍��W�A�������w��
        // ���_�̐�͌��_
        t3d.lookAt(USER_POS, new Point3d(0, 0, 0), new Vector3d(0, 1, 0));
        t3d.invert();

        steerTG.setTransform(t3d); // �ύX�������_��ݒ�
    }

    /**
     * �}�E�X����
     * 
     * @param canvas
     *            �L�����o�X
     */
    private void orbitControls(Canvas3D canvas) {
        OrbitBehavior orbit = new OrbitBehavior(canvas,
                OrbitBehavior.REVERSE_ALL);
        orbit.setSchedulingBounds(bounds); // �e�����y�ڂ��͈͂�ݒ�

        ViewingPlatform vp = universe.getViewingPlatform(); // ���_���擾
        vp.setViewPlatformBehavior(orbit); // ���_���}�E�X�Ő���\�ɂȂ�
    }
}
