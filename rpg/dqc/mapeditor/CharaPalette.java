package mapeditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CharaPalette extends JDialog {
    public static final int WIDTH = 256;
    public static final int HEIGHT = 128;
    
    // �L�����N�^�[�̃T�C�Y�i�s�N�Z���P�ʁj
    private static final int CS = 16;
    
    // �L�����N�^�[��
    private static final int NUM_CHARAS = 128;
    // 1�s�̃L�����N�^�[��
    private static final int NUM_CHARAS_IN_ROW = 16;
    
    // �L�����N�^�[�C���[�W
    private Image charaImage = null;
    // �L�����N�^�[���Ƃɕ��������C���[�W
    // divImages[charaNo][direction]
    private Image[][] divImages = null;
    
    // �I������Ă���L�����N�^�[�ԍ�
    private int selectedCharaNo;
    
    public CharaPalette(JFrame owner) {
        super(owner, "�L�����N�^�[�p���b�g", false);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBounds(680, 500, WIDTH, HEIGHT);
        setResizable(false);
        
        // �_�C�A���O�̏�Ƀp�l�����̂���
        CharaPanel charaPanel = new CharaPanel();
        getContentPane().add(charaPanel);
        
        pack();
        
        loadImage();
    }
    
    /**
     * �I������Ă���L�����N�^�[�ԍ�
     * 
     * @return �I������Ă���L�����N�^�[�ԍ�
     */
    public int getSelectedCharaNo() {
        return selectedCharaNo;
    }
    
    /**
     * ���������L�����N�^�[�C���[�W��Ԃ�
     * 
     * @return ���������L�����N�^�[�C���[�W
     */
    public Image[][] getCharaImages() {
        return divImages;
    }
    
    private void loadImage() {
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("image/character_16.png"));
        charaImage = icon.getImage();
        
        // �L�����N�^�[���ƂɃC���[�W�𕪊�
        divImages = new Image[NUM_CHARAS][4];
        for (int i=0; i<NUM_CHARAS; i++) {
            for (int j=0; j<4; j++) {
                // �`�����m��
                divImages[i][j] = createImage(CS, CS);
                // �`��
                int cx = (i % NUM_CHARAS_IN_ROW) * (CS * 2);
                int cy = (i / NUM_CHARAS_IN_ROW) * (CS * 4);
                Graphics g = divImages[i][j].getGraphics();
                g.drawImage(charaImage, 0, 0, CS, CS,
                        cx, cy + j * CS,
                        cx + CS, cy + j * CS + CS,
                        null);
                g.dispose();
            }
        }
    }
    
    public class CharaPanel extends JPanel implements MouseListener {       
        public CharaPanel() {
            setPreferredSize(new Dimension(WIDTH, HEIGHT));
            
            // �}�E�X�ŃN���b�N�����Ƃ����̃L�����N�^�[�I������
            addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    int x = e.getX() / CS;
                    int y = e.getY() / CS;
                    
                    int charaNo = y * NUM_CHARAS_IN_ROW + x;
                    if (charaNo > NUM_CHARAS) {
                        charaNo = NUM_CHARAS;
                    }
                    
                    selectedCharaNo = charaNo;
                    // �I������Ă���L�����N�^�[��g�ň͂�
                    repaint();
                }
            });
        }
        
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            for (int i=0; i<NUM_CHARAS; i++) {
                int c = (i % NUM_CHARAS_IN_ROW);
                int r = (i / NUM_CHARAS_IN_ROW);
                // i�Ԗڂ̃L�����N�^�[�̍��W
                int cx = c * (CS * 2);
                int cy = r * (CS * 4);
                g.drawImage(charaImage, c*CS, r*CS, c*CS+CS, r*CS+CS,
                        cx, cy,
                        cx+CS, cy+CS,
                        null);
            }
            
            // �I������Ă���L�����N�^�[��g�ň͂�
            int x = selectedCharaNo % NUM_CHARAS_IN_ROW;
            int y = selectedCharaNo / NUM_CHARAS_IN_ROW;
            g.setColor(Color.YELLOW);
            g.drawRect(x*CS, y*CS, CS, CS);
        }

        public void mouseClicked(MouseEvent e) {
            int x = e.getX() / CS;
            int y = e.getY() / CS;
            
            int charaNo = y * NUM_CHARAS_IN_ROW + x;
            if (charaNo > NUM_CHARAS) {
                charaNo = NUM_CHARAS;
            }
            
            selectedCharaNo = charaNo;
            
            repaint();
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }
    }
}
