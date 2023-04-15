package ca.encodeous.pong.system;

import ca.encodeous.pong.Constants;
import ca.encodeous.pong.gui.InputHandler;
import ca.encodeous.pong.physics.GameEntity;

/**
 * A class that hooks into the PongWindow/InputHandler to capture the player's inputs
 * Supports both touch, mouse and keyboard inputs.
 */
public class PlayerController implements Controller {
    private double dir = 0;
    private InputHandler game;

    public GameEntity getEntity() {
        return entity;
    }

    private GameEntity entity;
    @Override
    public double getPaddleAccel() {
        double accel = dir * Constants.PADDLE_ACCEL;
        dir = 0;
        return accel;
    }
    public PlayerController(InputHandler game) {
        game.subscribeKeyTick(Constants.KEY_UP, ()->{
            dir -= 1;
        });
        game.subscribeKeyTick(Constants.KEY_DOWN, ()->{
            dir += 1;
        });
        this.game = game;
    }
    public void finishSubscription(GameEntity paddle){
        entity = paddle;
        game.subscribeMouseTick(pos -> {
            double diff = pos.y - paddle.getBounds().getCenterY();
            // https://www.desmos.com/calculator/ifpqjk7old
            double closeMultiplier = 2 * (diff * diff) / (diff * diff + Constants.PADDLE_RESPONSIVENESS);
            double tdir = Math.signum(diff);
            dir += tdir * closeMultiplier;
        });
    }
}
