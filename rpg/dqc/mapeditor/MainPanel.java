/*
 * �}�b�v�G�f�B�^�̃��C���p�l��
 */
package mapeditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import dqc.Chara;
import dqc.Door;
import dqc.Event;
import dqc.Move;
import dqc.Treasure;

public class MainPanel extends JPanel implements MouseListener, MouseMotionListener, KeyListener {
    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;
    
    public static final int CS = 16;
    
    // �}�b�v�`�b�v�p���b�g�ւ̎Q��
    private MapchipPalette mapchipPalette;
    
    // �}�b�v�`�b�v�C���[�W
    private Image[] mapchipImages;
    
    // �L�����N�^�[�C���[�W
    private Image[][] charaImages;
    
    // �}�b�v
    private int[][] map;
    // �}�b�v�̑傫��
    private int row;
    private int col;
    
    // �}�E�X�̈ʒu�i�s�N�Z���P�ʁj
    private int mouseX, mouseY;
    // �}�E�X�̈ʒu�i�}�X�P�ʁj
    private int x, y;
    
    // �C�x���g�_�C�A���O�ւ̎Q��
    private EventDialog eventDialog;
    
    // ���p�l���ւ̎Q��
    private InfoPanel infoPanel;
    
    public MainPanel(MapchipPalette mapchipPalette, CharaPalette charaPalette, EventDialog eventDialog, InfoPanel infoPanel) {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        
        this.mapchipPalette = mapchipPalette;
        mapchipImages = mapchipPalette.getMapchipImages();
        
        charaImages = charaPalette.getCharaImages();
        
        this.eventDialog = eventDialog;
        this.infoPanel = infoPanel;
        
        // �}�b�v��������
        initMap(30, 40);
    }
    
    /**
     * �}�b�v��������
     * 
     * @param row �s��
     * @param col ��
     */
    public void initMap(int row, int col) {
        this.row = row;
        this.col = col;
        map = new int[row][col];
        
        // �}�b�v�`�b�v�p���b�g�őI������Ă���}�b�v�`�b�v�ԍ����擾
        int selectedMapchipNo = mapchipPalette.getSelectedMapchipNo();
        for (int i=0; i<row; i++) {
            for (int j=0; j<col; j++) {
                map[i][j] = selectedMapchipNo;
            }
        }
    }
    
