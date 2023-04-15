package ca.encodeous.pong.system;

import ca.encodeous.pong.physics.GameObject;

import javax.swing.*;
import java.util.ArrayList;

/**
 * A core class that represents a generic world
 * This class is run on the server side to provide ticking, physics simulation and basic game event updates
 */
public abstract class GameSystem {
    private final Timer ticker;
    protected final GameSystemEvents[] events;
    protected final ArrayList<GameObject> objects = new ArrayList<>();
    public ArrayList<GameObject> checkForCollisionAt(GameObject object){
        ArrayList<GameObject> colliders = new ArrayList<>();
        if(object.isCollidable()){
            for(int j = 0; j < objects.size(); j++){
                GameObject other = objects.get(j);
                if(object != other && other.isCollidable()){
                    if(object.willCollide(other)){
                        colliders.add(other);
                    }
                }
            }
        }
        return colliders;
    }
    public GameSystem(GameSystemEvents... events){
        this.events = events;
        ticker = new Timer(1, e -> {
            for (GameSystemEvents ev : events) {
                ev.preTick();
            }
            tick();
            for (int i = 0; i < objects.size(); i++) {
                GameObject o = objects.get(i);
                o.tick(this);
                for (GameSystemEvents ev : events) {
                    ev.updated(o);
                }
            }
            for (GameSystemEvents ev : events) {
                ev.postTick();
            }
        });
    }

    protected abstract void setup();

    public void startGame() {
        setup();
        ticker.start();
    }

    public void resumeGame() {
        ticker.start();
    }

    public void stopGame() {
        ticker.stop();
    }

    protected abstract void tick();

    /**
     * Adds the object the world
     */
    public void add(GameObject o){
        objects.add(o);
        for (GameSystemEvents ev : events) {
            ev.created(o);
        }
    }
    /**
     * Removes the object from the world
     */
    public void remove(GameObject o){
        objects.remove(o);
        for (GameSystemEvents ev : events) {
            ev.removed(o);
        }
    }
}
