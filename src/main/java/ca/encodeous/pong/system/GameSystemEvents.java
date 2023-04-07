package ca.encodeous.pong.system;

import ca.encodeous.pong.physics.GameEntity;
import ca.encodeous.pong.physics.GameObject;

public interface GameSystemEvents {
    /**
     * Called before ticking the objects
     */
    void preTick();

    /**
     * Called after updating the last object
     */
    void postTick();
    /**
     * Called when a player has won the game
     */
    void win(int playerId);

    /**
     * Called when a player has scored a point
     */
    void scoreUpdate(int[] playerScores);

    /**
     * Called when the object has been updated during a tick
     */
    void updated(GameEntity object);

    /**
     * Called when an object is created
     */
    void created(GameEntity object);
    /**
     * Called when an object is removed
     */
    void removed(GameEntity object);
}
