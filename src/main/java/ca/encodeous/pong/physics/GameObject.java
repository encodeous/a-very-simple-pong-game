package ca.encodeous.pong.physics;

import ca.encodeous.pong.Constants;
import ca.encodeous.pong.system.Ball;
import ca.encodeous.pong.system.GameSystem;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.UUID;

/**
 * A base object that runs server-side
 * It has built-in support for collisions and physics
 */
public abstract class GameObject extends GameEntity {
    private static final double ACCEL_DRAG = 0.2;
    // world-coordinates
    private Vec2d position, velocity, acceleration;
    private final Rectangle2D.Double bound;
    private boolean collidable = true;

    public void setId(UUID id) {
        this.id = id;
    }

    private UUID id;

    /**
     * true if the object can give or recieve collisions
     */
    public boolean isCollidable() {
        return collidable;
    }

    /**
     * Set to true if the object should be considered in collisions
     */
    public void setCollidable(boolean collidable) {
        this.collidable = collidable;
    }

    public UUID getId(){
        return id;
    }

    public GameObject(){
        position = new Vec2d(0, 0);
        velocity = new Vec2d(0, 0);
        acceleration = new Vec2d(0, 0);
        bound = new Rectangle2D.Double();
        id = UUID.randomUUID();
    }

    /**
     * Sets the pixel width and height of the object
     *
     * @param width		a width in pixels
     * @param height	a height in pixels
     */
    public void setSize(int width, int height) {
        setSize(width, (double) height);
    }

    /**
     * Sets the pixel width and height of the object
     *
     * @param width		a width in pixels
     * @param height	a height in pixels
     */
    public void setSize(double width, double height) {
        bound.setFrame(position.x, position.y, width, height);
    }

    /**
     * Gets the x component of the coordinate of the upper left corner of
     * this object
     *
     * The coordinate is relative to the playing field, with <code>0</code>
     * being the far left of the field, and positive values moving toward the right
     * of the field
     */
    public int getX() {
        return (int) position.x;
    }

    /**
     * Gets the y component of the coordinate of the upper left corner of
     * this object
     *
     * The coordinate is relative to the playing field, with <code>0</code>
     * being the top of the field, and positive values moving toward the
     * bottom of the field
     */
    public int getY() {
        return (int) position.y;
    }

    /**
     * Sets the x (horizontal) position of this object
     *
     * Setting the x position will not affect the y position
     *
     * @param x		the x position of the upper left corner of this object
     */
    public void setX(int x) {
        position.x = x;
    }

    /**
     * Sets the y (vertical) position of this object
     *
     * Setting the y position will not affect the x position
     *
     * @param y	the y position of the upper left corner of this object
     */
    public void setY(int y) {
        position.y = y;
    }

    /**
     * Gets the bounding box for the object
     */
    public Rectangle2D getBounds(){
        return bound;
    }


    /**
     * Returns <code>true</code> if this object collides with another
     * <code>ca.encodeous.pong.physics.GameObject</code>
     *
     * @param o		the <code>ca.encodeous.pong.physics.GameObject</code> to test for collision
     * @return		<code>true</code> if collision occurs
     */
    public boolean collides(GameObject o) {
        if(!o.isCollidable() || !isCollidable()) return false;
        return getBounds().intersects(o.getBounds());
    }

    /**
     * Checks if the X velocity will induce a collision
     * @return true if it will collide
     */
    public boolean willCollideX(GameObject o){
        Rectangle2D bound = getBounds();
        double d = getVelocity().x;
        // apply velocity, check for collision, then undo the applied velocity
        bound.setFrame(position.x + d, position.y, bound.getWidth(), bound.getHeight());
        boolean collides = bound.intersects(o.getBounds());
        bound.setFrame(position.x, position.y, bound.getWidth(), bound.getHeight());
        return collides;
    }
    /**
     * Checks if the Y velocity will induce a collision
     * @return true if it will collide
     */
    public boolean willCollideY(GameObject o){
        Rectangle2D bound = getBounds();
        double d = getVelocity().y;
        // apply velocity, check for collision, then undo the applied velocity
        bound.setFrame(position.x, position.y + d, bound.getWidth(), bound.getHeight());
        boolean collides = bound.intersects(o.getBounds());
        bound.setFrame(position.x, position.y, bound.getWidth(), bound.getHeight());
        return collides;
    }

