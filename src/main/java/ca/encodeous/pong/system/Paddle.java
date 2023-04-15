package ca.encodeous.pong.system;

import ca.encodeous.pong.Constants;
import ca.encodeous.pong.physics.GameObject;
import ca.encodeous.pong.physics.Vec2d;
import ca.encodeous.pong.rendering.BoxObject;

/**
 * A class that represents the paddle.
 * (Has special friction compared to other objects)
 */
public class Paddle extends BoxObject {
    // Add any state variables here

    /**
     * Fill in this method with code that describes the behavior
     * of a paddle from one moment to the next
     */
    public void act() {
        // add more friction to the paddles
        setVelocity(getVelocity().multiply(Constants.PADDLE_DRAG));
    }

    @Override
    public boolean onCollideWith(GameObject other) {
        // stop paddle from behaving like an unstoppable force
        setVelocity(new Vec2d(0, 0));
        return true;
    }
}