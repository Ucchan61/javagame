/*
 * �l�H���]�N���X
 * 
 * Created on 2007/01/08
 */

public class Chatbot {
    // �l�H���]�̖��O
    private String name;

    public Chatbot(String name) {
        this.name = name;
    }

    /**
     * ���̓��b�Z�[�W�ɑ΂��锽�����b�Z�[�W��Ԃ�
     * 
     * @param message ���̓��b�Z�[�W
     * @return �������b�Z�[�W
     */
    public String getResponse(String message) {

        // �����������̂��ǂ���

        return message; // �Ƃ肠���������ޕԂ�
    }

    /**
     * �l�H���]�̖��O��Ԃ�
     * 
     * @return ���O
     */
    public String getName() {
        return name;
    }
}
