import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;

/*
 * Created on 2005/04/23
 *
 */

/**
 * @author mori
 *  
 */
public class Map {
    // �`�b�v�Z�b�g�̃T�C�Y�i�P�ʁF�s�N�Z���j
    private static final int CS = 16;

    // �}�b�v
    private int[][] map;
    // �s�A�񐔁i�}�X�j
    private int row;
    private int col;

    // �}�b�v�Z�b�g
    private Image floorImage;
    private Image wallImage;
    private Image barrierImage;
    
    /**
     * �R���X�g���N�^�B
     * 
     * @param filename �}�b�v�f�[�^�̃t�@�C����
     */
    public Map(String filename) {
        // �}�b�v��ǂݍ���
        load(filename);
        // show();

        // ���̃C���[�W��ǂݍ���
        ImageIcon icon = new ImageIcon(getClass().getResource("floor.gif"));
        floorImage = icon.getImage();

        // �ǂ̃C���[�W��ǂݍ���
        icon = new ImageIcon(getClass().getResource("wall.gif"));
        wallImage = icon.getImage();
    }

    /**
     * �}�b�v��`���B
     * 
     * @param g �w�肳�ꂽGraphics�E�B���h�E
     */
    public void draw(Graphics g) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                // map�̒l�ɉ����ĉ摜��`��
                switch (map[i][j]) {
                    case 0 : // ��
                        g.drawImage(floorImage, j * CS, i * CS, null);
                        break;
                    case 1 : // ��
                        g.drawImage(wallImage, j * CS, i * CS, null);
                        break;
                    case 2:  // �o���A
                        g.drawImage(barrierImage, j*CS, i*CS, null);
                        break;
                }
            }
        }
    }

    /**
     * (x,y)�ɂԂ�����̂����邩���ׂ�B
     * 
     * @param x �}�b�v��x���W
     * @param y �}�b�v��y���W
     * @return (x,y)�ɂԂ�����̂���������true��Ԃ��B
     */
    public boolean isHit(int x, int y) {
        // (x,y)�ɕǂ���������Ԃ���
        if (map[y][x] == 1) {
            return true;
        }

        // �Ȃ���΂Ԃ���Ȃ�
        return false;
    }

    /**
     * �s����Ԃ�
     * 
     * @return �s��
     */
    public int getRow() {
        return row;
    }

    /**
     * �񐔂�Ԃ�
     * 
     * @return ��
     */
    public int getCol() {
        return col;
    }

    /**
     * �t�@�C������}�b�v��ǂݍ���
     * 
     * @param filename �ǂݍ��ރ}�b�v�f�[�^�̃t�@�C����
     */
    private void load(String filename) {
        try {
            // map.dat�t�@�C�����J��
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    getClass().getResourceAsStream(filename)));
            // 1�s�ǂݍ���
            String line = br.readLine();
            // �}�b�v�t�@�C���̍ŏ���1�s��
            // 10 10
            // �̂悤�ɍs���Ɨ񐔂������Ă���Ƃ���
            // �󔒂ŕ���
            StringTokenizer st = new StringTokenizer(line);
            // row��col��ǂ�
            row = Integer.parseInt(st.nextToken());
            col = Integer.parseInt(st.nextToken());
            // �}�b�v���쐬
            map = new int[row][col];
            for (int i = 0; i < row; i++) {
                line = br.readLine();
                for (int j = 0; j < col; j++) {
                    map[i][j] = Integer.parseInt(line.charAt(j) + "");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * �}�b�v���R���\�[���ɕ\���B�f�o�b�O�p�B
     */
    public void show() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }

    /**
     * �n�`�R�X�g��Ԃ�
     * @param pos ���W
     * @return �n�`�R�X�g
     */
    public int getCost(Point pos) {
        return 1;
    }
}