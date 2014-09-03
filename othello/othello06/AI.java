/*
 * Created on 2004/12/22
 *
 */

/**
 * �I�Z����AI�B
 * 
 * @author mori
 *  
 */
public class AI {
    // �[�ǂ݂��郌�x���i�傫���l���Ƃ��̂��������Ԃ��������Ă��܂��̂Œ��Ӂj
    private static final int SEARCH_LEVEL = 5;
    // ���C���p�l���ւ̎Q��
    private MainPanel panel;

    /**
     * �R���X�g���N�^�B���C���p�l���ւ̎Q�Ƃ�ۑ��B
     * 
     * @param panel ���C���p�l���ւ̎Q�ƁB
     */
    public AI(MainPanel panel) {
        this.panel = panel;
    }

    /**
     * �R���s���[�^�̎�����肷��B
     *  
     */
    public void compute() {
        // �~�j�}�b�N�X�@�Ő΂�łꏊ�����߂�
        // �߂��Ă���l�� bestX+bestY*MASU
        int temp = minMax(true, SEARCH_LEVEL);

        // �ꏊ�����߂�
        int x = temp % MainPanel.MASU;
        int y = temp / MainPanel.MASU;

        // �ł����ꏊ�A�Ђ�����Ԃ����΂̈ʒu���L�^
        Undo undo = new Undo(x, y);
        // ���̏ꏊ�Ɏ��ۂɐ΂�ł�
        panel.putDownStone(x, y, false);
        // ���ۂɂЂ�����Ԃ�
        panel.reverse(undo, false);
        // ��Ԃ�ς���
        panel.nextTurn();
    }

    /**
     * �~�j�}�b�N�X�@�B�őP���T������B�łꏊ��T�������Ŏ��ۂɂ͑ł��Ȃ��B
     * 
     * @param flag AI�̎�Ԃ̂Ƃ�true�A�v���C���[�̎�Ԃ̂Ƃ�false�B
     * @param level ��ǂ݂̎萔�B
     * @return �q�m�[�h�ł͔Ֆʂ̕]���l�B���[�g�m�[�h�ł͍ő�]���l�����ꏊ�ibestX + bestY * MAS�j�B
     */
    private int minMax(boolean flag, int level) {
        // �m�[�h�̕]���l
        int value;
        // �q�m�[�h����`�d���Ă����]���l
        int childValue;
        // �~�j�}�b�N�X�@�ŋ��߂��ő�̕]���l�����ꏊ
        int bestX = 0;
        int bestY = 0;

        // �Q�[���؂̖��[�ł͔Ֆʕ]��
        // ���̑��̃m�[�h��MIN or MAX�œ`�d����
        if (level == 0) {
            return 0; // ���ۂ͔Ֆʂ�]�����ĕ]���l�����߂�B�����ł͂Ƃ肠����0�ɂ��Ă����B
        }

        if (flag) {
            // AI�̎�Ԃł͍ő�̕]���l�����������̂ōŏ��ɍŏ��l���Z�b�g���Ă���
            value = Integer.MIN_VALUE;
        } else {
            // �v���C���[�̎�Ԃł͍ŏ��̕]���l�����������̂ōŏ��ɍő�l���Z�b�g���Ă���
            value = Integer.MAX_VALUE;
        }

        // �łĂ�Ƃ���͂��ׂĎ����i���������Ŏ��ۂɂ͑ł��Ȃ��j
        for (int y = 0; y < MainPanel.MASU; y++) {
            for (int x = 0; x < MainPanel.MASU; x++) {
                if (panel.canPutDown(x, y)) {
                    Undo undo = new Undo(x, y);
                    // �����ɑł��Ă݂�i�Ֆʕ`��͂��Ȃ��̂�true�w��j
                    panel.putDownStone(x, y, true);
                    // �Ђ�����Ԃ��i�Ֆʕ`��͂��Ȃ��̂�true�w��j
                    panel.reverse(undo, true);
                    // ��Ԃ�ς���
                    panel.nextTurn();
                    // �q�m�[�h�̕]���l���v�Z�i�ċA�j
                    // ���x�͑���̔ԂȂ̂�flag���t�]����
                    childValue = minMax(!flag, level - 1);
                    // �q�m�[�h�Ƃ��̃m�[�h�̕]���l���r����
                    if (flag) {
                        // AI�̃m�[�h�Ȃ�q�m�[�h�̒��ōő�̕]���l��I��
                        if (childValue > value) {
                            value = childValue;
                            bestX = x;
                            bestY = y;
                        }
                    } else {
                        // �v���C���[�̃m�[�h�Ȃ�q�m�[�h�̒��ōŏ��̕]���l��I��
                        if (childValue < value) {
                            value = childValue;
                            bestX = x;
                            bestY = y;
                        }
                    }
                    // �łO�ɖ߂�
                    panel.undoBoard(undo);
                }
            }
        }

        if (level == SEARCH_LEVEL) {
            // ���[�g�m�[�h�Ȃ�ő�]���l�����ꏊ��Ԃ�
            return bestX + bestY * MainPanel.MASU;
        } else {
            // �q�m�[�h�Ȃ�m�[�h�̕]���l��Ԃ�
            return value;
        }
    }
}