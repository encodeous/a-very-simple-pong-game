package ca.encodeous.pong.ai;

import ca.encodeous.pong.Pong;
import ca.encodeous.pong.system.Controller;
import ca.encodeous.pong.system.Paddle;
import ca.encodeous.pong.system.PongSystem;

public abstract class Ai implements Controller {
    public abstract void initialize(PongSystem system, Paddle paddle);
}
