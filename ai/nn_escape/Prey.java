import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

/*
 * Created on 2005/05/13
 *
 */

/**
 * �l���N���X
 * �j���[�����l�b�g���g���ē����s�����w�K����
 * @author mori
 *
 */
public class Prey extends Chara {
    private static final int GS = MainPanel.GS;
    
    // �P���f�[�^�Z�b�g
    // ����9�}�X�ŒǐՎ҂�����ꏊ��\���Ă���
    // 012
    // 345
    // 678
    private static final int[][] trainingSet = new int[][] {
            {1,0,0,0,0,0,0,0,0},  // 0
            {0,1,0,0,0,0,0,0,0},  // 1
            {0,0,1,0,0,0,0,0,0},  // 2
            {0,0,0,1,0,0,0,0,0},  // 3
            {0,0,0,0,0,0,0,0,0},  // ����ɂ��Ȃ�
            {0,0,0,0,0,1,0,0,0},  // 5
            {0,0,0,0,0,0,1,0,0},  // 6
            {0,0,0,0,0,0,0,1,0},  // 7
            {0,0,0,0,0,0,0,0,1},  // 8
    };
    
    // ���t�M��
    // ����9�}�X�̓�����ꏊ��\���Ă���
    private static final int[][] teacherSet = new int[][] {
            {0,0,0,0,0,0,0,0,1},  // 8
            {0,0,0,0,0,0,0,1,0},  // 7
            {0,0,0,0,0,0,1,0,0},  // 6
            {0,0,0,0,0,1,0,0,0},  // 5
            {0,0,0,0,1,0,0,0,0},  // 4�i�ړ����Ȃ��j
            {0,0,0,1,0,0,0,0,0},  // 3
            {0,0,1,0,0,0,0,0,0},  // 2
            {0,1,0,0,0,0,0,0,0},  // 1
            {1,0,0,0,0,0,0,0,0},  // 0
    };
    
    // �Z���T�[
    private int[] sensor;
    // �j���[�����l�b�g
    private NeuralNetwork brain;
    // �ǐՎ҂ւ̎Q��
    private Predator predator;
    private Random rand;
    
    public Prey(int x, int y, Predator predator) {
        super(x, y);
        this.predator = predator;
        
        brain = new NeuralNetwork();
        brain.init(9, 50, 9);

        // �P���f�[�^�Z�b�g�Ƌ��t�M�������Ɋw�K����
        // �j���[�����l�b�g�͗\�ߊw�K���Ă����K�v������
        learn();
        
        // �Z���T�[�͎���9�}�X
        sensor = new int[9];
        
        rand = new Random(System.currentTimeMillis());
    }

    /**
     * �j���[�����l�b�g�̏o�͂Ɋ�Â��ē�����
     */
    public void escape() {
        // ���͂��ώ@���ăZ���T�[�f�[�^���擾
        // sensor[]�Ɋi�[�����
        sense();
        
        // �j���[�����l�b�g��sensor�̒l���Z�b�g
        for (int i=0; i<sensor.length; i++) {
            brain.setInput(i, sensor[i]);
        }
        // �o�͂��v�Z
        brain.feedForward();
        // �ő�o�͂����m�[�h�ԍ����擾
        int dir = brain.getMaxOutputID();
        // ���̕����ɓ�����
        move(dir);
    }
    
    /**
     * �l����`�悷��
     * 
     * @param g �`��I�u�W�F�N�g
     */
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x * GS, y * GS, GS, GS);
    }
    
    /**
     * �P���f�[�^���w�K����
     */
    private void learn() {
        // �P���f�[�^���w�K
        double error = 1.0;
        int count = 0;
        while ((error > 0.0001) && (count < 50000)) {
            error = 0;
            count++;
            // �e�P���f�[�^���덷���������Ȃ�܂ŌJ��Ԃ��w�K
            for (int i=0; i<trainingSet.length; i++) {
                // ���͒l��ݒ�
                for (int j=0; j<trainingSet[i].length; j++) {
                    brain.setInput(j, trainingSet[i][j]);
                }
                // ���t�M����ݒ�
                for (int j=0; j<teacherSet[i].length; j++) {
                    brain.setTeacherValue(j, teacherSet[i][j]);
                }
                // �w�K�J�n
                brain.feedForward();
                error += brain.calculateError();
                brain.backPropagate();
            }
            error /= trainingSet.length;
//            System.out.println(count + "\t" + error);
        }
    }
    
    /**
     * �Z���T�[�f�[�^���Z�b�g����
     */
    private void sense() {
        // 0�ŏ�����
        for (int i=0; i<sensor.length; i++) {
            sensor[i] = 0;
        }
        
        // �ǐՎ҂̈ʒu���擾
        int px = predator.x;
        int py = predator.y;
        
        // �ǐՎ҂̂���ꏊ�̃Z���T�[��1�ɃZ�b�g
        if (x-1 == px && y-1 == py) {
            sensor[0] = 1;
        } else if (x == px && y-1 == py) {
            sensor[1] = 1;
        } else if (x+1 == px && y-1 == py) {
            sensor[2] = 1;
        } else if (x-1 == px && y == py) {
            sensor[3] = 1;
        } else if (x+1 == px && y == py) {
            sensor[5] = 1;
        } else if (x-1 == px && y+1 == py) {
            sensor[6] = 1;
        } else if (x ==px && y+1 == py) {
            sensor[7] = 1;
        } else if (x+1 == px && y+1 == py) {
            sensor[8] = 1;
        }
      
//        for (int i=0; i<sensor.length; i++) {
//            System.out.print(sensor[i]);
//        }
//        System.out.println();
    }
}
