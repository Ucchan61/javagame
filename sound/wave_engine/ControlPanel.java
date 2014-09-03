import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/*
 * Created on 2006/11/05
 */

public class ControlPanel extends JPanel implements ActionListener {
    private JButton attackButton, spellButton, escapeButton;
    private WaveEngine waveEngine;

    public ControlPanel(WaveEngine waveEngine) {
        this.waveEngine = waveEngine;
        initGUI();
    }

    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();

        if (button == attackButton) {
            waveEngine.play("attack");
        } else if (button == spellButton) {
            waveEngine.play("spell");
        } else if (button == escapeButton) {
            waveEngine.play("escape");
        }
    }

    /**
     * GUI��������
     */
    private void initGUI() {
        attackButton = new JButton("��������");
        spellButton = new JButton("�������");
        escapeButton = new JButton("�ɂ���");

        add(attackButton);
        add(spellButton);
        add(escapeButton);

        attackButton.addActionListener(this);
        spellButton.addActionListener(this);
        escapeButton.addActionListener(this);
    }
}