    /**
     * �}�b�v���t�@�C������ǂݍ���
     * 
     * @param mapFile �}�b�v�t�@�C����
     */
    public void loadMap(String mapFile) {
        try {
            FileInputStream in = new FileInputStream(mapFile);
            
            // �s���E�񐔂�ǂݍ���
            row = in.read();
            col = in.read();
            
            // �}�b�v��ǂݍ���
            map = new int[row][col];
            for (int i=0; i<row; i++) {
                for (int j=0; j<col; j++) {
                    map[i][j] = in.read();  // ����8�r�b�g��ǂݍ���
                    map[i][j] = (in.read() << 8) | map[i][j];  // ���8�r�b�g��ǂݍ���Ō���
                }
            }
            
            in.close();
            
            // �p�l���̑傫�����}�b�v�̑傫���Ɠ����ɂ���
            setPreferredSize(new Dimension(col * CS, row * CS));
            
            // �C�x���g�̓ǂݍ���
            String eventFile = mapFile.substring(0, mapFile.lastIndexOf(".")) + ".evt";
            loadEvent(eventFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * �C�x���g�̓ǂݍ���
     * 
     * @param eventFile �C�x���g�t�@�C����
     */
    private void loadEvent(String eventFile) {
        // ���݂̃C�x���g���폜
        eventDialog.setEventList(null);
        
        ArrayList eventList = new ArrayList();
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(eventFile));
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
                    // TODO: �}�b�v��BGM���Z�b�g����_�C�A���O�����
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
        
        eventDialog.setEventList(eventList);
    }

    /**
     * �}�b�v���t�@�C���֏�������
     * 
     * @param mapFile �}�b�v�t�@�C��
     */
    public void saveMap(String mapFile) {
        try {
            FileOutputStream out = new FileOutputStream(mapFile);

            // �s���E�񐔂���������
            out.write(row);
            out.write(col);
            
            // �}�b�v�f�[�^����������
            for (int i=0; i<row; i++) {
                for (int j=0; j<col; j++) {
                    out.write(map[i][j]);  // ����8�r�b�g����������
                    out.write(map[i][j] >> 8);  // ���8�r�b�g����������
                }
            }
            
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // �C�x���g�̕ۑ�
        String eventFile = mapFile.substring(0, mapFile.lastIndexOf(".")) + ".evt";
        saveEvent(eventFile);
    }
    
    /**
     * �C�x���g��ۑ�
     * 
     * @param eventFile �C�x���g�t�@�C����
     */
    private void saveEvent(String eventFile) {
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(
                    new FileWriter(eventFile)));
            
            // TODO: BGM���͉ς�
            ArrayList eventList = eventDialog.getEventList();
            for (int i=0; i<eventList.size(); i++) {
                Event evt = (Event) eventList.get(i);
                pw.println(evt.toString());
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * �I�����Ă���}�b�v�`�b�v�œh��Ԃ�
     *
     */
    public void fillMap() {
        int selectedMapchipNo = mapchipPalette.getSelectedMapchipNo();
        
        for (int i=0; i<row; i++) {
            for (int j=0; j<col; j++) {
                map[i][j] = selectedMapchipNo;
            }
        }
        
        repaint();
    }
    
    /**
     * �C�x���g��ǉ�����
     *
     */
    public void addEvent() {
        // ���łɃC�x���g����������u���Ȃ�
        ArrayList eventList = eventDialog.getEventList();
        for (int i=0; i<eventList.size(); i++) {
            Event evt = (Event) eventList.get(i);
            if (x == evt.getX() && y == evt.getY()) {
                JOptionPane.showMessageDialog(MainPanel.this, "���łɃC�x���g������܂��B");
                return;
            }
        }
        eventDialog.setPos(x, y);
        eventDialog.setVisible(true);
    }

    public void removeEvent() {
        // �J�[�\���ʒu�̃C�x���g����������
        ArrayList eventList = eventDialog.getEventList();
        for (int i=0; i<eventList.size(); i++) {
            Event evt = (Event) eventList.get(i);
            if (x == evt.getX() && y == evt.getY()) {
                eventList.remove(evt);  // �C�x���g���폜
                repaint();
                return;
            }
        }
        
        JOptionPane.showMessageDialog(MainPanel.this, "�폜�ł���C�x���g�͂���܂���B");
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // �}�b�v�`�b�v��`��
        for (int i=0; i<row; i++) {
            for (int j=0; j<col; j++) {
                g.drawImage(mapchipImages[map[i][j]], j * CS, i * CS, this);
            }
        }

        // �O���b�h��`��
        g.setColor(Color.BLACK);
        for (int i=0; i<row; i+=4) {
            g.drawLine(0, i * CS, col * CS, i * CS);
        }
        for (int j=0; j<col; j+=4) {
            g.drawLine(j*CS, 0, j*CS, row * CS);
        }
        
        // �C�x���g��`��
        g.setColor(Color.RED);
        ArrayList eventList = eventDialog.getEventList();
        if (eventList != null) {
            for (int i=0; i<eventList.size(); i++) {
                Event evt = (Event) eventList.get(i);

                if (evt instanceof Chara) {
                    Chara chara = (Chara)evt;
                    g.drawImage(charaImages[chara.getImageNo()][chara.getDirection()], chara.getX() * CS, chara.getY() * CS, this);
                } else {
                    g.drawImage(mapchipImages[evt.getImageNo()], evt.getX() * CS, evt.getY() * CS, this);
                }
                g.drawRect(evt.getX() * CS, evt.getY() * CS, CS, CS);
            }
        }
        
        // �}�b�v�̑I���ʒu�ɃJ�[�\���\��
        g.setColor(Color.YELLOW);
        g.drawRect(x * CS, y * CS, CS, CS);
    }

    public void mouseClicked(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        
        x = mouseX / CS;
        y = mouseY / CS;
        
        if (SwingUtilities.isLeftMouseButton(e)) {  // ���N���b�N�̏ꍇ
            // �}�b�v�`�b�v�p���b�g����擾�����ԍ����Z�b�g
            if (x >= 0 && x < col && y >= 0 && y < row) {
                map[y][x] = mapchipPalette.getSelectedMapchipNo();
            }
        } else if (SwingUtilities.isRightMouseButton(e)) {  // �E�N���b�N�̏ꍇ
            // ���݈ʒu�̃}�b�v�`�b�v�ԍ����Z�b�g
            mapchipPalette.setSelectedMapchipNo(map[y][x]);
            mapchipPalette.repaint();
        }
        
        // ���p�l�����X�V
        infoPanel.setPos(x, y, mouseX, mouseY);
        
        repaint();
        
        // TODO: �_�u���N���b�N�ŃC�x���g�_�C�A���O�J��
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        
        x = mouseX / CS;
        y = mouseY / CS;
        
        // �}�b�v�`�b�v�p���b�g����擾�����ԍ����Z�b�g
        if (x >= 0 && x < col && y >= 0 && y < row) {
            map[y][x] = mapchipPalette.getSelectedMapchipNo();
        }
        
        // ���p�l�����X�V
        infoPanel.setPos(x, y, mouseX, mouseY);
        
        repaint();
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
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
        Chara chara = new Chara(x, y, imageNo, direction, moveType, message, null);

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
