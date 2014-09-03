/*
 * Created on 2005/05/04
 *
 */

/**
 * �����F��
 * @author mori
 */
public class NNTest {
    public static void main(String[] args) {
        NeuralNetwork nn = new NeuralNetwork();
        nn.init(9, 50, 9);
        nn.setLearningRate(0.2);
        
        // �P���f�[�^�̍쐬
        double[][] trainingSet = new double[][] {
                {1,0,0,0,0,0,0,0},
                {0,1,0,0,0,0,0,0},
                {0,0,1,0,0,0,0,0},
                {0,0,0,1,0,0,0,0},
                {0,0,0,0,1,0,0,0},
                {0,0,0,0,0,1,0,0},
                {0,0,0,0,0,0,1,0},
                {0,0,0,0,0,0,0,1},
        };
        
        // ���t�M��
        double[][] teacherSet = new double[][] {
                {0,0,0,0,0,0,0,1},
                {0,0,0,0,0,0,1,0},
                {0,0,0,0,0,1,0,0},
                {0,0,0,0,1,0,0,0},
                {0,0,0,1,0,0,0,0},
                {0,0,1,0,0,0,0,0},
                {0,1,0,0,0,0,0,0},
                {1,0,0,0,0,0,0,0},
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
        nn.setInput(0, 1);
        nn.setInput(1, 0);
        nn.setInput(2, 0);
        nn.setInput(3, 0);
        nn.setInput(4, 0);
        nn.setInput(5, 0);
        nn.setInput(6, 0);
        nn.setInput(7, 0);
        nn.feedForward();   // �o�͂��v�Z
        int id = nn.getMaxOutputID();
        System.out.println(id + " " + nn.getOutput(id));
    }
}
