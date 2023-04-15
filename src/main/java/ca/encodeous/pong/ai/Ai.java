package ca.encodeous.pong.ai;

import ca.encodeous.pong.system.Controller;
import ca.encodeous.pong.system.Paddle;
import ca.encodeous.pong.system.PongSystem;

/**
 * Abstract class that represents any Ai/locally hosted controller
 */
public abstract class Ai implements Controller {
    /**
     * Called to initialize the ai with the necessary parameters.
     * @param system the local pong physics system
     * @param paddle the paddle that the Ai is in control of
     */
    public abstract void initialize(PongSystem system, Paddle paddle);
}
