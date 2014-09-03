import java.util.Enumeration;

import javax.media.j3d.Behavior;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Transform3D;
import javax.media.j3d.WakeupOnElapsedTime;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

/*
 * Created on 2006/02/04
 */

/**
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
        BoundingSphere bounds = new BoundingSphere(new Point3d(), Double.POSITIVE_INFINITY);
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
        Vector3d newVelocity = new Vector3d();
        
        Vector3d oldPosition = ball.getPosition();
        Vector3d oldVelocity = ball.getVelocity();
        Vector3d acceleration = ball.getAcceleration();
        
        // ���x�̍X�V
        newVelocity.x = oldVelocity.x + acceleration.x;
        newVelocity.y = oldVelocity.y + acceleration.y;
        newVelocity.z = oldVelocity.z + acceleration.z;

        // �ʒu�̍X�V
        newPosition.x = oldPosition.x + newVelocity.x;
        newPosition.y = oldPosition.y + newVelocity.y;
        newPosition.z = oldPosition.z + newVelocity.z;

        // �͈͂𒴂��Ă����瑬�x�𔽓]
        float radius = ball.getRadius();
        if (newPosition.y < -2.0 + radius) {
            newPosition.y = -2.0 + radius;  // ���ɂ߂肱�܂Ȃ��悤��
            newVelocity.y = (-newVelocity.y) * 0.8;  // ���x�͂��񂾂񗎂Ƃ�
        }

        // �V�����ʒu�Ƒ��x���Z�b�g
        ball.setPosition(newPosition);
        ball.setVelocity(newVelocity);

        // �{�[�����ړ�
        ball.move();

        // �r�w�C�r�A�ɒʒm
        wakeupOn(trigger);
    }

}