package ca.encodeous.pong.system;

import ca.encodeous.pong.Pong;
import ca.encodeous.pong.physics.Vec2d;

public interface Controller {
    /**
     * A number that represents the acceleration applied to the paddle
     * Called every tick.
     */
    double getPaddleAccel();
}
