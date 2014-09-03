/*
 * �쐬��: 2004/12/06
 *
 */
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;
/**
 * ���C���p�l���B�����w�K���������ăA�j���[�V�����ɂ��Ă���B
 * @author mori
 *  
 */
public class MainPanel extends JPanel implements Runnable, KeyListener {
    // �p�l���T�C�Y
    public static final int WIDTH = 680;
    public static final int HEIGHT = 240;

    // �G�[�W�F���g
    private Agent agent;
    // ��
    private Environment env;
    // �G�[�W�F���g�A�j���[�V�����p�X���b�h
    private Thread thread;
    // �A�j���[�V�����̃X�s�[�h
    private int sleepTime;
    // �A�j���[�V�������X�L�b�v���邩�̃t���O
    private boolean skipFlag;
    // �G�s�\�[�h���ƃX�e�b�v���i�C���t�H���[�V�����p�l���ɕ\������̂ŃC���X�^���X�ϐ��ɂ���j
    private int episode, step;
    // InfoPanel�ւ̎Q��
    private InfoPanel infoPanel;

    // �������[�h���������[�h��
    private boolean isGuideMode = true;
    // �G�[�W�F���g�̍s���i�L�[���͂ŃA�N�Z�X����j
    int action = 0;

    /**
     * �R���X�g���N�^�B���ƃG�[�W�F���g���쐬���ăX���b�h���J�n�B
     */
    public MainPanel() {
        // �p�l���̐����T�C�Y��ݒ�Apack()����Ƃ��ɕK�v
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        env = new Environment(this);
        agent = new Agent(env);

        sleepTime = 80;
        skipFlag = false;

        addKeyListener(this);
        setFocusable(true);

        thread = new Thread(this);
        thread.start();
    }

    /**
     * �p�l��������������B
     */
    public void init() {
        agent.initQ();
        env.init();
        episode = step = 0;
        infoPanel.setEpisodeLabel(0 + "");
        infoPanel.setStepLabel(0 + "");
    }

    /**
     * �����w�K�œ|���U�q��������w�K����B
     */
    public void run() {
        // ���
        int state;
        // �����
        int nextState;
        // ���s��
        int nextAction;
        // ��V
        double reward;

        episode = 0;
        // ���l�֐���������
        agent.initQ();
        while (true) {
            // �G�s�\�[�h�J�n
            step = 0;
            // ���̏�Ԃ�����������
            env.init();
            // ���̏�Ԃ�m�o����
            state = env.getStateNum();
            while (!env.isFailed()) {
                // �X�e�b�v�J�n
                // �s����I���{���ۂɈړ�
                if (!isGuideMode) {  // �������[�h�Ȃ�
                    action = agent.selectAction();
                }
                // �s���ɉ����Ċ��̏�Ԃ�J��
                env.nextState(action);

                // �������s�����炾���Ɛ��ꉺ����܂ŕ\���𑱂���
                if (env.isFailed()) {
                    while (!env.isDroopy()) {
                        env.nextState(action);
                        if (!skipFlag) {
                            repaint();
                            try {
                                Thread.sleep(sleepTime);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                // ����Ԃ�m�o����
                nextState = env.getStateNum();
                // ��V�𓾂�
                reward = env.getReward();
                // ���l���ő�̍s����I������i�ړ��͂��Ȃ��j
                nextAction = agent.selectBestAction();
                // ���l�֐����X�V����iQ-Learning�j
                agent.updateQ(state, action, reward, nextState, nextAction);
                // ����Ԃ֑J��
                state = nextState;
                // �X�e�b�v�I��
                step++;
                infoPanel.setStepLabel(step + "");

                if (!skipFlag) {
                    repaint();
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            // �G�s�\�[�h�I��
            System.out.println("EPISODE: " + episode + "\t" + "STEP: " + step);
            episode++;
            infoPanel.setEpisodeLabel(episode + "");
        }
    }

    /**
     * �A�j���[�V�������X�L�b�v���邩��ݒ肷��B
     * 
     * @param flag �X�L�b�v����Ȃ�true�B
     */
    public void skip(boolean flag) {
        if (flag) {
            skipFlag = true;
        } else {
            skipFlag = false;
        }
    }

    /**
     * �n�ʂƃG�[�W�F���g��`���B
     */
    public void paintComponent(Graphics g) {
        // ��ʂ��N���A����
        g.setColor(getBackground());
        g.fillRect(0, 0, WIDTH, HEIGHT);
        if (!skipFlag) {
            // �n�ʂ�`��
            env.draw(g);
        }
        
        // ���[�h�������`��
        if (isGuideMode) {
            drawTextCentering(g, "�������[�h");
        } else {
            drawTextCentering(g, "�������[�h");
        }
    }

    /**
     * ���݂̃G�s�\�[�h����Ԃ��B
     * 
     * @return �G�s�\�[�h���B
     */
    public int getEpisode() {
        return episode;
    }

    /**
     * ���݂̃X�e�b�v����Ԃ��B
     * 
     * @return �X�e�b�v���B
     */
    public int getStep() {
        return step;
    }

    /**
     * �C���t�H���[�V�����p�l���ւ̎Q�Ƃ��Z�b�g����B
     * 
     * @param panel �C���t�H���[�V�����p�l���B
     */
    public void setInfoPanel(InfoPanel panel) {
        infoPanel = panel;
    }

    // ��ʂ̒����ɕ������\������
    private void drawTextCentering(Graphics g, String s) {
        Font f = new Font("SansSerif", Font.BOLD, 20);
        g.setFont(f);
        FontMetrics fm = g.getFontMetrics();
        g.setColor(Color.RED);
        g.drawString(s, WIDTH / 2 - fm.stringWidth(s) / 2,
                HEIGHT / 2 + fm.getDescent());
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        
        switch (key) {
            case KeyEvent.VK_SPACE:
                changeMode();
            case KeyEvent.VK_LEFT:
                action = 0;
                break;
            case KeyEvent.VK_RIGHT:
                action = 1;
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    /**
     * ���[�h��ύX����
     */
    public void changeMode() {
        isGuideMode = !isGuideMode;
        if (isGuideMode) {
            sleepTime = 80;
        } else {
            sleepTime = 20;
        }
    }
}
