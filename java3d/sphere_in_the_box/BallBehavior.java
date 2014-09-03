import java.util.Enumeration;

import javax.media.j3d.Behavior;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Transform3D;
import javax.media.j3d.WakeupOnElapsedTime;
import javax.vecmath.Vector3d;

/*
 * Created on 2006/01/28
 */

/**
 * �{�[�������ˉ��r�w�C�r�A
 * 
 * @author mori
 */
public class BallBehavior extends Behavior {
    private Ball ball; // �{�[��
    private Transform3D positionT3D;
    private WakeupOnElapsedTime trigger;

    public BallBehavior(Ball ball, long interval) {
        this.ball = ball;

        // interval�Ԋu�ŌĂяo���g���K���쐬
        trigger = new WakeupOnElapsedTime(interval);

        // �͈͂�ݒ�i���ꂪ�Ȃ��Ɠ����Ȃ��j
        BoundingSphere bounds = new BoundingSphere();
        setSchedulingBounds(bounds);
    }

    /**
     * �r�w�C�r�A�̏�����
     */
    public void initialize() {
        wakeupOn(trigger);
    }

    /**
     * �r�w�C�r�A�̓��e�i�{�[���̈ړ��j
     */
    public void processStimulus(Enumeration criteria) {
        Vector3d newPosition = new Vector3d();
        Vector3d newSpeed = new Vector3d();

        Vector3d oldPosition = ball.getPosition();
        Vector3d oldSpeed = ball.getSpeed();

        // ���x�̍X�V�i�����j
        newSpeed.x = oldSpeed.x;
        newSpeed.y = oldSpeed.y;
        newSpeed.z = oldSpeed.z;

        // �ʒu�̍X�V
        newPosition.x = oldPosition.x + newSpeed.x;
        newPosition.y = oldPosition.y + newSpeed.y;
        newPosition.z = oldPosition.z + newSpeed.z;

        // �͈͂𒴂��Ă����瑬�x�𔽓]
        float radius = ball.getRadius();
        if (newPosition.x < -0.5 + radius || newPosition.x > 0.5 - radius) {
            newSpeed.x = -newSpeed.x;
        }
        if (newPosition.y < -0.5 + radius || newPosition.y > 0.5 - radius) {
            newSpeed.y = -newSpeed.y;
        }
        if (newPosition.z < -0.5 + radius || newPosition.z > 0.5 - radius) {
            newSpeed.z = -newSpeed.z;
        }

        // �V�����ʒu�ƃX�s�[�h���Z�b�g
        ball.setPosition(newPosition);
        ball.setSpeed(newSpeed);

        // �{�[�����ړ�
        ball.move();

        // �r�w�C�r�A�ɒʒm
        wakeupOn(trigger);
    }

}