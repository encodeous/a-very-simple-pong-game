package ca.encodeous.pong.system;

import ca.encodeous.pong.Constants;
import ca.encodeous.pong.ai.Ai;
import ca.encodeous.pong.ai.TuneableAi;
import ca.encodeous.pong.physics.GameObject;
import ca.encodeous.pong.physics.Vec2d;
import ca.encodeous.pong.rendering.BoxObject;

public class PongSystem extends GameSystem {
    public Ball getBall() {
        return ball;
    }

    // Add any state variables or object references here
    private Ball ball;

    public Paddle getLeftPaddle() {
        return left;
    }

    public Paddle getRightPaddle() {
        return right;
    }

    private Paddle left, right;
    private BoxObject leftBound, topBound, rightBound, bottomBound;
    private Controller leftPlayer, rightPlayer;
    private int leftScore, rightScore;
    public PongSystem(Controller lController, Controller rController, GameSystemEvents... events) {
        super(events);
        leftPlayer = lController;
        rightPlayer = rController;
    }

    /**
     * Fill in this method with code that tells the game what to do
     * before actual play begins
     */
    @Override
    protected void setup() {
        leftBound = new BoxObject(){
            @Override
            public void onCollideBy(GameObject other) {
                if(other == ball){
                    rightScore++;
                    recalculateRound();
                }
            }
        };
        leftBound.setSize(Constants.SMALL_SIZE, Constants.WORLD_HEIGHT);
        leftBound.setPosition(new Vec2d(-Constants.SMALL_SIZE, 0));
        add(leftBound);

        rightBound = new BoxObject(){
            @Override
            public void onCollideBy(GameObject other) {
                if(other == ball){
                    leftScore++;
                    recalculateRound();
                }
            }
        };
        rightBound.setSize(Constants.SMALL_SIZE, Constants.WORLD_HEIGHT);
        rightBound.setPosition(new Vec2d(Constants.WORLD_WIDTH, 0));
        add(rightBound);

        topBound = new BoxObject();
        topBound.setSize(Constants.WORLD_WIDTH, Constants.SMALL_SIZE);
        topBound.setPosition(new Vec2d(0, -Constants.SMALL_SIZE));
        add(topBound);

        bottomBound = new BoxObject();
        bottomBound.setSize(Constants.WORLD_WIDTH, Constants.SMALL_SIZE);
        bottomBound.setPosition(new Vec2d(0, Constants.WORLD_HEIGHT));
        add(bottomBound);

        left = new Paddle();
        left.setSize(Constants.SMALL_SIZE, Constants.LARGE_SIZE);
        left.setPositionCenter(new Vec2d(Constants.SMALL_SIZE, Constants.WORLD_HEIGHT / 2.0));
        left.setId(Constants.LEFT_PADDLE);
        add(left);
        right = new Paddle();
        right.setSize(Constants.SMALL_SIZE, Constants.LARGE_SIZE);
        right.setPositionCenter(new Vec2d(Constants.WORLD_WIDTH - Constants.SMALL_SIZE, Constants.WORLD_HEIGHT / 2.0));
        right.setId(Constants.RIGHT_PADDLE);
        add(right);

        startRound();
    }

    @Override
    protected void cleanup() {

    }

    /**
     * Updates the score, and handles the win condition.
     */
    public void recalculateRound(){
        for (GameSystemEvents ev : events) {
            ev.scoreUpdate(new int[] { leftScore, rightScore });
        }
        if(leftScore >= Constants.POINTS_TO_WIN || rightScore >= Constants.POINTS_TO_WIN){
            stopGame();
            Thread t1 = new Thread(() -> {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                resumeGame();
            });
            t1.start();
            if(leftScore > rightScore){
                for (GameSystemEvents ev : events) {
                    ev.win(0);
                }
            }else{
                for (GameSystemEvents ev : events) {
                    ev.win(1);
                }
            }
            leftScore = 0;
            rightScore = 0;
            for (GameSystemEvents ev : events) {
                ev.scoreUpdate(new int[] { leftScore, rightScore });
            }
        }
        startRound();
    }

    /**
     * Resets the ball and its location
     * Provides a random direction
     */
    private void startRound(){
        if(ball != null){
            remove(ball);
        }
        ball = new Ball();
        ball.setSize(Constants.BALL_SIZE, Constants.BALL_SIZE);
        ball.setPosition(new Vec2d(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT).multiply(0.5));
        // north is 0 degree
        double degree = Math.random() * 180;
        // ensure that the ball can only be launched at a >< shape
        if(degree >= 90) degree += 90;
        // offset the degree by 45 degrees to make a cone shape
        ball.setVelocity(Vec2d.ofPolar(Constants.INITIAL_VELOCITY, degree + 45));
        ball.setId(Constants.BALL_ID);
        add(ball);

        // initialize players
        if(leftPlayer instanceof Ai){
            ((Ai)leftPlayer).initialize(this, left);
        }
        if(rightPlayer instanceof Ai){
            ((Ai)rightPlayer).initialize(this, right);
        }
    }

    /**
     * Fill in this method with code that tells the playing field what to do
     * from one moment to the next
     */
    public void tick() {
        left.setAcceleration(new Vec2d(0, leftPlayer.getPaddleAccel()));
        right.setAcceleration(new Vec2d(0, rightPlayer.getPaddleAccel()));
    }
}
