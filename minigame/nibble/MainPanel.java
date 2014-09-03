/*
 * �쐬��: 2004/12/14
 *
 */
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
/**
 * ���C���p�l��
 * 
 * @author mori
 *  
 */
public class MainPanel extends JPanel implements Runnable, KeyListener {
    // �t�B�[���h�̍s��
    public static final int ROW = 30;
    // �t�B�[���h�̗�
    public static final int COL = 40;
    // �t�B�[���h�̃O���b�h�T�C�Y
    public static final int GS = 16;
    // �Q�[����ԁF�X�^�[�g���
    public static final int START = 0;
    // �Q�[����ԁF�v���C���
    public static final int PLAY = 1;
    // �Q�[����ԁF�Q�[���I�[�o�[���
    public static final int GAMEOVER = 2;

    // �Q�[�����
    private int gameState;
    // �X�R�A
    private int score;
    // �c�莞��
    private int limit;

    // ��
    private Snake snake;
    // �^�̐�
    private static final int NUM_TOAD = 50;
    // �^
    private Toad[] toad;

    // ����������
    private Random rand = new Random();
    // �X���b�h
    private Thread thread;
    // InfoPanel�ւ̎Q��
    private InfoPanel infoPanel;
    
    // �^�C�g���w�i
    private Image titleImage;
    // �Q�[���I�[�o�[�w�i
    private Image gameoverImage;
    
    /**
     * �R���X�g���N�^
     *  
     */
    public MainPanel(InfoPanel infoPanel) {
        // �p�l���̑傫���ipack()����Ƃ��ɕK�v�j
        setPreferredSize(new Dimension(640, 480));
        this.infoPanel = infoPanel;
        // �C���[�W�����[�h����
        loadImage();
        // �L�[���X�i�[��o�^
        addKeyListener(this);
        // �Q�[����Ԃ�START�ɐݒ�
        gameState = START;
    }

    /**
     * �Q�[��������������
     *  
     */
    public void newGame() {
        snake = new Snake(10, 7, this);
        toad = new Toad[NUM_TOAD];
        for (int i = 0; i < NUM_TOAD; i++) {
            makeToad(i);
        }
        score = 0;
        limit = 600;
        thread = new Thread(this);
        thread.start();
        gameState = PLAY;
    }

