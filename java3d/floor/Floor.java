import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Material;
import javax.vecmath.Color3f;

import com.sun.j3d.utils.geometry.Box;

/*
 * Created on 2006/07/28
 */

public class Floor {
    private BranchGroup floorBG; // ����\��BG

    public Floor() {
        floorBG = new BranchGroup();

        Appearance app = new Appearance();
        
        // ��
        Material mat = new Material();
        mat.setAmbientColor(new Color3f(1.0f, 1.0f, 0.0f));  // ���F
        mat.setSpecularColor(new Color3f(1.0f, 1.0f, 1.0f));
        app.setMaterial(mat);

        // �����쐬
        Box floor = new Box(10.0f, 0.001f, 10.0f, app);

        floorBG.addChild(floor);
    }

    // BG��Ԃ�
    public BranchGroup getBG() {
        return floorBG;
    }
}
