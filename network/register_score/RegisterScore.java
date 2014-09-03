import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/*
 * Created on 2005/07/30
 *
 */

/**
 * @author mori
 *
 */
public class RegisterScore {
    public static void main(String[] args) {
        // �ȂȂ��̓��_��9999�������Ƃ����f�[�^
        String data = "name=�ȂȂ�&score=9999";
        try {
            // CGI��\��URL�I�u�W�F�N�g
            URL cgiURL = new URL("http://javagame.main.jp/cgi-bin/score/reg_score.cgi");
            // �ڑ�
            URLConnection uc = cgiURL.openConnection();
            uc.setDoOutput(true);
            uc.setUseCaches(false);
            // CGI�ւ̏������ݗp�X�g���[�����J��
            PrintWriter pw = new PrintWriter(uc.getOutputStream());
            // CGI�Ƀf�[�^�𑗐M����
            pw.print(data);
            // �X�g���[�������
            pw.close();
            
            // CGI����̓ǂݍ��ݗp�X�g���[�����J��
            BufferedReader br = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            // CGI�̏o�͂�ǂݍ���ŃR���\�[����ʂɕ\��
            String line = "";
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            // �X�g���[�������
            br.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
