package ca.encodeous.pong.network;

import ca.encodeous.pong.system.Controller;

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
