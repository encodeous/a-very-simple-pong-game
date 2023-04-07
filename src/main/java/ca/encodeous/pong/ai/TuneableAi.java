package ca.encodeous.pong.ai;

import ca.encodeous.pong.Constants;
import ca.encodeous.pong.physics.GameObject;
import ca.encodeous.pong.system.Paddle;
import ca.encodeous.pong.system.PongSystem;
import org.spongepowered.noise.module.source.Perlin;

public class TuneableAi extends Ai {
    public static final int FRANK_NERF = 400;
    public static final int BOB_NERF = 700;
    GameObject ball, paddle;
    private Perlin noise;
    private final int NERF_FACTOR;
    int tick = 0;
    @Override
    public double getPaddleAccel() {
        tick++;
        double nny = (noise.get(tick / 500.0, 1, 1.23) - 1) * NERF_FACTOR;
        double dy = (ball.getCenterPos().y + nny) - paddle.getCenterPos().y;
        if(Math.abs(dy) > paddle.getBounds().getHeight() / 6){
            return Math.signum(dy) * Constants.PADDLE_ACCEL;
        }
        return 0;
    }

    @Override
    public void initialize(PongSystem system, Paddle paddle) {
        this.ball = system.getBall();
        this.paddle = paddle;
        noise = new Perlin();
        noise.setSeed((int) (0xDEADBEEF + 10000 * ball.getVelocity().x));
    }

    public TuneableAi(int nerfFactor) {
        NERF_FACTOR = nerfFactor;
    }
}
