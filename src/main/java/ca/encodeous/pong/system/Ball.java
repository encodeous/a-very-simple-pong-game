package ca.encodeous.pong.system;

import ca.encodeous.pong.Constants;
import ca.encodeous.pong.rendering.BoxObject;
import ca.encodeous.pong.physics.GameObject;

/**
 * The pong ball
 */
public class Ball extends BoxObject {

    @Override
    public boolean onCollideWith(GameObject other) {
        if(other instanceof Paddle){
            setVelocity(getVelocity().multiply(Constants.SPEED_INCREASE));
            setVelocity(getVelocity().add(other.getVelocity().multiply(Constants.BALL_COEFF_FRICTION)));
        }
        return true;
    }
}