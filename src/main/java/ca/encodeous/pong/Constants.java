package ca.encodeous.pong;

import java.util.UUID;

/**
 * Constants that can be tweaked to modify the game
 */
public class Constants {
    public static final int KEY_DOWN = 40;
    public static final int POINTS_TO_WIN = 11;
    public static final int KEY_UP = 38;
    public static final double BALL_COEFF_FRICTION = 0.1;
    public static final double PADDLE_DRAG = 0.85;
    public static final double PADDLE_ACCEL = 2.0;
    public static final double SPEED_INCREASE = 1.1;
    public static final double MAX_VELOCITY = 19.9;
    public static final double WORLD_WIDTH = 700;
    public static final double WORLD_HEIGHT = 500;
    /**
     * Responsible for setting the thickness of the paddle as well as the collision detection zones for the border of the screen
     */
    public static final double SMALL_SIZE = 10;
    public static final double BALL_SIZE = 25;
    /**
     * Maximum degree (-x/2, x/2) where x is the value - that the ball can be randomly rotated when it bounces
     */
    public static final double BOUNCE_RAND_DEG = 20;
    public static final double PADDLE_HEIGHT = 80;
    public static final double INITIAL_VELOCITY = 4;
    /**
     * Specifies the maximum degree to which the Ai can be nerfed.
     */
    public static final double MAX_NERF = 1000;
    /**
     * The 't' variable to the function https://www.desmos.com/calculator/6gbcumupg1
     * Determines how much de-bounce is applied to the paddle
     */
    public static final int PADDLE_RESPONSIVENESS = 1000;
    public static final int PROTOCOL_PORT = 8080;
    /**
     * Size in bytes that specifies the maximal amount of data that can be sent in a single packet.
     */
    public static final int MAX_PACKET_SIZE = 2000;
    public static final UUID LEFT_PADDLE = new UUID(1, 0);
    public static final UUID RIGHT_PADDLE = new UUID(2, 0);
    public static final UUID BALL_ID = new UUID(3, 0);
}
