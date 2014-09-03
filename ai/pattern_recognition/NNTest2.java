/*
 * Created on 2005/05/04
 *
 */

/**
 * �����F��
 * @author mori
 */
public class NNTest2 {
    public static void main(String[] args) {
        NeuralNetwork nn = new NeuralNetwork();
        nn.init(25, 50, 10);
        nn.setLearningRate(0.2);
        
        // �P���f�[�^�̍쐬
        double[][] trainingSet = new double[][] {
                {1,1,1,1,1,1,0,0,0,1,1,0,0,0,1,1,0,0,0,1,1,1,1,1,1},  // 0
                {0,0,1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1,0,0},  // 1
                {1,1,1,1,1,0,0,0,0,1,1,1,1,1,1,1,0,0,0,0,1,1,1,1,1},  // 2
                {1,1,1,1,1,0,0,0,0,1,1,1,1,1,1,0,0,0,0,1,1,1,1,1,1},  // 3
                {1,0,0,0,0,1,0,0,0,0,1,0,1,0,0,1,1,1,1,1,0,0,1,0,0},  // 4
                {1,1,1,1,1,1,0,0,0,0,1,1,1,1,1,0,0,0,0,1,1,1,1,1,1},  // 5
                {1,1,1,1,1,1,0,0,0,0,1,1,1,1,1,1,0,0,0,1,1,1,1,1,1},  // 6
                {1,1,1,1,1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1},  // 7
                {1,1,1,1,1,1,0,0,0,1,1,1,1,1,1,1,0,0,0,1,1,1,1,1,1},  // 8
                {1,1,1,1,1,1,0,0,0,1,1,1,1,1,1,0,0,0,0,1,0,0,0,0,1},  // 9
        };
        
        // ���t�M���i���ꂼ��̃p�^�[���ɂ����镔������1�j
        // �P�ʍs��ɂȂ邱�Ƃɒ��ӁI
        double[][] teacherSet = new double[][] {
                {1,0,0,0,0,0,0,0,0,0},  // 0
                {0,1,0,0,0,0,0,0,0,0},  // 1
                {0,0,1,0,0,0,0,0,0,0},  // 2
                {0,0,0,1,0,0,0,0,0,0},  // 3
                {0,0,0,0,1,0,0,0,0,0},  // 4
                {0,0,0,0,0,1,0,0,0,0},  // 5
                {0,0,0,0,0,0,1,0,0,0},  // 6
                {0,0,0,0,0,0,0,1,0,0},  // 7
                {0,0,0,0,0,0,0,0,1,0},  // 8
                {0,0,0,0,0,0,0,0,0,1},  // 9
        };
            
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
                    nn.setInput(j, trainingSet[i][j]);
                }
                // ���t�M����ݒ�
                for (int j=0; j<teacherSet[i].length; j++) {
                    nn.setTeacherValue(j, teacherSet[i][j]);
                }
                // �w�K�J�n
                nn.feedForward();
                error += nn.calculateError();
                nn.backPropagate();
            }
            error /= trainingSet.length;
            System.out.println(count + "\t" + error);
        }
        
        // �w�K����
        // 3�Ԗڂ̌P���f�[�^�����Ă݂�
        int testData = 3;
        for (int j=0; j<trainingSet[testData].length; j++) {
            nn.setInput(j, trainingSet[testData][j]);
        }
        nn.feedForward();   // �o�͂��v�Z
        int id = nn.getMaxOutputID();
        // ������3���o�͂��ꂽ�ł���H
        System.out.println(id + " " + nn.getOutput(id));
    }
}
