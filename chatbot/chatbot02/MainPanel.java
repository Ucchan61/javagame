import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/*
 * Created on 2007/03/10
 */

public class MainPanel extends JPanel implements ActionListener {
    private static final int WIDTH = 480;
    private static final int HEIGHT = 480;

    // ���b�Z�[�W����\���G���A
    private JTextArea dialogueArea;
    // ���b�Z�[�W���̓t�B�[���h
    private JTextField inputField;

    private Chatbot chatbot = new Chatbot("�l�H���]2��");

    public MainPanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        initGUI();
    }

    /**
     * GUI������������
     */
    private void initGUI() {
        setLayout(new BorderLayout());

        // ���b�Z�[�W����\���G���A
        dialogueArea = new JTextArea();
        dialogueArea.setEditable(false);
        dialogueArea.setLineWrap(true);
        dialogueArea.append("�l�H���]�v���g�^�C�v\n\n");

        // ���b�Z�[�W���̓t�B�[���h
        inputField = new JTextField("���b�Z�[�W����͂��Ă�������");
        inputField.selectAll();

        // �p�l���ɒǉ�
        JScrollPane scrollPane = new JScrollPane(dialogueArea);
        scrollPane.setAutoscrolls(true);
        add(scrollPane, BorderLayout.CENTER);
        add(inputField, BorderLayout.SOUTH);

        inputField.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        // ���̓��b�Z�[�W���擾
        String input = inputField.getText();
        dialogueArea.append("���Ȃ�\t" + input + "\n");

        // �l�H���]�̔������b�Z�[�W���擾
        String response = chatbot.getResponse(input);
        dialogueArea.append(chatbot.getName() + "\t" + response + "\n");
        dialogueArea.setCaretPosition(dialogueArea.getText().length());

        inputField.setText("");
    }
}
