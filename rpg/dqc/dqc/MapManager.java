package dqc;

import java.util.HashMap;

public class MapManager {
    // �}�b�v��
    private static final String[] mapNames = {
        "king_room", "castle", "overworld", "town"};
    
    // �}�b�v��->�}�b�v�I�u�W�F�N�g�ւ̃n�b�V��
    private HashMap maps;

    // ���݂̃}�b�v
    public Map curMap;
    
    public MapManager() {
        maps = new HashMap();
        
        loadMap();
    }
    
    /**
     * �}�b�v��Ԃ�
     * 
     * @param mapName �}�b�v��
     * @return �}�b�v�I�u�W�F�N�g
     */
    public Map getMap(String mapName) {
        return (Map)maps.get(mapName);
    }
    
    /**
     * ���݂̃}�b�v��Ԃ�
     * 
     * @return ���݂̃}�b�v
     */
    public Map getCurMap() {
        return curMap;
    }
    
    /**
     * �ړ���̃}�b�v���Z�b�g����
     * 
     * @param map �ړ���̃}�b�v
     */
    public void setCurMap(Map map) {
        curMap = map;
    }
    
    /**
     * �}�b�v�����[�h����
     *
     */
    private void loadMap() {
        for (int i=0; i<mapNames.length; i++) {
            Map map = new Map(mapNames[i]);
            maps.put(mapNames[i], map);
        }
    }
}