    /**
     * �Q�[�����[�v
     */
    public void run() {
        while (true) {
            // PLAY��ԈȊO�͉������Ȃ�
            if (gameState != PLAY)
                break;
            // �^�[�����ƂɎւ̃T�C�Y���������_����������
            score += snake.getSize();
            // �c�莞�Ԃ����炷
            limit--;
            // �c�莞�Ԃ��Ȃ��Ȃ�����Q�[���I�[�o�[
            if (limit < 0) {
                gameOver();
            }

            // �ւ̌����Ă�������ֈړ�
            // �ւ̌�����keyPressed()�ŃZ�b�g�����
            // �L�[�������Ȃ��Ƃ����Ɠ��������ֈړ�����
            snake.move();
            // �^���ړ�
            for (int i = 0; i < NUM_TOAD; i++) {
                toad[i].move();
            }
            // �ւ���ʂ̊O�֏o����Q�[���I�[�o�[
            if (snake.isOutOfField()) {
                gameOver();
                break;
            }
            // �ւ������̐g�̂ɐG�ꂽ��Q�[���I�[�o�[
            if (snake.touchOwnBody()) {
                gameOver();
                break;
            }
            // �ւ��^��H�ׂ����ǂ����`�F�b�N����
            // �H�ׂ���ւ̐g�̂͐L�т�
            for (int i = 0; i < NUM_TOAD; i++) {
                if (snake.eat(toad[i])) {
                    // �Q�R�`
                    toad[i].croak();
                    // i�Ԗڂ͐H�ׂ�ꂿ������̂ŐV�������
                    makeToad(i);
                }
            }
            // �C���t�H���[�V�����p�l���ɏ���\������
            infoPanel.setLengthLabel(snake.getSize() + "");
            infoPanel.setScoreLabel(score + "");
            infoPanel.setTimeLabel(limit + "");

            repaint();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // �Q�[���I�[�o�[�ɂȂ���break��while���[�v���ʂ����Ƃ��ɂ�
        // �ĕ`�悳���悤��
        repaint();
    }

    /**
     * JPanel���L�[�{�[�h���󂯕t���邽�߂ɕK�v
     *  
     */
    public boolean isFocusable() {
        // �p�l�����L�[�{�[�h���󂯕t����悤�ɂ���
        return true;
    }

    /**
     * �L�[���� �ւ̌������Z�b�g����
     */
    public void keyPressed(KeyEvent event) {
        // �Q�[����Ԃɉ����ăL�[����������
        if (gameState == START) {
            // START��ʂŃX�y�[�X�������ƃQ�[�����n�܂�
            if (event.getKeyCode() == KeyEvent.VK_SPACE) {
                gameState = PLAY;
                // �V�K�Q�[��
                newGame();
            }
        } else if (gameState == PLAY) {
            // PLAY��ʂȂ�ւ̕������Z�b�g����
            int key = event.getKeyCode();
            int dir = snake.getDir();
            if (key == KeyEvent.VK_LEFT && dir != Snake.RIGHT) {
                // �E�����Ă���Ƃ��ɍ��Ɉړ��͂ł��Ȃ��i�g�̂ɂԂ����Ă��܂��j
                snake.setDir(Snake.LEFT);
            } else if (key == KeyEvent.VK_DOWN && dir != Snake.UP) {
                snake.setDir(Snake.DOWN);
            } else if (key == KeyEvent.VK_UP && dir != Snake.DOWN) {
                snake.setDir(Snake.UP);
            } else if (key == KeyEvent.VK_RIGHT && dir != Snake.LEFT) {
                snake.setDir(Snake.RIGHT);
            }
        } else if (gameState == GAMEOVER) {
            // GAMEOVER��ʂȂ�X�y�[�X�ōŏ��ɖ߂�
            if (event.getKeyCode() == KeyEvent.VK_SPACE) {
                gameState = START;
            }
        }

        repaint();
    }

    public void keyReleased(KeyEvent event) {
    }
    public void keyTyped(KeyEvent event) {
    }

    /**
     * �`�揈��
     * 
     * @param g �O���t�B�b�N�n���h��
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // �Q�[����Ԃɉ����ĉ�ʂ�ς���
        if (gameState == START) {
            g.drawImage(titleImage, 0, 0, this);
        } else if (gameState == PLAY) {
            clear(g);
            snake.draw(g);
            for (int i = 0; i < NUM_TOAD; i++) {
                toad[i].draw(g);
            }
        } else if (gameState == GAMEOVER) {
            g.drawImage(gameoverImage, 0, 0, this);
            g.setFont(new Font("SansSerif", Font.BOLD, 28));
            FontMetrics fm = g.getFontMetrics();
            g.setColor(Color.WHITE);
            String s = "YOUR LENGTH: " + snake.getSize();
            g.drawString(s, 
                    (this.getWidth() - fm.stringWidth(s)) / 2,
                    this.getHeight() / 2 + fm.getDescent());
            s = "YOUR SCORE: " + score;
            g.drawString(s,
                    (this.getWidth() - fm.stringWidth(s)) / 2,
                    this.getHeight() / 2 + fm.getDescent() + fm.getHeight());
        }
    }

    /**
     * n�Ԗڂ̊^��V�����쐬����
     * 
     * @param n
     */
    private void makeToad(int n) {
        // �����_���Ȉʒu
        Point p = new Point(rand.nextInt(COL), rand.nextInt(ROW));
        // ��:��:��=1:4:5�̔䗦�ŏo��悤�ɂ���
        double x = rand.nextDouble();
        if (x < 0.1) {
            toad[n] = new GoldToad(p, this);
        } else if (x < 0.5) {
            toad[n] = new BlueToad(p, this);
        } else {
            toad[n] = new GreenToad(p, this);
        }
    }
    
    /**
     * �Q�[���I�[�o�[�����B
     *
     */
    private void gameOver() {
        // �Ō�ɃX�R�A��������Ă��܂��̂łЂ��Ă���
        score -= snake.getSize();
        // �Q�[���I�[�o�[��Ԃ�
        gameState = GAMEOVER;
    }
    
    /**
     * ��ʂ��N���A����
     * 
     * @param g �O���t�B�b�N�n���h��
     */
    private void clear(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getSize().width, getSize().height);
    }
    
    /**
     * �C���[�W�����[�h����B
     *
     */
    private void loadImage() {
        MediaTracker tracker = new MediaTracker(this);
        titleImage = Toolkit.getDefaultToolkit().getImage(
                getClass().getResource("title.jpg"));
        gameoverImage = Toolkit.getDefaultToolkit().getImage(
                getClass().getResource("gameover.jpg"));
        tracker.addImage(titleImage, 0);
        tracker.addImage(gameoverImage, 0);
        try {
            tracker.waitForAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}