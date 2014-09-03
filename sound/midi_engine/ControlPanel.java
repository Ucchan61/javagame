import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/*
 * Created on 2006/11/05
 */

public class ControlPanel extends JPanel implements ActionListener {
    private JButton springButton, summerButton, fallButton, winterButton;
    private MidiEngine midiEngine;
    
    public ControlPanel(MidiEngine midiEngine) {
        this.midiEngine = midiEngine;
        initGUI();
    }

    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();

        if (button == springButton) {
            midiEngine.play("�t�̐����ꏊ��");
        } else if (button == summerButton) {
            midiEngine.play("�Ă̗�");
        } else if (button == fallButton) {
            midiEngine.play("�H�̖�");
        } else if (button == winterButton) {
            midiEngine.play("�~�̐�");
        }
    }

    /**
     * GUI��������
     */
    private void initGUI() {
        springButton = new JButton("�t");
        summerButton = new JButton("��");
        fallButton = new JButton("�H");
        winterButton = new JButton("�~");
        
        add(springButton);
        add(summerButton);
        add(fallButton);
        add(winterButton);
        
        springButton.addActionListener(this);
        summerButton.addActionListener(this);
        fallButton.addActionListener(this);
        winterButton.addActionListener(this);
    }
}