    /**
     * Checks if the velocity will induce the object to collide on the next tick
     * @return true if it will collide
     */
    public boolean willCollide(GameObject o){
        return willCollideX(o) || willCollideY(o) || collides(o);
    }

    /**
     * Called when the current object collides with another
     * @param other
     * @return return false to stop handling other collisions.
     */
    public boolean onCollideWith(GameObject other){
        // to be overridden
        return true;
    }
    /**
     * Called when another object collides with the current one
     * @param other
     */
    public void onCollideBy(GameObject other){
        // to be overridden
    }

    /**
     * This method should be implemented to provide a <i>behavior</i>
     * for this object.
     *
     * The <code>ca.encodeous.pong.system.Game</code> will automatically call this method every
     * millisecond. It can be implemented to provide basic behavior for
     * an object, such as movement.
     */
    public abstract void act();

    /**
     * Handles automatic object changes, such as position and world-to-camera position synchronization
     * @return whether the component requires a repaint
     */
    public void tick(GameSystem game){
        // tick movement
        velocity = velocity.add(acceleration);
        ArrayList<GameObject> collisions = game.checkForCollisionAt(this);
        // modified new direction vector, is able to reflect the direction of movement
        Vec2d avgVec = new Vec2d(0, 0);
        boolean collide = false;
        if(!collisions.isEmpty()){
            collide = true;
            for (GameObject other : collisions){
                // allow corner bounces by adding the bounce to a x and y component scalar.
                if(willCollideX(other)){
                    avgVec = avgVec.add(new Vec2d(-1, 0));
                }
                if(willCollideY(other)){
                    avgVec = avgVec.add(new Vec2d(0, -1));
                }
                other.onCollideBy(this);
                if(!onCollideWith(other)){
                    break;
                }
            }
        }
        if(avgVec.x == 0){
            avgVec.x = 1;
        }
        if(avgVec.y == 0){
            avgVec.y = 1;
        }
        // mean of vectors.
        avgVec.multiply(1.0/collisions.size());
        velocity = velocity.multiply(avgVec);
        if(this instanceof Ball && collide){
            velocity = Vec2d.ofPolar(velocity.magnitude(), velocity.getDirection()
                    + (Math.random() * (Constants.BOUNCE_RAND_DEG) - Constants.BOUNCE_RAND_DEG / 2));
        }
        position = position.add(velocity);
        acceleration = acceleration.multiply(ACCEL_DRAG);
        setPosition(position);
        syncCoordinates();
        act();
    }

    /**
     * Synchronizes the world coordinates to the screen coordinate space
     */
    private void syncCoordinates(){
        bound.setFrame(position.x, position.y, bound.width, bound.height);
    }

    /**
     * Gets the position of the object
     */
    public Vec2d getPosition() {
        return position;
    }

    /**
     * Gets the size of the object as a vector
     */
    public Vec2d getSizeVec(){
        return new Vec2d(bound.width, bound.height);
    }

    /**
     * Gets the center of the object with respects to the size
     */
    public Vec2d getCenterPos(){
        return position.add(getSizeVec().multiply(0.5));
    }

    /**
     * Sets the top-left position of the object
     */
    public void setPosition(Vec2d position) {
        this.position = position;
    }

    /**
     * Sets the position of the object with respects to the center
     */
    public void setPositionCenter(Vec2d pos){
        this.position = pos.subtract(getSizeVec().multiply(0.5));
    }

    /**
     * Gets the velocity of the object
     */
    public Vec2d getVelocity() {
        return velocity;
    }

    /**
     * Sets the velocity of the object
     */
    public void setVelocity(Vec2d velocity) {
        if(velocity.magnitude() > Constants.MAX_VELOCITY){
            velocity = velocity.normalize().multiply(Constants.MAX_VELOCITY);
        }
        this.velocity = velocity;
    }

    /**
     * Gets the acceleration of the object
     */
    public Vec2d getAcceleration() {
        return acceleration;
    }

    /**
     * Sets the acceleration of the object
     */
    public void setAcceleration(Vec2d acceleration) {
        this.acceleration = acceleration;
    }
}