import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

/*
 * �l�H���]�N���X
 * 
 * Created on 2007/03/10
 */

public class Chatbot {
    // �l�H���]�̖��O
    private String name;
    // ����
    private ArrayList dic;
    // ����������
    private Random rand;

    public Chatbot(String name) {
        this.name = name;

        dic = new ArrayList();
        rand = new Random(System.currentTimeMillis());

        // �����̃��[�h
        loadDic();
    }

    /**
     * ���̓��b�Z�[�W�ɑ΂��锽�����b�Z�[�W��Ԃ�
     * 
     * @param message ���̓��b�Z�[�W
     * @return �������b�Z�[�W
     */
    public String getResponse(String message) {
        // �������烉���_���ɉ�b���Ђ��ς��Ă���
        String response = (String)dic.get(rand.nextInt(dic.size()));

        return response;
    }

    /**
     * �l�H���]�̖��O��Ԃ�
     * 
     * @return ���O
     */
    public String getName() {
        return name;
    }

    /**
     * �����̃��[�h
     */
    private void loadDic() {
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(getClass().getResourceAsStream("dic/basic.txt")));
            String line;
            while ((line = br.readLine()) != null) {
                dic.add(line); // �����ɒǉ�
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
