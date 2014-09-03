import java.util.Random;

/*
 * Created on 2005/05/03
 *
 */

/**
 * �j���[�����l�b�g���\������w�N���X
 * 
 * @author mori
 *  
 */
public class Layer {
    int numNodes; // �m�[�h��
    int numChildNodes; // �q�w�̃m�[�h��
    int numParentNodes; // �e�w�̃m�[�h��
    double[][] weights; // ���̑w�Ǝq�w�Ԃ̏d��
    double[] neuronValues; // �m�[�h�̊����l
    double[] teacherValues; // ���t�M��
    double[] errors; // �덷
    double[] biasWeights; // �o�C�A�X�̏d��
    double[] biasValues; // �o�C�A�X�l�i�o�C�A�X�̏d��*�o�C�A�X�l��������臒l�j
    double learningRate; // �w�K��

    Layer parentLayer; // �e�w�ւ̎Q��
    Layer childLayer; // �q�w�ւ̎Q��

    Random rand;

    public Layer() {
        parentLayer = null;
        childLayer = null;

        rand = new Random();
    }

    /**
     * �w������������
     * 
     * @param numNodes �w�Ɋ܂܂��m�[�h��
     * @param parent �e�w�ւ̎Q��
     * @param child �q�w�ւ̎Q��
     */
    public void init(int numNodes, Layer parent, Layer child) {
        neuronValues = new double[numNodes];
        teacherValues = new double[numNodes];
        errors = new double[numNodes];

        if (parent != null) { // �B��w�E�o�͑w
            parentLayer = parent;
        }

        if (child != null) { // ���͑w�E�B��w
            childLayer = child;

            weights = new double[numNodes][numChildNodes];

            // ���͑w�E�B��w���B��w�E�o�͑w�̃o�C�A�X���Ǘ�����
            // ������numChildNodes�̑傫���ɂȂ��Ă���
            // ��������ƃv���O�������Ȍ��ɂȂ�
            biasValues = new double[numChildNodes];
            biasWeights = new double[numChildNodes];
        } else {
            weights = null;
            biasValues = null;
            biasWeights = null;
        }

        // 0�ŏ�����
        for (int i = 0; i < numNodes; i++) {
            neuronValues[i] = 0;
            teacherValues[i] = 0;
            errors[i] = 0;

            if (child != null) { // ���͑w�E�B��w
                for (int j = 0; j < numChildNodes; j++) {
                    weights[i][j] = 0;
                }
            }
        }

        // �o�C�A�X�l�Əd�݂�������
        if (child != null) { // ���͑w�E�B��w
            for (int i = 0; i < numChildNodes; i++) {
                biasValues[i] = -1;
                biasWeights[i] = 0;
            }
        }

        // �w�K��
        learningRate = 0.2;
    }

    /**
     * �d�݂������_���ɐݒ肷��
     */
    public void randomizeWeights() {
        rand.setSeed(System.currentTimeMillis());

        // �d�݂�-1.0�`1.0�̗���
        for (int i = 0; i < numNodes; i++) {
            for (int j = 0; j < numChildNodes; j++) {
                int num = rand.nextInt(200);
                weights[i][j] = num / 100.0 - 1;
            }
        }

        // �o�C�A�X��-1.0�`1.0�̗���
        for (int i = 0; i < numChildNodes; i++) {
            int num = rand.nextInt(200);
            biasWeights[i] = num / 100.0 - 1;
        }
    }

    /**
     * �덷���v�Z����
     */
    public void calculateErrors() {
        if (childLayer == null) { // �o�͑w
            for (int i = 0; i < numNodes; i++) {
                errors[i] = (teacherValues[i] - neuronValues[i])
                        * neuronValues[i] * (1.0 - neuronValues[i]);
            }
        } else if (parentLayer == null) { // ���͑w
            for (int i = 0; i < numNodes; i++) {
                errors[i] = 0.0;
            }
        } else { // �B��w
            for (int i = 0; i < numNodes; i++) {
                double sum = 0;
                for (int j = 0; j < numChildNodes; j++) {
                    sum += childLayer.errors[j] * weights[i][j];
                }
                errors[i] = sum * neuronValues[i] * (1.0 - neuronValues[i]);
            }
        }
    }

    /**
     * �덷�����Ƃɏd�݂𒲐�����
     */
    public void adjustWeights() {
        double dw;

        if (childLayer != null) {  // ���͑w�E�B��w
            // �d�݂𒲐�
            for (int i = 0; i < numNodes; i++) {
                for (int j = 0; j < numChildNodes; j++) {
                    dw = learningRate * childLayer.errors[j] * neuronValues[i];
                    weights[i][j] += dw;
                }
            }

            // �o�C�A�X�i臒l�j������
            for (int i = 0; i < numChildNodes; i++) {
                biasWeights[i] += learningRate * childLayer.errors[i]
                        * biasValues[i];
            }
        }
    }

    /**
     * �w�̊e�j���[�����̊����l���v�Z����i�O�����j
     */
    public void calculateNeuronValues() {
        double sum;

        if (parentLayer != null) {  // �B��w�E�o�͑w
            for (int j = 0; j < numNodes; j++) {
                sum = 0.0;
                // �e�̑w�̏o�͒l�Əd�݂������đ������킹��
                for (int i = 0; i < numParentNodes; i++) {
                    sum += parentLayer.neuronValues[i]
                            * parentLayer.weights[i][j];
                }
                // �o�C�A�X�i臒l�j
                // �o�C�A�X�͐e�̑w�������Ă���
                sum += parentLayer.biasValues[j] * parentLayer.biasWeights[j];

                // �V�O���C�h�֐���ʂ�
                neuronValues[j] = sigmoid(sum);
            }
        }
    }

    /**
     * �V�O���C�h�֐�
     */
    private double sigmoid(double x) {
        return 1.0 / (1 + Math.exp(-x));
    }
}