import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/*
 * Created on 2007/02/02
 */

public class MainPanel extends JPanel {
    private static final int WIDTH = 640;
    private static final int HEIGHT = 480;

    private JTextArea textArea;
    private JButton readFileButton;
    private JButton writeFileButton;

    public MainPanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        initGUI();
    }

    /**
     * GUI������������
     */
    private void initGUI() {
        textArea = new JTextArea();
        textArea.setEditable(true);

        readFileButton = new JButton("�ǂݍ���");
        writeFileButton = new JButton("��������");

        // 2�̃{�^�����܂Ƃ߂�p�l��
        JPanel p1 = new JPanel();
        p1.add(readFileButton);
        p1.add(writeFileButton);

        setLayout(new BorderLayout());
        add(textArea, BorderLayout.CENTER);
        add(p1, BorderLayout.SOUTH);

        // �ǂݍ��݃{�^�����������Ƃ��̏���
        readFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // test.txt����t�@�C����ǂݍ���Ńe�L�X�g�G���A�ɕ\��
                    BufferedReader br = new BufferedReader(new FileReader("test.txt"));  // �t�@�C�����J��
                    String line;
                    while ((line = br.readLine()) != null) {  // 1�s���ǂݍ���
                        textArea.append(line + "\n");  // �ǂݍ���1�s���e�L�X�g�G���A�ɕ\��
                    }
                    br.close();  // �t�@�C�������
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // �������݃{�^�����������Ƃ��̏���
        writeFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // test.txt�Ƀe�L�X�g�G���A�̓��e����������
                    BufferedWriter bw = new BufferedWriter(new FileWriter("test.txt"));  // �t�@�C�����J��
                    bw.write(textArea.getText());
                    bw.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
