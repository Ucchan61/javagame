/*
 * �C�x���g���[�_�[
 * 
 */
package dqc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class EventLoader {
    // �Ăяo������Map�ւ̎Q��
    private Map map;
    
    public EventLoader(Map map) {
        this.map = map;
    }
    
    public ArrayList load(String eventFile) {
        // �Ăяo�����ɖ߂��C�x���g���X�g
        ArrayList eventList = new ArrayList();
        
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    getClass().getClassLoader().getResourceAsStream(eventFile), "Shift_JIS"));
            String line;
            while ((line = br.readLine()) != null) {
                // ��s��ǂݔ�΂�
                if (line.equals("")) continue;
                // �R�����g�s��ǂݔ�΂�
                if (line.startsWith("#")) continue;
                StringTokenizer st = new StringTokenizer(line, ",");
                // �C�x���g�����擾����
                // �C�x���g�^�C�v���擾���ăC�x���g���Ƃɏ�������
                String eventType = st.nextToken();
                if (eventType.equals("BGM")) {  // BGM�C�x���g
                    map.setBGM(st.nextToken());
                } else if (eventType.equals("CHARA")) {  // �L�����N�^�[�C�x���g
                    eventList.add(createChara(st));
                } else if (eventType.equals("TREASURE")) {  // �󔠃C�x���g
                    eventList.add(createTreasure(st));
                } else if (eventType.equals("DOOR")) {  // �h�A�C�x���g
                    eventList.add(createDoor(st));
                } else if (eventType.equals("MOVE")) {  // �ړ��C�x���g
                    eventList.add(createMove(st));
                }
            }
        } catch (UnsupportedCharsetException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return eventList;
    }

    /**
     * CHARA�C�x���g��ǂݍ����Chara�I�u�W�F�N�g�𐶐�
     * 
     * @param st
     */
    private Chara createChara(StringTokenizer st) {
        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());
        int imageNo = Integer.parseInt(st.nextToken());
        int direction = Integer.parseInt(st.nextToken());
        int moveType = Integer.parseInt(st.nextToken());
        String message = st.nextToken();
        Chara chara = new Chara(x, y, imageNo, direction, moveType, message, map);

        return chara;
    }
    
    /**
     * TREASURE�C�x���g��ǂݍ����Treasure�I�u�W�F�N�g�𐶐�
     * 
     * @param st
     */
    private Treasure createTreasure(StringTokenizer st) {
        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());
        int imageNo = Integer.parseInt(st.nextToken());
        String itemName = st.nextToken();
        Treasure treasure = new Treasure(x, y, imageNo, itemName);
        
        return treasure;
    }

    /**
     * DOOR�C�x���g��ǂݍ����Door�I�u�W�F�N�g�𐶐�
     * 
     * @param st
     */
    private Door createDoor(StringTokenizer st) {
        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());
        int imageNo = Integer.parseInt(st.nextToken());
        Door door = new Door(x, y, imageNo);
        
        return door;
    }
    
    /**
     * MOVE�C�x���g��ǂݍ����Move�I�u�W�F�N�g�𐶐�
     * 
     * @param st
     */
    private Move createMove(StringTokenizer st) {
        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());
        int imageNo = Integer.parseInt(st.nextToken());
        String destMapName = st.nextToken();
        int destX = Integer.parseInt(st.nextToken());
        int destY = Integer.parseInt(st.nextToken());
        Move move = new Move(x, y, imageNo, destMapName, destX, destY);
        
        return move;
    }
}
