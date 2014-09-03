import java.awt.*;
import java.util.*;

public class Maze {
    // �O���b�h�T�C�Y�iGrid Size�j
    private static final int GS = 16;

    // ������\���萔
    private static final int UP = 0;
    private static final int DOWN = 1;
    private static final int LEFT = 2;
    private static final int RIGHT = 3;

    // ���ƕǂ̒萔�l���`
    private static final int FLOOR = 0;
    private static final int WALL = 1;

    // ���H�̍s���A��
    private int row;
    private int col;
    // �X�^�[�g�̍��W
    private Point startPos;
    // �S�[���̍��W
    private Point goalPos;
    // ��Ԑ�
    private int numState;
    // �s����
    private int numAction;

    // ���H
    private int[][] maze;
    // ���̏��
    private State state;

    // ����������
    private Random rand;
    // �C���[�W
    private Image floorImage, wallImage, throneImage, slimeImage;

    /**
     * �R���X�g���N�^�B
     * 
     * @param row ���H�̍s���B
     * @param col ���H�̗񐔁B
     * @param start ���H�̃X�^�[�g���W�B
     * @param goal ���H�̃S�[�����W�B
     */
    public Maze(int row, int col, Point start, Point goal, Component panel) {
        this.row = row;
        this.col = col;
        startPos = new Point(start);
        goalPos = new Point(goal);
        // ��Ԑ��͖��H�̃}�X�̐���������
        numState = row * col;
        // �s�����͏㉺���E��4��
        numAction = 4;

        rand = new Random();

        // �����_�����H���쐬
        maze = new int[row][col];

        // ��Ԃ�������
        state = new State(startPos.x, startPos.y);
        build();
        
        // �C���[�W�����[�h
        loadImage(panel);
    }

    /**
     * ���H������������B
     * ���H�ł̓G�[�W�F���g�̈ʒu�����H�̏�ԂȂ̂�
     * �G�[�W�F���g���X�^�[�g�n�_�ɖ߂�
     * 
     */
    public void init() {
        state.setPos(startPos.x, startPos.y);
    }

    /**
     * �G�[�W�F���g�̂�����W�����Ԕԍ����v�Z���ĕԂ��B ���H�̍��ォ��E���Ɍ������Ċe�}�X�ɏ��Ԃɔԍ������蓖�ĂĂ���B
     * 
     * @return ��Ԕԍ��B
     */
    public int getStateNum() {
        return state.y * getCol() + state.x;
    }

    /**
     * ���̏�Ԃ֑J�ڂ���B ���ۂɂ̓G�[�W�F���g���ړ�����B
     * 
     * @param action �ړ���������B
     */
    public void nextState(int action) {
        switch (action) {
            case UP :
                if (!isHit(state.x, state.y - 1))
                    state.y--;
                break;
            case DOWN :
                if (!isHit(state.x, state.y + 1))
                    state.y++;
                break;
            case LEFT :
                if (!isHit(state.x - 1, state.y))
                    state.x--;
                break;
            case RIGHT :
                if (!isHit(state.x + 1, state.y))
                    state.x++;
                break;
        }
    }

    /**
     * ���̏�Ԃɉ����ĕ�V��Ԃ��B
     * 
     * @return �S�[���ɂ�����0�A����ȊO�̏�ԂȂ�-1�𓾂�B
     */
    public double getReward() {
        // �S�[���ɂ�����0�A����ȊO�Ȃ�-1
        if (isGoal()) {
            return 0.0;
        } else {
            return -1.0;
        }
    }

    /**
     * (x,y)�ɕǂ����邩���ׂ�B
     * 
     * @param x x���W�B
     * @param y y���W�B
     * @return (x,y)�ɕǂ���������true��Ԃ��B
     */
    public boolean isHit(int x, int y) {
        if (maze[y][x] == WALL)
            return true;
        else
            return false;
    }

    /**
     * �G�[�W�F���g���S�[���ɂ��邩���ׂ�B
     * 
     * @return �G�[�W�F���g���S�[���ɂ�����true��Ԃ��B
     */
    public boolean isGoal() {
        if (state.x == getGoalPos().x && state.y == getGoalPos().y) {
            return true;
        }
        return false;
    }

