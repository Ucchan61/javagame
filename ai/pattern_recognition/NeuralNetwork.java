/*
 * Created on 2005/05/04
 *
 */

/**
 * @author mori
 *
 */
public class NeuralNetwork {
    private Layer inputLayer;   // ���͑w
    private Layer hiddenLayer;  // �B��w
    private Layer outputLayer;  // �o�͑w
    
    /**
     * �j���[�����l�b�g��������
     * @param numInputNodes ���͑w�̃m�[�h��
     * @param numHiddenNodes �B��w�̃m�[�h��
     * @param numOutputNodes �o�͑w�̃m�[�h��
     */
    public void init(int numInputNodes, int numHiddenNodes, int numOutputNodes) {
        inputLayer = new Layer();
        hiddenLayer = new Layer();
        outputLayer = new Layer();
        
        // ���͑w�i�e�w���Ȃ����Ƃɒ��Ӂj
        inputLayer.numNodes = numInputNodes;
        inputLayer.numChildNodes = numHiddenNodes;
        inputLayer.numParentNodes = 0;
        inputLayer.init(numInputNodes, null, hiddenLayer);
        inputLayer.randomizeWeights();
        
        // �B��w
        hiddenLayer.numNodes = numHiddenNodes;
        hiddenLayer.numChildNodes = numOutputNodes;
        hiddenLayer.numParentNodes = numInputNodes;
        hiddenLayer.init(numHiddenNodes, inputLayer, outputLayer);
        hiddenLayer.randomizeWeights();

        // �o�͑w�i�q�w���Ȃ����Ƃɒ��Ӂj
        outputLayer.numNodes = numOutputNodes;
        outputLayer.numChildNodes = 0;
        outputLayer.numParentNodes = numHiddenNodes;
        outputLayer.init(numOutputNodes, hiddenLayer, null);
        // �o�͑w�ɏd�݂͂Ȃ��̂�randomizeWeights()�͕K�v�Ȃ�
    }
    
    /**
     * ���͑w�ւ̓��͂�ݒ肷��
     * @param i �m�[�h�ԍ�
     * @param value �l
     */
    public void setInput(int i, double value) {
        if ((i >= 0) && (i < inputLayer.numNodes)) {
            inputLayer.neuronValues[i] = value;
        }
    }
    
    /**
     * �o�͑w�̏o�͂𓾂�
     * @param i �m�[�h�ԍ�
     * @return �o�͑wi�Ԗڂ̃m�[�h�̏o�͒l
     */
    public double getOutput(int i) {
        if ((i >= 0) && (i < outputLayer.numNodes)) {
            return outputLayer.neuronValues[i];
        }
        
        return Double.MAX_VALUE;  // �G���[
    }
    
    /**
     * ���t�M����ݒ肷��
     * @param i �m�[�h�ԍ�
     * @param value ���t�M���̒l
     */
    public void setTeacherValue(int i, double value) {
        if ((i >= 0) && (i < outputLayer.numNodes)) {
            outputLayer.teacherValues[i] = value;
        }
    }
    
    /**
     * �O�����`�d�i���Ԃ͏d�v�j
     */
    public void feedForward() {
        inputLayer.calculateNeuronValues();
        hiddenLayer.calculateNeuronValues();
        outputLayer.calculateNeuronValues();
    }
    
    /**
     * �t�����`�d�i���Ԃ͏d�v�j
     */
    public void backPropagate() {
        outputLayer.calculateErrors();
        hiddenLayer.calculateErrors();
        
        hiddenLayer.adjustWeights();
        inputLayer.adjustWeights();
    }
    
    /**
     * �o�͑w�ōő�o�͂����m�[�h�ԍ���Ԃ�
     * @return �ő�o�͂����m�[�h�ԍ�
     */
    public int getMaxOutputID() {
        double max = outputLayer.neuronValues[0];
        int id = 0;
        
        for (int i=1; i<outputLayer.numNodes; i++) {
            if (outputLayer.neuronValues[i] > max) {
                max = outputLayer.neuronValues[i];
                id = i;
            }
        }
        
        return id;
    }
    
    /**
     * �o�͂Ƌ��t�M���̕���2��덷���v�Z����
     * @return ����2��덷
     */
    public double calculateError() {
        double error = 0;
        
        for (int i=0; i<outputLayer.numNodes; i++) {
            error += Math.pow(outputLayer.neuronValues[i] - outputLayer.teacherValues[i], 2);
        }
        
        error /= outputLayer.numNodes;
        
        return error;
    }
    
    /**
     * �w�K����ݒ肷��
     * @param rate �w�K��
     */
    public void setLearningRate(double rate) {
        inputLayer.learningRate = rate;
        hiddenLayer.learningRate = rate;
        outputLayer.learningRate = rate;
    }
}
