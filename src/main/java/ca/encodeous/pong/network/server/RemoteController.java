package ca.encodeous.pong.network.server;

import ca.encodeous.pong.system.Controller;

/**
 * A fictitious controller that exists merely as a shim for the acceleration packets sent by the client
 */
public class RemoteController implements Controller {
    public void setAccel(double lastAccel) {
        this.lastAccel = lastAccel;
    }

    private double lastAccel = 0;
    @Override
    public double getPaddleAccel() {
        double temp = lastAccel;
        lastAccel = 0;
        return temp;
    }
}
