/**
 * ���̏�Ԃ�\���N���X�B���H���̏�Ԃ̓G�[�W�F���g�̍��W�ɂ���Č��܂�B
 * 
 * @author mori
 *  
 */
public class State {
    // �G�[�W�F���g�̍��W
    public int x;
    public int y;

    public State(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }
}