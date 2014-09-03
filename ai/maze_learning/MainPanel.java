import java.awt.*;
import javax.swing.*;

public class MainPanel extends JPanel implements Runnable {
    // �p�l���T�C�Y
    private static final int WIDTH = 624;
    private static final int HEIGHT = 624;

    // 1�G�s�\�[�h�̍ő�X�e�b�v���iMax Steps per Episode�j
    private static final double MSE = 10000;

    // ���H��
    private Maze maze;
    // �G�[�W�F���g
    private Agent agent;
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

    /**
     * �R���X�g���N�^�B���H�ƃG�[�W�F���g���쐬���ăX���b�h���J�n�B
     */
    public MainPanel() {
        // �p�l���̐����T�C�Y��ݒ�Apack()����Ƃ��ɕK�v
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        maze = new Maze(39, 39, new Point(1, 1), new Point(37, 37), this);
        agent = new Agent(maze);

        sleepTime = 100;
        skipFlag = false;

        thread = new Thread(this);
        thread.start();
    }

    /**
     * ���H�ƃG�[�W�F���g�����������čŏ�����w�K���n�߂�B
     */
    public void init() {
        // �����_�����H����蒼��
        maze.build();
        // �G�[�W�F���g�̊w�K���ʂ�����
        agent.initQ();
        episode = step = 0;
        infoPanel.setEpisodeLabel(0 + "");
        infoPanel.setStepLabel(0 + "");
    }

    /**
     * �����w�K�Ŗ��H���w�K����B
     */
    public void run() {
        // ���
        int state;
        // �s��
        int action;
        // �����
        int nextState;
        // ���s��
        int nextAction;
        // ��V
        double reward;
        // 1�G�s�\�[�h���̕�V���v
        int sumReward;

        episode = 0;
        // ���l�֐���������
        agent.initQ();
        while (true) {
            // �G�s�\�[�h�J�n
            step = 0;
            sumReward = 0;
            // ���H�̏�Ԃ�����������
            maze.init();
            // ��Ԃ�m�o����
            state = maze.getStateNum();
            while (!maze.isGoal() && step < MSE) {
                // �X�e�b�v�J�n
                // �s����I���{���ۂɈړ�
                action = agent.selectAction();
                maze.nextState(action);
                if (!skipFlag) repaint();
                // ����Ԃ�m�o����
                nextState = maze.getStateNum();
                // ��V�𓾂�
                reward = maze.getReward();
                // ���l���ő�̍s����I������i�ړ��͂��Ȃ��j
                nextAction = agent.selectBestAction();
                // ���l�֐����X�V����iQ-Learning�j
                agent.updateQ(state, action, reward, nextState, nextAction);
                // ����Ԃ֑J��
                state = nextState;
                sumReward += reward;
                // �X�e�b�v�I��
                step++;

                if (!skipFlag) {
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            // �G�s�\�[�h�I��
            //System.out.println("EPISODE: " + episode + "\t" + "STEP: " + step);
            infoPanel.setEpisodeLabel(episode + "");
            infoPanel.setStepLabel(step + "");
            episode++;
        }
    }

    /**
     * �A�j���[�V�������X�L�b�v���邩��ݒ肷��B
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
     * ���H�ƃG�[�W�F���g��`���B
     */
    public void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        if (!skipFlag) {
            // ���H��`��
            maze.draw(g);
        } else {
            drawTextCentering(g, "Now Learning...");
        }
    }

    /**
     * ���݂̃G�s�\�[�h����Ԃ��B
     * @return �G�s�\�[�h���B
     */
    public int getEpisode() {
        return episode;
    }

    /**
     * ���݂̃X�e�b�v����Ԃ��B
     * @return �X�e�b�v���B
     */
    public int getStep() {
        return step;
    }

    /**
     * �C���t�H���[�V�����p�l���ւ̎Q�Ƃ��Z�b�g����B
     * @param panel�@�C���t�H���[�V�����p�l���B
     */
    public void setInfoPanel(InfoPanel panel) {
        infoPanel = panel;
    }

    // ��ʂ̒����ɕ������\������
    private void drawTextCentering(Graphics g, String s) {
        Font f = new Font("SansSerif", Font.BOLD, 20);
        g.setFont(f);
        FontMetrics fm = g.getFontMetrics();
        g.setColor(Color.YELLOW);
        g.drawString(
            s,
            WIDTH / 2 - fm.stringWidth(s) / 2,
            HEIGHT / 2 + fm.getDescent());
    }
}
