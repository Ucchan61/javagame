import java.awt.Container;

/*
 * Created on 2005/01/16
 *
 */
import javax.swing.*;
/**
 * @author mori
 *  
 */
public class ChaseTest extends JFrame {
    public ChaseTest() {
        // �^�C�g����ݒ�
        setTitle("�ǐ�");

        // ���C���p�l�����쐬���ăt���[���ɒǉ�
        MainPanel panel = new MainPanel();
        Container contentPane = getContentPane();
        contentPane.add(panel);

        // �p�l���T�C�Y�ɍ��킹�ăt���[���T�C�Y�������ݒ�
        pack();
    }

    public static void main(String[] args) {
        ChaseTest frame = new ChaseTest();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}