import java.awt.image.WritableRaster;

/*
 * ���^�{�[��
 * �wC����t������S500�̋ɈӁx��p.331���Q�l
 * 
 * Created on 2006/04/30
 */

public class Metaball {
    private int x; // �{�[���̒��S���W
    private int y;
    private int vx; // �{�[���̈ړ����x
    private int vy;
    private Pixel[] pixels; // �{�[���̊e�s�N�Z�����
    private Palette palette; // �p���b�g

    private static final int RADIUS = 70; // �{�[���̔��a
    private static final int MAX_PIXELS = (2 * RADIUS) * (2 * RADIUS); // �~���͂ގl�p�`�̃s�N�Z����

    /**
     * �R���X�g���N�^
     * 
     * @param x ���^�{�[���̒��S��X���W
     * @param y
     * @param vx
     * @param vy
     */
    public Metaball(int x, int y, int vx, int vy) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        pixels = new Pixel[MAX_PIXELS];
        for (int i = 0; i < MAX_PIXELS; i++) {
            pixels[i] = new Pixel();
        }
        palette = new Palette();

        init();
    }

    /**
     * ���^�{�[����������
     */
    public void init() {
        int no;

        int c = 0;
        // �~�̒��g�̊e�s�N�Z���ɂ��ď���
        // �~�̒��g�̓_(x,y)�͂�x^2+y^2 < r^2�𖞂����̂�
        // z = r^2 - x^2 - y^2 > 0�Ȃ�~�̒��g�Ɣ���ł���
        for (int i = -RADIUS; i < RADIUS; i++) {
            for (int j = -RADIUS; j < RADIUS; j++) {
                double z = RADIUS * RADIUS - i * i - j * j; // �~�̓����̓_������
                if (z < 0) { // �~�̊O��
                    no = 0;
                } else { // �~�̒���
                    // �~���̃s�N�Z���̐F�i�p���b�g�ԍ��j�����肵�Ă���
                    z = Math.sqrt(z);
                    double t = z / RADIUS;
                    no = (int) (Palette.MAX_PAL * (t * t * t * t));
                    if (no > 255)
                        no = 255;
                    if (no < 0)
                        no = 0;
                }

                pixels[c].dx = i;
                pixels[c].dy = j;
                pixels[c].no = no;
                c++;
            }
        }
    }

    /**
     * ���^�{�[����`��
     * 
     * @param raster
     */
    public void draw(WritableRaster raster) {
        for (int i = 0; i < MAX_PIXELS; i++) {
            int sx = x + pixels[i].dx;
            if (sx < 0 || sx > MainPanel.WIDTH)
                continue;
            int sy = y + pixels[i].dy;
            if (sy < 0 || sy > MainPanel.HEIGHT)
                continue;

            // �{�[���̐F���C���[�W��RGB�։��Z����
            int no = pixels[i].no; // �p���b�g�ԍ�
            int[] color = palette.getColor(no); // �p���b�g�ԍ�����RGB�����o��

            // �X�N���[���̐F�����o��
            int[] p = new int[3];
            raster.getPixel(sx, sy, p);

            // �X�N���[���̐F�Ƀ{�[���̐F�����Z����
            for (int j = 0; j < 3; j++) {
                p[j] += color[j];
                if (p[j] > 255)
                    p[j] = 255;
            }

            // �X�N���[���ɐV�����F����������
            raster.setPixel(sx, sy, p);
        }
    }

    /**
     * ���^�{�[�����ړ�����
     */
    public void move() {
        x += vx;
        y += vy;

        // �͈͓����`�F�b�N
        if (x < RADIUS) {
            x = RADIUS;
            vx = -vx;
        }
        if (y < RADIUS) {
            y = RADIUS;
            vy = -vy;
        }
        if (x > MainPanel.WIDTH - RADIUS) {
            x = MainPanel.WIDTH - RADIUS;
            vx = -vx;
        }
        if (y > MainPanel.HEIGHT - RADIUS) {
            y = MainPanel.HEIGHT - RADIUS;
            vy = -vy;
        }
    }

    /**
     * ���^�{�[�����̃s�N�Z�����
     */
    class Pixel {
        public int dx; // �{�[���̒��S����̕΍�
        public int dy;
        public int no; // �p���b�g�ԍ�
    }

    /**
     * �p���b�g�N���X 0�`255�̊e�ԍ��ɐF���Z�b�g�ł���
     */
    class Palette {
        public static final int MAX_PAL = 256;
        private int[] red; // �Ԑ���
        private int[] green; // �ΐ���
        private int[] blue; // ����

        public Palette() {
            red = new int[MAX_PAL];
            green = new int[MAX_PAL];
            blue = new int[MAX_PAL];

            // �p���b�g��������
            init();
        }

        /**
         * �p���b�g������������
         */
        public void init() {
            // �e�p���b�g�ԍ���RGB��ݒ�
            // �Ԋ
            int r, g, b;
            for (int i = 0; i < MAX_PAL; i++) {
                r = g = b = 0;
                // ������r,g,b�̏��Ԃ�ς���ƈႤ�F�̃��^�{�[�����ł���
                if (i >= 0)
                    r = 4 * i;
                if (i >= 2)
                    g = 4 * (i / 2);
                if (i >= 4)
                    b = 4 * (i / 4);

                if (r > 255)
                    r = 255;
                if (g > 255)
                    g = 255;
                if (b > 255)
                    b = 255;

                red[i] = r;
                green[i] = g;
                blue[i] = b;
            }
        }

        /**
         * �p���b�g�ԍ�����RGB�z��֕ϊ�
         * 
         * @param no �p���b�g�ԍ�
         * @return RGB�z��
         */
        public int[] getColor(int no) {
            int[] color = new int[3];
            color[0] = red[no];
            color[1] = green[no];
            color[2] = blue[no];

            return color;
        }
    }
}
