/*
 * Created on 2005/05/04
 *
 */

/**
 * Exclusive OR�̊w�K
 * @author mori
 */
public class NNTest {
    public static void main(String[] args) {
        NeuralNetwork nn = new NeuralNetwork();
        nn.init(2, 2, 1);
        nn.setLearningRate(0.2);
        
        // �P���f�[�^�̍쐬
        double[][] trainingSet = new double[4][3];
        // �P���f�[�^0
        trainingSet[0][0] = 0;  // ����1
        trainingSet[0][1] = 0;  // ����2
        trainingSet[0][2] = 0;  // ���t
        
        // �P���f�[�^1
        trainingSet[1][0] = 0;
        trainingSet[1][1] = 1;
        trainingSet[1][2] = 1;
        
        // �P���f�[�^2
        trainingSet[2][0] = 1;
        trainingSet[2][1] = 0;
        trainingSet[2][2] = 1;
        
        // �P���f�[�^3
        trainingSet[3][0] = 1;
        trainingSet[3][1] = 1;
        trainingSet[3][2] = 0;
        
        // �P���f�[�^���w�K
        double error = 1.0;
        int count = 0;
        while ((error > 0.0001) && (count < 50000)) {
            error = 0;
            count++;
            // 4�̌P���f�[�^���덷���������Ȃ�܂ŌJ��Ԃ��w�K
            for (int i=0; i<4; i++) {
                // ���͑w�ɒl��ݒ�
                nn.setInput(0, trainingSet[i][0]);
                nn.setInput(1, trainingSet[i][1]);
                // ���t�M����ݒ�
                nn.setTeacherValue(0, trainingSet[i][2]);
                // �w�K�J�n
                nn.feedForward();
                error += nn.calculateError();
                nn.backPropagate();
            }
            error /= 4.0;
            System.out.println(count + "\t" + error);
        }
        
        // �w�K����
        nn.setInput(0, 0);  // ����1
        nn.setInput(1, 0);  // ����2
        nn.feedForward();   // �o�͂��v�Z
        System.out.println(nn.getOutput(0));
    }
}
