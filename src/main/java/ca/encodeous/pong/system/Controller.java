package ca.encodeous.pong.system;

public interface Controller {
    /**
     * A number that represents the acceleration applied to the paddle
     * Called every tick.
     */
    double getPaddleAccel();
}
