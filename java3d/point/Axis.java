import javax.media.j3d.BranchGroup;
import javax.media.j3d.LineArray;
import javax.media.j3d.Shape3D;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;

/*
 * Created on 2006/01/01
 */

/**
 * ���W����BG���\�z����
 * 
 * @author mori
 */
public class Axis {
    private BranchGroup axisBG;

    public Axis() {
        axisBG = new BranchGroup();

        // Geometry�̐���
        LineArray axisX = new LineArray(2, LineArray.COORDINATES | LineArray.COLOR_3);
        LineArray axisY = new LineArray(2, LineArray.COORDINATES | LineArray.COLOR_3);
        LineArray axisZ = new LineArray(2, LineArray.COORDINATES | LineArray.COLOR_3);

        // ���_�̃Z�b�g
        Color3f red = new Color3f(1.0f, 0.0f, 0.0f);
        Color3f green = new Color3f(0.0f, 1.0f, 0.0f);
        Color3f blue = new Color3f(0.0f, 0.0f, 1.0f);

        axisX.setCoordinate(0, new Point3f(-1.0f, 0.0f, 0.0f));
        axisX.setCoordinate(1, new Point3f(1.0f, 0.0f, 0.0f));
        axisX.setColor(0, red);
        axisX.setColor(1, red);

        axisY.setCoordinate(0, new Point3f(0.0f, -1.0f, 0.0f));
        axisY.setCoordinate(1, new Point3f(0.0f, 1.0f, 0.0f));
        axisY.setColor(0, green);
        axisY.setColor(1, green);

        axisZ.setCoordinate(0, new Point3f(0.0f, 0.0f, -1.0f));
        axisZ.setCoordinate(1, new Point3f(0.0f, 0.0f, 1.0f));
        axisZ.setColor(0, blue);
        axisZ.setColor(1, blue);

        // BG�ɒǉ�
        axisBG.addChild(new Shape3D(axisX));
        axisBG.addChild(new Shape3D(axisY));
        axisBG.addChild(new Shape3D(axisZ));
    }

    /**
     * �\�z�������W����BG��Ԃ�
     * 
     * @return ���W����BG
     */
    public BranchGroup getBG() {
        return axisBG;
    }
}