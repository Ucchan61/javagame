import java.awt.Container;

import javax.swing.JFrame;

/*
 * Created on 2005/12/17
 *
 */

/**
 * @author mori
 *  
 */
public class MapEditor extends JFrame {
    public MapEditor() {
        setTitle("�ЂȂ���");
        setResizable(false);

        // �p�l�����쐬
        MainPanel panel = new MainPanel();
        Container contentPane = getContentPane();
        contentPane.add(panel);

        // �p�l���T�C�Y�ɍ��킹�ăt���[���T�C�Y�������ݒ�
        pack();
    }

    public static void main(String[] args) {
        MapEditor frame = new MapEditor();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}