    /**
     * ���H��`���B
     * 
     * @param g �O���t�B�b�N�X�I�u�W�F�N�g�B
     */
    public void draw(Graphics g) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (maze[i][j] == FLOOR)
                    g.drawImage(floorImage, j * GS, i * GS, null);
                else if (maze[i][j] == WALL)
                    g.drawImage(wallImage, j * GS, i * GS, null);
            }
        }

        // �X�^�[�g�ƃS�[���̃C���[�W��`��
        g.drawImage(throneImage, startPos.x * GS, startPos.y * GS, null);
        g.drawImage(throneImage, goalPos.x * GS, goalPos.y * GS, null);

        // �G�[�W�F���g��`��
        g.drawImage(slimeImage, state.x * GS, state.y * GS, GS, GS, null);
    }

    /**
     * ���H�̍s����Ԃ��B
     * 
     * @return �s���B
     */
    public int getRow() {
        return row;
    }

    /**
     * ���H�̗񐔂�Ԃ��B
     * 
     * @return �񐔁B
     */
    public int getCol() {
        return col;
    }

    /**
     * ���H�̏�Ԑ���Ԃ��B
     * 
     * @return ��Ԑ��B
     */
    public int getNumState() {
        return numState;
    }

    /**
     * ���H�Ŏ�肦��s������Ԃ��B
     * 
     * @return �s�����B
     */
    public int getNumAction() {
        return numAction;
    }

    /**
     * �X�^�[�g���W��Ԃ��B
     * 
     * @return �X�^�[�g���W�B
     */
    public Point getStartPos() {
        return new Point(startPos);
    }
    
    /**
     * �S�[�����W��Ԃ��B
     * 
     * @return �S�[�����W�B
     */
    public Point getGoalPos() {
        return new Point(goalPos);
    }

    /**
     * �C���[�W�����[�h����B
     * 
     * @param panel
     */
    private void loadImage(Component panel) {
        MediaTracker tracker = new MediaTracker(panel);

        // ���̃C���[�W��ǂݍ���
        floorImage = Toolkit.getDefaultToolkit().getImage(
                getClass().getResource("floor.gif"));
        tracker.addImage(floorImage, 0);

        // �ǂ̃C���[�W��ǂݍ���
        wallImage = Toolkit.getDefaultToolkit().getImage(
                getClass().getResource("wall.gif"));
        tracker.addImage(wallImage, 0);

        // �X�^�[�g�ƃS�[���̃C���[�W��ǂݍ���
        throneImage = Toolkit.getDefaultToolkit().getImage(
                getClass().getResource("throne.gif"));
        tracker.addImage(throneImage, 0);

        // �X���C���̃C���[�W��ǂݍ���
        slimeImage = Toolkit.getDefaultToolkit().getImage(
                getClass().getResource("slime.gif"));
        tracker.addImage(slimeImage, 0);

        try {
            tracker.waitForAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * ���H�̕ǂ�_�|���@�ō��B
     */
    public void build() {
        init();
        
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                maze[i][j] = FLOOR;
            }
        }
        
        // �k���O�ǂ����
        for (int i = 0; i < col; i++)
            maze[0][i] = WALL;
        // �쑤�O�ǂ����
        for (int i = 0; i < col; i++)
            maze[row - 1][i] = WALL;
        // �����O�ǂ����
        for (int i = 0; i < row; i++)
            maze[i][0] = WALL;
        // �����O�ǂ����
        for (int i = 0; i < row; i++)
            maze[i][col - 1] = WALL;
        // ���ǂ�1�����ɍ��
        for (int i = 0; i < row; i += 2)
            for (int j = 0; j < col; j += 2)
                maze[i][j] = WALL;
        // ��ԍ��̓��ǂ͖k�쐼���̂����ꂩ�ɕǂ��̂΂�
        for (int i = 2; i < row - 1; i += 2) {
            int dir = rand.nextInt(4);
            switch (dir) {
                case UP :
                    maze[i - 1][2] = WALL;
                    break;
                case DOWN :
                    maze[i + 1][2] = WALL;
                    break;
                case LEFT :
                    maze[i][1] = WALL;
                    break;
                case RIGHT :
                    maze[i][3] = WALL;
                    break;
            }
        }
        // ���̑��̓��ǂ͖k�쓌�̂����ꂩ�ɕǂ��̂΂�
        for (int i = 2; i < row - 1; i += 2) {
            for (int j = 4; j < col - 1; j += 2) {
                int dir = rand.nextInt(4);
                switch (dir) {
                    case UP :
                        maze[i - 1][j] = WALL;
                        break;
                    case DOWN :
                        maze[i + 1][j] = WALL;
                        break;
                    case RIGHT :
                        maze[i][j + 1] = WALL;
                        break;
                }
            }
        }
    }
}