package ca.encodeous.pong.ai;

import ca.encodeous.pong.physics.GameObject;
import ca.encodeous.pong.physics.Vec2d;
import ca.encodeous.pong.system.Paddle;
import ca.encodeous.pong.system.PongSystem;

/**
 * The hardest Ai, (nearly) impossible to win against.
 */
public class BuilderTheBob extends Ai {
    GameObject ball, paddle;
    private PongSystem system;
    @Override
    public double getPaddleAccel() {
        double dy = ball.getCenterPos().y - paddle.getCenterPos().y;
        double k = 1;
        do{
            paddle.setVelocity(paddle.getVelocity().add(new Vec2d(0, dy * k)));
            k /= 2;
            if(k <= 0.0001){
                return 0;
            }
        } while (!system.checkForCollisionAt(paddle).isEmpty());
        return 0;
    }

    @Override
    public void initialize(PongSystem system, Paddle paddle) {
        this.system = system;
        this.ball = system.getBall();
        this.paddle = paddle;
    }
}
